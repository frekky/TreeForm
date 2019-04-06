
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

import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

/**
 * @author Donald Derrick
 * @version 0.1
 *
 * This is one of several Listener classes (part of the Java Command design pattern
 * interface) designed to fire UserControl commands that operate non-sentence
 * GUI interaction in TreeFrom
 *
 */
public class ListenerInternalFrame implements InternalFrameListener {

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
    public ListenerInternalFrame(UserFrame pUserFrame) {
        mUserFrame = pUserFrame;
    }

    /**
     * @param pIFE - Passes an action event to the listener
     *
     * This command changes the observed zoom factor to that of the selected UIF.
     *
     */
    public void internalFrameActivated(InternalFrameEvent pIFE) {
        mUserFrame.getObservableZoom().setValue(((UserInternalFrame)pIFE.getSource()).getScale());
    }

    public void internalFrameClosed(InternalFrameEvent pIFE) {

    }

    /**
     * @param pIFE - Passes an action event to the listener
     *
     * This command closes an internalFrame and informs the
     *  observableNew that another IF has closed.
     *
     */

    public void internalFrameClosing(InternalFrameEvent pIFE) {
        mUserFrame.getUserControl().closeTree();
        mUserFrame.getObservableNew().setValue(mUserFrame.getDesktopPane().getAllFrames().length);
    }

    public void internalFrameDeactivated(InternalFrameEvent pIFE) {

    }

    public void internalFrameDeiconified(InternalFrameEvent pIFE) {

    }


    public void internalFrameIconified(InternalFrameEvent pIFE) {

    }

    public void internalFrameOpened(InternalFrameEvent pIFE) {

    }
}
