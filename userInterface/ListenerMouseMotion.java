
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

import java.awt.Component;
import java.awt.Container;
import java.awt.Point;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.SwingUtilities;

/**
 * @author Donald Derrick
 * @version 0.1 
 * <br>
 * This is one of several Listener classes (part of the Java Command design pattern
 * interface) designed to fire UserControl commands that operate non-sentence
 * GUI interaction in TreeFrom
 *  
 */
public class ListenerMouseMotion implements MouseMotionListener {

	/**
	 * 
	 * @uml.property name="mUserFrame"
	 * @uml.associationEnd 
	 * @uml.property name="mUserFrame" multiplicity="(1 1)"
	 */
	private UserFrame mUserFrame;

	/**
	 * Constructor
	 * @param pUserFrame - Passes a copy of the user frame (which currently works
	 * as the facade for this program 
	 * <br>
	 * NOTE: This is not strictly speaking the correct way to do things, and
	 * future revisions should involve implementing a joined facade class instead of
	 * using the UserFrame and the UserInternalFrame as the two facades.
	 */
	public ListenerMouseMotion(UserFrame pUserFrame) {
		mUserFrame = pUserFrame;
	}

	/**
	 * @param pME - Passes a mouse event to the listener
	 * <br>
	 * This command makes a convenience reference to the UserBrowserButton.
	 * It then checks to see if the mouse is dragged over a UserInternalFrame.
	 * <br>
	 * If it is, it runs through a routine to highlight any structure, feature,
	 * or association under the mouse button.
	 * <br>
	 */

	public void mouseDragged(MouseEvent pME) {
		if (((pME.getModifiersEx() & InputEvent.SHIFT_DOWN_MASK) == 0) ||
				((pME.getModifiersEx() & InputEvent.ALT_DOWN_MASK) == 0))
		{
			UserBrowserButton lUBB = (UserBrowserButton) pME.getSource();
			Container lC = mUserFrame.getContentPane();
			Point lP =
				SwingUtilities.convertPoint(
					(Component) pME.getSource(),
					pME.getPoint(),
					lC);
			Component lComponent =
				mUserFrame.getDesktopPane().getComponentAt(
					lP.x,
					lP.y);
			lP =
				new Point(
					lP.x - lUBB.getWidth()/2,
					lP.y);
			
			lUBB.getTempLabel().setLocation(lP);
			lUBB.getTempLabel().setVisible(true);
			lUBB.repaint();
			if (lComponent instanceof UserInternalFrame) 
			{
							lP =
					SwingUtilities.convertPoint(
						(Component) pME.getSource(),
						pME.getPoint(),
						((UserInternalFrame) lComponent).getContentPane());
					
				((UserInternalFrame) lComponent).getSyntaxFacade().setHighlight(
					((UserInternalFrame) lComponent).getSyntaxFacade().getUnder(
						lP,
						null,true));
			}
		}
	}

	public void mouseMoved(MouseEvent pME) {

	}

}
