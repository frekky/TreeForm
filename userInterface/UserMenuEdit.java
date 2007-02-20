
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
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

/**
 * 
 * @author Donald Derrick
 * @version 0.1
 * <br>
 * date: 19-Aug-2004
 * <br>
 * <br>
 * This class contains the edit menu.  
 *
 */
public class UserMenuEdit extends JMenu {

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
	 * @uml.property name="mSelectAll"
	 * @uml.associationEnd 
	 * @uml.property name="mSelectAll" multiplicity="(1 1)"
	 */
	private UserMenuItemSelectAll mSelectAll;

	/**
	 * 
	 * @uml.property name="mDeleteSubtree"
	 * @uml.associationEnd 
	 * @uml.property name="mDeleteSubtree" multiplicity="(1 1)"
	 */
	private UserMenuItemDeleteSubtree mCopyTree;

	/**
	 * 
	 * @uml.property name="mDelete"
	 * @uml.associationEnd 
	 * @uml.property name="mDelete" multiplicity="(1 1)"
	 */
	private UserMenuItemDelete mDelete;

	/**
	 * 
	 * @uml.property name="mPaste"
	 * @uml.associationEnd 
	 * @uml.property name="mPaste" multiplicity="(1 1)"
	 */
	private UserMenuItemPaste mPaste;

	/**
	 * 
	 * @uml.property name="mCopy"
	 * @uml.associationEnd 
	 * @uml.property name="mCopy" multiplicity="(1 1)"
	 */
	private UserMenuItemCopy mCopy;

	/**
	 * 
	 * @uml.property name="mCut"
	 * @uml.associationEnd 
	 * @uml.property name="mCut" multiplicity="(1 1)"
	 */
	private UserMenuItemCut mCut;

	/**
	 * 
	 * @uml.property name="mRedo"
	 * @uml.associationEnd 
	 * @uml.property name="mRedo" multiplicity="(1 1)"
	 */
	private UserMenuItemRedo mRedo;

	/**
	 * 
	 * @uml.property name="mUndo"
	 * @uml.associationEnd 
	 * @uml.property name="mUndo" multiplicity="(1 1)"
	 */
	private UserMenuItemUndo mUndo;

	/**
	 * @param pString The title of the menu
	 * @param pUserFrame The UserFrame used in this instance of TreeForm (Awwwwk, polly want a cracker!)
	 * Note full i18n
	 * 
	 */
	public UserMenuEdit(String pString, UserFrame pUserFrame) {
		super(pString);
		mUserFrame = pUserFrame;
		mUndo = new UserMenuItemUndo((String) mUserFrame.getI18n().getObject("UNDO_LABEL"), (ImageIcon) mUserFrame.getI18n().getObject("UNDO_ICON_SMALL"),mUserFrame.getObservableStack(),mUserFrame.getObservableNew());
		mUndo.addActionListener(new ListenerUndo(mUserFrame));
		mUndo.setMnemonic('U');
		mUndo.setAccelerator(KeyStroke.getKeyStroke(
							   KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
		mUndo.setEnabled(false);
		mUserFrame.getObservableStack().addObserver(mUndo);	
		mUserFrame.getObservableNew().addObserver(mUndo);
		this.add(mUndo);		
		mRedo = new UserMenuItemRedo((String) mUserFrame.getI18n().getObject("REDO_LABEL"), (ImageIcon) mUserFrame.getI18n().getObject("REDO_ICON_SMALL"),mUserFrame.getObservableStack(), mUserFrame.getObservableNew());
		mRedo.addActionListener(new ListenerRedo(mUserFrame));
		mRedo.setMnemonic('R');
		mRedo.setAccelerator(KeyStroke.getKeyStroke(
							   KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
		
		mRedo.setEnabled(false);
		mUserFrame.getObservableStack().addObserver(mRedo);	
		mUserFrame.getObservableNew().addObserver(mRedo);
		this.add(mRedo);
		
		this.addSeparator();
		
		mCut = new UserMenuItemCut((String) mUserFrame.getI18n().getObject("CUT_LABEL"), (ImageIcon) mUserFrame.getI18n().getObject("CUT_ICON_SMALL"),mUserFrame.getObservableNew());
		mCut.addActionListener(new ListenerCut(mUserFrame));
		mCut.setMnemonic('U');
		mCut.setAccelerator(KeyStroke.getKeyStroke(
					   KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		mCut.setEnabled(false);
		mUserFrame.getObservableNew().addObserver(mCut);	
		this.add(mCut);
		mCopy = new UserMenuItemCopy((String) mUserFrame.getI18n().getObject("COPY_LABEL"), (ImageIcon) mUserFrame.getI18n().getObject("COPY_ICON_SMALL"),mUserFrame.getObservableNew());
		mCopy.addActionListener(new ListenerCopy(mUserFrame));
		mCopy.setMnemonic('C');
		mCopy.setAccelerator(KeyStroke.getKeyStroke(
					   KeyEvent.VK_C, ActionEvent.CTRL_MASK));
		mCopy.setEnabled(false);
		mUserFrame.getObservableNew().addObserver(mCopy);	
		this.add(mCopy);
		mPaste = new UserMenuItemPaste((String) mUserFrame.getI18n().getObject("PASTE_LABEL"), (ImageIcon) mUserFrame.getI18n().getObject("PASTE_ICON_SMALL"),mUserFrame.getObservableNew());
		mPaste.addActionListener(new ListenerPaste(mUserFrame));
		mPaste.setMnemonic('P');
		mPaste.setAccelerator(KeyStroke.getKeyStroke(
					   KeyEvent.VK_V, ActionEvent.CTRL_MASK));
		mPaste.setEnabled(false);
		mUserFrame.getObservableNew().addObserver(mPaste);	
		this.add(mPaste);

		this.addSeparator();
		
//		mDelete = new UserMenuItemDelete((String) mUserFrame.getI18n().getObject("COPY_TREE_LABEL"),mUserFrame.getObservableNew());
//		mDelete.addActionListener(new ListenerDeleteSubtree(mUserFrame));
//		mDelete.setEnabled(false);
//		mUserFrame.getObservableNew().addObserver(mDelete);	
		
//		this.add(mDelete);
		
		mCopyTree = new UserMenuItemDeleteSubtree((String) mUserFrame.getI18n().getObject("COPY_TREE_LABEL"), (ImageIcon) mUserFrame.getI18n().getObject("COPY_TREE_ICON_SMALL"),mUserFrame.getObservableNew());
		mCopyTree.addActionListener(new ListenerCopyTree(mUserFrame));
		mCopyTree.setEnabled(false);
		mUserFrame.getObservableNew().addObserver(mCopyTree);	
		this.add(mCopyTree);
		mSelectAll = new UserMenuItemSelectAll((String) mUserFrame.getI18n().getObject("SELECTALL_LABEL"),mUserFrame.getObservableNew());
		mSelectAll.addActionListener(new ListenerSelectAll(mUserFrame));
		mSelectAll.setMnemonic('A');
		mSelectAll.setAccelerator(KeyStroke.getKeyStroke(
							   KeyEvent.VK_A, ActionEvent.CTRL_MASK));
		mSelectAll.setEnabled(false);
		mUserFrame.getObservableNew().addObserver(mSelectAll);	
		this.add(mSelectAll);					
	}
/**
 * 
 * @return
 */
	protected JMenuItem getSelectAll()
	{
		return mSelectAll;
	}
/**
 * 
 * @return
 */
	protected JMenuItem getDeleteSubtree()
	{
		return mCopyTree;
	}
/**
 * 
 * @return
 */
	protected JMenuItem getDelete()
	{
		return mDelete;
	}
/**
 * 
 * @return
 */
	protected JMenuItem getPaste()
	{
		return mPaste;
	}
/**
 * 
 * @return
 */
	protected JMenuItem getCopy()
	{
		return mCopy;
	}
/**
 * 
 * @return
 */
	protected JMenuItem getCut()
	{
		return mCut;
	}
/**
 * 
 * @return
 */
	protected JMenuItem getRedo()
	{
		return mRedo;
	}
/**
 * 
 * @return
 */
	protected JMenuItem getUndo()
	{
		return mUndo;
	}
}
