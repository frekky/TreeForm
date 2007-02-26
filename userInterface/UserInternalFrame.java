
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
 * @version 0.1
 * <br>
 * date: 19-Aug-2004
 * <br>
 * <br>
 *
 */
public class UserInternalFrame extends JInternalFrame{

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
	 * @uml.property name="mSyntaxFacade" multiplicity="(1 1)" inverse="mUIF:syntaxTree.SyntaxFacade"
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
	 * Sets up the UserInternalFrame - including the default document name and the
	 * UserFrame.  Please note that the UserInternalFrame acts as the Facade interface to the 
	 * SyntaxFacade.  While not strictly the correct way to do things, it's not bad because
	 * the UserInternalFrame doesn't do much - so using it as a Facade is not overly
	 * confusing.
	 */
	public UserInternalFrame (UserFrame pUserFrame, int pCount) {
		super("Document #" + pCount, 
				true, //resizable
				true, //closable
				true, //maximizable
				true);//iconifiable
		mUserFrame = pUserFrame;
		mSyntaxFacade = new SyntaxFacade(this);
		mSaveFileType = SaveFileType.XML;
		//...Create the GUI and put it in the window...

		//...Then set the window size or call pack...
		setSize(mUserFrame.getDesktopPane().getSize());
		mSyntacticViewLayout = SyntacticViewLayout.D_STRUCTURE;
		this.setScale( 1.0F);
		setMinHeight(this.getHeight());
		setMinWidth(this.getWidth());
		this.setContentPane(new UserContentPane());

	}
	public class UserContentPane extends Container{

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
		private int mLeftmostStartPrevious;
		private int mRightmostStartPrevious;
		private int mHeightStartPrevious;
		private int mLeftmostEndPrevious;
		private int mRightmostEndPrevious;
		private int mHeightEndPrevious;
		public void paint(Graphics G)
		{
			super.paint(G);
			displayMovement(G);
		}
		private void displayMovement(Graphics G) {
			if (getSyntaxFacade().getSentence().getChildren().size()>0)
			{
				SyntacticStructure mR = (SyntacticStructure) getSyntaxFacade().getSentence().getChildren().get(0);
				Graphics2D lGraphics2D = (Graphics2D) G;
				lGraphics2D.scale(Sizer.scaleWidth() * getScale(), Sizer.scaleHeight() * getScale());
				//set the font		
				// Set the g2D to antialias.
				lGraphics2D.setColor(Color.BLACK);
				lGraphics2D.setRenderingHint(
						RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
				lGraphics2D.setRenderingHint(
						RenderingHints.KEY_RENDERING,
						RenderingHints.VALUE_RENDER_QUALITY);
				lGraphics2D.setStroke(new BasicStroke(.5F));
				resetCounters(mR);
				displayMovementRecursive(mR,lGraphics2D);
			}
		}
		private void resetCounters(SyntacticStructure start)
		{
			start.setPadBottomCount(0);
			start.setPadLeftCount(0);
			start.setPadRightCount(0);
			start.setPadStartLeftCount(0);
			start.setPadStartRightCount(0);
			for(int i = 0; i < start.getChildren().size();i++)
			{
				SyntacticStructure w = (SyntacticStructure) start.getChildren().get(i);
				resetCounters(w);
			}
		}
		private void displayMovementRecursive(SyntacticStructure start, Graphics2D contentGraphics) {

			for(int i = 0; i < start.getStartTrace().size();i++)
			{
				SyntacticStructure end = (SyntacticStructure) start.getStartTrace().get(i);
				paintMovement(start,end,contentGraphics);
			}
			for(int i = 0; i < start.getChildren().size();i++)
			{
				SyntacticStructure w = (SyntacticStructure) start.getChildren().get(i);
				displayMovementRecursive(w,contentGraphics);
			}

		}

		private void paintMovement(SyntacticStructure start, SyntacticStructure end, Graphics2D contentGraphics) {
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
			if (common)
			{
				left = getSyntaxFacade().checkInsideDirection(start,end,start.getLevel(), end.getLevel());
			}
			else
			{	
				getHighestUncommonAncestors(uAStart,uAEnd);
				left = checkOutsideDirection(uAStart,uAEnd);
			}
			right = left;
			drawStart(start,left,contentGraphics);
			drawEnd(end,right,contentGraphics);
			boolean done = goToLowestLevel(start.getLevel(),end.getLevel(),left, contentGraphics);
			if (!done)
			{
				if (!common)
				{
					right = !left;
				}
				mStart = lDStart;
				mEnd = lDEnd;
				lDStart = getSyntaxFacade().getLower(lDStart, lDStart.getNumber(), lDStart.getLevel(), lDStart.getLevel()+1, right);
				lDEnd = getSyntaxFacade().getLower(lDEnd, lDEnd.getNumber(),lDEnd.getLevel(),lDEnd.getLevel()+1,left);
				//System.out.println("second start down at level = " + lDStart.getPreorder());
				//System.out.println("second end down at level = " + lDEnd.getPreorder());
				while (!(lDStart == null || lDEnd == null 
						|| (left && lDStart.getAbsoluteOrder() < lDEnd.getAbsoluteOrder())
						|| (!left && lDStart.getAbsoluteOrder() > lDEnd.getAbsoluteOrder())))
				{
					//System.out.println(lDStart + " : " + lDEnd);
					if (left)
					{
						// TODO 
					}
					else
					{
						// TODO
					}
					if (right)
					{
						// TODO
					}
					else
					{
						// TODO
					}
					mStart = lDStart;
					mEnd = lDEnd;
					lDStart = getSyntaxFacade().getLower(lDStart, lDStart.getNumber(), lDStart.getLevel(), lDStart.getLevel()+1, right);
					lDEnd = getSyntaxFacade().getLower(lDEnd, lDEnd.getNumber(),lDEnd.getLevel(),lDEnd.getLevel()+1,left);
				}
			}
			else
			{
				drawLinesStart(contentGraphics,true);
				drawLinesEnd(contentGraphics,true);
			}
		}
		private void drawStart(SyntacticStructure start, boolean left, Graphics2D contentGraphics) {
			if (left)
			{
				GeneralPath polly = new GeneralPath();
				polly.moveTo((float)start.getButtonX() - start.getPadStartLeftCount() *padWidth +3,
						 (float)start.getButtonY() + start.getPadStartLeftCount() *padLength+2.25f);
				polly.lineTo((float)start.getButtonX() - start.getPadStartLeftCount() *padWidth
						,  (float)start.getButtonY() + (start.getPadStartLeftCount() *padLength) +5.25f);
				polly.lineTo((float)start.getButtonX() -start.getPadStartLeftCount() *padWidth 
						, (float) start.getButtonY() + (start.getPadStartLeftCount() *padLength) -0.75f);
				polly.closePath();
				contentGraphics.fill(polly);
				
				contentGraphics.drawLine((int)start.getButtonX() - start.getPadStartLeftCount() *padWidth ,
						(int)start.getButtonY() + (start.getPadStartLeftCount() *padLength) + 2,
						(int)start.getButtonX() - (start.getPadStartLeftCount() *padWidth) - 4,
						(int)start.getButtonY() + (start.getPadStartLeftCount() *padLength) + 2);
				setWidthStart(start,left);
				mHeightStart = (int)start.getButtonY() + (start.getPadStartLeftCount() *padLength) + 2;	
				start.setPadStartLeftCount(start.getPadStartLeftCount()+1);
			}
			else
			{
				GeneralPath polly = new GeneralPath();
				polly.moveTo((float)start.getButtonX() + start.getButtonWidth() + (start.getPadStartRightCount() *padWidth) +2,
						(float) start.getButtonY() + start.getPadStartRightCount() *padLength + 2.25f);
				polly.lineTo((float)start.getButtonX() + start.getButtonWidth() + (start.getPadStartRightCount() *padWidth) +6
						, (float) start.getButtonY() + (start.getPadStartRightCount() *padLength) +5.25f);
				polly.lineTo((float)start.getButtonX() + start.getButtonWidth() + (start.getPadStartRightCount() *padWidth) +6
						, (float) start.getButtonY() + (start.getPadStartRightCount() *padLength) -0.75f);
				polly.closePath();
				contentGraphics.fill(polly);
				
				contentGraphics.drawLine((int)start.getButtonX() + start.getButtonWidth() + (start.getPadStartRightCount() *padWidth) +4,
						(int)start.getButtonY() + start.getPadStartRightCount() *padLength + 2,
						(int)start.getButtonX() + start.getButtonWidth() + (start.getPadStartRightCount() *padWidth) + 8,
						(int)start.getButtonY() + start.getPadStartRightCount() *padLength + 2);
				setWidthStart(start,left);
				mHeightStart = (int)start.getButtonY() + (start.getPadStartRightCount() *padLength) + 2;
				start.setPadStartRightCount(start.getPadStartRightCount()+1);
			}
			mHeightStartPrevious = mHeightStart;
			mRightmostStartPrevious = mRightmostStart;
			mLeftmostStartPrevious = mLeftmostStart;
		}
		private void setWidthStart(SyntacticStructure start, boolean left) {
			if (left)
			{
				mRightmostStart = (int) start.getButtonX();
				if (start.getAbsoluteOrder() > 0)
				{
					SyntacticStructure w = (SyntacticStructure) ((LinkedList) getSyntaxFacade().getLinkedArray().get(start.getLevel())).get(start.getAbsoluteOrder()-1);
					mLeftmostStart = (int) (w.getButtonX() + w.getButtonWidth());
				}
				else
				{
					mLeftmostStart = (int) getSyntaxFacade().getLeftShift();
				}
			}
			else
			{
				mLeftmostStart = (int) start.getButtonX() + start.getButtonWidth();
				if (start.getAbsoluteOrder() < ((LinkedList) getSyntaxFacade().getLinkedArray().get(start.getLevel())).size()-1)
				{
					SyntacticStructure w = (SyntacticStructure) ((LinkedList) getSyntaxFacade().getLinkedArray().get(start.getLevel())).get(start.getAbsoluteOrder()+1);
					mRightmostStart = (int) (w.getButtonX());
				}
				else
				{
					mRightmostStart = (int) getSyntaxFacade().getRightShift();
				}
			}
		}
		private void setWidthEnd(SyntacticStructure start, boolean left) {
			if (left)
			{
				mRightmostEnd = (int) start.getButtonX();
				if (start.getAbsoluteOrder() > 0)
				{
					SyntacticStructure w = (SyntacticStructure) ((LinkedList) getSyntaxFacade().getLinkedArray().get(start.getLevel())).get(start.getAbsoluteOrder()-1);
					mLeftmostEnd = (int) (w.getButtonX() + w.getButtonWidth());
				}
				else
				{
					mLeftmostEnd = (int) getSyntaxFacade().getLeftShift();
				}
			}
			else
			{
				mLeftmostEnd = (int) start.getButtonX() + start.getButtonWidth();
				if (start.getAbsoluteOrder() < ((LinkedList) getSyntaxFacade().getLinkedArray().get(start.getLevel())).size()-1)
				{
					SyntacticStructure w = (SyntacticStructure) ((LinkedList) getSyntaxFacade().getLinkedArray().get(start.getLevel())).get(start.getAbsoluteOrder()+1);
					mRightmostEnd = (int) (w.getButtonX());
				}
				else
				{
					mRightmostEnd = (int) getSyntaxFacade().getRightShift();
				}
			}
		}
		private void drawEnd(SyntacticStructure end, boolean left, Graphics2D contentGraphics) {
			if (left)
			{
				contentGraphics.fillArc((int)end.getButtonX() - end.getPadStartLeftCount() *padWidth,
						(int)end.getButtonY() + end.getPadStartLeftCount() *padLength,
					circle,circle, 0, 360);
				contentGraphics.drawLine((int)end.getButtonX() - end.getPadStartLeftCount() *padWidth,
						(int)end.getButtonY() + end.getPadStartLeftCount() *padLength + 2,
						(int)end.getButtonX() - (end.getPadStartLeftCount() *padWidth) -4,
						(int)end.getButtonY() + end.getPadStartLeftCount() *padLength + 2);
				setWidthEnd(end,left);
				mHeightEnd = (int)end.getButtonY() + (end.getPadStartLeftCount() *padLength) + 2;
				end.setPadStartLeftCount(end.getPadStartLeftCount()+1);
				//System.out.println("draw left");
			}
			else
			{
				contentGraphics.fillArc((int)end.getButtonX() + end.getButtonWidth() + end.getPadStartRightCount() *padWidth,
						(int)end.getButtonY() + end.getPadStartRightCount() *padLength,
						circle,circle, 0, 360);
				contentGraphics.drawLine((int)end.getButtonX() + end.getButtonWidth() + (end.getPadStartRightCount() *padWidth) +4,
						(int)end.getButtonY() + end.getPadStartRightCount() *padLength + 2,
						(int)end.getButtonX() + end.getButtonWidth() + (end.getPadStartRightCount() *padWidth) + 8,
						(int)end.getButtonY() + end.getPadStartRightCount() *padLength + 2);
				setWidthEnd(end,left);
				mHeightEnd = (int)end.getButtonY() + (end.getPadStartRightCount() *padLength) + 2;
				end.setPadStartRightCount(end.getPadStartRightCount()+1);
			}
			mHeightEndPrevious = mHeightEnd;
			mRightmostEndPrevious = mRightmostEnd;
			mLeftmostEndPrevious = mLeftmostEnd;
		}
		private boolean goToLowestLevel(int startLevel, int endLevel, boolean left, Graphics2D contentGraphics) {
			
			if (startLevel < endLevel)
			{
				if (left)
				{
					for(int j = startLevel; j < endLevel - 1; j++)
					{
						lDStart = getSyntaxFacade().getLower(lDStart,lDStart.getNumber(),lDStart.getLevel(),lDStart.getLevel()+1,!left);
						setWidthStart(lDStart,!left);
						mHeightStart = (int) lDStart.getButtonY();
						drawLinesStart(contentGraphics,false);
					}
					lDStart = getSyntaxFacade().getLower(lDStart,lDStart.getNumber(),lDStart.getLevel(),lDStart.getLevel()+1,!left);
					setWidthStart(lDStart,!left);
					mHeightStart = (int) lDStart.getButtonY();
					drawLinesStart(contentGraphics,false);
				}
				else
				{
					for(int j = startLevel; j < endLevel - 1; j++)
					{
						lDStart = getSyntaxFacade().getLower(lDStart,lDStart.getNumber(),lDStart.getLevel(),lDStart.getLevel()+1,!left);
						setWidthStart(lDStart,!left);
						mHeightStart = (int) lDStart.getButtonY();
						drawLinesStart(contentGraphics,false);
					}
					lDStart = getSyntaxFacade().getLower(lDStart,lDStart.getNumber(),lDStart.getLevel(),lDStart.getLevel()+1,!left);
					setWidthStart(lDStart,!left);
					mHeightStart = (int) lDStart.getButtonY();
					drawLinesStart(contentGraphics,false);
				}
			}
			else
			{
				if (left)
				{
					for(int j = endLevel; j < startLevel - 1; j++)
					{
						lDEnd = getSyntaxFacade().getLower(lDEnd,lDEnd.getNumber(),lDEnd.getLevel(),lDEnd.getLevel()+1,left);
						setWidthEnd(lDEnd,left);
						mHeightEnd = (int) lDEnd.getButtonY();
						drawLinesEnd(contentGraphics,false);
					}
					lDEnd = getSyntaxFacade().getLower(lDEnd,lDEnd.getNumber(),lDEnd.getLevel(),lDEnd.getLevel()+1,left);
					setWidthEnd(lDEnd,left);
					mHeightEnd = (int) lDEnd.getButtonY();
					drawLinesEnd(contentGraphics,false);
				}
				else
				{
					for(int j = endLevel; j < startLevel - 1; j++)
					{
						lDEnd = getSyntaxFacade().getLower(lDEnd,lDEnd.getNumber(),lDEnd.getLevel(),lDEnd.getLevel()+1,left);
						setWidthEnd(lDEnd,left);
						mHeightEnd = (int) lDEnd.getButtonY();
						drawLinesEnd(contentGraphics,false);
					}
					lDEnd = getSyntaxFacade().getLower(lDEnd,lDEnd.getNumber(),lDEnd.getLevel(),lDEnd.getLevel()+1,left);
					setWidthEnd(lDEnd,left);
					mHeightEnd = (int) lDEnd.getButtonY();
					drawLinesEnd(contentGraphics,false);
				}
			}
			if (lDStart.equals(lDEnd))
			{
				// TODO draw
				return true;
			}
			else
			{
				// TODO draw
				return false;
			}
		}
		private void drawLinesStart(Graphics2D contentGraphics,boolean override) {
			System.out.println("leftmost start = " + mLeftmostStart);
			System.out.println("rightmost start = " + mRightmostStart);
			if (mLeftmostStart >= mRightmostStart || override)
			{
				contentGraphics.drawLine(mLeftmostStartPrevious, mHeightStartPrevious, mLeftmostStart, mHeightStartPrevious);
				contentGraphics.drawLine(mLeftmostStart, mHeightStartPrevious, mLeftmostStart, mHeightStart);
				mLeftmostStartPrevious = mLeftmostStart;
				mRightmostStartPrevious = mRightmostStart;
				mHeightStartPrevious = mHeightStart;
			}
			
		}
		private void drawLinesEnd(Graphics2D contentGraphics,boolean override) {
			System.out.println("leftmost end = " + mLeftmostEnd);
			System.out.println("rightmost end = " + mRightmostEnd);
			if (mLeftmostEnd >= mRightmostEnd || override)
			{
				contentGraphics.drawLine(mLeftmostEndPrevious, mHeightEndPrevious, mLeftmostEnd, mHeightEndPrevious);
				contentGraphics.drawLine(mLeftmostEnd, mHeightEndPrevious, mLeftmostEnd, mHeightEnd);
				mLeftmostEndPrevious = mLeftmostEnd;
				mRightmostEndPrevious = mRightmostEnd;
				mHeightEndPrevious = mHeightEnd;
			}
			
		}
		private void getHighestUncommonAncestors(SyntacticStructure start, SyntacticStructure end) {
			while (!start.getSyntacticParent().equals(end.getSyntacticParent()))
			{
				start = (SyntacticStructure) start.getSyntacticParent();
				end = (SyntacticStructure) end.getSyntacticParent();
			}
		}
		private void synchronizeLevel() {
			int difference = uAStart.getLevel()-uAEnd.getLevel();
			if(difference != 0)
			{
				if (difference < 0)
				{
					for(int i = difference; i < 0; i++)
					{
						uAEnd = (SyntacticStructure) uAEnd.getSyntacticParent();
					}
				}
				else
				{
					for(int i = difference; i > 0; i--)
					{
						uAStart = (SyntacticStructure) uAStart.getSyntacticParent();
					}
				}
			}
			System.out.println(uAStart.getLevel() + " : " + uAEnd.getLevel());
		}		
		private boolean checkOutsideDirection(SyntacticStructure UAStart, SyntacticStructure UAEnd) {
			if(UAStart.getPreorder() < UAEnd.getPreorder())
			{
				//start is to the left of the end, so you go right!
				return false;
			}
			//start is to the right of end, so you go left!
			return true;
		}
	}	
	/**
	 * 
	 * @return returns the map of all the attributes of a given character at the position
	 * of the selected index of the selected syntactic object.  These attributes include
	 * font information, underline, and sub/superscript information.
	 */
	public Map getAttributes()
	{
		return mUserFrame.getUserControl().getAttributes();
	}
	/**
	 * 
	 * @return returns the SyntacticViewlayout (code not complete)
	 */
	public SyntacticViewLayout getSyntacticViewLayout()
	{
		return mSyntacticViewLayout;
	}
	/**
	 * 
	 * @param pSvl sets the SyntacticViewLayout.
	 */
	public void setSyntacticViewLayout(SyntacticViewLayout pSvl)
	{
		mSyntacticViewLayout = pSvl;
	}
	/**
	 * 
	 * @return Returns the SyntaxFacade
	 */
	public SyntaxFacade getSyntaxFacade()
	{
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
	 * @param pSaveFileType sets the SaveFileType
	 */
	public void setSaveFileType(SaveFileType pSaveFileType)
	{
		mSaveFileType = pSaveFileType;
	}
	/**
	 * 
	 * @param pScale set the Zoom scale
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
	 * object (NOT the same as the selected text!)
	 */
	public ObservableClipboard getObservableClipboard()
	{
		return mUserFrame.getObservableClipboard();
	}
	/**
	 * 
	 * @return Returns the ObservableFont containint the selected font
	 */
	public ObservableFont getObservableFont()
	{
		return mUserFrame.getObservableFont();
	}
	/**
	 * 
	 * @return Returns the ObservableFontSize containing the font size
	 */
	public ObservableFontSize getObservableFontSize()
	{
		return mUserFrame.getObservableFontSize();
	}
	/**
	 * 
	 * @return Returns the ObservableFontBold containing the font style
	 */
	public ObservableFontBold getObservableFontBold()
	{
		return mUserFrame.getObservableFontBold();
	}
	/**
	 * 
	 * @return Returns the ObservableFontBold containing the font style
	 */
	public ObservableFontItalic getObservableFontItalic()
	{
		return mUserFrame.getObservableFontItalic();
	}
	/**
	 * 
	 * @return Returns the ObservableFontBold containing the font underline feature
	 */
	public ObservableFontUnderline getObservableFontUnderline()
	{
		return mUserFrame.getObservableFontUnderline();
	}
	/**
	 * 
	 * @return Returns the ObservableFontBold containing the font subscript feature
	 */
	public ObservableFontSubscript getObservableFontSubscript()
	{
		return mUserFrame.getObservableSubscript();
	}
	/**
	 * 
	 * @return Returns the ObservableFontBold containing the font superscript feature
	 */
	public ObservableFontSuperscript getObservableFontSuperscript()
	{
		return mUserFrame.getObservableSuperscript();
	}
	/**
	 * 
	 * @param pSS the parent
	 * @param pSSChild the subtree
	 * <br>
	 * <br>
	 * activates the glass pane - needed for repositioning trees
	 */
	public void activateGlassPane(SyntacticStructure pSS, SyntacticStructure pSSChild)
	{
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
	public void deactivateGlassPane()
	{
		this.setGlassPane(new JLabel());
		this.getGlassPane().setVisible(false);		
	}
	/**
	 * 
	 * @return Returns the User Frame 
	 */
	public UserFrame getUserFrame()
	{
		return mUserFrame;
	}
	/**
	 * 
	 * @param mMinWidth sets the minimum width - needed for auto-sizing
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
	 * @param mMinHeight sets the minimum height - needed for auto-sizing.
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
