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

import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputAdapter;

/**
 * @author Donald Derrick
 * @version 0.1 
 * 
 * This is one of several Listener classes (part of the Java Command design pattern
 * interface) designed to fire UserControl commands that operate non-sentence
 * GUI interaction in TreeFrom
 *  
 */

public class ListenerBezierPane extends MouseInputAdapter {

	/**
	 * Constructor
	 * @param pUserFrame - Passes a copy of the user frame (which currently works
	 * as the facade for this program 
	 * 
	 * NOTE: This is not strictly speaking the correct way to do things, and
	 * future revisions should involve implementing a joined facade class instead of
	 * using the UserFrame and the UserInternalFrame as the two facades.
	 */


	public ListenerBezierPane(UserFrame pUserFrame) {
	}


	public void mouseMoved(MouseEvent pME) {
		
		
	}

	public void mouseDragged(MouseEvent e) {
		((UserBezierPane)e.getSource()).setPosition(e);
	}

	public void mouseClicked(MouseEvent pME) {
		if(pME.getClickCount() > 1)
		{
			((UserBezierPane)pME.getSource()).exit();
		}
	}

	public void mouseEntered(MouseEvent e) {
	
	}

	public void mouseExited(MouseEvent e) {
	
	}

	public void mousePressed(MouseEvent e) {
		
	}

	public void mouseReleased(MouseEvent e) {

	}

}
