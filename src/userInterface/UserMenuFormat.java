
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

import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 *
 * @author Donald Derrick
 * @version 0.1
 * <br>
 * date: 19-Aug-2004
 * <br>
 * <br>
 * The class that contains the format menu.  Note full i18n.
 */
public class UserMenuFormat extends JMenu {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     * @uml.property name="mFont"
     * @uml.associationEnd
     * @uml.property name="mFont" multiplicity="(1 1)"
     */
    private JMenuItem mFont;

    /**
     *
     * @uml.property name="mUserFrame"
     * @uml.associationEnd
     * @uml.property name="mUserFrame" multiplicity="(1 1)"
     */
    private UserFrame mUserFrame;

    /**
     *
     * @param pString The string containing the title of the menu
     * @param pUserFrame The UserFrame for this instance of TreeForm
     */
    public UserMenuFormat(String pString, UserFrame pUserFrame) {
        super(pString);
        mUserFrame = pUserFrame;
        mFont = new JMenuItem((String) mUserFrame.getI18n().getObject("FONT_LABEL"));
        //mFont.addActionListener(new ListenerFontName(mUserFrame));
        //TODO: a menu option for changing font information (if I ever care!)
        mFont.setMnemonic('D');
        this.add(mFont);
    }
    protected JMenuItem getMenuItemFont()
    {
        return mFont;
    }
}
