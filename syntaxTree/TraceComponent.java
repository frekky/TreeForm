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

	private SyntacticStructure mStart;

	private SyntacticStructure mEnd;

	private SyntacticStructure uAStart;

	private SyntacticStructure uAEnd;

	private SyntacticStructure lDStart;

	private SyntacticStructure lDEnd;

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

	private int mLeftmostStartLast;

	private int mRightmostStartLast;

	private int mLeftmostEndLast;

	private int mRightmostEndLast;

	private SyntacticStructure prevStart;

	private SyntacticStructure prevEnd;

	private static final float triangleLength = 3;

	static final int lineLength = 4;

	static final int padEdge = 6;
	
	public TraceComponent(UserInternalFrame pUserInternalFrame) {
		mUserInternalFrame = pUserInternalFrame;
		mSyntaxFacade = mUserInternalFrame.getSyntaxFacade();
	}
	public void paint(Graphics G) {
		this.setBounds(getUserInternalFrame().getBounds());
		super.paint(G);
		displayMovement(G);
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
		mStart = start;
		mEnd = end;
		uAStart = start;
		uAEnd = end;
		lDStart = start;
		lDEnd = end;
		prevStart = lDStart;
		prevEnd = lDEnd;
		boolean left = true;
		boolean right = true;
		synchronizeLevel();
		boolean common = uAStart.equals(uAEnd);
		if (common) {
			left = getSyntaxFacade().checkInsideDirection(start, end,
					start.getLevel(), end.getLevel());
			right = left;
		} else {
			getHighestUncommonAncestors(uAStart, uAEnd);
			left = checkOutsideDirection(uAStart, uAEnd);
			right = !left;
		}
		drawStart(start, left, contentGraphics);
		drawEnd(end, right, contentGraphics);
		boolean firstEnd = true;
		boolean firstStart = true;
		if (start.getLevel()<end.getLevel())
		{
			firstEnd = false;
		}
		if (end.getLevel()<start.getLevel())
		{
			firstStart = false;
		}
		boolean done = goToLowestLevel(start.getLevel(), end.getLevel(),
				left,right, contentGraphics);
		if (!done) {
			while (!(lDStart == null
					|| lDEnd == null
					|| (left && lDStart.getAbsoluteOrder() < lDEnd
							.getAbsoluteOrder()) || (!left && lDStart
					.getAbsoluteOrder() > lDEnd.getAbsoluteOrder()))) {
				setPrecedingStart();
				setPrecedingEnd();
				testWidthStart(lDStart, right);
				testWidthEnd(lDEnd, left);
				System.out.println("start = " + lDStart.getPreorder());
				System.out.println("end = " + lDEnd.getPreorder());
				drawLines(contentGraphics,true, firstStart, right);
				drawLines(contentGraphics,false, firstEnd, left);
				firstStart = false;
				firstEnd = false;
				prevStart = lDStart;
				prevEnd = lDEnd;
				lDStart = getSyntaxFacade().getLower(lDStart,
						lDStart.getNumber(), lDStart.getLevel(),
						lDStart.getLevel() + 1, right);
				lDEnd = getSyntaxFacade().getLower(lDEnd,
						lDEnd.getNumber(), lDEnd.getLevel(),
						lDEnd.getLevel() + 1, left);
			}
			drawBottom(contentGraphics, left, right);
		}
		System.out.println();
	}



	private void drawBottom(Graphics2D contentGraphics, boolean left, boolean right) {
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
				mEndY, mX, mEndY);
		contentGraphics.drawLine(mX, mStartY,
				mX, mY);				
		
		contentGraphics.drawLine(mEndX,
					mEndY, mX2, mEndY);
		contentGraphics.drawLine(mX2, mEndY,
					mX2, mY2);
		contentGraphics.drawLine(mX,mY,mX2,mY2);
	}
	private void drawStart(SyntacticStructure start, boolean left,
			Graphics2D contentGraphics) {
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
			testWidthStart(start, left);
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
			testWidthStart(start, left);
			mStartX = (int) start.getButtonX() + padEdge
					+ start.getButtonWidth()
					+ (start.getPadStartRightCount() * padWidth) + lineLength;
			mStartY = (int) (start.getButtonY() + (start.getTextHeight()/2) - (padLength/2)
			+ start.getPadStartRightCount() * padLength);
			start.setPadStartRightCount(start.getPadStartRightCount() + 1);
		}
	}

	private void testWidthStart(SyntacticStructure start, boolean left) {
		if (left) {
			mRightmostStart = (int) start.getButtonX();
			System.out.println("rightmost start preorder = " + start.getPreorder());
			if (start.getAbsoluteOrder() > 0) {
				SyntacticStructure w = (SyntacticStructure) ((LinkedList) getSyntaxFacade()
						.getLinkedArray().get(start.getLevel())).get(start
						.getAbsoluteOrder() - 1);
				System.out.println("leftmost start preorder = " + w.getPreorder());
				mLeftmostStart = (int) (w.getButtonX() + w.getButtonWidth());
				if (w.getPadRight() > 0)
				{
					mLeftmostStart += getSyntaxFacade().getPad(w);
				}
			} else {
				mLeftmostStart = (int) getSyntaxFacade().getLeftShift();
			}
		} else {
			mLeftmostStart = (int) start.getButtonX()
					+ start.getButtonWidth();
			System.out.println("leftmost start preorder = " + start.getPreorder());
			if (start.getPadRight() > 0)
			{
				mLeftmostStart += getSyntaxFacade().getPad(start);
			}
			if (start.getAbsoluteOrder() < ((LinkedList) getSyntaxFacade()
					.getLinkedArray().get(start.getLevel())).size() - 1) {
				SyntacticStructure w = (SyntacticStructure) ((LinkedList) getSyntaxFacade()
						.getLinkedArray().get(start.getLevel())).get(start
						.getAbsoluteOrder() + 1);
				System.out.println("rightmost start preorder = " + w.getPreorder());
				mRightmostStart = (int) (w.getButtonX());
			} else {
				mRightmostStart = (int) ((getSyntaxFacade().getRightShift() + getSyntaxFacade().getPad(start)
						/ Sizer.scaleWidth() / getUserInternalFrame().getScale()) + 1);
			}
		}
		System.out.println("Rightmost Start = " + mRightmostStart);
		System.out.println("Leftmost Start = " + mLeftmostStart);
		
	}

	private void testWidthEnd(SyntacticStructure start, boolean left) {
		if (left) {
			System.out.println("rightmost end preorder = " + start.getPreorder());
			mRightmostEnd = (int) start.getButtonX();
			if (start.getAbsoluteOrder() > 0) {
				SyntacticStructure w = (SyntacticStructure) ((LinkedList) getSyntaxFacade()
						.getLinkedArray().get(start.getLevel())).get(start
						.getAbsoluteOrder() - 1);
				System.out.println("leftmost end preorder = " + w.getPreorder());
				mLeftmostEnd = (int) (w.getButtonX() + w.getButtonWidth());
				if (w.getPadRight() > 0)
				{
					mLeftmostEnd += getSyntaxFacade().getPad(w);
				}
			} else {
				mLeftmostEnd = (int) getSyntaxFacade().getLeftShift();
			}
		} else {
			System.out.println("leftmost end preorder = " + start.getPreorder());
			mLeftmostEnd = (int) start.getButtonX()
					+ start.getButtonWidth();
			if (start.getPadRight() > 0)
			{
				mLeftmostEnd += getSyntaxFacade().getPad(start);
			}
			if (start.getAbsoluteOrder() < ((LinkedList) getSyntaxFacade()
					.getLinkedArray().get(start.getLevel())).size() - 1) {
				SyntacticStructure w = (SyntacticStructure) ((LinkedList) getSyntaxFacade()
						.getLinkedArray().get(start.getLevel())).get(start
						.getAbsoluteOrder() + 1);
				System.out.println("rightmost end preorder = " + w.getPreorder());
				mRightmostEnd = (int) (w.getButtonX());
			} else {
				mRightmostEnd = (int) ((getSyntaxFacade().getRightShift() + getSyntaxFacade().getPad(start)
						/ Sizer.scaleWidth() / getUserInternalFrame().getScale()) + 1);
			}
		}
		System.out.println("Rightmost End = " + mRightmostEnd);
		System.out.println("Leftmost End = " + mLeftmostEnd);
	}
	private void setPrecedingStart()
	{
		mStart = lDStart;
		mLeftmostStartPreceding = mLeftmostStart;
		mRightmostStartPreceding = mRightmostStart;
	}
	private void setPrecedingEnd()
	{
		mEnd = lDEnd;
		mLeftmostEndPreceding = mLeftmostEnd;
		mRightmostEndPreceding = mRightmostEnd;
	}
	private void setStart()
	{
		mLeftmostStartLast = mLeftmostStartPreceding;
		mRightmostStartLast = mRightmostStartPreceding;
	}
	private void setEnd()
	{
		mLeftmostEndLast = mLeftmostEndPreceding;
		mRightmostEndLast = mRightmostEndPreceding;
	}
	private void drawEnd(SyntacticStructure end, boolean left,
			Graphics2D contentGraphics) {
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
			testWidthEnd(end, left);
			mEndX = (int) end.getButtonX() - padEdge
					- (end.getPadStartLeftCount() * padWidth) - lineLength;
			mEndY = (int) end
			.getButtonY() + (end.getTextHeight()/2) - (padLength/2)
			+ end.getPadStartLeftCount() * padLength;
			end.setPadStartLeftCount(end.getPadStartLeftCount() + 1);
			// System.out.println("draw left");
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
			testWidthEnd(end, left);
			mEndX = (int) end.getButtonX() + padEdge + end.getButtonWidth()
					+ (end.getPadStartRightCount() * padWidth) + lineLength;
			mEndY = (int) end
			.getButtonY() + (end.getTextHeight()/2) - (padLength/2)
			+ end.getPadStartRightCount() * padLength;
			end.setPadStartRightCount(end.getPadStartRightCount() + 1);
		}
		
	}

	private boolean goToLowestLevel(int startLevel, int endLevel,
			boolean left, boolean right, Graphics2D contentGraphics) {
		boolean done = false;
		if (startLevel < endLevel) {
			testWidthStart(lDStart,left);
			setPrecedingStart();
			setStart();
			drawLines(contentGraphics, true, true, left);
			for (int j = startLevel; j < endLevel; j++) {
				setPrecedingStart();
				prevStart = lDStart;
				lDStart = getSyntaxFacade().getLower(lDStart,
						lDStart.getNumber(), lDStart.getLevel(),
						lDStart.getLevel() + 1, left);
				if (lDStart == null)
				{
					lDStart = lDEnd;
					j = endLevel;
				}
				else
				{
					testWidthStart(lDStart, left);
				}
				done = drawDone(contentGraphics, left);
			}
		} else if (endLevel < startLevel){
			testWidthEnd(lDEnd, right);
			setPrecedingEnd();
			setEnd();
			drawLines(contentGraphics, false, true, right);
			for (int j = endLevel; j < startLevel; j++) {
				setPrecedingEnd();
				prevEnd = lDEnd;
				lDEnd = getSyntaxFacade().getLower(lDEnd,
						lDEnd.getNumber(), lDEnd.getLevel(),
						lDEnd.getLevel() + 1, right);
//				System.out.println("end down = " + lDEnd.getPreorder());
//				System.out.println("left = " + right);
				if (lDEnd == null)
				{
					lDEnd = lDStart;
					j = startLevel;
				}
				else
				{
					testWidthEnd(lDEnd, right);
				}
				done = drawDone(contentGraphics, right);
			}
		}
		else
		{
			
			done = drawDone(contentGraphics, left);
			done = drawDone(contentGraphics,right);
		}
		return done;
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
//		System.out.println("rightmost start preceding = " + mRightmostStartPreceding);
//		System.out.println("rightmost start last = " + mRightmostStartLast);
//		System.out.println("rightmost start = " + mRightmostStart);
//		System.out.println("leftmost start preceding = " + mLeftmostStartPreceding);
//		System.out.println("leftmost start last = " + mLeftmostStartLast);
//		System.out.println("leftmost start = " + mLeftmostStart);
//		System.out.println("start x = " + mStartX);
//		
//		System.out.println("");
//		System.out.println("rightmost end preceding = " + mRightmostEndPreceding);
//		System.out.println("rightmost end last = " + mRightmostEndLast);
//		System.out.println("rightmost end = " + mRightmostEnd);
//		System.out.println("leftmost end preceding = " + mLeftmostEndPreceding);
//		System.out.println("leftmost end last = " + mLeftmostEndLast);
//		System.out.println("leftmost end = " + mLeftmostEnd);	
//		System.out.println("end x = " + mEndX);
//		System.out.println("");
//		if (start)
//		{
//			if((mLeftmostStart > mStartX || mRightmostStart < mStartX)  && mLeftmostStart >= mRightmostStartPreceding )
//			{
//				int mX = getX(prevStart,left);
//				int mY = getY(prevStart,left);
//			
//				contentGraphics.drawLine(mStartX,
//						mEndY, mX, mEndY);
//				contentGraphics.drawLine(mX, mStartY,
//						mX, mY);
//				mStartX = mX;	
//				mStartY = mY;	
//				setStart();
//				
//			}
//		}
//		else
//		{
//			if((mLeftmostEnd > mEndX || mRightmostEnd < mEndX)  && mLeftmostEnd >= mRightmostEndPreceding )
//			{
//				int	mX = getX(prevEnd,left);
//				int	mY = getY(prevEnd,left);
//
//				contentGraphics.drawLine(mEndX,
//							mEndY, mX, mEndY);
//				contentGraphics.drawLine(mX, mEndY,
//							mX, mY);
//				mEndX = mX;
//				mEndY = mY;
//				setEnd();
//				
//			}
//		}


		if (start)
		{
		//System.out.println("this is start");
		if (mLeftmostStartPreceding >= mRightmostStart || mLeftmostStart >= mRightmostStartPreceding )
			{
				int mX = getX(lDStart,left);
				int mY = getY(lDStart,left);
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
			setStart();
			}
		}
		else
		{
			//System.out.println("This is end");
			if (mLeftmostEndPreceding >= mRightmostEnd || mLeftmostEnd >= mRightmostEndPreceding)
			{
				int	mX = getX(lDEnd,left);
				int	mY = getY(lDEnd,left);
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
				setEnd();
			}
		}

	}
	private boolean drawDone(Graphics2D contentGraphics, boolean left)
	{
		boolean testStart = false;
		if (lDStart.getLevel() == lDEnd.getLevel())
		{
			//System.out.println("same level = " + lDStart.getLevel());
			LinkedList hold = (LinkedList) 
					getSyntaxFacade().getLinkedArray().get(lDStart.getLevel());
			if (lDStart.equals(lDEnd))
				testStart = true;
			if (lDEnd.getAbsoluteOrder() < hold.size() -1 && !left)
			{
				//System.out.println(((SyntacticStructure)hold.get(lDEnd.getAbsoluteOrder() +1)).getPreorder());
				if(lDStart.equals(hold.get(lDEnd.getAbsoluteOrder() +1)))
					testStart = true;
			}
			if (lDEnd.getAbsoluteOrder() > 0 && left)
			{
				//System.out.println(((SyntacticStructure)hold.get(lDEnd.getAbsoluteOrder() -1)).getPreorder());
				if(lDStart.equals(hold.get(lDEnd.getAbsoluteOrder() -1)))
					testStart = true;
			}	
			if(testStart)
			{
				if (lDEnd == mEnd)
				{	
					contentGraphics.drawLine(mStartX,
							mStartY, mEndX, mStartY);
					contentGraphics.drawLine(mEndX, mStartY,
							mEndX, mEndY);
				}
				else if(lDStart == mStart)
				{
					contentGraphics.drawLine(mEndX, mEndY,
							mStartX, mEndY);
					contentGraphics.drawLine(mStartX, mEndY,
							mStartX, mStartY);
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
		int difference = uAStart.getLevel() - uAEnd.getLevel();
		if (difference != 0) {
			if (difference < 0) {
				for (int i = difference; i < 0; i++) {
					uAEnd = (SyntacticStructure) uAEnd.getSyntacticParent();
				}
			} else {
				for (int i = difference; i > 0; i--) {
					uAStart = (SyntacticStructure) uAStart
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
}

