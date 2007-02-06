
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

import javax.swing.ImageIcon;
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
 * The class that contains the help menu.  Note full i18n.
 */
public class UserMenuHelp extends JMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @uml.property name="mAbout"
	 * @uml.associationEnd 
	 * @uml.property name="mAbout" multiplicity="(1 1)"
	 */
	private JMenuItem mAbout;

	/**
	 * 
	 * @uml.property name="mHelp"
	 * @uml.associationEnd 
	 * @uml.property name="mHelp" multiplicity="(1 1)"
	 */
	private JMenuItem mHelp;

	/**
	 * 
	 * @uml.property name="mUserFrame"
	 * @uml.associationEnd 
	 * @uml.property name="mUserFrame" multiplicity="(1 1)"
	 */
	private UserFrame mUserFrame;

	/**
	 * @param pString The title of the menu
	 * @param pUserFrame The UserFrame for this instance of TreeForm
	 */
	public UserMenuHelp(String pString, UserFrame pUserFrame) {
		super(pString);
		mUserFrame = pUserFrame;
		mHelp = new JMenuItem((String) mUserFrame.getI18n().getObject("CONTENTS_LABEL"));
		mHelp.addActionListener(new ListenerHelp(mUserFrame));
		mHelp.setMnemonic('H');
		this.add(mHelp);		
		mAbout = new JMenuItem((String) mUserFrame.getI18n().getObject("ABOUT_LABEL"),(ImageIcon) mUserFrame.getI18n().getObject("TREEFORM_ICON_SMALL"));
		mAbout.addActionListener(new ListenerAbout(mUserFrame));
		mAbout.setMnemonic('A');
		this.add(mAbout);
	}
	protected JMenuItem getAbout()
	{
		return mAbout;
	}
	protected JMenuItem getHelp()
	{
		return mHelp;
	}

}
