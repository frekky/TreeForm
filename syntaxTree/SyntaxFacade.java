
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

import java.awt.Component;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.io.File;
import java.text.AttributedString;
import java.util.LinkedList;

import javax.swing.JPopupMenu;

import staticFunctions.Sizer;
import userInterface.UserInternalFrame;
import enumerators.SyntacticFeatureType;
import enumerators.SyntacticLevel;
import enumerators.SyntacticStructureType;
/**
 * 
 * @author Donald Derrick
 * @version 0.1
 * <br>
 * date: 20-Aug-2004
 * <br>
 * <br>
 * Behemoth class containing the interface between the UserInterface and SyntaxTree,
 * <br>
 * What a monster of a class!
 * <br>
 */
public class SyntaxFacade {

	private String mPicture;
	private String mFile;

	/**
	 * 
	 * @uml.property name="mUnder"
	 * @uml.associationEnd 
	 * @uml.property name="mUnder" multiplicity="(0 1)"
	 */
	private SyntacticStructure mUnder;

	private Component mOldContainer;

	/**
	 * 
	 * @uml.property name="mContainer"
	 * @uml.associationEnd 
	 * @uml.property name="mContainer" multiplicity="(0 -1)" elementType="syntaxTree.SyntacticStructure"
	 */
	private Component mContainer;

	/**
	 * 
	 * @uml.property name="mUIF"
	 * @uml.associationEnd 
	 * @uml.property name="mUIF" multiplicity="(0 1)" inverse="mSyntaxFacade:userInterface.UserInternalFrame"
	 */
	private UserInternalFrame mUIF;

	/**
	 * 
	 * @uml.property name="mSentence"
	 * @uml.associationEnd 
	 * @uml.property name="mSentence" multiplicity="(0 1)"
	 */
	private Sentence mSentence;
	private SyntacticStructure mDefaultAncestor;
	private double mLeftShift;
	private double mShift;
	private double mChange;
	private int subtrees;
	private LinkedList mVariableHeight;




/**
 * 
 * @param pUIF The InternalFrame associated with this facade
 */
	public SyntaxFacade(UserInternalFrame pUIF) {
		setSentence(new Sentence());
		setUIF(pUIF);

	}

/**
 * 
 * @param pSST The SyntacticStructureType used to decide which builder to use.
 * @param pUIF The InternalFrame associated with this facade
 * @throws Exception An exception thrown when something goes wrong.
 * <br>
 * <br>
 * This command checks the passed syntacticStructureType,
 * and loads in the correct ConcreteBuilder.
 * The director then builds the structure, and positions it according to this formula:
 * <br>
 * If the container is a tree itself, chedck to see if it is a NULL head or not.
 * <br>
 * If yes, delete the null head and put this structure in it's place.
 * <br>
 * If no, check to see if there are any children to the head.
 * <br>
 * If no, attach the newly created structure directly.
 * <br>
 * If yes, call the glassPane to allow the user to choose where to position
 * the newly minted structure
 * <br>
 * Finally, redisplay the tree.
 */
	public void addSyntacticStructure(
		SyntacticStructureType pSST,
		UserInternalFrame pUIF)
		throws Exception {
		AbstractStructureBuilder lAB = null;
		SyntacticStructure lSyntacticStructure;

		if (pSST == SyntacticStructureType.ADJUNCT) {
			lAB = new AdjunctBuilder();
		} else if (pSST == SyntacticStructureType.BINARY) {
			lAB = new BinaryBuilder();
		} else if (pSST == SyntacticStructureType.HEAD) {
			lAB = new HeadBuilder();
		} else if (pSST == SyntacticStructureType.TRIANGLE) {
			lAB = new TriangleBuilder();
		} else if (pSST == SyntacticStructureType.TRINARY) {
			lAB = new TrinaryBuilder();
		} else if (pSST == SyntacticStructureType.UNARY) {
			lAB = new UnaryBuilder();
		} else if (pSST == SyntacticStructureType.X_BAR) {
			lAB = new XBarBuilder();
		} else if (pSST == SyntacticStructureType.MORPH) {
			lAB = new MorphBuilder();
		} else if (pSST == SyntacticStructureType.PHRASE) {
			lAB = new PhraseBuilder();
		} else {
			Exception typeNotFoundError = new TypeNotFoundError();
			throw typeNotFoundError;
		}
		lSyntacticStructure = StructureDirector.build(lAB, pUIF);
				
		if (getContainer() instanceof SyntacticStructure) {
			SyntacticStructure lSS = ((SyntacticStructure) getContainer());
			if (pSST == SyntacticStructureType.PHRASE)
			{
				if (lSS.getSyntacticParent() != null)
				{
					SyntacticStructure lSSParent = (SyntacticStructure) lSS.getSyntacticParent();
					int lI = lSSParent.getChildren().indexOf(lSS);
					lSSParent.getChildren().remove(lSS);
					lSSParent.getChildren().add(lI, lSyntacticStructure);
					lSyntacticStructure.getChildren().add(lSS);
					lSS.setSyntacticParent(lSyntacticStructure);
					lSyntacticStructure.setSyntacticParent(lSSParent);
				}
				else
				{
					lSyntacticStructure.getChildren().add(lSS);
					lSS.setSyntacticParent(lSyntacticStructure);
					getSentence().removeChild(lSS);
					getSentence().addChild(lSyntacticStructure);
				}
			}
			else
				{
				if (lSS.getSyntacticLevel() == SyntacticLevel.NULL) {
					SyntacticStructure lSSParent =
						(SyntacticStructure) lSS.getSyntacticParent();
					int lI = lSSParent.getChildren().indexOf(lSS);
					lSSParent.getChildren().remove(lSS);
					getUIF().remove(lSS);
					lSSParent.getChildren().add(lI, lSyntacticStructure);
					lSyntacticStructure.setSyntacticParent(lSSParent);
				} else {
					if (lSS.getChildren().size() == 0) {
						lSS.getChildren().add(lSyntacticStructure);
						lSyntacticStructure.setSyntacticParent(lSS);
					} else {
						getUIF().activateGlassPane(lSS, lSyntacticStructure);
					}
				}
			}
		} else {
			getSentence().addChild(lSyntacticStructure);
		}
		displayTree();

	}
public void displayTree() {
	if(mSentence.getChildren().size() > 0)
	{
		treeLayout(mSentence);
		getUIF().revalidate();
		//System.out.println("done!");
	}
}

private void treeLayout(Sentence sentence) {
	SyntacticStructure mR = (SyntacticStructure) mSentence.getChildren().getFirst();
	mLeftShift = 0;
	mShift = 0;
	initializeTree(mR,1,0);
	mVariableHeight = new LinkedList();
	firstWalk(mR,0);
	secondWalk(mR,-mR.getPrelim(),0);
	mLeftShift -= 12;
	thirdWalk(mR,0);
	fourthWalk(mR,0);
}

private void initializeTree(SyntacticStructure v,int number,int level) {
	v.setThread(null);
	v.setAncestor(v);
	v.setMod(0);
	v.setPrelim(0);
	v.setChange(0);
	v.setButtonY(0);
	v.setButtonX(0);
	v.setShift(0);
	v.setNumber(number);
	v.setLevel(level);
	for (int i = 0; i < v.getChildren().size();i++)
	{
		initializeTree((SyntacticStructure) v.getChildren().get(i),i+1,level+1);
	}
}

private void firstWalk(SyntacticStructure v,int position) {
	//System.out.println("begin firstwalk");
	if (v.getChildren().size() == 0)
	{
		if (position == 0)
		{
			v.setPrelim(0);
			//System.out.println("v = " + printText(v));
			//System.out.println("v prelim = " + v.getPrelim());
		}
		else
		{
			SyntacticStructure w = ((SyntacticStructure) (v.getSyntacticParent().getChildren().get(position-1)));
			v.setPrelim(w.getPrelim() + w.getButtonWidth());
			//System.out.println("v = " + printText(v));
			//System.out.println("v prelim = " + v.getPrelim());
		}
	}
	else
	{
		mDefaultAncestor = (SyntacticStructure) v.getChildren().getFirst();
		//System.out.println("defaultAncestor = " + printText(mDefaultAncestor));
		for (int i = 0; i < v.getChildren().size();i++)
		{
			firstWalk((SyntacticStructure) v.getChildren().get(i), i);
			apportion((SyntacticStructure) v.getChildren().get(i), i);
		}
		executeShifts(v);
		double midpoint = 0.5*(((SyntacticStructure) v.getChildren().getFirst()).getPrelim() 
				+ ((SyntacticStructure) v.getChildren().getLast()).getPrelim()
				- v.getButtonWidth()
				+ ((SyntacticStructure) v.getChildren().getLast()).getButtonWidth()
				);
		//System.out.println("midpoint = " + midpoint);
		if (position != 0)
		{
			// has a left sibling
			SyntacticStructure w = ((SyntacticStructure) 
					v.getSyntacticParent().getChildren().get(position-1));
			v.setPrelim(w.getPrelim() + w.getButtonWidth());
			v.setMod(v.getPrelim() - midpoint);
			//System.out.println("v = " + printText(v));
			//System.out.println("v prelim = " + v.getPrelim());
			//System.out.println("v setMod = " + v.getMod());
		}
		else
		{
			// no left sibling
			v.setPrelim(midpoint);
			//System.out.println("v = " + printText(v));
			//System.out.println("v prelim = " + v.getPrelim());
		}
	}
	//System.out.println("end firstwalk");
}

private void apportion(SyntacticStructure v, int p) 
{
	//System.out.println("begin apportion");
	if (p != 0)
	{
		SyntacticStructure VIP = v;
		SyntacticStructure VOP = v;
		SyntacticStructure VIN = (SyntacticStructure) v.getSyntacticParent().getChildren().get(p-1);
		SyntacticStructure VON = (SyntacticStructure) VIP.getSyntacticParent().getChildren().getFirst();
		double SIP = VIP.getMod();
		double SOP = VOP.getMod();
		double SIN = VIN.getMod();
		double SON = VON.getMod();
		//System.out.println("VIP = "+ printText(VIP));
		//System.out.println("VOP = "+ printText(VOP));
		//System.out.println("VIN = "+ printText(VIN));
		//System.out.println("VON = "+ printText(VON));
		//System.out.println("SIP = "+SIP);
		//System.out.println("SOP = "+SOP);
		//System.out.println("SIN = "+SIN);
		//System.out.println("SON = "+SON);
		while (nextRight(VIN) != null && nextLeft(VIP) != null)
		{
			VIN = nextRight(VIN);
			VIP = nextLeft(VIP);
			VON = nextLeft(VON);
			VOP = nextRight(VOP);
			//System.out.println("neither null");
			//System.out.println("VIP = "+ printText(VIP));
			//System.out.println("VOP = "+ printText(VOP));
			//System.out.println("VIN = "+ printText(VIN));
			//System.out.println("VON = "+ printText(VON));
			VOP.setAncestor(v);
			//System.out.println("VOP ancestor = "+printText(v));
			mShift = (VIN.getPrelim() + SIN) - (VIP.getPrelim() + SIP) 
			+ VIN.getButtonWidth();
			//System.out.println("VIN prelim = " + VIN.getPrelim() + " SIN = " + SIN);
			//System.out.println("VIP prelim = " + VIP.getPrelim() + " SIP = " + SIP);
			//System.out.println("VIN buttonWidth = " + VIN.getButtonWidth());
			//System.out.println("shift = " + mShift);
			if (mShift > 0)
			{
				moveSubtree(ancestor(VIN,v),v);
				SIP = SIP + mShift;
				SOP = SOP + mShift;
				//System.out.println("Shift > 0");
				//System.out.println("shift = " + mShift);
				//System.out.println("SIP = "+SIP);
				//System.out.println("SOP = "+SOP);
			}
			SIN = SIN + VIN.getMod();
			SIP = SIP + VIP.getMod();
			SON = SON + VON.getMod();
			SOP = SOP + VOP.getMod();
			//System.out.println("SIP = "+SIP);
			//System.out.println("SOP = "+SOP);
			//System.out.println("SIN = "+SIN);
			//System.out.println("SON = "+SON);
			//System.out.println("end iteration");
		}	
		if(nextRight(VIN) != null && nextRight(VOP) == null)
		{
			//System.out.println("nextRight VIN not null, nextright VOP null");
			VOP.setThread(nextRight(VIN));
			VOP.setMod(VOP.getMod() + SIN - SOP);
			//System.out.println("VOP thread = " + printText(VOP.getThread()));
			//System.out.println("VOP mod = " + VOP.getMod() );
		}
		if (nextLeft(VIP) != null && nextLeft(VON) == null)
		{
			//System.out.println("nextLeft VIP not null, nextLeft VON null");
			VON.setThread(nextLeft(VIP));
			VON.setMod(VON.getMod() + SIP - SON);
			mDefaultAncestor = v;
			//System.out.println("VON thread = " + printText(VON.getThread()));
			//System.out.println("VON mod = " + VON.getMod());
			//System.out.println("default ancestor changed to: " + printText(v));
		}
	}
	//System.out.println("end apportion");
}

private SyntacticStructure nextLeft(SyntacticStructure v) {
	if (v.getChildren().size() > 0)
	{
		//System.out.println("leftmost child of "+ printText(v) +" = " + printText((SyntacticStructure) v.getChildren().getFirst()));
		return (SyntacticStructure) v.getChildren().getFirst();
	}
	else
	{
		//System.out.println("nextleft thread of "+ printText(v) + " = "+ printText(v.getThread()));
		return v.getThread();
	}
}

private SyntacticStructure nextRight(SyntacticStructure v) {
	
	if (v.getChildren().size() > 0)
	{
		//System.out.println("rightmost child of "+ printText(v) +" = " + printText((SyntacticStructure) v.getChildren().getLast()));
		return (SyntacticStructure) v.getChildren().getLast();
	}
	else
	{		
		//System.out.println("nextright thread of "+ printText(v) +" = "+ printText(v.getThread()));
		return v.getThread();
	}
}

private void moveSubtree(SyntacticStructure wm, SyntacticStructure wp) {
	subtrees = wp.getNumber() - wm.getNumber() - 1;
	if (subtrees <= 0)
	{
		subtrees = 1;
	}
	wp.setChange(wp.getChange() - mShift/subtrees);
	wp.setShift(wp.getShift() + mShift);
	wm.setChange(wm.getChange() + mShift/subtrees);
	wp.setPrelim(wp.getPrelim() + mShift);
	wp.setMod(wp.getMod() + mShift);
	//System.out.println("begin moveSubtree");
	//System.out.println("wm = " + printText(wm) + " : wp = " +printText(wp));
	//System.out.println("subtrees = " + subtrees);
	//System.out.println("shift = " + mShift);
	//System.out.println("wp change = " + wp.getChange());
	//System.out.println("wp shift =  " + wp.getShift());
	//System.out.println("wm change = " + wm.getChange());
	//System.out.println("wp prelim = " + wp.getPrelim());
	//System.out.println("wp mod = "+ wp.getMod());
	//System.out.println("end moveSubtree");
}

private void executeShifts(SyntacticStructure v) {
	mShift = 0;
	//mShift = v.getButtonWidth()/2;
	mChange = 0;
	//System.out.println("begin execute shifts");
	for(int i = v.getChildren().size()-1; i >= 0; i--)
	{
		SyntacticStructure w = (SyntacticStructure) v.getChildren().get(i);
		
		w.setPrelim(w.getPrelim() + mShift);
		w.setMod(w.getMod() + mShift);
		mChange = mChange + w.getChange();
		mShift = mShift + w.getShift() + mChange;
		//System.out.println("shift # "+ i + " for " + printText(w));
		//System.out.println("prelim = " + w.getPrelim());
		//System.out.println("mod = " + w.getMod());
		//System.out.println("change = " + mChange);
		//System.out.println("shift = " + mShift);
	}
	//System.out.println("end execute shifts");
}

private SyntacticStructure ancestor(SyntacticStructure vin, SyntacticStructure v) {

		//System.out.println("VIN Ancestor's parent = " + printText((SyntacticStructure) vin.getAncestor().getSyntacticParent()) + " : v parent = " + printText((SyntacticStructure) v.getSyntacticParent()));
	if (vin.getAncestor().getSyntacticParent().equals(v.getSyntacticParent()) && !vin.getAncestor().equals(v))
	{
		//System.out.println("ancestor = " + printText(vin.getAncestor()) + " not " + printText(mDefaultAncestor));
		return vin.getAncestor();
	}
	else
	{
		//System.out.println("(default) ancestor = " + printText(mDefaultAncestor) + " not " + printText(vin.getAncestor()));
		return mDefaultAncestor;
	}
}

private void secondWalk(SyntacticStructure v, double m, int level) {
	v.setButtonX(v.getPrelim() + m);
	//System.out.println("prelim position of " + printText(v) + " is " + v.getPrelim());
	//System.out.println("final position of " + printText(v) + " is " + v.getButtonX());
	if (v.getButtonX() < mLeftShift)
	{
		mLeftShift = v.getButtonX();
	}
	if (mVariableHeight.size() <= level)
	{
		mVariableHeight.add(new Integer(v.getButtonHeight()));
		//System.out.println("level = " + level + " button height = " + v.getButtonHeight());
	}
	else
	{
		if (((Integer)mVariableHeight.get(level)).intValue() < v.getButtonHeight())
		{
			mVariableHeight.remove(level);
			mVariableHeight.add(level,new Integer(v.getButtonHeight()));
			//System.out.println("level = " + level + " button height = " + v.getButtonHeight());
		}	
	}
	v.setButtonY(level * 35);
	v.setBounds(
			(int) (v.getButtonX()
				* Sizer.scaleWidth()
				* getUIF().getScale()),
			(int) (v.getButtonY()
				* Sizer.scaleHeight()
				* getUIF().getScale()),
			(int) (v.getButtonWidth()
				* Sizer.scaleWidth()
				* getUIF().getScale()),
			(int) (v.getButtonHeight()
				* Sizer.scaleHeight()
				* getUIF().getScale()));

	for(int i = 0;i < v.getChildren().size();i++)
	{
		secondWalk((SyntacticStructure) v.getChildren().get(i), m + v.getMod(), level + 1);
	}
}

private void thirdWalk(SyntacticStructure v, int level)
{
	Rectangle r = v.getBounds();
	v.setButtonX(v.getButtonX() - mLeftShift);
	int tempY = 0;
	//int tempYeven = 0;
	for (int i = 0; i <level;i++)
	{
		tempY += ((Integer) mVariableHeight.get(i)).intValue();
	}
	tempY = tempY + (((Integer) mVariableHeight.get(level)).intValue()-v.getButtonHeight())/2;
	//System.out.println("button height = " + v.getButtonHeight() + " and max height = " + ((Integer) mVariableHeight.get(level)).intValue());
	//System.out.println("difference = " + (((Integer) mVariableHeight.get(level)).intValue()-v.getButtonHeight())/2 + " level " + level);
	v.setButtonY(tempY);
	tempY = (int) (tempY * Sizer.scaleHeight() * getUIF().getScale());
	r.y = tempY;
	r.x = (int) (r.x - (mLeftShift * Sizer.scaleWidth() * getUIF().getScale()));

	v.setBounds(r);
	int lFeatureHeight = 0;
	
	for (int i = 0; i < v.getSyntacticFeatureSet().size(); i++) {
		SyntacticFeatureSet lSFS =
			(SyntacticFeatureSet) v.getSyntacticFeatureSet().get(i);
		int lFeatureWidth = 0;
		for (int j = 0; j < lSFS.getSyntacticFeature().size(); j++) {
			SyntacticFeature lSF =
				(SyntacticFeature) lSFS.getSyntacticFeature().get(j);
			lSF.setBounds(
				((int)v.getButtonX()
					- ((lSFS.getWidth() - v.getButtonWidth()) / 2)
					+ lFeatureWidth),
				((int)v.getButtonY() + v.getTextHeight() + lFeatureHeight),
				lSF.getTextWidth(),
				lSF.getTextHeight());
			lSF.setBounds(
				(int) (lSF.getBounds().x
					* Sizer.scaleWidth()
					* getUIF().getScale()),
				(int) (lSF.getBounds().y
					* Sizer.scaleHeight()
					* getUIF().getScale()),
				(int) (lSF.getBounds().width
					* Sizer.scaleWidth()
					* getUIF().getScale()),
				(int) (lSF.getBounds().height
					* Sizer.scaleHeight()
					* getUIF().getScale()));

			lFeatureWidth += lSF.getTextWidth();
		}
		lFeatureHeight += lSFS.getHeight();
	}

	for (int i = 0; i < v.getSyntacticAssociation().size(); i++) {

		SyntacticAssociation lSA =
			(SyntacticAssociation) v.getSyntacticAssociation().get(i);

		lSA.setBounds(
			((int) v.getButtonX() - (lSA.getTextWidth() - v.getButtonWidth()) / 2),
			((int)v.getButtonY() + v.getTextHeight() + lFeatureHeight),
			lSA.getTextWidth(),
			lSA.getTextHeight());
		lSA.setBounds(
			(int) (lSA.getBounds().x
				* Sizer.scaleWidth()
				* getUIF().getScale()),
			(int) (lSA.getBounds().y
				* Sizer.scaleHeight()
				* getUIF().getScale()),
			(int) (lSA.getBounds().width
				* Sizer.scaleWidth()
				* getUIF().getScale()),
			(int) (lSA.getBounds().height
				* Sizer.scaleHeight()
				* getUIF().getScale()));

		lFeatureHeight += lSA.getTextHeight();
	}
	
	for(int i = 0;i < v.getChildren().size();i++)
	{
		thirdWalk((SyntacticStructure) v.getChildren().get(i),level+1);
	}
}


private void fourthWalk(SyntacticStructure v, int level)

{
	
	//v.getSyntacticStructureLines().setBounds(x, y, width, height);
	if (v.getChildren().size() > 0)
	{
		SyntacticStructure left = (SyntacticStructure) v.getChildren().getFirst();
		SyntacticStructure right = (SyntacticStructure) v.getChildren().getLast();
		//System.out.print("begin paint");
		v.getSyntacticStructureLines().setBounds(
			(int) (left.getButtonX() * Sizer.scaleWidth() * getUIF().getScale()),
			(int) ((v.getButtonY() + v.getButtonHeight() - Sizer.lineLength()) * Sizer.scaleHeight() * getUIF().getScale()),
			(int) ((right.getButtonX()+right.getButtonWidth()-left.getButtonX()) * Sizer.scaleWidth() * getUIF().getScale()),
			(int) ((left.getButtonY() + (left.getButtonHeight()/2)-(v.getButtonY()))* Sizer.scaleHeight() * getUIF().getScale()));
		//v.getSyntacticStructureLines().paint(v.getSyntacticStructureLines().getGraphics());
		//System.out.println("end paint");
	}
	for(int i = 0;i < v.getChildren().size();i++)
	{
		fourthWalk((SyntacticStructure) v.getChildren().get(i),level+1);
	}
}
/**
 * 
 * @param pSF SyntacticFeature
 * @param pME MouseEvent
 * <br>
 * <br>
 * This function moves a SyntacticFeature aroudn with the mouse. The purpose
 * is to make it easy for a user to see that a SyntacticFeature is being dragged to
 * a new association point.
 */
	public void translateSyntacticFeature(SyntacticFeature pSF, MouseEvent pME) {
		int lX = pME.getX() - pSF.getBounds().width / 2;
		int lY = pME.getY() - pSF.getBounds().height / 2;
		Rectangle lBounds = pSF.getBounds();
		pSF.setBounds(
			lBounds.x + lX,
			lBounds.y + lY,
			lBounds.width,
			lBounds.height);
	}
/**
 * 
 * @param pSS SyntacticStructure
 * @param pME MouseEvent
 * <br>
 * This command is the public method for moving a SyntacticStructure.  It provides the 
 */
	public void translateSyntacticStructure(
		SyntacticStructure pSS,
		MouseEvent pME) {
		int lX = pME.getX() - pSS.getBounds().width / 2;
		int lY = pME.getY() - pSS.getBounds().height / 2;
		translateSubtree(pSS, lX, lY);
		getUIF().repaint();
	}
/**
 * 
 * @param pSS SyntacticStructure
 * @param pX delta X
 * @param pY delta Y
 * <br>
 * This command moves a subtree, it's lines, feature sets, and associations
 * by the delta X and Y provided by mouse movement.
 */
	private void translateSubtree(SyntacticStructure pSS, int pX, int pY) {
		Rectangle lBounds = pSS.getBounds();
		pSS.setBounds(
			lBounds.x + pX,
			lBounds.y + pY,
			lBounds.width,
			lBounds.height);
		lBounds = pSS.getSyntacticStructureLines().getBounds();
		pSS.getSyntacticStructureLines().setBounds(
			lBounds.x + pX,
			lBounds.y + pY,
			lBounds.width,
			lBounds.height);
		for (int i = 0; i < pSS.getSyntacticFeatureSet().size(); i++) {
			SyntacticFeatureSet lSFS =
				(SyntacticFeatureSet) pSS.getSyntacticFeatureSet().get(i);
			for (int j = 0; j < lSFS.getSyntacticFeature().size(); j++) {
				SyntacticFeature lSF =
					(SyntacticFeature) lSFS.getSyntacticFeature().get(j);
				Rectangle nBounds = lSF.getBounds();
				lSF.setBounds(
					nBounds.x + pX,
					nBounds.y + pY,
					nBounds.width,
					nBounds.height);
			}
		}
		for (int i = 0; i < pSS.getSyntacticAssociation().size(); i++) {
			SyntacticAssociation lSA =
				(SyntacticAssociation) pSS.getSyntacticAssociation().get(i);
			Rectangle nBounds = lSA.getBounds();
			lSA.setBounds(
				nBounds.x + pX,
				nBounds.y + pY,
				nBounds.width,
				nBounds.height);
		}
		for (int i = 0; i < pSS.getChildren().size(); i++) {
			translateSubtree((SyntacticStructure) pSS.getChildren().get(i), pX, pY);
		}
	}


/**
 * 
 * @param pParent The parent SyntacticStructure 
 * @param pChild The child SyntacticStructure
 * <br>
 * <br>
 * Move the syntactic structure by reassociating it with a new parent.
 * <br>
 * if the new parent has no child nodes, just add it.
 * <br>
 * if the new parent has child nodes, hide the moved structure, and get
 * the program user to select it's insertion position using the GlassPane
 * interface.
 */
	public void moveSyntacticStructure(
		SyntacticStructure pParent,
		SyntacticStructure pChild) {
		if (pChild.getSyntacticParent() != null) {
			pChild.getSyntacticParent().getChildren().remove(pChild);
			if (pParent.getChildren().size() == 0) {
				pParent.getChildren().add(pChild);
				pChild.setSyntacticParent(pParent);
				displayTree();
			} else {
				hideTree(pChild);
				displayTree();
				getUIF().activateGlassPane(pParent, pChild);
			}
		} else {
			displayTree();
		}
	}
/**
 * 
 * @param pSS The syntacticStructure to delete.
 * <br>
 * First, delete the subtree
 * <br>
 * Then set the clipboard to the object's parent, and disassociate
 * the structure from it's parent.  If there is no parent,
 * remove the child from the sentence.
 */
	public void deleteSyntacticStructure(SyntacticStructure pSS) {
		deleteSubtree(pSS);
		getUIF().getObservableClipboard().setValue(pSS.getSyntacticParent());
		if (pSS.getSyntacticParent() != null) {
			pSS.getSyntacticParent().getChildren().remove(pSS);
		} else {
			mSentence.removeChild(pSS);
		}
		displayTree();
	}
/**
 * 
 * @param pSS The SyntacticStructure to delete.
 * <br>
 * <br>
 * This operation removes a structure, it's lines, it's features/featuresets,
 * and it's associations (including associations attached to other
 * syntactic structures - of course) from the InternalFrame.
 * <br>
 * This function is recursive.
 */
	public void deleteSubtree(SyntacticStructure pSS) {
		getUIF().remove(pSS.getSyntacticStructureLines());
		getUIF().remove(pSS);
		for (int j = 0; j < pSS.getSyntacticFeatureSet().size(); j++) {
			SyntacticFeatureSet lSFS =
				(SyntacticFeatureSet) pSS.getSyntacticFeatureSet().get(j);
			for (int k = 0; k < lSFS.getSyntacticFeature().size(); k++) {
				SyntacticFeature lSF =
					(SyntacticFeature) lSFS.getSyntacticFeature().get(k);
				for (int l = 0;
					l < lSF.getSyntacticAssociation().size();
					l++) {
					SyntacticAssociation lSA =
						(SyntacticAssociation) lSF
							.getSyntacticAssociation()
							.get(
							l);
					SyntacticStructure lSS = lSA.getSyntacticStructure();
					lSS.getSyntacticAssociation().remove(lSA);
					lSS.testXY();
					getUIF().remove(lSA);
				}
				getUIF().remove(lSF);
			}
		}
		for (int i = 0; i < pSS.getChildren().size(); i++) {
			deleteSubtree((SyntacticStructure) pSS.getChildren().get(i));
		}
	}
/**
 * 
 * @param pSF SyntacticFeature to delete
 * <br>
 * this funtion just deletes a syntactic feature and all of it's associations
 * from a SyntacticStructure and from the InternalFrame.
 */
	public void deleteSyntacticFeature(SyntacticFeature pSF) {
		SyntacticFeatureSet lSFS = pSF.getSyntacticFeatureSet();
		LinkedList lSAS = pSF.getSyntacticAssociation();
		SyntacticStructure lSS = lSFS.getSyntacticStructure();
		for (int i = 0; i < lSAS.size(); i++) {
			SyntacticAssociation lSA = (SyntacticAssociation) lSAS.get(i);
			System.out.println(i);
			SyntacticStructure nSS = lSA.getSyntacticStructure();
			nSS.getSyntacticAssociation().remove(lSA);
			getUIF().getContentPane().remove(lSA);
			nSS.testXY();
		}
		lSFS.getSyntacticFeature().remove(pSF);
		getUIF().getContentPane().remove(pSF);
		if (lSFS.getSyntacticFeature().size() == 0) {
			lSFS.getSyntacticFeature().remove(lSFS);
		}
		lSS.testXY();
		displayTree();
	}
/**
 * 
 * @param pSFT SyntacticFeatureType
 * @param pUIF The internalFrame to add the structure to (I really need to get rid of
 * this crap, the facade already HAS this frame!) 
 * @throws Exception The thrown excpetion if the type is not found.
 * <br>
 * This function simply selects the correct FeatureBuilder based on the desired
 * features, and attaches the new featureset to both the UIF and the relevant
 * SyntacticStructure.
 */
	public void addSyntacticFeatureToStructure(
		SyntacticFeatureType pSFT,
		UserInternalFrame pUIF)
		throws Exception {
		if (getContainer() instanceof SyntacticStructure) {
			AbstractFeatureBuilder lAB = null;
			if (pSFT == SyntacticFeatureType.THETA) {
				lAB = new ThetaBuilder();
			} else if (pSFT == SyntacticFeatureType.CASE) {
				lAB = new CaseBuilder();
			} else if (pSFT == SyntacticFeatureType.FEATURE) {
				lAB = new FeatureBuilder();
			} else {
				Exception typeNotFoundError = new TypeNotFoundError();
				throw typeNotFoundError;
			}
			SyntacticFeatureSet lSFS = FeatureDirector.build(lAB, pUIF);
			SyntacticStructure lSS = ((SyntacticStructure) getContainer());
			lSS.getSyntacticFeatureSet().add(lSFS);
			((SyntacticStructure) getContainer()).testXY();
			displayTree();
		}
	}

/**
 * 
 * @return the title of the sentence
 */
	public String getName() {
		return getSentence().getName();
	}
/**
 * 
 * @param pString the title of the sentence
 */
	public void setName(String pString) {
		getSentence().setName(pString);

	}

/**
 * 
 * @param pContainer sets the highlight, which includes removing the old
 * highlight properly.
 */
	public void setHighlight(Component pContainer) {
		setContainer(pContainer);
		if (getContainer() != mOldContainer) {
			//boolean lPaintTest = false;
			if (getContainer() instanceof SyntacticStructure) {
				((SyntacticStructure) getContainer()).setOver(true);
				((SyntacticStructure) getContainer()).repaint();
			}
			if (mOldContainer instanceof SyntacticStructure) {
				((SyntacticStructure) mOldContainer).setOver(false);
				((SyntacticStructure) mOldContainer).repaint();
			}
			mOldContainer = getContainer();
		}
	}
/**
 * 
 * @param mSentence Accessor
 */
	public void setSentence(Sentence mSentence) {
		this.mSentence = mSentence;
	}
/**
 * 
 * @return Accessor
 */
	public Sentence getSentence() {
		return mSentence;
	}
/**
 * 
 * @param pObject The object whose class is studied to decide what popup menu to produce
 * @return The correct popup menu
 */
	public JPopupMenu getPopupMenu(Object pObject) {
		if (pObject instanceof SyntacticStructure) {
			return new SSPopupMenu(this, (SyntacticStructure) pObject);
		} else if (pObject instanceof SyntacticFeature) {
			return new SFPopupMenu(this, (SyntacticFeature) pObject);
		} else if (pObject instanceof SyntacticAssociation) {
			return new SAPopupMenu(this, (SyntacticAssociation) pObject);
		} else {
			return null;
		}

	}
/**
 * @param pSS The SyntacticStructure to reposition.
 * <br>
 * This command just hides the selected subtree, and recalls the glassPane interface so that
 * the user can reorder this child among the other children.
 */
	public void repositionSyntacticStructure(SyntacticStructure pSS) {
		if (pSS.getSyntacticParent() instanceof SyntacticStructure) {
			pSS.getSyntacticParent().getChildren().remove(pSS);
			hideTree(pSS);
			displayTree();
			getUIF().activateGlassPane(
				(SyntacticStructure) pSS.getSyntacticParent(),
				pSS);
		}
	}
/**
 * 
 * @param pSS the tree to hide.
 * <br>
 * this function just hides a subtree by giving it no boundary space.
 */
	private void hideTree(SyntacticStructure pSS) {
		pSS.setBounds(0, 0, 0, 0);
		pSS.getSyntacticStructureLines().setBounds(0, 0, 0, 0);
		for (int i = 0; i < pSS.getSyntacticFeatureSet().size(); i++) {
			SyntacticFeatureSet lSFS =
				(SyntacticFeatureSet) pSS.getSyntacticFeatureSet().get(i);
			for (int j = 0; j < lSFS.getSyntacticFeature().size(); j++) {
				SyntacticFeature lSF =
					(SyntacticFeature) lSFS.getSyntacticFeature().get(j);
				lSF.setBounds(0, 0, 0, 0);
			}
		}
		for (int i = 0; i < pSS.getSyntacticAssociation().size(); i++) {
			(
				(SyntacticAssociation) pSS.getSyntacticAssociation().get(
					i)).setBounds(
				0,
				0,
				0,
				0);
		}
		for (int i = 0; i < pSS.getChildren().size(); i++) {
			hideTree((SyntacticStructure) pSS.getChildren().get(i));
		}
	}
/**
 * 
 * @param mUIF Accessor
 */
	private void setUIF(UserInternalFrame mUIF) {
		this.mUIF = mUIF;
	}
/**
 * 
 * @return Accessor
 */
	public UserInternalFrame getUIF() {
		return mUIF;
	}
/**
 * 
 * @param pContainerPoint The point to test
 * @param pSource The source of the call.
 * @return Returns the structure under the point, if any.
 */
	public SyntacticStructure getUnder(Point pContainerPoint, Object pSource) {
		mUnder = null;
		getUnderRecursive(getSentence(), pContainerPoint, pSource);
		return mUnder;
	}
/**
 * 
 * @param pRT The RepositionTree to test
 * @param pContainerPoint The originating container point
 * @param pSource The original calling source.
 * <br>
 * this function is a basic test to see if a mouse pointer is hovering
 * over a syntacticstructure or not.  If it is, mUnder is set to that structure.
 * <br>
 * The formula is tail recursive.
 * <br>
 */
	private void getUnderRecursive(
		RepositionTree pRT,
		Point pContainerPoint,
		Object pSource) {
		if (pRT instanceof SyntacticStructure) {
			SyntacticStructure lSS = (SyntacticStructure) pRT;
			if ((pContainerPoint.x >= lSS.getBounds().x
				&& pContainerPoint.x <= lSS.getBounds().x + lSS.getBounds().width)
				&& (pContainerPoint.y >= lSS.getBounds().y
					&& pContainerPoint.y
						<= lSS.getBounds().y + lSS.getBounds().height)) {
				if (lSS != pSource) {
					mUnder = lSS;
				}
			}
		}

		for (int i = 0; i < pRT.getChildren().size(); i++) {
			getUnderRecursive(
				(RepositionTree) pRT.getChildren().get(i),
				pContainerPoint,
				pSource);
		}
	}
/**
 * 
 * @param pFile accessor
 */
	public void setFile(String pFile) {
		mFile = pFile;
	}
/**
 * 
 * @return Returns a file object based on the path - convenience method.
 */
	public File getFile() {
		if (mFile != null)
			return new File(mFile);
		return null;
	}
/**
 * 
 * @param pPicture Accessor - picture export name
 */
	public void setPicture(String pPicture) {
		mPicture = pPicture;
	}
/**
 * 
 * @return returns a file object pointing to the location of the picture.
 */
	public File getPicture() {
		if (mPicture != null)
			return new File(mPicture);
		return null;
	}
/**
 * 
 * @param mContainer accessor
 */
	public void setContainer(Component mContainer) {
		this.mContainer = mContainer;
	}
/**
 * 
 * @return accessor
 */
	public Component getContainer() {
		return mContainer;
	}
/**
 * 
 * @param pSSParent The SyntacticStructure that will receive the association
 * @param pFeature The feature to be associated.
 * <br>
 * This just properly builds an association and adds it to both a feature
 * and a SyntacticStructure.  Associations are liked objects whose
 * text changes with that of their associated features.  Useful in Syntax tasks.
 */
	public void associateSyntacticFeature(
		SyntacticStructure pSSParent,
		SyntacticFeature pFeature) {
		SyntacticAssociation lSA = new SyntacticAssociation(getUIF());
		lSA.setSyntacticStructure(pSSParent);
		lSA.setSyntacticFeature(pFeature);
		pFeature.getSyntacticAssociation().add(lSA);
		pSSParent.getSyntacticAssociation().add(lSA);
		getUIF().getContentPane().add(lSA);
		lSA.setHead(pFeature.getHead());
		displayTree();
	}
/**
 * 
 * @param mSF The feautreSet to delete.
 * This function deletes all the features in a feature set.
 * Since deleting the last feature also deletes the featureSet,
 * there will be no memory leaks here.
 */
	public void deleteSyntacticFeatureSet(SyntacticFeature mSF) {
		while (mSF.getSyntacticFeatureSet().getSyntacticFeature().size()
			!= 0) {
			deleteSyntacticFeature(
				(SyntacticFeature) mSF
					.getSyntacticFeatureSet()
					.getSyntacticFeature()
					.get(
					0));
		}

	}
/**
 * 
 * @param mSF The feature who'se featureSet will receive a new feature.
 * <br>
 * This is a simple class that adds a new feature to a featureSet.
 * Very useful if you have, say, a need for a third theta role.
 * (Sorry fellow computer geeks, but you really do need to learn Syntax
 * to understand this program!)
 */
	public void addSyntacticFeature(SyntacticFeature mSF) {
		SyntacticFeature lSF = new SyntacticFeature(getUIF());
		AttributedString lAttributedString = new AttributedString("Feature");
		Font lFont = new Font("Doulos SIL", Font.PLAIN, Sizer.fontSize() - 2);
		lAttributedString.addAttribute(TextAttribute.FONT, lFont);
		lSF.setSyntacticFeatureSet(mSF.getSyntacticFeatureSet());
		mSF.getSyntacticFeatureSet().getSyntacticFeature().add(lSF);
		lSF.setHead(lAttributedString);
		getUIF().getContentPane().add(lSF);
		displayTree();
	}
/**
 * @param mSA the association to be deleted.
 */
	public void deleteSyntacticAssociation(SyntacticAssociation mSA) {
		SyntacticStructure lSS = mSA.getSyntacticStructure();
		mSA.getSyntacticStructure().getSyntacticAssociation().remove(mSA);
		mSA.getSyntacticFeature().getSyntacticAssociation().remove(mSA);
		getUIF().getContentPane().remove(mSA);
		lSS.testXY();
		displayTree();

	}

//	private String printText(SyntacticStructure v) {
//		if (v != null)
//		{
//		AttributedString as = v.getHead();
//		AttributedCharacterIterator iter = as.getIterator();
//		int j = 0;
//		String s = "";
//		if (iter.first() != AttributedCharacterIterator.DONE)
//		{
//			j = 1;
//			while (iter.next() != AttributedCharacterIterator.DONE)
//			{
//				j++;
//			}
//		}
//		for (int i = 0; i < j;i++)
//		{
//			if(i == 0)
//			{
//				s = s + iter.first();
//			}
//			else
//				s = s + iter.next();
//		}
//		return s;
//		}
//		else
//		{
//			return "null";
//		}
//	}

}
