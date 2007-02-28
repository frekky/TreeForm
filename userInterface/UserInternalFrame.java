//TreeForm Syntax Tree Drawing Software
//Copyright (C) 2006  Donald Derrick

//This program is free software; you can redistribute it and/or
//modify it under the terms of the GNU General Public License
//as published by the Free Software Foundation; either version 2
//of the License, or (at your option) any later version.

//This program is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.

//You should have received a copy of the GNU General Public License
//along with this program; if not, write to the Free Software
//Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
//package userInterface;

package userInterface;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;
import java.util.LinkedList;
import java.util.Map;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;

import staticFunctions.Sizer;
import syntaxTree.SyntacticStructure;
import syntaxTree.SyntaxFacade;
import enumerators.SaveFileType;
import enumerators.SyntacticViewLayout;

/**
 * 
 * @author Donald Derrick
 * @version 0.1 <br>
 *          date: 19-Aug-2004 <br>
 *          <br>
 * 
 */
public class UserInternalFrame extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int mMinWidth;

	private int mMinHeight;

	/**
	 * 
	 * @uml.property name="mSaveFileType"
	 * @uml.associationEnd
	 * @uml.property name="mSaveFileType" multiplicity="(1 1)"
	 */
	private SaveFileType mSaveFileType;

	/**
	 * 
	 * @uml.property name="mSyntaxFacade"
	 * @uml.associationEnd
	 * @uml.property name="mSyntaxFacade" multiplicity="(1 1)"
	 *               inverse="mUIF:syntaxTree.SyntaxFacade"
	 */
	private SyntaxFacade mSyntaxFacade;

	/**
	 * 
	 * @uml.property name="mSyntacticViewLayout"
	 * @uml.associationEnd
	 * @uml.property name="mSyntacticViewLayout" multiplicity="(1 1)"
	 */
	private SyntacticViewLayout mSyntacticViewLayout;

	/**
	 * 
	 * @uml.property name="mUserFrame"
	 * @uml.associationEnd
	 * @uml.property name="mUserFrame" multiplicity="(1 1)"
	 */
	private UserFrame mUserFrame;

	private float mScale;

	/**
	 * 
	 * @param pUserFrame
	 * @param pCount
	 * 
	 * Sets up the UserInternalFrame - including the default document name and
	 * the UserFrame. Please note that the UserInternalFrame acts as the Facade
	 * interface to the SyntaxFacade. While not strictly the correct way to do
	 * things, it's not bad because the UserInternalFrame doesn't do much - so
	 * using it as a Facade is not overly confusing.
	 */
	public UserInternalFrame(UserFrame pUserFrame, int pCount) {
		super("Document #" + pCount, true, // resizable
				true, // closable
				true, // maximizable
				true);// iconifiable
		mUserFrame = pUserFrame;
		mSyntaxFacade = new SyntaxFacade(this);
		mSaveFileType = SaveFileType.XML;
		// ...Create the GUI and put it in the window...

		// ...Then set the window size or call pack...
		setSize(mUserFrame.getDesktopPane().getSize());
		mSyntacticViewLayout = SyntacticViewLayout.D_STRUCTURE;
		this.setScale(1.0F);
		setMinHeight(this.getHeight());
		setMinWidth(this.getWidth());
		this.setContentPane(new UserContentPane());

	}

	public class UserContentPane extends Container {

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

		public void paint(Graphics G) {
			super.paint(G);
			displayMovement(G);
			//System.out.println("");
		}

		private void displayMovement(Graphics G) {
			if (getSyntaxFacade().getSentence().getChildren().size() > 0) {
				SyntacticStructure mR = (SyntacticStructure) getSyntaxFacade()
						.getSentence().getChildren().get(0);
				Graphics2D lGraphics2D = (Graphics2D) G;
				lGraphics2D.scale(Sizer.scaleWidth() * getScale(), Sizer
						.scaleHeight()
						* getScale());
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

				lDStart = getSyntaxFacade().getLower(lDStart,
						lDStart.getNumber(), lDStart.getLevel(),
						lDStart.getLevel() + 1, right);
				lDEnd = getSyntaxFacade().getLower(lDEnd, lDEnd.getNumber(),
						lDEnd.getLevel(), lDEnd.getLevel() + 1, left);
				while (!(lDStart == null
						|| lDEnd == null
						|| (left && lDStart.getAbsoluteOrder() < lDEnd
								.getAbsoluteOrder()) || (!left && lDStart
						.getAbsoluteOrder() > lDEnd.getAbsoluteOrder()))) {
					setPrecedingStart();
					setPrecedingEnd();
					testWidthStart(lDStart, left);
					testWidthEnd(lDEnd, right);
					drawLinesStart(contentGraphics, left, false,false);
					drawLinesEnd(contentGraphics, right, false,false);
					lDStart = getSyntaxFacade().getLower(lDStart,
							lDStart.getNumber(), lDStart.getLevel(),
							lDStart.getLevel() + 1, right);
					lDEnd = getSyntaxFacade().getLower(lDEnd,
							lDEnd.getNumber(), lDEnd.getLevel(),
							lDEnd.getLevel() + 1, left);
				}
//				drawLinesStart(contentGraphics, left, true);
//				drawLinesEnd(contentGraphics, right, true);
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
							/ Sizer.scaleWidth() / getScale()) + 1);
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
							/ Sizer.scaleWidth() / getScale()) + 1);
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
				boolean override, boolean first) {

			if (mRightmostStartPreceding <= mLeftmostStart) 
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
			else if (mLeftmostStartPreceding >= mRightmostStart)
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
				boolean override, boolean first) {

			if (mRightmostEndPreceding <= mLeftmostEnd) {
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
			else if (mLeftmostEndPreceding >= mRightmostEnd)
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
	}

	/**
	 * 
	 * @return returns the map of all the attributes of a given character at the
	 *         position of the selected index of the selected syntactic object.
	 *         These attributes include font information, underline, and
	 *         sub/superscript information.
	 */
	public Map getAttributes() {
		return mUserFrame.getUserControl().getAttributes();
	}

	/**
	 * 
	 * @return returns the SyntacticViewlayout (code not complete)
	 */
	public SyntacticViewLayout getSyntacticViewLayout() {
		return mSyntacticViewLayout;
	}

	/**
	 * 
	 * @param pSvl
	 *            sets the SyntacticViewLayout.
	 */
	public void setSyntacticViewLayout(SyntacticViewLayout pSvl) {
		mSyntacticViewLayout = pSvl;
	}

	/**
	 * 
	 * @return Returns the SyntaxFacade
	 */
	public SyntaxFacade getSyntaxFacade() {
		return mSyntaxFacade;
	}

	/**
	 * @return Returns the SaveFileType
	 */
	public SaveFileType getSaveFileType() {
		return mSaveFileType;
	}

	/**
	 * 
	 * @param pSaveFileType
	 *            sets the SaveFileType
	 */
	public void setSaveFileType(SaveFileType pSaveFileType) {
		mSaveFileType = pSaveFileType;
	}

	/**
	 * 
	 * @param pScale
	 *            set the Zoom scale
	 */
	public void setScale(float pScale) {
		mScale = pScale;
	}

	/**
	 * 
	 * @return Returns the Zoom Scale
	 */
	public float getScale() {
		return mScale;
	}

	/**
	 * 
	 * @return Returns the ObservableClipboard containing the selected syntactic
	 *         object (NOT the same as the selected text!)
	 */
	public ObservableClipboard getObservableClipboard() {
		return mUserFrame.getObservableClipboard();
	}

	/**
	 * 
	 * @return Returns the ObservableFont containint the selected font
	 */
	public ObservableFont getObservableFont() {
		return mUserFrame.getObservableFont();
	}

	/**
	 * 
	 * @return Returns the ObservableFontSize containing the font size
	 */
	public ObservableFontSize getObservableFontSize() {
		return mUserFrame.getObservableFontSize();
	}

	/**
	 * 
	 * @return Returns the ObservableFontBold containing the font style
	 */
	public ObservableFontBold getObservableFontBold() {
		return mUserFrame.getObservableFontBold();
	}

	/**
	 * 
	 * @return Returns the ObservableFontBold containing the font style
	 */
	public ObservableFontItalic getObservableFontItalic() {
		return mUserFrame.getObservableFontItalic();
	}

	/**
	 * 
	 * @return Returns the ObservableFontBold containing the font underline
	 *         feature
	 */
	public ObservableFontUnderline getObservableFontUnderline() {
		return mUserFrame.getObservableFontUnderline();
	}

	/**
	 * 
	 * @return Returns the ObservableFontBold containing the font subscript
	 *         feature
	 */
	public ObservableFontSubscript getObservableFontSubscript() {
		return mUserFrame.getObservableSubscript();
	}

	/**
	 * 
	 * @return Returns the ObservableFontBold containing the font superscript
	 *         feature
	 */
	public ObservableFontSuperscript getObservableFontSuperscript() {
		return mUserFrame.getObservableSuperscript();
	}

	/**
	 * 
	 * @param pSS
	 *            the parent
	 * @param pSSChild
	 *            the subtree <br>
	 *            <br>
	 *            activates the glass pane - needed for repositioning trees
	 */
	public void activateGlassPane(SyntacticStructure pSS,
			SyntacticStructure pSSChild) {
		UserGlassPane lUGP = new UserGlassPane(mUserFrame, pSS, pSSChild);
		lUGP.setSyntacticStructure(pSS);
		ListenerGlassPane lListenerGlassPane = new ListenerGlassPane(mUserFrame);
		lUGP.addMouseListener(lListenerGlassPane);
		lUGP.addMouseMotionListener(lListenerGlassPane);
		this.setGlassPane(lUGP);
		this.getGlassPane().setVisible(true);
	}

	/**
	 * turns off the glass pane - needed to continue editing trees.
	 * 
	 */
	public void deactivateGlassPane() {
		this.setGlassPane(new JLabel());
		this.getGlassPane().setVisible(false);
	}

	/**
	 * 
	 * @return Returns the User Frame
	 */
	public UserFrame getUserFrame() {
		return mUserFrame;
	}

	/**
	 * 
	 * @param mMinWidth
	 *            sets the minimum width - needed for auto-sizing
	 */
	public void setMinWidth(int mMinWidth) {
		this.mMinWidth = mMinWidth;
	}

	/**
	 * 
	 * @return gets the minimum width - needed for auto-sizing
	 */
	public int getMinWidth() {
		return mMinWidth;
	}

	/**
	 * 
	 * @param mMinHeight
	 *            sets the minimum height - needed for auto-sizing.
	 */
	public void setMinHeight(int mMinHeight) {
		this.mMinHeight = mMinHeight;
	}

	/**
	 * 
	 * @return sets the minimum height - needed for auto-sizing.
	 */
	public int getMinHeight() {
		return mMinHeight;
	}

}
