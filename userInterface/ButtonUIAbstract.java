
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


/*
 * Created on 24-Jul-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package userInterface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicButtonUI;

import staticFunctions.Sizer;

/**
 * @author Donald Derrick
 * @version 0.1
 *
 * This is an abstract class designed to extend the functionality of the BasicButtonUI
 * provided by Java.  The intent it to override the look and feel UI and provide our 
 * own painted contents for the Object Browser buttons (all the pretty buttons that 
 * are dragged to the main screen and used to create syntactic structures.)
 *
 */
public class ButtonUIAbstract extends BasicButtonUI {

	protected static ButtonUIAbstract mBUI = new ButtonUIAbstract();
	protected Graphics mGraphics;

	/**
	 * 
	 * @uml.property name="mComponent"
	 * @uml.associationEnd 
	 * @uml.property name="mComponent" multiplicity="(0 1)"
	 */
	protected JComponent mComponent;

	/**
	 * 
	 * @uml.property name="mButton"
	 * @uml.associationEnd 
	 * @uml.property name="mButton" multiplicity="(0 1)"
	 */
	protected AbstractButton mButton;

	/**
	 * 
	 * @uml.property name="mModel"
	 * @uml.associationEnd 
	 * @uml.property name="mModel" multiplicity="(0 1)"
	 */
	protected ButtonModel mModel;

	protected Graphics2D mGraphics2D;
	protected Dimension mDim;
	protected Font mFont;
	protected FontRenderContext mFrc;
	public ButtonUIAbstract() {
		super();
		//this.
	}
	/**
	 * @param pC
	 * 
	 * This instruction overrides the default installUI and makes sure the component
	 * is an AbstractButton.  This command therefore deliberately reduces the scope
	 * of this class.
	 * 
	 */
	public void installUI(JComponent pC) {
		
		AbstractButton lB = (AbstractButton) pC;
		super.installListeners(lB);
		
	}
	/**
	 * @param pG
	 * @param pC
	 * Pre: none
	 * Post: the standard tasks shared by all Object Browser buttons are completed
	 * 
	 * This method creates the convenience references for the Button Model,
	 * Graphics2d object, fonts etc...
	 * This method also sets the anti-aliasing RenderingHints so graphics
	 * work looks good regardless of screen resolution.
	 * 
	 */
	protected void prepaint(Graphics pG, JComponent pC) {
			
		mGraphics = pG;
		mComponent = pC;
		mButton = (AbstractButton) mComponent;
		mModel = mButton.getModel();
		mGraphics2D = (Graphics2D) mGraphics;
		mDim = Sizer.buttonSize();
		mGraphics2D.scale(Sizer.scaleWidth(), Sizer.scaleHeight());	
		mFont = new Font("Doulos SIL", Font.BOLD, Sizer.UIFontSize());
		mGraphics2D.setColor(Color.BLACK);
		mGraphics2D.setRenderingHint(
			RenderingHints.KEY_ANTIALIASING,
			RenderingHints.VALUE_ANTIALIAS_ON);
		mGraphics2D.setRenderingHint(
			RenderingHints.KEY_RENDERING,
			RenderingHints.VALUE_RENDER_QUALITY);
		mFrc = mGraphics2D.getFontRenderContext();	
	}
	protected void postpaint() {
		
		/**
		 * 
		 * This method does all the post painting operations, including drawing the
		 * rectangle border and setting highlighting when the mouse is over the button.
		 * 
		 */
		
		mGraphics2D.setColor(Color.GRAY);
		mGraphics2D.drawRect(0, 0, (int) mDim.getWidth() - 1, (int) mDim.getHeight() - 1);
		if (mModel.isArmed() && mModel.isPressed())
		{
			mGraphics2D.setColor(new Color(0,100,255,50));
			mGraphics2D.fillRect(0,0,mDim.width,mDim.height);
		}
		if (mButton.getIcon() != null) {	
		mButton.getIcon().paintIcon(
			mComponent,
			mGraphics,
			(int) mDim.getWidth(),
			(int) mDim.getHeight());
		}
	}
}
