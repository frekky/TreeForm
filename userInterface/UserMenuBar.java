
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
import javax.swing.JMenuBar;
/**
 * 
 * @author Donald Derrick
 * @version 0.1
 * <br>
 * date: 19-Aug-2004
 * <br>
 * <br>
 *
 */
public class UserMenuBar extends JMenuBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @uml.property name="mHelp"
	 * @uml.associationEnd 
	 * @uml.property name="mHelp" multiplicity="(1 1)"
	 */
	private UserMenuHelp mHelp;

	/**
	 * 
	 * @uml.property name="mWindow"
	 * @uml.associationEnd 
	 * @uml.property name="mWindow" multiplicity="(1 1)"
	 */
	private UserMenuWindow mWindow;

	/**
	 * 
	 * @uml.property name="mFormat"
	 * @uml.associationEnd 
	 * @uml.property name="mFormat" multiplicity="(1 1)"
	 */
	private UserMenuFormat mFormat;

	/**
	 * 
	 * @uml.property name="mView"
	 * @uml.associationEnd 
	 * @uml.property name="mView" multiplicity="(1 1)"
	 */
	private UserMenuView mView;

	/**
	 * 
	 * @uml.property name="mEdit"
	 * @uml.associationEnd 
	 * @uml.property name="mEdit" multiplicity="(1 1)"
	 */
	private UserMenuEdit mEdit;

	/**
	 * 
	 * @uml.property name="mFile"
	 * @uml.associationEnd 
	 * @uml.property name="mFile" multiplicity="(1 1)"
	 */
	private UserMenuFile mFile;

	/**
	 * 
	 * @uml.property name="mUserFrame"
	 * @uml.associationEnd 
	 * @uml.property name="mUserFrame" multiplicity="(1 1)"
	 */
	private UserFrame mUserFrame;

/**
 * 
 * @param pUserFrame the UserFrame for this instance of TreeForm
 */
	public UserMenuBar(UserFrame pUserFrame) {
		super();
		mUserFrame = pUserFrame;
		mFile = new UserMenuFile((String) mUserFrame.getI18n().getObject("FILE_LABEL"),mUserFrame);
		mFile.setMnemonic('F');
		this.add(mFile);
		mEdit = new UserMenuEdit((String) mUserFrame.getI18n().getObject("EDIT_LABEL"),mUserFrame);
		mEdit.setMnemonic('E');
		this.add(mEdit);
//		mView = new UserMenuView((String) mUserFrame.getI18n().getObject("VIEW_LABEL"),mUserFrame);
//		mView.setMnemonic('V');
//		this.add(mView);
		mFormat = new UserMenuFormat((String) mUserFrame.getI18n().getObject("FORMAT_LABEL"),mUserFrame);
		mFormat.setMnemonic('F');
		this.add(mFormat);
		mWindow = new UserMenuWindow((String) mUserFrame.getI18n().getObject("WINDOW_LABEL"),mUserFrame.getDesktopPane(), mUserFrame);
		mWindow.setMnemonic('W');
		this.add(mWindow);
		mHelp = new UserMenuHelp((String) mUserFrame.getI18n().getObject("HELP_LABEL"),mUserFrame);
		mHelp.setMnemonic('H');
		this.add(mHelp);	
	}
/**
 * 
 * @return returns the Help menu
 */
	protected UserMenuHelp getHelp()
	{
		return mHelp;
	}
/**
 * 
 * @return Returns the Window Menu
 */
	protected UserMenuWindow getWindow()
	{
		return mWindow;
	}
/**
 * 
 * @return Returns the Format Menu
 */
	protected UserMenuFormat getFormat()
	{
		return mFormat;
	}
/**
 * 
 * @return Returns the View Menu
 */	
	protected UserMenuView getView()
	{
		return mView;
	}
/**
 * 
 * @return Returns the Edit Menu
 */
	protected UserMenuEdit getEdit()
	{
		return mEdit;
	}
/**
 * 
 * @return Returns the File Menu
 */
	protected UserMenuFile getFile()
	{
		return mFile;
	}
}
