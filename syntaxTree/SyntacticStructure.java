
//TreeForm Syntax Tree Drawing Software
//Copyright (C) 2006  Donald Derrick
//
//This program is free software; you can redistribute it and/or
//modify it under the terms of the GNU General Public License
//as published by the Free Software Foundation; either version 2
//of the License, or (at your option) any later version.
//
//This program is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.
//
//You should have received a copy of the GNU General Public License
//along with this program; if not, write to the Free Software
//Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
//package userInterface;
package syntaxTree;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.text.AttributedString;
import java.util.LinkedList;
import java.util.Map;

import javax.swing.JComponent;

import staticFunctions.Sizer;
import userInterface.UserInternalFrame;
import enumerators.SyntacticLevel;

/**
 * @author Donald Derrick * @version 0.1 * <br> * date: 20-Aug-2004 * <br> * <br> * The Main Tree class - SyntacticStructure. * <br> * This class holds and helps display the tree nodes, * their feature sets, * and their associations.
 * 
 * @uml.stereotype name="tagged" isDefined="true" 
 * @uml.stereotype name="entity" 
 */

public class SyntacticStructure extends EditableComponent implements RepositionTree{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @uml.property name="mSyntacticStructureLines"
	 * @uml.associationEnd 
	 * @uml.property name="mSyntacticStructureLines" multiplicity="(1 1)" inverse="this$0:syntaxTree.SyntacticStructure$SyntacticStructureLines"
	 */
	private SyntacticStructureLines mSyntacticStructureLines;

	private int mMinWidth;

	/**
	 * 
	 * @uml.property name="mChildren"
	 * @uml.associationEnd 
	 * @uml.property name="mChildren" multiplicity="(0 -1)" inverse="mChildren:syntaxTree.SyntacticStructure"
	 */
	private LinkedList mChildren;

	private int mButtonHeight;
	private int mButtonWidth;

	/**
	 * 
	 * @uml.property name="mParent"
	 * @uml.associationEnd 
	 * @uml.property name="mParent" multiplicity="(1 1)"
	 */
	private RepositionTree mParent;

	private double mPrelim;
	private double mY;

	/**
	 * 
	 * @uml.property name="mSyntacticAssociation"
	 * @uml.associationEnd 
	 * @uml.property name="mSyntacticAssociation" multiplicity="(0 -1)" inverse="mSyntacticStructure:syntaxTree.SyntacticAssociation"
	 */
	private LinkedList mSyntacticAssociation;

	/**
	 * 
	 * @uml.property name="mSyntacticFeatureSet"
	 * @uml.associationEnd 
	 * @uml.property name="mSyntacticFeatureSet" multiplicity="(0 -1)" elementType="syntaxTree.SyntacticFeature"
	 */
	private LinkedList mSyntacticFeatureSet;

	/**
	 * 
	 * @uml.property name="mSyntacticLevel"
	 * @uml.associationEnd 
	 * @uml.property name="mSyntacticLevel" multiplicity="(0 1)"
	 */
	private SyntacticLevel mSyntacticLevel;

	private boolean mVisibility;

	private double mMod;

	private SyntacticStructure mThread;

	private SyntacticStructure mAncestor;

	private double mChange;

	private double mShift;

	private double mX;

	private int mNumber;

	private int mLevel;

	private int mPreorder;

	private LinkedList mStartTrace;

	private LinkedList mEndTrace;

	private int mPadBottom;

	private int mAbsoluteOrder;

	private int mPadStartLeft;

	private int mPadLeft;

	private int mPadStartRight;

	private int mPadRight;

	private int mPadStartLeftCount;

	private int mPadStartRightCount;

	private int mPadRightCount;

	private int mPadLeftCount;

	private int mPadBottomCount;
	
/**
 * 
 * @author Donald Derrick
 * @version 0.1
 * <br>
 * date: 20-Aug-2004
 * <br>
 * <br>
 * This class holds the line drawing facilities for SyntacticStructures.
 */
	public class SyntacticStructureLines extends JComponent 
{

	private static final long serialVersionUID = 1L;

	public void paint(Graphics pG) {
		Graphics lGraphics = pG;
		Graphics2D lGraphics2D = (Graphics2D) lGraphics;
		// get the dimension of the butt
		lGraphics2D.scale(Sizer.scaleWidth() * getUserInternalFrame().getScale(), Sizer.scaleHeight() * getUserInternalFrame().getScale());
		//set the font		
		// Set the g2D to antialias.
		lGraphics2D.setColor(Color.BLACK);
		lGraphics2D.setRenderingHint(
			RenderingHints.KEY_ANTIALIASING,
			RenderingHints.VALUE_ANTIALIAS_ON);
		lGraphics2D.setRenderingHint(
			RenderingHints.KEY_RENDERING,
			RenderingHints.VALUE_RENDER_QUALITY);

			// draw the lines according to the directions!
			if (getChildren().size() > 0)
			{
				SyntacticStructure left = (SyntacticStructure) getChildren().getFirst();
				//SyntacticStructure right = (SyntacticStructure) getChildren().getLast();
				SyntacticStructure w = (SyntacticStructure) getChildren().getFirst();
				//int width = (int) (right.getButtonX() + right.getButtonWidth()-left.getButtonX());
				int halfway = (int) (getButtonX() - left.getButtonX() + getButtonWidth()/2);
				int relativeWidth = 0;
				for (int i = 0; i < getChildren().size(); i++) 
				{				
					w = (SyntacticStructure) getChildren().get(i);
					
					relativeWidth = (int) (w.getButtonX() - left.getButtonX());	
					//System.out.println("width = " + halfway + " : relativeWidth = " + relativeWidth);
					//lGraphics2D.drawLine(x1, y1, x2, y2);
					if (getSyntacticLevel() == SyntacticLevel.TRIANGLE)
					{
						lGraphics2D.drawLine(halfway, 1, relativeWidth, Sizer.lineLength()+1);
						lGraphics2D.drawLine(halfway, 1, relativeWidth + w.getButtonWidth(), Sizer.lineLength()+1);
						lGraphics2D.drawLine(relativeWidth, Sizer.lineLength()+1, relativeWidth + w.getButtonWidth(), Sizer.lineLength()+1);
					}
					else
					{
					lGraphics2D.drawLine(
						(int) (halfway),
						(int) (1),
						(int) (relativeWidth + w.getButtonWidth()/2),
						(int) (w.getButtonY()-getButtonY()-getButtonHeight()+Sizer.lineLength())
						);
					}
					//System.out.println("width = " + halfway + " : relativeWidth = " + relativeWidth);
				}
			}
		}

	}
/**
 * 
 * @param pUserInternalFrame The InternalFrame holding the SyntacticStructure
 * @param pParent The Parent (Sentence or SyntacticStructure) holding this
 * SyntacticStructure.  Needed to visual positioning among other tasks.
 */
	public SyntacticStructure(UserInternalFrame pUserInternalFrame, RepositionTree pParent)
	{
		super(pUserInternalFrame);
		mChildren =  new LinkedList();
		mSyntacticAssociation = new LinkedList();
		mSyntacticFeatureSet= new LinkedList();
		mStartTrace = new LinkedList();
		mEndTrace = new LinkedList();
		mParent = pParent;
		setSyntacticStructureLines(new SyntacticStructureLines());
	}

	
/**
 * 
 * @return Returns the Button Height - which is the TOTAL height of the structure,
 * featureSets, Associations, and drawn lines.
 */
	public int getButtonHeight()
	{
		return mButtonHeight;
	}
/**
 * 
 * @return Returns the ButtonWidth which is the MAXIMUM of the width of all children,
 * the width of the text, the dictated minimum width (from collision detection),
 * and the assigned button width.  Complicated, but necessary (trust me!)
 */
	public int getButtonWidth() {
		int lButtonWidth = getChildWidth() > getTextWidth() ? getChildWidth() : getTextWidth();
		lButtonWidth = lButtonWidth > getMinWidth() ? lButtonWidth : getMinWidth();
		lButtonWidth = lButtonWidth > mButtonWidth ? lButtonWidth : mButtonWidth;
		return lButtonWidth;
	}
/**
 * 
 * @return Returns the button x position (unscaled)
 */
	public double getPrelim() {
		return mPrelim;
	}
/**
 * 
 * @return Returns the button y position (unscaled)
 */
	public double getButtonY() {
		return mY;
	}
/**
 * Returns all the SyntacticStructure children.
 */
	public LinkedList getChildren() {
		return mChildren;
	}
/**
 * 
 * @return Returns the width of all the Children based on this algorithm:
 * <br>
 * If no children: get the text width of this SyntacticStructure
 * <br>
 * If there ARE children: get the text width of all the children combined.
 */
	public int getChildWidth()
	{
		int lWidth = 0;
		if (this.getChildren().size() == 0)
		{
			lWidth = getTextWidth();
		}
		else
		{
			for (int i = 0; i < this.getChildren().size(); i++) 
			{
			lWidth
				+= ((SyntacticStructure) this.getChildren().get(i)).getMinWidth();
			}
		}
		return lWidth;
	}
/**
 * 
 * @return Return the set minimum width - this value is created from calculations
 * in the SyntaxFacade
 */
	public int getMinWidth()
	{
		return mMinWidth;
	}
/**
 * 
 * @param pMinWidth set the minimum width - accessed by the SyntaxFacade
 */
	public void setMinWidth(int pMinWidth)
	{
		mMinWidth = pMinWidth;
	}
/**
 * 
 * @return Returns the list of SyntacticAssociations
 */
	public LinkedList getSyntacticAssociation() {
		return mSyntacticAssociation;
	}
/**
 * 
 * @return sets the SyntacticLevel - currently uninforced.
 */
	public SyntacticLevel getSyntacticLevel() {
		return mSyntacticLevel;
	}
/**
 * 
 * @return Returns the parent of this SyntacticStructure as a RepositionTree Interface
 */
	public RepositionTree getSyntacticParent() {
		return mParent;
	}
/**
 * 
 * @return Returns the visibility of this SyntacticStructure - UNIMPLEMENTED
 */
	public boolean getVisibility() {
		return mVisibility;
	}
/**
 * Sets the head text of the syntacticStructure
 */
	public void setHead(AttributedString pHead) {
		super.setHead(pHead);
		testXY();
	}
/**
 * inserts text into the head text of the syntacticStructure
 */
	public void insertHead(AttributedString pHead, int pInt) {
		super.insertHead(pHead,pInt);
		testXY();
	}
/**
 * 
 * @param pHeight sets the buttonHeight, which is a variable in calculating
 * the true buttonHeight
 */
	public void setButtonHeight(int pHeight)
	{
		mButtonHeight = pHeight;
	}
/**
 * 
 * @param mButtonWidth Sets the buttonWidth
 */
	public void setButtonWidth(int mButtonWidth) {
		this.mButtonWidth = mButtonWidth;
	}
/**
 * 
 * @param midpoint Sets the buttonX position, used in displaying the button
 */
	public void setPrelim(double prelim) {
		mPrelim = prelim;
	}
/**
 * 
 * @param level Sets the buttonY position, used in displaying the button
 */
	public void setButtonY(double level) {
		mY = level;
	}
/**
 * 
 * @param children sets the LinkedList of children.
 */
	public void setChildren(LinkedList children) {
		this.mChildren = children;
	}
	public void setMod(double pMod)
	{
		mMod = pMod;
	}
	public void setThread(SyntacticStructure pThread)
	{
		mThread = pThread;
	}
	public void setAncestor(SyntacticStructure pAncestor)
	{
		mAncestor = pAncestor;
	}
	public double getMod()
	{
		return mMod;
	}
	public SyntacticStructure getThread()
	{
		return mThread;
	}
	public SyntacticStructure getAncestor()
	{
		return mAncestor;
	}
/**
 * 
 * @param syntacticAssociation Sets the SyntacticAssociation
 */
	public void setSyntacticAssociation(LinkedList syntacticAssociation) {
		this.mSyntacticAssociation = syntacticAssociation;
	}
/**
 * 
 * @param syntacticLevel Sets the SyntacticLevel - UNINFORCED
 */
	public void setSyntacticLevel(SyntacticLevel syntacticLevel) {
		this.mSyntacticLevel = syntacticLevel;
	}
/**
 * 
 * @param pParent Sets the parent of this SyntacticStructure
 */
	public void setSyntacticParent(RepositionTree pParent) {
		mParent = pParent;
	}
/**
 * 
 * @param visibility Sets the visibility of this SyntacticStructure - UNIMPLEMENTED
 */
	public void setVisibility(boolean visibility) {
		this.mVisibility = visibility;
	}
/**
 * Runs a test of height and width.
 * <br>
 * <br>
 * The Formula works like this:
 * <br>
 * Get the line length, and set the height based on that figure.
 * <br>
 * Get the text width, and set the width based on that figure.
 * <br>
 * Walk through each syntacticAssociation, add the height of each association together,
 * and replace the width with the highest of the width and the association width.
 * <br>
 * Do the same with the SyntacticFeatureSets
 * <br>
 * Then set the TextWidth based on the calculated width,
 * and the ButtonHeight based on the calculated height.
 */
	public void testXY()
	{
		super.testXY();

		int lHeight = Sizer.lineLength();
		lHeight = (int) (lHeight + getTextHeight()+2);
		int lWidth = getTextWidth();
		for(int i = 0; i < this.getSyntacticAssociation().size(); i++)
		{
			lHeight += ((SyntacticAssociation)this.getSyntacticAssociation().get(i)).getTextHeight();
			lWidth = lWidth > ((SyntacticAssociation)this.getSyntacticAssociation().get(i)).getTextWidth() ? lWidth :((SyntacticAssociation)getSyntacticAssociation().get(i)).getTextWidth();

		}
		for(int i = 0; i < this.getSyntacticFeatureSet().size(); i++)
		{
			lHeight += ((SyntacticFeatureSet)this.getSyntacticFeatureSet().get(i)).getHeight();
			lWidth = lWidth > ((SyntacticFeatureSet)this.getSyntacticFeatureSet().get(i)).getWidth() ? lWidth :((SyntacticFeatureSet)getSyntacticFeatureSet().get(i)).getWidth();

		}
		this.setTextWidth(lWidth);
		this.setButtonHeight(lHeight);
	}
/**
 * 
 * @param mSyntacticStructureLines Sets the component holding the drawn lines.
 */
	public void setSyntacticStructureLines(SyntacticStructureLines mSyntacticStructureLines) {
		this.mSyntacticStructureLines = mSyntacticStructureLines;
	}
/**
 * 
 * @return Return the component holding the drawn lines.
 */
	public SyntacticStructureLines getSyntacticStructureLines() {
		return mSyntacticStructureLines;
	}
/**
 * Sets the features of highlighted text and redisplays the tree.
 */
	public void setHighlight(Map map) 
	{
		super.setHighlight(map);
			//getUserInternalFrame().getSyntaxFacade().redisplayTree();	
	}
/**
 * repaint both the structure and it's lines
 */	
	public void repaint()
	{
		super.repaint();
		getSyntacticStructureLines().repaint();
	}
/**
 * 
 * @param pSyntacticFeatureSet sets the LinkedList of FeatureSets
 */
	public void setSyntacticFeatureSet(LinkedList pSyntacticFeatureSet) {
		mSyntacticFeatureSet = pSyntacticFeatureSet;
	}
/**
 * 
 * @return Returns the list of FeatureSets
 */
	public LinkedList getSyntacticFeatureSet() {
		return mSyntacticFeatureSet;
	}
/**
 * Delete text in the head, and retest XY
 */
	public void deleteHead()
	{
		super.deleteHead();
		testXY();
	}
	public double getChange() {
		return mChange;
	}
	public void setChange(double change) {
		mChange = change;
	}
	public double getShift() {
		return mShift;
	}
	public void setShift(double shift) {
		mShift = shift;
	}
	public void setButtonX(double x) {
		mX = x;
	}
	public double getButtonX()
	{
		return mX;
	}
	public void setNumber(int number) {
		mNumber = number;
		
	}
	public int getNumber()
	{
		return mNumber;
	}
	public void setLevel(int level) {
		mLevel = level;
	}
	public int getLevel()
	{
		return mLevel;
	}



	public void setPreorder(int preorder) {
		mPreorder = preorder;
	}
	public int getPreorder()
	{
		return mPreorder;
	}
	
	public LinkedList getEndTrace()
	{
		return mEndTrace;
	}
	public LinkedList getStartTrace()
	{
		return mStartTrace;
	}


	public int getPadBottom() {
		// TODO Auto-generated method stub
		return mPadBottom;
	}


	public void setPadBottom(int padBottom) {
		mPadBottom = padBottom;
	}


	public void setAbsoluteOrder(int i) {
		mAbsoluteOrder = i;
		
	}
	public int getAbsoluteOrder()
	{
		return mAbsoluteOrder;
	}


	public int getPadStartLeft() {
		// TODO Auto-generated method stub
		return mPadStartLeft;
	}


	public void setPadStartLeft(int i) {
		mPadStartLeft = i;
		
	}


	public int getPadLeft() {
		// TODO Auto-generated method stub
		return mPadLeft;
	}


	public void setPadLeft(int i) {
		mPadLeft = i;
		
	}


	public int getPadStartRight() {
		return mPadStartRight;
	}


	public void setPadStartRight(int i) {
		mPadStartRight = i;
		
	}


	public int getPadRight() {
		return mPadRight;
	}


	public void setPadRight(int i) {
		mPadRight = i;
		
	}


	public int getPadStartLeftCount() {
		// TODO Auto-generated method stub
		return mPadStartLeftCount;
	}


	public void setPadStartLeftCount(int i) {
		mPadStartLeftCount = i;
		
	}


	public void setPadStartRightCount(int i) {
		mPadStartRightCount = i;
		
	}


	public void setPadRightCount(int i) {
		mPadRightCount = i;
		
	}


	public void setPadLeftCount(int i) {
		mPadLeftCount = i;
		
	}


	public void setPadBottomCount(int i) {
		mPadBottomCount = i;
		
	}

	public int getPadStartRightCount() {
		// TODO Auto-generated method stub
		return mPadStartRightCount;
	}
	
	public int getPadRightCount() {
		return mPadRightCount;
	}
	
	public int getPadLeftCount() {
		return mPadLeftCount;
	}
	
	public int getPadBottomCount() {
		return mPadBottomCount;
	}
}
