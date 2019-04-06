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

import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;

import syntaxTree.EditableComponent;
import syntaxTree.SyntacticAssociation;
import syntaxTree.SyntacticFeature;
import syntaxTree.SyntacticFeatureSet;
import syntaxTree.SyntacticStructure;

/**
 * @author Donald Derrick
 * @version 0.1
 *
 * This is one of several Listener classes (part of the Java Command design
 * pattern interface) designed to fire UserControl commands that operate
 * non-sentence GUI interaction in TreeFrom
 *
 */

public class ListenerMovementPane extends MouseInputAdapter {

    private UserFrame mUserFrame;

    /**
     * Constructor
     *
     * @param pUserFrame -
     *            Passes a copy of the user frame (which currently works as the
     *            facade for this program
     *
     * NOTE: This is not strictly speaking the correct way to do things, and
     * future revisions should involve implementing a joined facade class
     * instead of using the UserFrame and the UserInternalFrame as the two
     * facades.
     * @param structure
     */

    public ListenerMovementPane(UserFrame pUserFrame) {
        mUserFrame = pUserFrame;
    }

    /**
     * @param pME -
     *            Passes a mouse event to the listener
     *
     * This command sets the position of the mouse in the glass pane. This is
     * the mechanism used to change the colors of the dots and help the user
     * select where to position trees.
     *
     */

    public void mouseMoved(MouseEvent pME) {


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

        ((UserMovementPane) pME.getSource()).setPosition(pME);
    }

    public void mouseDragged(MouseEvent e) {

    }

    /**
     * @param pME -
     *            Passes a mouse event to the listener
     *
     * This command calls the commands to place the subtree in the correct
     * position among it's siblings.
     *
     */

    public void mouseClicked(MouseEvent pME) {

        Container container = mUserFrame.getContentPane();
        Point containerPoint = SwingUtilities.convertPoint((Component) pME
            .getSource(), pME.getPoint(), container);
        Component lComponent = mUserFrame.getDesktopPane().getComponentAt(
            containerPoint.x, containerPoint.y);

        if (lComponent instanceof UserInternalFrame) {
            Component hold = ((UserInternalFrame) lComponent).getSyntaxFacade()
                .getContainer();
            if (hold != null)
            {
                ((EditableComponent) hold).setOver(false);
                hold.repaint();
            }
            if (hold instanceof SyntacticAssociation)
            {
                hold = ((SyntacticAssociation)hold).getSyntacticStructure();
            }
            if (hold instanceof SyntacticFeature)
            {
                hold = ((SyntacticFeatureSet)((SyntacticFeature)hold).getSyntacticFeatureSet()).getSyntacticStructure();
            }
            if (hold instanceof SyntacticStructure)
            {
                ((UserMovementPane) pME.getSource())
                .setEnd((SyntacticStructure) hold);
            }
        }
        mUserFrame.getInternalFrame().deactivateMovementPane();
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