
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


import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.beans.*;

/**
 * Menu component that handles the functionality expected of a standard
 * "Windows" menu for J applications.

 *
 * @author Donald Derrick
 * @version 0.1
 * <br>
 * date: 19-Aug-2004
 * <br>
 * <br>
 * This class modified from JavaWorld Magazine.
 * http://www.javaworld.com/javaworld/jw-05-2001/jw-0525-mdi.html?#resources
 */

public class UserMenuWindow extends JMenu {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     * @uml.property name="mUserFrame"
     * @uml.associationEnd
     * @uml.property name="mUserFrame" multiplicity="(1 1)"
     */
    private UserFrame mUserFrame;

    /**
     *
     * @uml.property name="mDesktop"
     * @uml.associationEnd
     * @uml.property name="mDesktop" multiplicity="(1 1)"
     */
    private UserDesktopPane mDesktop;

    /**
     *
     * @uml.property name="mCascade"
     * @uml.associationEnd
     * @uml.property name="mCascade" multiplicity="(1 1)"
     */
    private JMenuItem mCascade;

    /**
     *
     * @uml.property name="mTile"
     * @uml.associationEnd
     * @uml.property name="mTile" multiplicity="(1 1)"
     */
    private JMenuItem mTile;

    /**
     *
     * @param pString Title
     * @param pDesktop DesktopPane (containing all the internal frames)
     * @param pUserFrame UserFrame from this instance of TreeForm
     */
    public UserMenuWindow(String pString, UserDesktopPane pDesktop, UserFrame pUserFrame) {
        super(pString);
        mDesktop = pDesktop;
        mUserFrame = pUserFrame;
        mCascade = new JMenuItem((String) mUserFrame.getI18n().getObject("CASCADE_LABEL"));
        mTile = new JMenuItem((String) mUserFrame.getI18n().getObject("TILE_LABEL"));
        mCascade.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                mDesktop.cascadeFrames();
            }
        });
        mTile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                mDesktop.tileFrames();
            }
        });
        addMenuListener(new MenuListener() {
            public void menuCanceled (MenuEvent e) {}

            public void menuDeselected (MenuEvent e) {
                removeAll();
            }

            public void menuSelected (MenuEvent e) {
                buildChildMenus();
            }
        });
    }
    /**
     *
     * Sets up the children menus depending on the current desktop state
     */

    private void buildChildMenus() {
        int i;
        ChildMenuItem menu;
        JInternalFrame[] array = mDesktop.getAllFrames();

        add(mCascade);
        add(mTile);
        if (array.length > 0) addSeparator();
        mCascade.setEnabled(array.length > 0);
        mTile.setEnabled(array.length > 0);

        for (i = 0; i < array.length; i++) {
            menu = new ChildMenuItem(array[i]);
            menu.setState(i == 0);
            menu.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    JInternalFrame frame = ((ChildMenuItem)ae.getSource()).getFrame();
                    frame.moveToFront();
                    try {
                        frame.setSelected(true);
                    } catch (PropertyVetoException e) {
                        e.printStackTrace();
                    }
                }
            });
            menu.setIcon(array[i].getFrameIcon());
            add(menu);
        }
    }
    protected JMenuItem getCascade()
    {
        return mCascade;
    }
    protected JMenuItem getTile()
    {
        return mTile;
    }

    /**
     * This JCheckBoxMenuItem descendant is used to track the child frame that corresponds
     * to a give menu.
     */
    public class ChildMenuItem extends JCheckBoxMenuItem {

        /**
         *
         */
        private static final long serialVersionUID = 1L;
        /**
         *
         * @uml.property name="frame"
         */
        private JInternalFrame frame;


        public ChildMenuItem(JInternalFrame frame) {
            super(frame.getTitle());
            this.frame=frame;
        }

        /**
         *
         * @uml.property name="frame"
         */
        public JInternalFrame getFrame() {
            return frame;
        }

    }
}

