
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

import enumerators.SyntacticViewLayout;

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
public class UserMenuView extends JMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @uml.property name="mZoom"
	 * @uml.associationEnd 
	 * @uml.property name="mZoom" multiplicity="(1 1)"
	 */
	private JMenuItem mZoom;

	/**
	 * 
	 * @uml.property name="mLabelledBracket"
	 * @uml.associationEnd 
	 * @uml.property name="mLabelledBracket" multiplicity="(1 1)"
	 */
	private JMenuItem mLabelledBracket;

	/**
	 * 
	 * @uml.property name="mLForm"
	 * @uml.associationEnd 
	 * @uml.property name="mLForm" multiplicity="(1 1)"
	 */
	private JMenuItem mLForm;

	/**
	 * 
	 * @uml.property name="mSStructure"
	 * @uml.associationEnd 
	 * @uml.property name="mSStructure" multiplicity="(1 1)"
	 */
	private JMenuItem mSStructure;

	/**
	 * 
	 * @uml.property name="mDStructure"
	 * @uml.associationEnd 
	 * @uml.property name="mDStructure" multiplicity="(1 1)"
	 */
	private JMenuItem mDStructure;

	/**
	 * 
	 * @uml.property name="mUserFrame"
	 * @uml.associationEnd 
	 * @uml.property name="mUserFrame" multiplicity="(1 1)"
	 */
	private UserFrame mUserFrame;

	/**
	 * @param pString The Title
	 * @param pUserFrame The UserFrame for this instance of TreeForm
	 */
	public UserMenuView(String pString, UserFrame pUserFrame) {
		super(pString);
		mUserFrame = pUserFrame;
		mDStructure = new JMenuItem((String) mUserFrame.getI18n().getObject("DEEPSTRUCTURE_LABEL"), (ImageIcon) mUserFrame.getI18n().getObject("DEEPSTRUCTURE_ICON_SMALL"));
		mDStructure.addActionListener(new ListenerChangeViewLayout(mUserFrame, SyntacticViewLayout.D_STRUCTURE));
		mDStructure.setMnemonic('D');
		this.add(mDStructure);
		mSStructure = new JMenuItem((String) mUserFrame.getI18n().getObject("SURFACESTRUCTURE_LABEL"), (ImageIcon) mUserFrame.getI18n().getObject("SURFACESTRUCTURE_ICON_SMALL"));
		mSStructure.addActionListener(new ListenerChangeViewLayout(mUserFrame, SyntacticViewLayout.S_STRUCTURE));
		mSStructure.setMnemonic('S');
		this.add(mSStructure);	
		mLForm = new JMenuItem((String) mUserFrame.getI18n().getObject("LOGICALFORM_LABEL"), (ImageIcon) mUserFrame.getI18n().getObject("LOGICALFORM_ICON_SMALL"));
		mLForm.addActionListener(new ListenerChangeViewLayout(mUserFrame, SyntacticViewLayout.L_STRUCTURE));
		mLForm.setMnemonic('L');
		this.add(mLForm);
		mLabelledBracket = new JMenuItem((String) mUserFrame.getI18n().getObject("LABELLEDBRACKET_LABEL"), (ImageIcon) mUserFrame.getI18n().getObject("LABELLEDBRACKET_ICON_SMALL"));
		mLabelledBracket.addActionListener(new ListenerChangeViewLayout(mUserFrame, SyntacticViewLayout.LABELLED_BRACKET));
		mLabelledBracket.setMnemonic('B');
		this.add(mLabelledBracket);	
		this.addSeparator();
		mZoom = new JMenuItem((String) mUserFrame.getI18n().getObject("ZOOM_LABEL"));
		mZoom.addActionListener(new ListenerZoom(mUserFrame));
		mZoom.setMnemonic('Z');
		this.add(mZoom);			
	}
	protected JMenuItem getZoom()
	{
		return mZoom;
	}
	protected JMenuItem getLabelledBracket()
	{
		return mLabelledBracket;
	}
	protected JMenuItem getLStructure()
	{
		return mLForm;
	}
	protected JMenuItem getSStructure()
	{
		return mSStructure;
	}
	protected JMenuItem getDStructure()
	{
		return mDStructure;
	}

}
