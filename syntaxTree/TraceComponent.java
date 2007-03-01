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

	private static final int padWidth = 8;

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
	
	public TraceComponent(UserInternalFrame pUserInternalFrame) {
		mUserInternalFrame = pUserInternalFrame;
		mSyntaxFacade = mUserInternalFrame.getSyntaxFacade();
	}
	public void paint(Graphics G) {
		this.setBounds(getUserInternalFrame().getBounds());
		super.paint(G);
		displayMovement(G);
		System.out.println("happy painting");
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
		testWidthStart(start, left);
		setPrecedingStart();
		drawEnd(end, right, contentGraphics);
		testWidthEnd(end,right);
		setPrecedingEnd();
		boolean done = goToLowestLevel(start.getLevel(), end.getLevel(),
				left,right, contentGraphics);
		System.out.println("done = " + done);
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
				drawLinesStart(contentGraphics, right, false,false);
				drawLinesEnd(contentGraphics, left, false,false);
				lDStart = getSyntaxFacade().getLower(lDStart,
						lDStart.getNumber(), lDStart.getLevel(),
						lDStart.getLevel() + 1, right);
				lDEnd = getSyntaxFacade().getLower(lDEnd,
						lDEnd.getNumber(), lDEnd.getLevel(),
						lDEnd.getLevel() + 1, left);
			}
//				drawLinesStart(contentGraphics, left, true,false);
//				drawLinesEnd(contentGraphics, right, true,false);
//				setPrecedingStart();
//				setPrecedingEnd();
		} else {
			if (start.getLevel() - end.getLevel() < 0) 
			{
			} 
			else 
			{
				
			}
			drawEasy(contentGraphics, left);
		}
	}

	private void drawEasy(Graphics2D contentGraphics, boolean left) 
	{
		System.out.println("start x =" + mStartX);
		System.out.println("start y =" + mStartY);
		System.out.println("end x =" + mEndX);
		System.out.println("end y =" + mEndY);
		System.out.println("DRAW EASY");
		if (lDStart.equals(mStart)) {
			contentGraphics.drawLine(mEndX, mEndY,
					mStartX, mEndY);
			contentGraphics.drawLine(mStartX, mEndY,
					mStartX, mStartY);
		}
		if (lDEnd.equals(mEnd)) {
			contentGraphics.drawLine(mStartX,
					mStartY, mEndX, mStartY);
			contentGraphics.drawLine(mEndX, mStartY,
					mEndX, mEndY);
		}
	}

	private void drawStart(SyntacticStructure start, boolean left,
			Graphics2D contentGraphics) {
		if (left) {
			contentGraphics.fillArc((int) start.getButtonX()
					- start.getPadStartLeftCount() * padWidth, (int) start
					.getButtonY() + (start.getTextHeight()/2) - (padLength/2)- (circle/2)
					+ start.getPadStartLeftCount() * padLength, circle,
					circle, 0, 360);

			contentGraphics
					.drawLine(
							(int) start.getButtonX()
									- start.getPadStartLeftCount()
									* padWidth,
									(int) start
									.getButtonY() + (start.getTextHeight()/2) - (padLength/2)
									+ start.getPadStartLeftCount() * padLength,
							(int) start.getButtonX()
									- (start.getPadStartLeftCount() * padWidth)
									- 4,
									(int) start
									.getButtonY() + (start.getTextHeight()/2) - (padLength/2)
									+ start.getPadStartLeftCount() * padLength);
			testWidthStart(start, left);
			mStartX = (int) start.getButtonX()
					- (start.getPadStartLeftCount() * padWidth) - 4;
			mStartY = (int) start
			.getButtonY() + (start.getTextHeight()/2) - (padLength/2)
			+ start.getPadStartLeftCount() * padLength;
			start.setPadStartLeftCount(start.getPadStartLeftCount() + 1);
		} else {
			contentGraphics.fillArc((int) start.getButtonX()
					+ start.getButtonWidth() + start.getPadStartRightCount()
					* padWidth-4,
					(int) start.getButtonY() + (start.getTextHeight()/2) - (circle/2) - (padLength/2)
					+ start.getPadStartRightCount() * padLength,
					circle,
					circle, 0, 360);

			contentGraphics
					.drawLine(
							(int) start.getButtonX()
									+ start.getButtonWidth()
									+ (start.getPadStartRightCount() * padWidth)
									,
									(int) start.getButtonY() + (start.getTextHeight()/2) - (padLength/2)
									+ start.getPadStartRightCount() * padLength,
							(int) start.getButtonX()
									+ start.getButtonWidth()
									+ (start.getPadStartRightCount() * padWidth)
									+ 4, (int) start.getButtonY() + (start.getTextHeight()/2) - (padLength/2)
									+ start.getPadStartRightCount() * padLength);
			testWidthStart(start, left);
			mStartX = (int) start.getButtonX()
					+ start.getButtonWidth()
					+ (start.getPadStartRightCount() * padWidth) + 4;
			mStartY = (int) (start.getButtonY() + (start.getTextHeight()/2) - (padLength/2)
			+ start.getPadStartRightCount() * padLength);
			start.setPadStartRightCount(start.getPadStartRightCount() + 1);
		}
	}

	private void testWidthStart(SyntacticStructure start, boolean left) {
		if (left) {
			mRightmostStart = (int) start.getButtonX();
			if (start.getAbsoluteOrder() > 0) {
				SyntacticStructure w = (SyntacticStructure) ((LinkedList) getSyntaxFacade()
						.getLinkedArray().get(start.getLevel())).get(start
						.getAbsoluteOrder() - 1);
				mLeftmostStart = (int) (w.getButtonX() + w.getButtonWidth());
				if (w.getPad())
				{
					mLeftmostStart += w.getPadRight() * 4 + w.getPadStartRight() * 8;
				}
			} else {
				mLeftmostStart = (int) getSyntaxFacade().getLeftShift();
			}
		} else {
			mLeftmostStart = (int) start.getButtonX()
					+ start.getButtonWidth();
			if (start.getPad())
			{
				mLeftmostStart += start.getPadRight() * 4 + start.getPadStartRight() * 8;
			}
			if (start.getAbsoluteOrder() < ((LinkedList) getSyntaxFacade()
					.getLinkedArray().get(start.getLevel())).size() - 1) {
				SyntacticStructure w = (SyntacticStructure) ((LinkedList) getSyntaxFacade()
						.getLinkedArray().get(start.getLevel())).get(start
						.getAbsoluteOrder() + 1);
				mRightmostStart = (int) (w.getButtonX());
			} else {
				mRightmostStart = (int) ((getSyntaxFacade().getRightShift()
						/ Sizer.scaleWidth() / getUserInternalFrame().getScale()) + 1);
			}
		}
	}

	private void testWidthEnd(SyntacticStructure start, boolean left) {
		if (left) {
			mRightmostEnd = (int) start.getButtonX();
			if (start.getAbsoluteOrder() > 0) {
				SyntacticStructure w = (SyntacticStructure) ((LinkedList) getSyntaxFacade()
						.getLinkedArray().get(start.getLevel())).get(start
						.getAbsoluteOrder() - 1);
				mLeftmostEnd = (int) (w.getButtonX() + w.getButtonWidth());
				if (w.getPad())
				{
					mLeftmostEnd += w.getPadRight() * 4 + w.getPadStartRight() * 8;
				}
			} else {
				mLeftmostEnd = (int) getSyntaxFacade().getLeftShift();
			}
		} else {
			mLeftmostEnd = (int) start.getButtonX()
					+ start.getButtonWidth();
			if (start.getPad())
			{
				mLeftmostEnd += start.getPadRight() * 4 + start.getPadStartRight() * 8;
			}
			if (start.getAbsoluteOrder() < ((LinkedList) getSyntaxFacade()
					.getLinkedArray().get(start.getLevel())).size() - 1) {
				SyntacticStructure w = (SyntacticStructure) ((LinkedList) getSyntaxFacade()
						.getLinkedArray().get(start.getLevel())).get(start
						.getAbsoluteOrder() + 1);
				mRightmostEnd = (int) (w.getButtonX());
			} else {
				mRightmostEnd = (int) ((getSyntaxFacade().getRightShift()
						/ Sizer.scaleWidth() / getUserInternalFrame().getScale()) + 1);
			}
		}
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

	private void drawEnd(SyntacticStructure end, boolean left,
			Graphics2D contentGraphics) {
		if (left) {
			
			GeneralPath polly = new GeneralPath();
			polly.moveTo((float) end.getButtonX()
					- end.getPadStartLeftCount() * padWidth + 3,
					(float) end
					.getButtonY() + (end.getTextHeight()/2) - (padLength/2)
					+ end.getPadStartLeftCount() * padLength
							+ 0.25f);
			polly.lineTo((float) end.getButtonX()
					- end.getPadStartLeftCount() * padWidth,
					(float) end
					.getButtonY() + (end.getTextHeight()/2) - (padLength/2)
					+ end.getPadStartLeftCount() * padLength
							+ 3.25f);
			polly.lineTo((float) end.getButtonX()
					- end.getPadStartLeftCount() * padWidth,
					(float) end
					.getButtonY() + (end.getTextHeight()/2) - (padLength/2)
					+ end.getPadStartLeftCount() * padLength
							- 2.75f);
			polly.closePath();
			contentGraphics.fill(polly);
			
			contentGraphics.drawLine((int) end.getButtonX()
					- (end.getPadStartLeftCount() * padWidth) -4, 	 (int) end
					.getButtonY() + (end.getTextHeight()/2) - (padLength/2)
					+ end.getPadStartLeftCount() * padLength, (int) end
					.getButtonX()
					- (end.getPadStartLeftCount() * padWidth),
					 (int) end
						.getButtonY() + (end.getTextHeight()/2) - (padLength/2)
						+ end.getPadStartLeftCount() * padLength);
			testWidthEnd(end, left);
			mEndX = (int) end.getButtonX()
					- (end.getPadStartLeftCount() * padWidth) -4;
			mEndY = (int) end
			.getButtonY() + (end.getTextHeight()/2) - (padLength/2)
			+ end.getPadStartLeftCount() * padLength;
			end.setPadStartLeftCount(end.getPadStartLeftCount() + 1);
			// System.out.println("draw left");
		} else {
			
			GeneralPath polly = new GeneralPath();
			polly.moveTo((float) end.getButtonX()
					+ end.getButtonWidth()
					+ (end.getPadStartRightCount() * padWidth) -4 ,
					(float) end
					.getButtonY() + (end.getTextHeight()/2) - (padLength/2)
					+ end.getPadStartRightCount() * padLength
							+ 0.25f);
			polly.lineTo((float) end.getButtonX()
					+ end.getButtonWidth()
					+ (end.getPadStartRightCount() * padWidth),
					(float) end
					.getButtonY() + (end.getTextHeight()/2) - (padLength/2)
					+ end.getPadStartRightCount() * padLength
							+ 3.25f);
			polly.lineTo((float) end.getButtonX()
					+ end.getButtonWidth()
					+ (end.getPadStartRightCount() * padWidth),
					(float) end
					.getButtonY() + (end.getTextHeight()/2) - (padLength/2)
					+ end.getPadStartRightCount() * padLength
							- 2.75f);
			polly.closePath();
			contentGraphics.fill(polly);
			
			contentGraphics.drawLine((int) end.getButtonX()
					+ end.getButtonWidth()
					+ (end.getPadStartRightCount() * padWidth) ,
					(int) end
					.getButtonY() + (end.getTextHeight()/2) - (padLength/2)
					+ end.getPadStartRightCount() * padLength, (int) end.getButtonX()
							+ end.getButtonWidth()
							+ (end.getPadStartRightCount() * padWidth) + 4,
							(int) end
							.getButtonY() + (end.getTextHeight()/2) - (padLength/2)
							+ end.getPadStartRightCount() * padLength);
			testWidthEnd(end, left);
			mEndX = (int) end.getButtonX() + end.getButtonWidth()
					+ (end.getPadStartRightCount() * padWidth) + 4;
			mEndY = (int) end
			.getButtonY() + (end.getTextHeight()/2) - (padLength/2)
			+ end.getPadStartRightCount() * padLength;
			end.setPadStartRightCount(end.getPadStartRightCount() + 1);
		}
		
	}

	private boolean goToLowestLevel(int startLevel, int endLevel,
			boolean left, boolean right, Graphics2D contentGraphics) {

		if (startLevel < endLevel) {
			testWidthStart(lDStart, right);
			//drawOverStart(contentGraphics, right);
			drawLinesStart(contentGraphics, right, false,true);
			for (int j = startLevel; j < endLevel - 1; j++) {
				
				setPrecedingStart();
				lDStart = getSyntaxFacade().getLower(lDStart,
						lDStart.getNumber(), lDStart.getLevel(),
						lDStart.getLevel() + 1, right);
				testWidthStart(lDStart, right);
				//drawOverStart(contentGraphics, right);
				drawLinesStart(contentGraphics, right, false,false);
			}
			setPrecedingStart();
			lDStart = getSyntaxFacade().getLower(lDStart,
					lDStart.getNumber(), lDStart.getLevel(),
					lDStart.getLevel() + 1, right);
			testWidthStart(lDStart, right);
			if (!lDStart.equals(lDEnd)) {
				//drawOverStart(contentGraphics, right);
				drawLinesStart(contentGraphics, right, false,false);
			}
		} else {
			testWidthEnd(lDEnd, left);
			//drawOverEnd(contentGraphics, left);
			drawLinesEnd(contentGraphics, left, false,true);
			for (int j = endLevel; j < startLevel - 1; j++) {
				setPrecedingEnd();
				lDEnd = getSyntaxFacade().getLower(lDEnd,
						lDEnd.getNumber(), lDEnd.getLevel(),
						lDEnd.getLevel() + 1, left);
				testWidthEnd(lDEnd, left);
				//drawOverEnd(contentGraphics, left);
				drawLinesEnd(contentGraphics, left, false,false);
			}
			setPrecedingEnd();
			lDEnd = getSyntaxFacade().getLower(lDEnd, lDEnd.getNumber(),
					lDEnd.getLevel(), lDEnd.getLevel() + 1, left);
			testWidthEnd(lDEnd, left);
			if (!lDStart.equals(lDEnd)) {
				//drawOverEnd(contentGraphics, left);
				drawLinesEnd(contentGraphics, left, false,false);
			}
		}
		if (lDStart.equals(lDEnd)) {
			return true;
		}
		return false;
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


	private void drawLinesStart(Graphics2D contentGraphics, boolean left,
			boolean sync, boolean first) {

		if ((mRightmostStartPreceding <= mLeftmostStart && !sync) 
				|| (mLeftmostStartPreceding >= mRightmostStart && sync)
				) 
		{
			int mX = getX(lDStart,left);
			int mY = getY(lDStart,left);
			System.out.println("x =" + mX);
			System.out.println("y =" + mY);
			System.out.println("start x =" + mStartX);
			System.out.println("start y =" + mStartY);
			System.out.println("START 1");
			if (!first)
			{
			contentGraphics.drawLine(mStartX,
					mStartY, mX, mStartY);
				mStartX = mX;
			}
			contentGraphics.drawLine(mX, mStartY,
					mX, mY);
			mStartY = mY;
		}
		if (mLeftmostStartPreceding >= mRightmostStart && !sync
				|| (mRightmostStartPreceding <= mLeftmostStart && sync) )
		{
			int mX = getX(lDStart,left);
			int mY = getY(lDStart,left);
			System.out.println("x =" + mX);
			System.out.println("y =" + mY);
			System.out.println("start x =" + mStartX);
			System.out.println("start y =" + mStartY);
			System.out.println("START 2");
			if (!first)
			{
			contentGraphics.drawLine(mStartX,
					mY, mX, mY);
			mStartX = mX;
			}
			contentGraphics.drawLine(mStartX, mStartY,
					mStartX, mY);
			mStartY = mY;
		}
	}
	private void drawLinesEnd(Graphics2D contentGraphics, boolean left,
			boolean sync, boolean first) {

		if ((mRightmostEndPreceding <= mLeftmostEnd && !sync)
				|| (mLeftmostEndPreceding >= mRightmostEnd && sync)) {
			int	mX = getX(lDEnd,left);
			int	mY = getY(lDEnd,left);
			System.out.println("x =" + mX);
			System.out.println("y =" + mY);
			System.out.println("end x =" + mEndX);
			System.out.println("end y =" + mEndY);
			System.out.println("END 1");
			
			if (!first)
			{
			contentGraphics.drawLine(mEndX,
						mEndY, mX, mEndY);
			mEndX = mX;
			}
			else
			{
				mX -= 4;
			}
			contentGraphics.drawLine(mX, mEndY,
						mX, mY);
			mEndY = mY;
		}
		else if ((mLeftmostEndPreceding >= mRightmostEnd && !sync)
				|| (mRightmostEndPreceding <= mLeftmostEnd && sync))
		{
			int	mX = getX(lDEnd,left);
			int	mY = getY(lDEnd,left);
			System.out.println("x =" + mX);
			System.out.println("y =" + mY);
			System.out.println("end x =" + mEndX);
			System.out.println("end y =" + mEndY);
			System.out.println("END 2");
			if (!first)
			{
			contentGraphics.drawLine(mEndX,
						mY, mX, mY);
			mEndX = mX;
			}
			else
			{
				System.out.println("first X");
			}
			contentGraphics.drawLine(mEndX, mEndY,
						mEndX, mY);
			mEndY = mY;
			
		}
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
		// System.out.println(uAStart.getLevel() + " : " +
		// uAEnd.getLevel());
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

