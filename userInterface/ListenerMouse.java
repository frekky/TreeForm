
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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.SwingUtilities;

import syntaxTree.SyntacticStructure;

import enumerators.SyntacticFeatureType;
import enumerators.SyntacticOperationType;
import enumerators.SyntacticStructureType;
/**
 * @author Donald Derrick
 * @version 0.1 
 * 
 * This is one of several Listener classes (part of the Java Command design pattern
 * interface) designed to fire UserControl commands that operate non-sentence
 * GUI interaction in TreeFrom
 *  
 */
public class ListenerMouse implements MouseListener {

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
	 * 
	 * NOTE: This is not strictly speaking the correct way to do things, and
	 * future revisions should involve implementing a joined facade class instead of
	 * using the UserFrame and the UserInternalFrame as the two facades.
	 */
	public ListenerMouse(UserFrame pUserFrame) {

		mUserFrame = pUserFrame;
	}

	public void mouseClicked(MouseEvent pME) {
	}

	public void mouseEntered(MouseEvent pME) {
		UserBrowserButton lUBB = (UserBrowserButton) pME.getSource();
		lUBB.setHighlight(true);
	}

	public void mouseExited(MouseEvent pME) {
		UserBrowserButton lUBB = (UserBrowserButton) pME.getSource();
		lUBB.setHighlight(false);
	}

	public void mousePressed(MouseEvent pME) {
		
	}

	/**
	 * @param pME - Passes a mouse event to the listener
	 * 
	 * This command checks to see where the mouse was released.  If the object was
	 * released over a UserInternalFrame, it then checks to see if it was released
	 * over a SyntacticStructure.  The Observable Button's syntactic structure or 
	 * feature type is then passed to the SyntaxFacade so that the correct builder
	 * can generated the correct structure or feature.
	 * 
	 */

	public void mouseReleased(MouseEvent pME) {
		UserBrowserButton lUBB = (UserBrowserButton) pME.getSource();
		lUBB.getTempLabel().setVisible(false);
		Container container = mUserFrame.getContentPane();
		Point containerPoint =
			SwingUtilities.convertPoint(
				(Component) pME.getSource(),
				pME.getPoint(),
				container);
		Component lComponent =
			mUserFrame.getDesktopPane().getComponentAt(
				containerPoint.x,
				containerPoint.y);
		containerPoint =
			new Point(
				containerPoint.x - lUBB.getPressedX(),
				containerPoint.y - lUBB.getPressedY());
				
		if (lComponent instanceof UserInternalFrame) {
	
			containerPoint =
				SwingUtilities.convertPoint(
					(Component) pME.getSource(),
					pME.getPoint(),
					((UserInternalFrame) lComponent).getContentPane());
				if (lUBB.getButtonType() instanceof SyntacticStructureType)
				{
				try {
					((UserInternalFrame)lComponent).getSyntaxFacade().
					addSyntacticStructure((SyntacticStructureType) 
							lUBB.getButtonType(),((UserInternalFrame)lComponent), 
							((UserInternalFrame)lComponent).getSyntaxFacade().getContainer());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				else if (lUBB.getButtonType() instanceof SyntacticFeatureType)
				{
					try {
						((UserInternalFrame)lComponent).getSyntaxFacade().addSyntacticFeatureToStructure((SyntacticFeatureType) lUBB.getButtonType(),
								((UserInternalFrame)lComponent),
								((UserInternalFrame)lComponent).getSyntaxFacade().getContainer());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				else if (lUBB.getButtonType() instanceof SyntacticOperationType)
				{
					if (((UserInternalFrame)lComponent).getSyntaxFacade().getContainer() instanceof SyntacticStructure)
					{
						mUserFrame.getInternalFrame().activateMovementPane((SyntacticStructure) ((UserInternalFrame)lComponent).getSyntaxFacade().getContainer());
					}
				}
		}
	}
}