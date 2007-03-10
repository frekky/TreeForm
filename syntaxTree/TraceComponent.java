package syntaxTree;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;
import java.util.LinkedList;

import javax.swing.JComponent;

import staticFunctions.Sizer;

import userInterface.UserInternalFrame;

public class TraceComponent extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private SyntacticStructure firstStart;

	private SyntacticStructure firstEnd;

	private SyntacticStructure ancestorStart;

	private SyntacticStructure ancestorEnd;

	private SyntacticStructure currentStart;

	private SyntacticStructure currentEnd;

	static final int padWidth = 6;

	private static final int padLength = 4;

	private static final int circle = 4;

	private int mLeftmostStart;

	private int mRightmostStart;

	private int mLeftmostEnd;

	private int mRightmostEnd;

	private int mLeftmostStartPreceding;

	private int mRightmostStartPreceding;

	private int mLeftmostEndPreceding;

	private int mRightmostEndPreceding;

	private int mStartX;

	private int mStartY;

	private int mEndX;

	private int mEndY;

	private UserInternalFrame mUserInternalFrame;

	private SyntaxFacade mSyntaxFacade;

	private int mLeftmostStartNext;

	private int mRightmostStartNext;

	private int mLeftmostEndNext;

	private int mRightmostEndNext;

	private SyntacticStructure prevStart;

	private SyntacticStructure prevEnd;

	private SyntacticStructure nextStart;
	
	private SyntacticStructure nextEnd;

	private boolean firstLineEnd;

	private boolean firstLineStart;

	private LinkedList mLinkedArray;

	private boolean mDrawTrace;


	private static final float triangleLength = 3;

	static final int lineLength = 4;

	static final int padEdge = 6;
	
	public TraceComponent(UserInternalFrame pUserInternalFrame) {
		mUserInternalFrame = pUserInternalFrame;
		mSyntaxFacade = mUserInternalFrame.getSyntaxFacade();
		mDrawTrace = true;
	}
	public void paint(Graphics G) {
		this.setBounds(getUserInternalFrame().getBounds());
		super.paint(G);
		if(mDrawTrace)
		{
		displayMovement(G);
		}
	}

	private void displayMovement(Graphics G) {
		if (getSyntaxFacade().getSentence().getChildren().size() > 0) {
			SyntacticStructure mR = (SyntacticStructure) getSyntaxFacade()
					.getSentence().getChildren().get(0);
			Graphics2D lGraphics2D = (Graphics2D) G;
			lGraphics2D.scale(Sizer.scaleWidth() * getUserInternalFrame().getScale(), Sizer
					.scaleHeight()
					* getUserInternalFrame().getScale());
			// set the font
			// Set the g2D to antialias.
			lGraphics2D.setColor(Color.BLACK);
			lGraphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			lGraphics2D.setRenderingHint(RenderingHints.KEY_RENDERING,
					RenderingHints.VALUE_RENDER_QUALITY);
			lGraphics2D.setStroke(new BasicStroke(.5F));
			resetCounters(mR);
			mLinkedArray = getSyntaxFacade().getLinkedArray();
			displayMovementRecursive(mR, lGraphics2D);
		}
	}

	private void resetCounters(SyntacticStructure start) {
		start.setPadBottomCount(0);
		start.setPadLeftCount(0);
		start.setPadRightCount(0);
		start.setPadStartLeftCount(0);
		start.setPadStartRightCount(0);
		for (int i = 0; i < start.getChildren().size(); i++) {
			SyntacticStructure w = (SyntacticStructure) start.getChildren()
					.get(i);
			resetCounters(w);
		}
	}

	private void displayMovementRecursive(SyntacticStructure start,
			Graphics2D contentGraphics) {

		for (int i = 0; i < start.getStartTrace().size(); i++) {
			SyntacticStructure end = (SyntacticStructure) start
					.getStartTrace().get(i);
			drawMovement(start, end, contentGraphics);
		}
		for (int i = 0; i < start.getChildren().size(); i++) {
			SyntacticStructure w = (SyntacticStructure) start.getChildren()
					.get(i);
			displayMovementRecursive(w, contentGraphics);
		}
	}

	private void drawMovement(SyntacticStructure start,
			SyntacticStructure end, Graphics2D contentGraphics) {
		firstStart = start;
		firstEnd = end;
		ancestorStart = start;
		ancestorEnd = end;
		currentStart = start;
		currentEnd = end;
		prevStart = currentStart;
		prevEnd = currentEnd;
		boolean left = true;
		boolean right = true;
		synchronizeLevel();
		boolean common = ancestorStart.equals(ancestorEnd);
		if (common) {
			left = getSyntaxFacade().checkInsideDirection(start, end,
					start.getLevel(), end.getLevel());
			right = left;
		} else {
			getHighestUncommonAncestors(ancestorStart, ancestorEnd);
			left = checkOutsideDirection(ancestorStart, ancestorEnd);
			right = !left;
		}
		drawStart(start, left, contentGraphics);
		drawEnd(end, right, contentGraphics);
		firstLineEnd = true;
		firstLineStart = true;
		testWidthStart(currentStart, right,false);
		testWidthEnd(currentEnd, left,false);
		setPrecedingStart();
		setPrecedingEnd();
		boolean done = drawDone(contentGraphics, left, right);
		//System.out.println("about to walk down " + done);
		while (!done)
		{
			int startLevel = currentStart.getLevel();
			int endLevel = currentEnd.getLevel();
			if (endLevel >= startLevel)
			{
				setPrecedingStart();
				testWidthStart(currentStart, left,false);
				drawLines(contentGraphics,true, firstLineStart, left);
				firstLineStart = false;
				prevStart = currentStart;
				
				currentStart = getSyntaxFacade().getLower(currentStart,
						currentStart.getNumber(), currentStart.getLevel(),
						currentStart.getLevel() + 1, left);
				if (currentStart == null)
				{
					//System.out.println("step one");
					if (mLinkedArray.size() > prevStart.getLevel()+1)
					{
						//System.out.println("step two");
						currentStart = (SyntacticStructure) ((LinkedList) 
							mLinkedArray.get(prevStart.getLevel()+1)).get(0);
					}
				}
			}	
			if (startLevel >= endLevel)
			{
				setPrecedingEnd();
				testWidthEnd(currentEnd, right,false);
				drawLines(contentGraphics,false, firstLineEnd, right);
				firstLineEnd = false;
				prevEnd = currentEnd;
				currentEnd = getSyntaxFacade().getLower(currentEnd,
						currentEnd.getNumber(), currentEnd.getLevel(),
						currentEnd.getLevel() + 1, right);
				if (currentEnd == null)
				{
					//System.out.println("step one");
					if (mLinkedArray.size() > prevEnd.getLevel()+1)
					{
						//System.out.println("step two");
						currentEnd = (SyntacticStructure) ((LinkedList) 
							mLinkedArray.get(prevEnd.getLevel()+1)).get(0);
					}
				}
			}
			//System.out.println("current Start = " + currentStart.getPreorder());
			//System.out.println("current End = " + currentEnd.getPreorder());
			done = drawDone(contentGraphics, left,right);
			if (startLevel == endLevel && !done)
			{
				done = drawBottom(contentGraphics,left,right);
			}
		}
		System.out.println("");
	}

	private boolean drawBottom(Graphics2D contentGraphics, boolean left, boolean right) {
		if((currentStart == null
				|| currentEnd == null
				|| (left && currentStart.getAbsoluteOrder() < currentEnd
						.getAbsoluteOrder()) || (!left && currentStart
				.getAbsoluteOrder() > currentEnd.getAbsoluteOrder())))
		{
			int mX = getX(prevStart,left);
			int mY = getY(prevStart,left);
			int mX2 = getX(prevEnd,right);
			int mY2 = getY(prevEnd,right);
			if (mY > mY2)
			{
				mY2=mY;
			}
			else
			{
				mY=mY2;
			}
			contentGraphics.drawLine(mStartX,
					mStartY, mX, mStartY);
			contentGraphics.drawLine(mX, mStartY,
					mX, mY);				
			
			contentGraphics.drawLine(mEndX,
						mEndY, mX2, mEndY);
			contentGraphics.drawLine(mX2, mEndY,
						mX2, mY2);
			contentGraphics.drawLine(mX,mY,mX2,mY2);
			return true;
		}
		return false;
	}
	private void drawStart(SyntacticStructure start, boolean left,
			Graphics2D contentGraphics) {
		if (start.getChildren().size() == 0)
		{
			float padBottom = 0;
			
			padBottom = - (start.getPadStartLeft() * padWidth) + start.getPadStartLeftCount() * padWidth;
			
			contentGraphics.fillArc((int)(start.getButtonX() 
					+ start.getButtonWidth()/2 + padBottom - circle/2),(int) (start
							.getButtonY() + start.getTextHeight()), circle,
					circle, 0, 360);
			contentGraphics.drawLine((int)(start.getButtonX() 
					+ start.getButtonWidth()/2 + padBottom),(int) (start
					.getButtonY() + start.getTextHeight() + circle),(int)(start.getButtonX() 
							+ start.getButtonWidth()/2 + padBottom),(int) (start
									.getButtonY() + start.getTextHeight() + lineLength*2));
	
			mStartX = (int)(start.getButtonX() 
					+ start.getButtonWidth()/2 + padBottom);
			mStartY = (int) (start
					.getButtonY() + start.getTextHeight() + lineLength*2);
		}
		else
		{
		if (left) {
			contentGraphics.fillArc((int) start.getButtonX() - padEdge  
					- start.getPadStartLeftCount() * padWidth, (int) start
					.getButtonY() + (start.getTextHeight()/2) - (padLength/2)- (circle/2)
					+ start.getPadStartLeftCount() * padLength, circle,
					circle, 0, 360);

			contentGraphics
					.drawLine(
							(int) start.getButtonX() - padEdge
									- start.getPadStartLeftCount()
									* padWidth,
									(int) start
									.getButtonY() + (start.getTextHeight()/2) - (padLength/2)
									+ start.getPadStartLeftCount() * padLength,
							(int) start.getButtonX()
									- padEdge - (start.getPadStartLeftCount() * padWidth)
									- lineLength,
									(int) start
									.getButtonY() + (start.getTextHeight()/2) - (padLength/2)
									+ start.getPadStartLeftCount() * padLength);
			//testWidthStart(start, left,false);
			mStartX = (int) start.getButtonX() - padEdge
					- (start.getPadStartLeftCount() * padWidth) - lineLength;
			mStartY = (int) start
			.getButtonY() + (start.getTextHeight()/2) - (padLength/2)
			+ start.getPadStartLeftCount() * padLength;
			start.setPadStartLeftCount(start.getPadStartLeftCount() + 1);
		} else {
			contentGraphics.fillArc((int) start.getButtonX() + padEdge
					+ start.getButtonWidth() + start.getPadStartRightCount()
					* padWidth - lineLength,
					(int) start.getButtonY() + (start.getTextHeight()/2) - (circle/2) - (padLength/2)
					+ start.getPadStartRightCount() * padLength,
					circle,
					circle, 0, 360);

			contentGraphics
					.drawLine(
							(int) start.getButtonX() + padEdge
									+ start.getButtonWidth()
									+ (start.getPadStartRightCount() * padWidth)
									,
									(int) start.getButtonY() + (start.getTextHeight()/2) - (padLength/2)
									+ start.getPadStartRightCount() * padLength,
							(int) start.getButtonX() + padEdge
									+ start.getButtonWidth()
									+ (start.getPadStartRightCount() * padWidth)
									+ lineLength, (int) start.getButtonY() + (start.getTextHeight()/2) - (padLength/2)
									+ start.getPadStartRightCount() * padLength);
			//testWidthStart(start, left,false);
			mStartX = (int) start.getButtonX() + padEdge
					+ start.getButtonWidth()
					+ (start.getPadStartRightCount() * padWidth) + lineLength;
			mStartY = (int) (start.getButtonY() + (start.getTextHeight()/2) - (padLength/2)
			+ start.getPadStartRightCount() * padLength);
			start.setPadStartRightCount(start.getPadStartRightCount() + 1);
		}
		}
	}

	private void testWidthStart(SyntacticStructure start, boolean left,boolean next) {
		int rs;
		int ls;
		if (left) {
			rs = (int) start.getButtonX();
			//System.out.println("rightmost start preorder = " + start.getPreorder());
			if (start.getAbsoluteOrder() > 0) {
				SyntacticStructure w = (SyntacticStructure) ((LinkedList) getSyntaxFacade()
						.getLinkedArray().get(start.getLevel())).get(start
						.getAbsoluteOrder() - 1);
				//System.out.println("leftmost start preorder = " + w.getPreorder());
				ls = (int) (w.getButtonX() + w.getButtonWidth());
				if (w.getPadRight() > 0)
				{
					ls += getSyntaxFacade().getPad(w);
				}
			} else {
				ls = (int) getSyntaxFacade().getLeftShift();
			}
		} else {
			ls = (int) start.getButtonX()
					+ start.getButtonWidth();
			//System.out.println("leftmost start preorder = " + start.getPreorder());
			if (start.getPadRight() > 0)
			{
				ls += getSyntaxFacade().getPad(start);
			}
			if (start.getAbsoluteOrder() < ((LinkedList) getSyntaxFacade()
					.getLinkedArray().get(start.getLevel())).size() - 1) {
				SyntacticStructure w = (SyntacticStructure) ((LinkedList) getSyntaxFacade()
						.getLinkedArray().get(start.getLevel())).get(start
						.getAbsoluteOrder() + 1);
				//System.out.println("rightmost start preorder = " + w.getPreorder());
				rs = (int) (w.getButtonX());
			} else {
				rs = (int) ((getSyntaxFacade().getRightShift() + getSyntaxFacade().getPad(start)
						/ Sizer.scaleWidth() / getUserInternalFrame().getScale()) + 1);
			}
		}
		if (next)
		{
			mRightmostStartNext = rs;
			mLeftmostStartNext = ls;
		}
		else
		{
			mRightmostStart = rs;
			mLeftmostStart = ls;
		}
		//System.out.println("Rightmost Start = " + mRightmostStart);
		//System.out.println("Leftmost Start = " + mLeftmostStart);		
	}

	private void testWidthEnd(SyntacticStructure start, boolean left, boolean next) {
		int re;
		int le;
		if (left) {
			//System.out.println("rightmost end preorder = " + start.getPreorder());
			re = (int) start.getButtonX();
			if (start.getAbsoluteOrder() > 0) {
				SyntacticStructure w = (SyntacticStructure) ((LinkedList) getSyntaxFacade()
						.getLinkedArray().get(start.getLevel())).get(start
						.getAbsoluteOrder() - 1);
				//System.out.println("leftmost end preorder = " + w.getPreorder());
				le = (int) (w.getButtonX() + w.getButtonWidth());
				if (w.getPadRight() > 0)
				{
					le += getSyntaxFacade().getPad(w);
				}
			} else {
				le = (int) getSyntaxFacade().getLeftShift();
			}
		} else {
			//System.out.println("leftmost end preorder = " + start.getPreorder());
			le = (int) start.getButtonX()
					+ start.getButtonWidth();
			if (start.getPadRight() > 0)
			{
				le += getSyntaxFacade().getPad(start);
			}
			if (start.getAbsoluteOrder() < ((LinkedList) getSyntaxFacade()
					.getLinkedArray().get(start.getLevel())).size() - 1) {
				SyntacticStructure w = (SyntacticStructure) ((LinkedList) getSyntaxFacade()
						.getLinkedArray().get(start.getLevel())).get(start
						.getAbsoluteOrder() + 1);
				//System.out.println("rightmost end preorder = " + w.getPreorder());
				re = (int) (w.getButtonX());
			} else {
				re = (int) ((getSyntaxFacade().getRightShift() + getSyntaxFacade().getPad(start)
						/ Sizer.scaleWidth() / getUserInternalFrame().getScale()) + 1);
			}
		}
		if (next)
		{
			mRightmostEndNext = re;
			mLeftmostEndNext = le;
		}
		else
		{
			mRightmostEnd = re;
			mLeftmostEnd = le;
		}
		//System.out.println("Rightmost End = " + mRightmostEnd);
		//System.out.println("Leftmost End = " + mLeftmostEnd);
	}
	private void setPrecedingStart()
	{
		
		mLeftmostStartPreceding = mLeftmostStart;
		mRightmostStartPreceding = mRightmostStart;
	}
	private void setPrecedingEnd()
	{
		
		mLeftmostEndPreceding = mLeftmostEnd;
		mRightmostEndPreceding = mRightmostEnd;
	}

	private void drawEnd(SyntacticStructure end, boolean left,
			Graphics2D contentGraphics) {
		if (end.getChildren().size() == 0)
		{
			float padBottom = 0;
			
				padBottom = - (end.getPadStartLeft() * padWidth) + end.getPadStartLeftCount() * padWidth;
			
			GeneralPath polly = new GeneralPath();
			// move the pollygon to the middle and bottom
			polly.moveTo((float) (end.getButtonX() 
					+ end.getButtonWidth()/2 + padBottom),
					(float) end
					.getButtonY() + end.getTextHeight());
			polly.lineTo((float) (end.getButtonX() 
					+ end.getButtonWidth()/2 + padBottom) - (triangleLength),
					(float) end
					.getButtonY() + end.getTextHeight() + triangleLength);
			polly.lineTo((float) (end.getButtonX() 
					+ end.getButtonWidth()/2 + padBottom) + (triangleLength),
					(float) end
					.getButtonY() + end.getTextHeight() + triangleLength);
			polly.closePath();
			contentGraphics.fill(polly);
			
			contentGraphics.drawLine((int)(end.getButtonX() 
					+ end.getButtonWidth()/2 + padBottom),(int) (end
					.getButtonY() + end.getTextHeight() + triangleLength),(int)(end.getButtonX() 
							+ end.getButtonWidth()/2 + padBottom),(int) (end
									.getButtonY() + end.getTextHeight() + lineLength*2));
			//testWidthEnd(end, left,false);
			mEndX = (int)(end.getButtonX() 
					+ end.getButtonWidth()/2 + padBottom);
			mEndY = (int) (end
					.getButtonY() + end.getTextHeight()+ lineLength*2);
			
		}
		else
		{
		if (left) {
			
			GeneralPath polly = new GeneralPath();
			polly.moveTo((float) end.getButtonX() - padEdge
					- end.getPadStartLeftCount() * padWidth + triangleLength,
					(float) end
					.getButtonY() + (end.getTextHeight()/2) - (padLength/2)
					+ end.getPadStartLeftCount() * padLength
							+ 0.25f);
			polly.lineTo((float) end.getButtonX() - padEdge
					- end.getPadStartLeftCount() * padWidth,
					(float) end
					.getButtonY() + (end.getTextHeight()/2) - (padLength/2)
					+ end.getPadStartLeftCount() * padLength
							+ 3.25f);
			polly.lineTo((float) end.getButtonX() - padEdge
					- end.getPadStartLeftCount() * padWidth,
					(float) end
					.getButtonY() + (end.getTextHeight()/2) - (padLength/2)
					+ end.getPadStartLeftCount() * padLength
							- 2.75f);
			polly.closePath();
			contentGraphics.fill(polly);
			
			contentGraphics.drawLine((int) end.getButtonX() - padEdge
					- (end.getPadStartLeftCount() * padWidth) - lineLength , (int) end
					.getButtonY() + (end.getTextHeight()/2) - (padLength/2)
					+ end.getPadStartLeftCount() * padLength, (int) end
					.getButtonX() - padEdge
					- (end.getPadStartLeftCount() * padWidth),
					 (int) end
						.getButtonY() + (end.getTextHeight()/2) - (padLength/2)
						+ end.getPadStartLeftCount() * padLength);
			//testWidthEnd(end, left,false);
			mEndX = (int) end.getButtonX() - padEdge
					- (end.getPadStartLeftCount() * padWidth) - lineLength;
			mEndY = (int) end
			.getButtonY() + (end.getTextHeight()/2) - (padLength/2)
			+ end.getPadStartLeftCount() * padLength;
		} else {
			
			GeneralPath polly = new GeneralPath();
			polly.moveTo((float) end.getButtonX() + padEdge
					+ end.getButtonWidth()
					+ (end.getPadStartRightCount() * padWidth) - triangleLength,
					(float) end
					.getButtonY() + (end.getTextHeight()/2) - (padLength/2)
					+ end.getPadStartRightCount() * padLength
							+ 0.25f);
			polly.lineTo((float) end.getButtonX() + padEdge
					+ end.getButtonWidth()
					+ (end.getPadStartRightCount() * padWidth),
					(float) end
					.getButtonY() + (end.getTextHeight()/2) - (padLength/2)
					+ end.getPadStartRightCount() * padLength
							+ 3.25f);
			polly.lineTo((float) end.getButtonX() + padEdge
					+ end.getButtonWidth()
					+ (end.getPadStartRightCount() * padWidth),
					(float) end
					.getButtonY() + (end.getTextHeight()/2) - (padLength/2)
					+ end.getPadStartRightCount() * padLength
							- 2.75f);
			polly.closePath();
			contentGraphics.fill(polly);
			
			contentGraphics.drawLine((int) end.getButtonX() + padEdge
					+ end.getButtonWidth()
					+ (end.getPadStartRightCount() * padWidth) ,
					(int) end
					.getButtonY() + (end.getTextHeight()/2) - (padLength/2)
					+ end.getPadStartRightCount() * padLength, (int) end.getButtonX() + padEdge
							+ end.getButtonWidth()
							+ (end.getPadStartRightCount() * padWidth) + lineLength,
							(int) end
							.getButtonY() + (end.getTextHeight()/2) - (padLength/2)
							+ end.getPadStartRightCount() * padLength);
			//testWidthEnd(end, left, false);
			mEndX = (int) end.getButtonX() + padEdge + end.getButtonWidth()
					+ (end.getPadStartRightCount() * padWidth) + lineLength;
			mEndY = (int) end
			.getButtonY() + (end.getTextHeight()/2) - (padLength/2)
			+ end.getPadStartRightCount() * padLength;
		}
		}
		if (left)
		{
			end.setPadStartLeftCount(end.getPadStartLeftCount() + 1);
		}
		else
		{
			end.setPadStartRightCount(end.getPadStartRightCount() + 1);
		}
	}

	private int getY(SyntacticStructure start, boolean left) {
		if (left)
		{
			if (start.getAbsoluteOrder() > 0)
			{
				start = (SyntacticStructure) ((LinkedList) getSyntaxFacade().getLinkedArray().get(start.getLevel())).get(start.getAbsoluteOrder()-1);
			}
		}
		int pad = ((Integer) getSyntaxFacade().getHeightPad().get(start.getLevel())).intValue();
		pad = (int) (start.getButtonY() + start.getTextHeight() + (start.getPadBottom() * 3) - (start.getPadBottomCount() * 3));
		start.setPadBottomCount(start.getPadBottomCount()+1);
		return pad;
	}

	private int getX(SyntacticStructure start, boolean left) {
		int pad = 0;
		if (left)
		{
			pad = (int) (start.getButtonX());
			if (start.getAbsoluteOrder() > 0)
			{
				start = (SyntacticStructure) ((LinkedList) getSyntaxFacade().getLinkedArray().get(start.getLevel())).get(start.getAbsoluteOrder()-1);
				pad = pad - (start.getPadRightCount() * 4); 
			}
		}
		else
		{
			pad = (int) (start.getButtonX() + start.getButtonWidth());
			pad = pad + (start.getPadRight() * 4) + (start.getPadStartRight() * 8) - (start.getPadRightCount() * 4); 
		}
		
		start.setPadRightCount(start.getPadRightCount()+1);
		return pad;
	}


	private void drawLines(Graphics2D contentGraphics,boolean start, boolean first, boolean left) {
		if (start)
		{
			if (mLeftmostStartPreceding >= mRightmostStart 
					//|| mLeftmostStart >= mRightmostStartPreceding
					)
			{
				int mX = getX(currentStart,left);
				int mY = getY(currentStart,left);
				if (!first)
				{
					contentGraphics.drawLine(mStartX,
							mY, mX, mY);
					contentGraphics.drawLine(mStartX, mStartY,
							mStartX, mY);
					mStartX = mX;	
				}
				else
				{
					contentGraphics.drawLine(mStartX, mStartY,
						mStartX, mY);
				}
				mStartY = mY;	
			}
			else
			{
				nextStart = getSyntaxFacade().getLower(currentStart,
						currentStart.getNumber(), currentStart.getLevel(),
						currentStart.getLevel() + 1, left);
				if (nextStart != null)
				{
					testWidthStart(nextStart,left,true);
					if((mRightmostStartNext >= mRightmostStart && mStartX > mRightmostStart)
							|| (mLeftmostStartNext <= mLeftmostStart && mStartX < mLeftmostStart))
					{
						int mX = getX(currentStart,left);
						int mY = getY(currentStart,left);
						contentGraphics.drawLine(mStartX,
								mStartY, mX, mStartY);
						contentGraphics.drawLine(mX, mStartY,
								mX, mY);
						mStartX = mX;	
						mStartY = mY;	
					}
				}
			}
		}
		else
		{
			if (mLeftmostEndPreceding >= mRightmostEnd 
					//|| mLeftmostEnd >= mRightmostEndPreceding
					)
			{
				int	mX = getX(currentEnd,left);
				int	mY = getY(currentEnd,left);
				if (!first)
				{
				contentGraphics.drawLine(mEndX,
							mY, mX, mY);
				contentGraphics.drawLine(mEndX, mEndY,
							mEndX, mY);
				mEndX = mX;
				}
				else
				{
					contentGraphics.drawLine(mEndX,
							mY, mEndX, mEndY);
				}
				mEndY = mY;
			}
			else
			{
				nextEnd = getSyntaxFacade().getLower(currentEnd,
						currentEnd.getNumber(), currentEnd.getLevel(),
						currentEnd.getLevel() + 1, left);
				if (nextEnd !=null)
				{
					testWidthEnd(nextEnd,left,true);
					if((mRightmostEndNext >= mRightmostEnd && mEndX > mRightmostEnd)
							|| (mLeftmostEndNext <= mLeftmostEnd && mEndX < mLeftmostEnd))
					{
						int	mX = getX(currentEnd,left);
						int	mY = getY(currentEnd,left);
		
						contentGraphics.drawLine(mEndX,
									mEndY, mX, mEndY);
						contentGraphics.drawLine(mX, mEndY,
									mX, mY);
						mEndX = mX;
						mEndY = mY;
					}
				}
			}
		}
	}
	private boolean drawDone(Graphics2D contentGraphics, boolean left,boolean right)
	{
		boolean testStart = false;
		if (currentStart !=null && currentEnd !=null)
		{
			if (currentStart.getLevel() == currentEnd.getLevel())
			{
				LinkedList hold = (LinkedList) 
					getSyntaxFacade().getLinkedArray().get(currentStart.getLevel());
				//System.out.println("current start = " + currentStart.getPreorder());
				//System.out.println("current end = " + currentEnd.getPreorder());
				if (currentStart.equals(currentEnd))
					testStart = true;
				if (currentEnd.getAbsoluteOrder() < hold.size() -1 && 
					(currentEnd.equals(currentEnd.getSyntacticParent().getChildren().getLast())
					|| currentEnd.getSyntacticParent().equals(currentStart.getSyntacticParent())))
					
				{
					if(currentStart.equals(hold.get(currentEnd.getAbsoluteOrder() +1)))
						testStart = true;
				}
				if (currentEnd.getAbsoluteOrder() > 0
						&& (currentEnd.equals(currentEnd.getSyntacticParent().getChildren().getFirst())
						|| currentEnd.getSyntacticParent().equals(currentStart.getSyntacticParent())))
				{
					if(currentStart.equals(hold.get(currentEnd.getAbsoluteOrder() -1)))
						testStart = true;
				}	
				if(testStart)
				{		
					System.out.println("here");
					System.out.println("firststart = " + firstStart.getPreorder());
					System.out.println("firstend = " + firstEnd.getPreorder());
					System.out.println("currentstart = " + currentStart.getPreorder());
					System.out.println("currentend = " + currentEnd.getPreorder());
					boolean horizontalFirst = true;
//					if (firstStart == currentStart && firstEnd == currentEnd)
//					{
//						System.out.println("here");
//						if ((firstStart.getChildren().size() == 0 && mStartY < mEndY)
//								|| (firstEnd.getChildren().size() == 0 && mEndY < mStartY))
//						{
//							horizontalFirst = false;
//						}
//						else
//						{
//							horizontalFirst = true;
//						}
//					}
//					else 
					if (firstStart == currentStart)
					{
						if (firstStart.getChildren().size() == 0)
						{
							if (mEndY > mStartY)
							{
								horizontalFirst = true;
							}
							else
							{
								horizontalFirst = false;
							}
						}
						else if (left)
						{
							if (mEndX > mStartX)
							{
								horizontalFirst = true;
							}
							else
							{
								horizontalFirst = false;
							}
						}
						else
						{
							if(mEndX < mStartX)
							{
								horizontalFirst = true;
							}
							else
							{
								horizontalFirst = false;
							}
						}
					}
					else if (firstEnd == currentEnd)
					{
						if (firstEnd.getChildren().size() == 0)
						{
							if(mStartY > mEndY)
							{
								horizontalFirst = false;
							}
							else
							{
								horizontalFirst = true;
							}
						}
						else if (right)
						{
							if (mStartX > mEndX)
							{
								horizontalFirst = false;
							}
							else
							{
								horizontalFirst = true;
							}
						}
						else
						{
							if (mStartX < mEndX)
							{
								horizontalFirst = false;
							}
							else
							{
								horizontalFirst = true;
							}
						}
					}
					else
					{
						System.out.println("this should never print");
					}
					if (horizontalFirst)
					{
						contentGraphics.drawLine(mEndX, mEndY,
								mStartX, mEndY);
						contentGraphics.drawLine(mStartX, mEndY,
								mStartX, mStartY);
					}
					else
					{
						contentGraphics.drawLine(mStartX,
								mStartY, mEndX, mStartY);
						contentGraphics.drawLine(mEndX, mStartY,
								mEndX, mEndY);
					}
				}
			}
		}
		return testStart;
	}

	private void getHighestUncommonAncestors(SyntacticStructure start,
			SyntacticStructure end) {
		while (!start.getSyntacticParent().equals(end.getSyntacticParent())) {
			start = (SyntacticStructure) start.getSyntacticParent();
			end = (SyntacticStructure) end.getSyntacticParent();
		}
	}

	private void synchronizeLevel() {
		int difference = ancestorStart.getLevel() - ancestorEnd.getLevel();
		if (difference != 0) {
			if (difference < 0) {
				for (int i = difference; i < 0; i++) {
					ancestorEnd = (SyntacticStructure) ancestorEnd.getSyntacticParent();
				}
			} else {
				for (int i = difference; i > 0; i--) {
					ancestorStart = (SyntacticStructure) ancestorStart
							.getSyntacticParent();
				}
			}
		}
	}

	private boolean checkOutsideDirection(SyntacticStructure UAStart,
			SyntacticStructure UAEnd) {
		if (UAStart.getPreorder() < UAEnd.getPreorder()) {
			// start is to the left of the end, so you go right!
			return false;
		}
		// start is to the right of end, so you go left!
		return true;
	}
	private SyntaxFacade getSyntaxFacade()
	{
		return mSyntaxFacade;
	}
	private UserInternalFrame getUserInternalFrame()
	{
		return mUserInternalFrame;
	}
	public void setDrawTrace(boolean drawTrace)
	{
		mDrawTrace = drawTrace;
	}
	public boolean getDrawTrace()
	{
		return mDrawTrace;
	}
}

