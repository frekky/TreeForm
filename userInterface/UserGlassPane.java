
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

package userInterface;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.geom.Arc2D;
import java.awt.geom.Point2D;
import java.util.HashMap;

import javax.swing.JComponent;

import staticFunctions.Sizer;
import syntaxTree.SyntacticStructure;

/**
 * 
 * @author Donald Derrick
 * @version 0.1
 * <br>
 * date: 19-Aug-2004
 * <br>
 * <br>
 * This is the glass pane used to draw all the pretty red/green dots used for 
 * positioning/repositioning subtrees.
 */
public class UserGlassPane extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @uml.property name="mSSChild"
	 * @uml.associationEnd 
	 * @uml.property name="mSSChild" multiplicity="(1 1)"
	 */
	private SyntacticStructure mSSChild;

	private int mMaxPosition;

	/**
	 * 
	 * @uml.property name="mPositions"
	 * @uml.associationEnd 
	 * @uml.property name="mPositions" multiplicity="(0 1)" qualifier="new:java.lang.Integer
	 * value:java.lang.Float"
	 */
	private HashMap mPositions;

	/**
	 * 
	 * @uml.property name="mPosition"
	 * @uml.associationEnd 
	 * @uml.property name="mPosition" multiplicity="(0 -1)" elementType="syntaxTree.SyntacticStructure"
	 */
	private int mPosition;

	/**
	 * 
	 * @uml.property name="mPoint"
	 * @uml.associationEnd 
	 * @uml.property name="mPoint" multiplicity="(0 0)"
	 */
	private Point2D.Float mPoint;

	/**
	 * 
	 * @uml.property name="mSS"
	 * @uml.associationEnd 
	 * @uml.property name="mSS" multiplicity="(1 1)"
	 */
	private SyntacticStructure mSS;

	/**
	 * 
	 * @uml.property name="mUserFrame"
	 * @uml.associationEnd 
	 * @uml.property name="mUserFrame" multiplicity="(1 1)"
	 */
	private UserFrame mUserFrame;

/**
 * 
 * @param pUserFrame The UserFrame for this instance of TreeForm
 * @param pSS The SyntacticStructure highlighted for child insertion
 * @param pSSChild The SyntacticStructure highlighted for insertion AS a child
 * 
 */
	public UserGlassPane(
		UserFrame pUserFrame,
		SyntacticStructure pSS,
		SyntacticStructure pSSChild) {
		mUserFrame = pUserFrame;
		mSS = pSS;
		mSSChild = pSSChild;
	}
/**
 * paintComponent is the command for drawing the circles.
 * The Algoritm is simple:
 * <br><br>
 * Turn on antialiasing
 * <br>
 * Build a rectangle containing the bounds of the syntacticstructurelines
 * beneath the highlighted structure
 * <br>
 * Set the scale of the component to be painted
 * <br>
 * Draw the circles midway between each line and just below them
 * <br>
 */
	public void paintComponent(Graphics g) {
		Graphics2D lGraphics2D = (Graphics2D) g;
		lGraphics2D.setRenderingHint(
			RenderingHints.KEY_ANTIALIASING,
			RenderingHints.VALUE_ANTIALIAS_ON);
		lGraphics2D.setRenderingHint(
			RenderingHints.KEY_RENDERING,
			RenderingHints.VALUE_RENDER_QUALITY);
		lGraphics2D.scale(
				Sizer.scaleWidth()
					* mUserFrame.getDesktopPane().getInternalFrame().getScale(),
				Sizer.scaleHeight()
					* mUserFrame.getDesktopPane().getInternalFrame().getScale());
		mPositions = new HashMap();
		Rectangle lRectangle = mSS.getSyntacticStructureLines().getBounds();
		mPoint =
			new Point2D
				.Float(
				lRectangle.x / (Sizer.scaleWidth() * mUserFrame.getDesktopPane().getInternalFrame().getScale()),
				(lRectangle.y + lRectangle.height/6)/ (Sizer.scaleHeight() * mUserFrame.getDesktopPane().getInternalFrame().getScale()));
		SyntacticStructure left = (SyntacticStructure) mSS.getChildren().getFirst();
		SyntacticStructure right = (SyntacticStructure) mSS.getChildren().getLast();
		int lI = 0;
		for (int i = 0; i < mSS.getChildren().size(); i++)
		{	
			SyntacticStructure w = (SyntacticStructure) mSS.getChildren().get(i);
			lI = (int) (w.getButtonX()-left.getButtonX());
			drawArc(lGraphics2D, lI,i);
			mPositions.put(new Integer(i),new Float((lI + mPoint.x) * Sizer.scaleWidth() * mUserFrame.getDesktopPane().getInternalFrame().getScale()));
		}
		lI = (int) (right.getButtonX()+right.getButtonWidth()-left.getButtonX());
		drawArc(lGraphics2D, lI,mSS.getChildren().size());
		mPositions.put(new Integer(mSS.getChildren().size()),new Float((lI + mPoint.x) * Sizer.scaleWidth() * mUserFrame.getDesktopPane().getInternalFrame().getScale()));
		mMaxPosition = mSS.getChildren().size();
	}
/**
 * 
 * @param pSS Sets the parent SyntacticStructure
 */
	public void setSyntacticStructure(SyntacticStructure pSS) {
		mSS = pSS;
	}
/**
 * 
 * @param lGraphics2D draw the individual circles.
 * @param pRelativePosition The relative position needed for putting the circle in
 * the correct place on the screen
 * @param pPosition The absoluteposition of the child node - used to draw the green
 * circle if the mouse is in the correct place to select that subtree insertion point.
 */
	private void drawArc(
		Graphics2D lGraphics2D,
		int pRelativePosition,
		int pPosition) {
		if (pPosition == getPosition()) {
			Color lColor = new Color(0, 255, 0);
			lGraphics2D.setColor(lColor);
			Arc2D lArc =
				new Arc2D.Float(
					mPoint.x + pRelativePosition-3,
					mPoint.y - 1,
					6,
					6,
					0,
					360,
					Arc2D.PIE);
			lGraphics2D.fill(lArc);
		} else {
			Color lColor = new Color(255, 0, 0);
			lGraphics2D.setColor(lColor);
			Arc2D lArc =
				new Arc2D.Float(
					mPoint.x + pRelativePosition-2,
					mPoint.y,
					4,
					4,
					0,
					360,
					Arc2D.PIE);
			lGraphics2D.fill(lArc);
		}
	}
/**
 * 
 * @param pME The position of the mouse in relation to where to insert the subtree
 * if the mouse is clicked.
 */
	public void setPosition(MouseEvent pME) {
		mPosition = mMaxPosition;
		for (int i = mMaxPosition; i >= 0; i--) {
			if (pME.getX()
				< ((Float) mPositions.get(new Integer(i))).intValue()) {
				mPosition = i;
			}
		}
		repaint();
	}
/**
 * 
 * @return Returns the selected insertion position.
 */
	public int getPosition() {
		return mPosition;
	}
/**
 * 
 * Sets the child subtree in the correct position.
 */
	public void setChild() {
		mSS.getChildren().add(mPosition, mSSChild);
		mSSChild.setSyntacticParent(mSS);
		mUserFrame
			.getSyntaxFacade()
			.displayTree();
		mUserFrame.getDesktopPane().getInternalFrame().deactivateGlassPane();
	}
}