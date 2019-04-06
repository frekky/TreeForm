
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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

/**
 * @author Donald Derrick
 * @version 0.1
 * <br>
 * This is one of several Listener classes (part of the Java Command design pattern
 * interface) designed to fire UserControl commands that operate non-sentence
 * GUI interaction in TreeFrom
 *
 */
public class ListenerZoom implements ActionListener {

    /**
     * Constructor
     * @param pUserFrame - Passes a copy of the user frame (which currently works
     * as the facade for this program
     * <br>
     * NOTE: This is not strictly speaking the correct way to do things, and
     * future revisions should involve implementing a joined facade class instead of
     * using the UserFrame and the UserInternalFrame as the two facades.
     *
     * @uml.property name="mUserFrame"
     * @uml.associationEnd
     * @uml.property name="mUserFrame" multiplicity="(1 1)"
     */
    private UserFrame mUserFrame;

    public ListenerZoom(UserFrame pUserFrame) {
        super();
        mUserFrame = pUserFrame;
    }

    /**
     * @param pAE Passes a mouse event to the listener
     * <br>
     * This command makes sure that typed zoom information is correct, fixes it
     * if it is not, and sends the zoom text back to the JComboBox.
     * <br>
     * The focus is then returned to any previously highlited syntax components stored
     * in the observable clipboard.
     * <br>
     *
     */
    public void actionPerformed(ActionEvent pAE) {
        String lZoomString = ((String)((JComboBox)pAE.getSource()).getSelectedItem());
        float lZoom = 1.0F;
        try
        {
            if (lZoomString.substring(lZoomString.length()-1,lZoomString.length()).equals("%"))
            {
                lZoom = (Float.parseFloat(lZoomString.substring(0,lZoomString.length()-1))/100);
            }
            else
            {
                lZoom = (Float.parseFloat(lZoomString)/100);
            }
        }
        catch (NumberFormatException e)
        {
            lZoom = 1.0F;
        }
        mUserFrame.getObservableZoom().setValue(lZoom);
        mUserFrame.getUserControl().zoom(lZoom);
        //		if (mUserFrame.getObservableClipboard().getValue() != null)
        //		{
        //			mUserFrame.getObservableClipboard().getValue().requestFocus();
        //		}
    }

}
