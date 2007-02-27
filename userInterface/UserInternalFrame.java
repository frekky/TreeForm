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

		private int mHeightStart;

		private int mLeftmostEnd;

		private int mRightmostEnd;

		private int mHeightEnd;

		private int mLeftmostStartLast;

		private int mRightmostStartLast;

		private int mHeightStartLast;

		private int mLeftmostEndLast;

		private int mRightmostEndLast;

		private int mHeightEndLast;

		private int mLeftmostStartPreceding;

		private int mRightmostStartPreceding;

		private int mHeightStartPreceding;

		private int mLeftmostEndPreceding;

		private int mRightmostEndPreceding;

		private int mHeightEndPreceding;

		public void paint(Graphics G) {
			super.paint(G);
			displayMovement(G);
			System.out.println("");
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
			drawEnd(end, right, contentGraphics);
			boolean done = goToLowestLevel(start.getLevel(), end.getLevel(),
					left,right, contentGraphics);
			if (!done) {
				mStart = lDStart;
				mEnd = lDEnd;
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
					setWidthStart(lDStart, left);
					setWidthEnd(lDEnd, right);
					drawOverStart(contentGraphics, left);
					drawLinesStart(contentGraphics, left, false);
					drawOverEnd(contentGraphics, right);
					drawLinesEnd(contentGraphics, right, false);
					mStart = lDStart;
					mEnd = lDEnd;
					lDStart = getSyntaxFacade().getLower(lDStart,
							lDStart.getNumber(), lDStart.getLevel(),
							lDStart.getLevel() + 1, right);
					lDEnd = getSyntaxFacade().getLower(lDEnd,
							lDEnd.getNumber(), lDEnd.getLevel(),
							lDEnd.getLevel() + 1, left);
				}
				drawOverStart(contentGraphics, left);
				drawLinesStart(contentGraphics, left, true);
				drawOverEnd(contentGraphics, right);
				drawLinesEnd(contentGraphics, right, true);
			} else {
				if (start.getLevel() - end.getLevel() < 0) {
					drawOverStart(contentGraphics, left);
				} else {
					drawOverEnd(contentGraphics, right);
				}
				drawEasy(contentGraphics, left);
			}
		}

		private void drawEasy(Graphics2D contentGraphics, boolean left) {
			if (!left) {
				if (lDStart.equals(mStart)) {
					contentGraphics.drawLine(mLeftmostEndLast, mHeightEndLast,
							mLeftmostStart, mHeightEndLast);
					contentGraphics.drawLine(mLeftmostStart, mHeightEndLast,
							mLeftmostStart, mHeightStart);
				}
				if (lDEnd.equals(mEnd)) {

					contentGraphics.drawLine(mLeftmostStartLast,
							mHeightStartLast, mLeftmostEnd, mHeightStartLast);
					contentGraphics.drawLine(mLeftmostEnd, mHeightStartLast,
							mLeftmostEnd, mHeightEnd);
				}
			} else {
				if (lDStart.equals(mStart)) {
					contentGraphics.drawLine(mRightmostEndLast, mHeightEndLast,
							mRightmostStart, mHeightEndLast);
					contentGraphics.drawLine(mRightmostStart, mHeightEndLast,
							mRightmostStart, mHeightStart);
				}
				if (lDEnd.equals(mEnd)) {
					contentGraphics.drawLine(mRightmostStartLast,
							mHeightStartLast, mRightmostEnd, mHeightStartLast);
					contentGraphics.drawLine(mRightmostEnd, mHeightStartLast,
							mRightmostEnd, mHeightEnd);
				}
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
				setWidthStart(start, left);
				mRightmostStart = (int) start.getButtonX()
						- start.getPadStartLeftCount() * padWidth - 4;
				mHeightStart = (int) start
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
				setWidthStart(start, left);
				mLeftmostStart = (int) start.getButtonX()
						+ start.getButtonWidth()
						+ (start.getPadStartRightCount() * padWidth) + 4;
				mHeightStart = (int) (start.getButtonY() + (start.getTextHeight()/2) - (padLength/2)
				+ start.getPadStartRightCount() * padLength);
				start.setPadStartRightCount(start.getPadStartRightCount() + 1);
			}
			mHeightStartLast = mHeightStart;
			mRightmostStartLast = mRightmostStart;
			mLeftmostStartLast = mLeftmostStart;
		}

		private void setWidthStart(SyntacticStructure start, boolean left) {
			mLeftmostStartPreceding = mLeftmostStart;
			mRightmostStartPreceding = mRightmostStart;
			mHeightStartPreceding = mHeightStart;
			if (left) {
				mRightmostStart = (int) start.getButtonX();
				if (start.getAbsoluteOrder() > 0) {
					SyntacticStructure w = (SyntacticStructure) ((LinkedList) getSyntaxFacade()
							.getLinkedArray().get(start.getLevel())).get(start
							.getAbsoluteOrder() - 1);
					mLeftmostStart = (int) (w.getButtonX() + w.getButtonWidth());
				} else {
					mLeftmostStart = (int) getSyntaxFacade().getLeftShift();
				}
			} else {
				mLeftmostStart = (int) start.getButtonX()
						+ start.getButtonWidth();
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
			mHeightStart = (int) lDStart.getButtonY()
					+ lDStart.getButtonHeight();
			System.out.println(mHeightStart);
		}

		private void setWidthEnd(SyntacticStructure start, boolean left) {
			mLeftmostEndPreceding = mLeftmostEnd;
			mRightmostEndPreceding = mRightmostEnd;
			mHeightEndPreceding = mHeightEnd;
			if (left) {
				mRightmostEnd = (int) start.getButtonX();
				if (start.getAbsoluteOrder() > 0) {
					SyntacticStructure w = (SyntacticStructure) ((LinkedList) getSyntaxFacade()
							.getLinkedArray().get(start.getLevel())).get(start
							.getAbsoluteOrder() - 1);
					mLeftmostEnd = (int) (w.getButtonX() + w.getButtonWidth());
				} else {
					mLeftmostEnd = (int) getSyntaxFacade().getLeftShift();
				}
			} else {
				mLeftmostEnd = (int) start.getButtonX()
						+ start.getButtonWidth();
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
			mHeightEnd = (int) lDEnd.getButtonY() + lDEnd.getButtonHeight();
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
						- end.getPadStartLeftCount() * padWidth, 	 (int) end
						.getButtonY() + (end.getTextHeight()/2) - (padLength/2)
						+ end.getPadStartLeftCount() * padLength, (int) end
						.getButtonX()
						- (end.getPadStartLeftCount() * padWidth) - 4,
						 (int) end
							.getButtonY() + (end.getTextHeight()/2) - (padLength/2)
							+ end.getPadStartLeftCount() * padLength);
				setWidthEnd(end, left);
				mRightmostEnd = (int) end.getButtonX()
						- end.getPadStartLeftCount() * padWidth - 4;
				mHeightEnd = (int) end
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
				setWidthEnd(end, left);
				mLeftmostEnd = (int) end.getButtonX() + end.getButtonWidth()
						+ (end.getPadStartRightCount() * padWidth) + 4;
				mHeightEnd = (int) end
				.getButtonY() + (end.getTextHeight()/2) - (padLength/2)
				+ end.getPadStartRightCount() * padLength;
				end.setPadStartRightCount(end.getPadStartRightCount() + 1);
			}
			mHeightEndLast = mHeightEnd;
			mRightmostEndLast = mRightmostEnd;
			mLeftmostEndLast = mLeftmostEnd;
		}

		private boolean goToLowestLevel(int startLevel, int endLevel,
				boolean left, boolean right, Graphics2D contentGraphics) {

			if (startLevel < endLevel) {
				if (right) {
					for (int j = startLevel; j < endLevel - 1; j++) {
						lDStart = getSyntaxFacade().getLower(lDStart,
								lDStart.getNumber(), lDStart.getLevel(),
								lDStart.getLevel() + 1, left);
						setWidthStart(lDStart, left);
						drawOverStart(contentGraphics, left);
						drawLinesStart(contentGraphics, left, false);
					}

				} else {
					for (int j = startLevel; j < endLevel - 1; j++) {
						lDStart = getSyntaxFacade().getLower(lDStart,
								lDStart.getNumber(), lDStart.getLevel(),
								lDStart.getLevel() + 1, left);
						setWidthStart(lDStart, left);
						drawOverStart(contentGraphics, left);
						drawLinesStart(contentGraphics, left, false);
					}
				}
				lDStart = getSyntaxFacade().getLower(lDStart,
						lDStart.getNumber(), lDStart.getLevel(),
						lDStart.getLevel() + 1, left);
				setWidthStart(lDStart, left);
				if (!lDStart.equals(lDEnd)) {
					drawOverStart(contentGraphics, left);
					drawLinesStart(contentGraphics, left, false);
				}
			} else {
				if (left) {
					for (int j = endLevel; j < startLevel - 1; j++) {
						lDEnd = getSyntaxFacade().getLower(lDEnd,
								lDEnd.getNumber(), lDEnd.getLevel(),
								lDEnd.getLevel() + 1, left);
						setWidthEnd(lDEnd, left);
						drawOverEnd(contentGraphics, left);
						drawLinesEnd(contentGraphics, left, false);
					}

				} else {
					for (int j = endLevel; j < startLevel - 1; j++) {
						lDEnd = getSyntaxFacade().getLower(lDEnd,
								lDEnd.getNumber(), lDEnd.getLevel(),
								lDEnd.getLevel() + 1, left);
						setWidthEnd(lDEnd, left);
						drawOverEnd(contentGraphics, left);
						drawLinesEnd(contentGraphics, left, false);
					}
				}
				lDEnd = getSyntaxFacade().getLower(lDEnd, lDEnd.getNumber(),
						lDEnd.getLevel(), lDEnd.getLevel() + 1, left);
				setWidthEnd(lDEnd, left);
				if (!lDStart.equals(lDEnd)) {
					drawOverEnd(contentGraphics, left);
					drawLinesEnd(contentGraphics, left, false);
				}
			}
			// System.out.println("start = " + lDStart.getPreorder());
			// System.out.println("end = " + lDEnd.getPreorder());
			if (lDStart.equals(lDEnd)) {
				// System.out.println("true");
				return true;
			}
			System.out.println("lDStart = " + lDStart.getPreorder());
			System.out.println("lDEnd = " + lDEnd.getPreorder());
			if (left) {
				if (lDStart.getAbsoluteOrder() > 0) {
					SyntacticStructure w = (SyntacticStructure) ((LinkedList) getSyntaxFacade()
							.getLinkedArray().get(lDStart.getLevel()))
							.get(lDStart.getAbsoluteOrder()-1);
					if(w.equals(lDEnd))
					{
						return true;
					}
				}
			} else {
				if (lDStart.getAbsoluteOrder() < ((LinkedList) getSyntaxFacade()
						.getLinkedArray().get(lDStart.getLevel())).size() - 1) {
					SyntacticStructure w = (SyntacticStructure) ((LinkedList) getSyntaxFacade()
							.getLinkedArray().get(lDStart.getLevel()))
							.get(lDStart.getAbsoluteOrder()+1);
					if(w.equals(lDEnd))
					{
						return true;
					}
				}
			}
			return false;
		}

		private void drawOverStart(Graphics2D contentGraphics, boolean left) {
			// System.out.println("mRightmostStart = " + mRightmostStart);
			// System.out.println("mRightmostStartPreceding = " +
			// mRightmostStartPreceding);
			// System.out.println("mLeftmostStart = " + mLeftmostStart);
			// System.out.println("mLeftmostStartPreceding = " +
			// mLeftmostStartPreceding);
			// System.out.println("left = " + left);
			// System.out.println("");
			if (left) {
				if (mRightmostStart > mRightmostStartPreceding) {
					contentGraphics.drawLine(mRightmostStartLast,
							mHeightStartLast, mRightmostStartPreceding,
							mHeightStartLast);
					contentGraphics.drawLine(mRightmostStartPreceding,
							mHeightStartLast, mRightmostStartPreceding,
							mHeightStartPreceding);
					mRightmostStartLast = mRightmostStartPreceding;
					mLeftmostStartLast = mLeftmostStartPreceding;
					mHeightStartLast = mHeightStartPreceding;
				}
			} else {
				if (mLeftmostStart < mLeftmostStartPreceding) {
					contentGraphics.drawLine(mLeftmostStartLast,
							mHeightStartLast, mLeftmostStartPreceding,
							mHeightStartLast);
					contentGraphics.drawLine(mLeftmostStartPreceding,
							mHeightStartLast, mLeftmostStartPreceding,
							mHeightStartPreceding);
					mRightmostStartLast = mRightmostStartPreceding;
					mLeftmostStartLast = mLeftmostStartPreceding;
					mHeightStartLast = mHeightStartPreceding;
				}
			}
		}

		private void drawLinesStart(Graphics2D contentGraphics, boolean left,
				boolean override) {

			if (mLeftmostStart >= mRightmostStart || override) {
				if (left) {

					contentGraphics
							.drawLine(mRightmostStartLast, mHeightStartLast,
									mRightmostStart, mHeightStartLast);
					contentGraphics.drawLine(mRightmostStart, mHeightStartLast,
							mRightmostStart, mHeightStart);
					mLeftmostStartLast = mLeftmostStart;
					mRightmostStartLast = mRightmostStart;
					mHeightStartLast = mHeightStart;

				} else {
					contentGraphics.drawLine(mLeftmostStartLast,
							mHeightStartLast, mLeftmostStart, mHeightStartLast);
					contentGraphics.drawLine(mLeftmostStart, mHeightStartLast,
							mLeftmostStart, mHeightStart);
					mLeftmostStartLast = mLeftmostStart;
					mRightmostStartLast = mRightmostStart;
					mHeightStartLast = mHeightStart;
				}
			}
		}

		private void drawOverEnd(Graphics2D contentGraphics, boolean left) {
			// System.out.println("mRightmostEnd = " + mRightmostEnd);
			// System.out.println("mRightmostEndPreceding = " +
			// mRightmostEndPreceding);
			// System.out.println("mLeftmostEnd = " + mLeftmostEnd);
			// System.out.println("mLeftmostEndPreceding = " +
			// mLeftmostEndPreceding);
			// System.out.println("left = " + left);
			// System.out.println("");
			if (left) {
				if (mRightmostEnd > mRightmostEndPreceding) {
					contentGraphics.drawLine(mRightmostEndLast, mHeightEndLast,
							mRightmostEndPreceding, mHeightEndLast);
					contentGraphics.drawLine(mRightmostEndPreceding,
							mHeightEndLast, mRightmostEndPreceding,
							mHeightEndPreceding);
					mRightmostEndLast = mRightmostEndPreceding;
					mLeftmostEndLast = mLeftmostEndPreceding;
					mHeightEndLast = mHeightEndPreceding;
				}
			} else {
				if (mLeftmostEnd < mLeftmostEndPreceding) {
					contentGraphics.drawLine(mLeftmostEndLast, mHeightEndLast,
							mLeftmostEndPreceding, mHeightEndLast);
					contentGraphics.drawLine(mLeftmostEndPreceding,
							mHeightEndLast, mLeftmostEndPreceding,
							mHeightEndPreceding);
					mRightmostEndLast = mRightmostEndPreceding;
					mLeftmostEndLast = mLeftmostEndPreceding;
					mHeightEndLast = mHeightEndPreceding;
				}
			}
		}

		private void drawLinesEnd(Graphics2D contentGraphics, boolean left,
				boolean override) {

			if (mLeftmostEnd >= mRightmostEnd || override) {
				if (left) {
					contentGraphics.drawLine(mRightmostEndLast, mHeightEndLast,
							mRightmostEnd, mHeightEndLast);
					contentGraphics.drawLine(mRightmostEnd, mHeightEndLast,
							mRightmostEnd, mHeightEnd);
					mLeftmostEndLast = mLeftmostEnd;
					mRightmostEndLast = mRightmostEnd;
					mHeightEndLast = mHeightEnd;
				} else {
					contentGraphics.drawLine(mLeftmostEndLast, mHeightEndLast,
							mLeftmostEnd, mHeightEndLast);
					contentGraphics.drawLine(mLeftmostEnd, mHeightEndLast,
							mLeftmostEnd, mHeightEnd);
					mLeftmostEndLast = mLeftmostEnd;
					mRightmostEndLast = mRightmostEnd;
					mHeightEndLast = mHeightEnd;
				}
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
