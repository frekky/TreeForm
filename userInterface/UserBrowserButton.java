
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
 * Created on 22-Jul-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package userInterface;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.font.FontRenderContext;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLayeredPane;
import javax.swing.plaf.ButtonUI;

import staticFunctions.Sizer;

/**
 * 
 * @author Donald Derrick
 * @version 0.1
 * 
 * This is the base class that holds all Browser Objects.  Browser Objects
 * are differentiated by their UI (pictures) and by their buttonType.
 * 
 */
public class UserBrowserButton extends JButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Object mButtonType;

	/**
	 * 
	 * @uml.property name="mUserFrame"
	 * @uml.associationEnd 
	 * @uml.property name="mUserFrame" multiplicity="(1 1)"
	 */
	private UserFrame mUserFrame;

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

	/**
	 * 
	 * @uml.property name="mCopy"
	 * @uml.associationEnd 
	 * @uml.property name="mCopy" multiplicity="(1 1)"
	 */
	private JButton mCopy;

	/**
 * 
 * @param pUserFrame The UserFrame, which is the main class/facade for each instance
 * of TreeForm
 * @param pButtonType The button type, which is either a SyntaxStructureType enumerator,
 * or a SyntaxFeatureType enumerator.
 */

	public UserBrowserButton(UserFrame pUserFrame, Object pButtonType) {
		super();
		mUserFrame = pUserFrame;
		mButtonType = pButtonType;
		mCopy = new JButton();
		
	}
/**
 * 
 * @return returns a copy of the temporary label that gets moved around the
 * screen when browser objects are selected and dragged around.
 */
	public JButton getTempLabel()
	{
		return mCopy;
	}
/**
 * 
 * sets the label of the button, as provided by the passed UserInterface.
 * In this case, a kind of ButtonUIAbstract.
 *
 */
	public void setLabel()
	{
		ButtonUI mUIObject = this.getUI();
		mCopy.setUI(mUIObject);
		mCopy.setVisible(false);
		mCopy.setBounds(0,0, Sizer.scaledButtonSize().width,Sizer.scaledButtonSize().height);
		mUserFrame.getLayeredPane().setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
		mUserFrame.getLayeredPane().add(mCopy,JLayeredPane.DRAG_LAYER);
		mUserFrame.getLayeredPane().moveToFront(mCopy);
	}
/**
 * 
 * @param pPressedX sets the PressedX value for positioning the temporary label
 * properly underneath the mouse.
 */
	public void setPressedX(int pPressedX) {
	}
/**
 * 
 * @return Returns the original mouse position X relative to the button.
 */
	public int getPressedX() {
		return this.getWidth()/2;
	}
/**
 * 
 * @param pPressedY sets the PressedY value for positioning the temporary label
 * properly underneat the mouse.
 */
	public void setPressedY(int pPressedY) {
	}
/**
 * 
 * @return Returns the original mouse position Y relative to the button.
 */
	public int getPressedY() {
		return 0;
	}
/**
 * 
 * @return Returns the ButtonType - used for Structure and Feature builders
 * 
 */
	public Object getButtonType()
	{
		return mButtonType;
	}
public void setHighlight(boolean b) {
	((ButtonUIAbstract) this.getUI()).setHighlight(b);
	
}

}
