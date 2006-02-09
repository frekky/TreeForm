
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
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.io.File;
import java.text.AttributedString;
import java.util.HashMap;
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
	private int mMaxWidth;
	private int mMaxHeight;

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
		redisplayTree();

	}
/**
 * 
 * @param pRT RepositionTree interface
 * @param pHM A Hashmap of levels, more below
 * @param pLevel The current leve.
 * <br>
 * Levels represents the position of the syntacticStructure in relation to the top of
 * the tree
 * <br>
 * The HashMap is a collection of SyntacticStructures organized by these levels.
 * It is just another way of loooking at the tree.
 */
	private void getLevels(RepositionTree pRT, HashMap pHM, int pLevel) {
		if (pHM.get(new Integer(pLevel)) == null) {
			pHM.put(new Integer(pLevel), new LinkedList());
		}
		((LinkedList) pHM.get(new Integer(pLevel))).add(pRT);
		for (int i = 0; i < pRT.getChildren().size(); i++) {
			getLevels(
				(RepositionTree) pRT.getChildren().get(i),
				pHM,
				pLevel + 1);
		}
	}
/**
 * 
 * @param pRT RepositionTree (SyntacticStructures and Sentences)
 * @param pX (The X position)
 * @param pY (The Y position)
 * <br>
 * This is a function designed to set the buttonX and buttonY position
 * of the tree + pX and +pY pixels.  This is a routine that changes
 * unscaled values of the position of a tree and is used in the redisplayTree method.
 */
	private void moveTree(RepositionTree pRT, int pX, int pY) {
		if (pRT instanceof SyntacticStructure) {
			SyntacticStructure pSS = (SyntacticStructure) pRT;
			pSS.setButtonX(pSS.getButtonX() + pX);
			pSS.setButtonY(pSS.getButtonY() + pY);
		}
		for (int i = 0; i < pRT.getChildren().size(); i++) {
			moveTree((RepositionTree) pRT.getChildren().get(i), pX, pY);
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
		moveSubtree(pSS, lX, lY);
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
	private void moveSubtree(SyntacticStructure pSS, int pX, int pY) {
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
			moveSubtree((SyntacticStructure) pSS.getChildren().get(i), pX, pY);
		}
	}
/**
 * 
 * @param pRT RepositionTree
 * @param pLeftMost The variable holding the leftmost box text beginning
 * @return The leftmost text beginning of a box.
 * <br>
 * This function is designed to make sure that all text is always visible on the 
 * screen.  This recursive function returns the leftmost position for processing.
 */
	private int getLeftmostX(RepositionTree pRT, int pLeftMost) {

				int lLeftMost = 0;
				for (int i = 0; i < pRT.getChildren().size(); i++) {
					lLeftMost =
						((SyntacticStructure) pRT.getChildren().get(i))
							.getButtonX()
							- (((SyntacticStructure) pRT.getChildren().get(i))
								.getTextWidth()
								/ 2);
					pLeftMost = (pLeftMost < lLeftMost ? pLeftMost : lLeftMost);
					pLeftMost =
						getLeftmostX(
							(RepositionTree) pRT.getChildren().get(i),
							pLeftMost);
				}
				return pLeftMost;
	}
/**
 * This is the main public function for redisplaying trees.  It works like this:
 * <br>
 * First it builds a hashmap of the tree containing all the levels of the tree.
 * This information is used for a 98% accurate collision detection system.
 * <br>
 * Then the program resets all the tree positions
 * <br>
 * Next it repositions the Y axis of the tree based on a simple formula.
 * <br>
 * Then it sets a test flag for collision detection.
 * <br>
 * The program then repositions the tree to negate colissions - one at a time.
 * It continues until no position are detected:
 * WARNING - this formula can introduce infinite loops!
 * <br>
 * The program then moves the tree right until it is completely visible on the screen.
 * <br>
 * Teh program then resizes the InternalFrame by getting the minimum size,
 * and setting the bounds based on this information.
 * <br>
 * Next the program sets the preferred size to the bounds, and revalidates.  Revalidation
 * is used to force scrollbars to reappear. Works great (finally)
 *
 */
	public void redisplayTree() {
		HashMap lHM = new HashMap();
		this.getLevels(getSentence(), lHM, 0);
		this.resetTree(getSentence());
		this.repositionY(getSentence());
		boolean lTest = false;
		while (!lTest) {
			this.setX(getSentence());
			lTest = this.repositionX(lHM);
		}
		this.moveTree(
			getSentence(),
			(- (this.getLeftmostX(getSentence(), 0)) + 5),
			5);
		this.displayTree(getSentence());
		setUIFBounds();
		
	}
/**
 * This is the algorithm for telling the UIF how big it should be.  The
 * goal is to make sure that a viewer can ALWAYS easily scroll around
 * and see the entire tree, no matter how much they zoom.
 * <br>
 * The function grabs the minimum bounds from the UIF, and then
 * checks the new maximum bounds based on leftmost and rightmost positions.
 * <br>
 * The method then chooses the largest of the two, and sets the bounds accordingly.
 * <br>
 */
public void setUIFBounds() {
	mMaxHeight = (int) (getBottommostY(getSentence(), 0) * Sizer.scaleHeight() * getUIF().getScale());
	mMaxHeight = mMaxHeight > mUIF.getMinHeight() ? mMaxHeight : mUIF.getMinHeight();
	mMaxWidth = (int) (getRightmostX(getSentence(), 0) * Sizer.scaleWidth() * getUIF().getScale());
	mMaxWidth = mMaxWidth > mUIF.getMinWidth() ? mMaxWidth : mUIF.getMinWidth();
	mUIF.setBounds(
		mUIF.getBounds().x,
		mUIF.getBounds().y,
		mMaxWidth,
		mMaxHeight);
	mUIF.getDesktopPane().setPreferredSize(
		new Dimension(
			mUIF.getBounds().x + mMaxWidth,
			mUIF.getBounds().y + mMaxHeight));
	mUIF.getDesktopPane().revalidate();
}

/**
 * @param pRT Retursive SyntacticStructure analysis
 * @param pBottomMost the starting bottommost value.
 * @return Returns the lowest Y position + 20 pixels.
 * <br>
 * The formula just gets tye Y position of each SyntacticStructure and adds the
 * buttonHeight (which is the height of the SyntacticStructure, it's lines,
 * it's feature sets, and it's associations put together.
 * <br>
 */
private int getBottommostY(RepositionTree pRT, int pBottomMost) {
	int lBottomMost = 0;
	for (int i = 0; i < pRT.getChildren().size(); i++) {
		lBottomMost =
			((SyntacticStructure) pRT.getChildren().get(i))
				.getButtonY()
				+ (((SyntacticStructure) pRT.getChildren().get(i))
					.getButtonHeight() + 20);
		pBottomMost = (pBottomMost > lBottomMost ? pBottomMost : lBottomMost);
		pBottomMost =
			getBottommostY(
				(RepositionTree) pRT.getChildren().get(i),
				pBottomMost);
	}
	return pBottomMost;
}

/**
 * 
 * @param pRT RepositionTree
 * @param pLeftMost The variable holding the rightmost box text beginning
 * @return The leftmost text beginning of a box.
 * <br>
 * This function is designed to make sure that all text is always visible on the 
 * screen.  This recursive function returns the leftmost position for processing.
 */
private int getRightmostX(RepositionTree pRT, int pRightMost) {
	int lRightMost = 0;
	for (int i = 0; i < pRT.getChildren().size(); i++) {
		lRightMost =
			((SyntacticStructure) pRT.getChildren().get(i))
				.getButtonX()
				+ (((SyntacticStructure) pRT.getChildren().get(i))
					.getTextWidth()/2) + 20;
		pRightMost = (pRightMost > lRightMost ? pRightMost : lRightMost);
		
		pRightMost =
			getRightmostX(
				(RepositionTree) pRT.getChildren().get(i),
				pRightMost);
	}
	return pRightMost;
}

/**
 * 
 * @param pRT The tree to be displayed - recursively.
 * <br>
 * This function is large, and does a lot (probably too much).
 * Here goes:
 * <br>
 * First, the program sets the base bounds of the syntacticStructure based on the buttonX
 * position minus the textWidth/2
 * <br>
 * Then it scales the bounds appropriately 
 * <br>
 * Then it starts adding in all the Features in all the featureset based on this formula:
 * <br>
 * get the width of the featureSet, which is the total width of the features
 * <br>
 * add the features horizontally.
 * <br>
 * <br>
 * Then the program adds the associations to the screen
 * vertically
 * <br> 
 * Then the function displays the lines below all the features and structures
 * <br>
 * Lastly, the function calls itself recursively for each child of the passed in
 * SyntacticStructure
 * <br>
 */
	public void displayTree(RepositionTree pRT) {
		if (pRT instanceof SyntacticStructure) {
			SyntacticStructure pSS = (SyntacticStructure) pRT;

			pSS.setBounds(
				(int) ((pSS.getButtonX() - (pSS.getTextWidth() / 2))),
				(int) (pSS.getButtonY()),
				(int) (pSS.getTextWidth()),
				(int) (pSS.getTextHeight()));

			pSS.setBounds(
				(int) (pSS.getBounds().x
					* Sizer.scaleWidth()
					* getUIF().getScale()),
				(int) (pSS.getBounds().y
					* Sizer.scaleWidth()
					* getUIF().getScale()),
				(int) (pSS.getBounds().width
					* Sizer.scaleWidth()
					* getUIF().getScale()),
				(int) (pSS.getBounds().height
					* Sizer.scaleWidth()
					* getUIF().getScale()));
			int lFeatureHeight = 0;
			for (int i = 0; i < pSS.getSyntacticFeatureSet().size(); i++) {
				SyntacticFeatureSet lSFS =
					(SyntacticFeatureSet) pSS.getSyntacticFeatureSet().get(i);
				int lFeatureWidth = 0;
				for (int j = 0; j < lSFS.getSyntacticFeature().size(); j++) {
					SyntacticFeature lSF =
						(SyntacticFeature) lSFS.getSyntacticFeature().get(j);
					lSF.setBounds(
						pSS.getButtonX()
							- (lSFS.getWidth() / 2)
							+ lFeatureWidth,
						pSS.getButtonY() + pSS.getTextHeight() + lFeatureHeight,
						lSF.getTextWidth(),
						lSF.getTextHeight());
					lSF.setBounds(
						(int) (lSF.getBounds().x
							* Sizer.scaleWidth()
							* getUIF().getScale()),
						(int) (lSF.getBounds().y
							* Sizer.scaleWidth()
							* getUIF().getScale()),
						(int) (lSF.getBounds().width
							* Sizer.scaleWidth()
							* getUIF().getScale()),
						(int) (lSF.getBounds().height
							* Sizer.scaleWidth()
							* getUIF().getScale()));

					lFeatureWidth += lSF.getTextWidth();
				}
				lFeatureHeight += lSFS.getHeight();
			}

			for (int i = 0; i < pSS.getSyntacticAssociation().size(); i++) {

				SyntacticAssociation lSA =
					(SyntacticAssociation) pSS.getSyntacticAssociation().get(i);

				lSA.setBounds(
					pSS.getButtonX() - (lSA.getTextWidth() / 2),
					pSS.getButtonY() + pSS.getTextHeight() + lFeatureHeight,
					lSA.getTextWidth(),
					lSA.getTextHeight());
				lSA.setBounds(
					(int) (lSA.getBounds().x
						* Sizer.scaleWidth()
						* getUIF().getScale()),
					(int) (lSA.getBounds().y
						* Sizer.scaleWidth()
						* getUIF().getScale()),
					(int) (lSA.getBounds().width
						* Sizer.scaleWidth()
						* getUIF().getScale()),
					(int) (lSA.getBounds().height
						* Sizer.scaleWidth()
						* getUIF().getScale()));

				lFeatureHeight += lSA.getTextHeight();
			}

			pSS.getSyntacticStructureLines().setBounds(
				(int) ((pSS.getButtonX() - (pSS.getButtonWidth() / 2))),
				(int) (pSS.getButtonY()
					+ pSS.getTextHeight()
					+ 1
					+ lFeatureHeight),
				(int) (pSS.getButtonWidth()),
				(int) (Sizer.lineLength() + 2));

			pSS.getSyntacticStructureLines().setBounds(
				(int) (pSS.getSyntacticStructureLines().getBounds().x
					* Sizer.scaleWidth()
					* getUIF().getScale()),
				(int) (pSS.getSyntacticStructureLines().getBounds().y
					* Sizer.scaleWidth()
					* getUIF().getScale()),
				(int) (pSS.getSyntacticStructureLines().getBounds().width
					* Sizer.scaleWidth()
					* getUIF().getScale()),
				(int) (pSS.getSyntacticStructureLines().getBounds().height
					* Sizer.scaleWidth()
					* getUIF().getScale()));
		}

		for (int i = 0; i < pRT.getChildren().size(); i++) {
			displayTree((RepositionTree) pRT.getChildren().get(i));
		}

	}
/**
 * 
 * @param pHM The hashmap.
 * @return true if there was no repositioning, otherwise false
 * This function is part of the collision detection system.
 * It calls each repositionling level from the BOTTOM to the TOP level.
 */
	private boolean repositionX(HashMap pHM) {
		int i = 0;
		while (pHM.get(new Integer(i + 1)) != null) {
			i++;
		}
		for (int j = i; j > 0; j--) {
			if (!repositionUp(pHM, j)) {
				return false;
			}
		}
		return true;
	}
/**
 * 
 * @param pHM The HashMap of all the SyntacticStructures
 * @param pLevel The level of the repositioning
 * @return collision detected or not.
 * <br>
 * Here's how it works:
 * <br>
 * walk through all the planks from 0 to the SECOND last one (no need to care about
 * the last one, since there are no objects in which the LAST item on a level may
 * collide!
 * <br>
 * <br>
 * Create two convenience references for the object to test, and it's closest 
 * neighbour to the right.
 * <br>
 * Then check to see if the buttonX position + the buttonWidth/2 + the MinimumWidth/2
 * is greater than the next buttonX position + it's buttonWidth/2 - it's minimumWidth/2
 * .  If this is the case,the two buttons would be drawn overlapping, and we've
 * detected collision.
 * <br>
 * If collision is detected, find out where the two buttons share a common
 * ancestor.
 * <br>
 * set the minimum width of each of the children of the common ancestor to the 
 * collision distance, and leave the function.
 * <br>
 * The base program will then test for collisions again, and in this way
 * all the collisions get repaired.
 * <br>
 * Stupid, but it works!
 */
	private boolean repositionUp(HashMap pHM, int pLevel) {
		for (int i = 0;
			i < ((LinkedList) pHM.get(new Integer(pLevel))).size() - 1;
			i++) {
			SyntacticStructure lSS =
				((SyntacticStructure) ((LinkedList) pHM
					.get(new Integer(pLevel)))
					.get(i));
			SyntacticStructure lSSNext =
				((SyntacticStructure) ((LinkedList) pHM
					.get(new Integer(pLevel)))
					.get(i + 1));
			if (lSS.getButtonX()
				+ ((lSS.getButtonWidth() / 2) + (lSS.getMinWidth() / 2))
				> lSSNext.getButtonX()
					+ (lSS.getButtonWidth() / 2)
					- (lSSNext.getMinWidth() / 2)) {
				int lMinWidth =
					(lSS.getButtonX()
						+ ((lSS.getButtonWidth() / 2) + (lSS.getMinWidth() / 2))
						- (lSSNext.getButtonX()
							+ (lSS.getButtonWidth() / 2)
							- (lSSNext.getMinWidth() / 2)));

				SyntacticStructure lRT =
					(SyntacticStructure) lSS.getSyntacticParent();
				SyntacticStructure lRTNext =
					(SyntacticStructure) lSSNext.getSyntacticParent();
				while (lRT != lRTNext) {
					lRT = (SyntacticStructure) lRT.getSyntacticParent();
					lRTNext = (SyntacticStructure) lRTNext.getSyntacticParent();
				}
				for (int j = 0; j < lRT.getChildren().size(); j++) {
					(
						(SyntacticStructure) lRT.getChildren().get(
							j)).setMinWidth(
						((SyntacticStructure) lRT.getChildren().get(j))
							.getMinWidth()
							+ lMinWidth);
				}
				return false;
			}
		}
		return true;
	}
/**
 * 
 * @param pRT The SyntacticStructure that will get a new buttonX
 * <br>
 * The new buttonX must be equal to the buttonX of the parent - the childwidth/2 of
 * the parent + the minimumWidth/2 of itself + the minimumwidth of all the children
 * to the left.
 * <br>
 * If there is no parent, i.e. this is the top of the tree, just set the position to 0
 * minus the textwidth/2.
 * <br>
 * Do this recursively for each child node.
 */
	private void setX(RepositionTree pRT) {
		if (pRT instanceof SyntacticStructure) {
			SyntacticStructure pSS = ((SyntacticStructure) pRT);
			if (pSS.getSyntacticParent() != null) {
				int lLength = 0;
				int lPosition = 0;
				SyntacticStructure lParent =
					(SyntacticStructure) pSS.getSyntacticParent();

				for (int i = 0; i < lParent.getChildren().indexOf(pSS); i++) {
					lPosition
						+= ((SyntacticStructure) lParent.getChildren().get(i))
							.getMinWidth();
				}
				pSS.setButtonX(
					(int) (lParent.getButtonX()
						- (lParent.getChildWidth() / 2)
						+ (pSS.getMinWidth() / 2)
						+ (lPosition)));

			} else {
				pSS.setButtonX(- (int) ((pSS.getTextWidth() / 2)));
			}
		}
		for (int i = 0; i < pRT.getChildren().size(); i++) {
			setX((RepositionTree) pRT.getChildren().get(i));
		}
	}
/**
 * 
 * @param pRT RepositionTree
 * <br>
 * Position the buttonY equal to the buttonY of the parent + the buttonHeight
 * of the parent, do this recursively.
 * <br>
 * The topmost node is of course Y = 0;
 */
	private void repositionY(RepositionTree pRT) {
		if (pRT instanceof SyntacticStructure) {
			SyntacticStructure pSS = ((SyntacticStructure) pRT);
			if (pSS.getSyntacticParent() == null) {
				pSS.setButtonY(0);
			} else {
				SyntacticStructure pSSParent =
					((SyntacticStructure) pSS.getSyntacticParent());
				pSS.setButtonY(
					pSSParent.getButtonY() + pSSParent.getButtonHeight());
			}
		}
		for (int i = 0; i < pRT.getChildren().size(); i++) {
			repositionY((RepositionTree) pRT.getChildren().get(i));
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
				redisplayTree();
			} else {
				hideTree(pChild);
				redisplayTree();
				getUIF().activateGlassPane(pParent, pChild);
			}
		} else {
			redisplayTree();
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
		redisplayTree();
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
		redisplayTree();
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
			SyntacticStructure lSyntacticStructure;

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
			redisplayTree();
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
 * @param pRT The RepositionTree to reset.
 * <br>
 * <br>
 * resets all the X and Y positions to 0, and resets the minimum width
 * so that collision detection will work correctly.
 */
	private void resetTree(RepositionTree pRT) {
		for (int i = 0; i < pRT.getChildren().size(); i++) {
			resetTree((RepositionTree) pRT.getChildren().get(i));
		}

		if (pRT instanceof SyntacticStructure) {
			SyntacticStructure pSS = (SyntacticStructure) pRT;
			pSS.setButtonX(0);
			pSS.setButtonY(0);
			pSS.setMinWidth(pSS.getTextWidth());

		}
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
			redisplayTree();
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
		redisplayTree();
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
		redisplayTree();
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
		redisplayTree();

	}
}
