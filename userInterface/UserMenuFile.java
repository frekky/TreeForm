
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

import java.awt.Toolkit;
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
 * The class that contains the file menu.  Note full i18n.
 */
public class UserMenuFile extends JMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @uml.property name="mExit"
	 * @uml.associationEnd 
	 * @uml.property name="mExit" multiplicity="(1 1)"
	 */
	private JMenuItem mExit;

	/**
	 * 
	 * @uml.property name="mProperties"
	 * @uml.associationEnd 
	 * @uml.property name="mProperties" multiplicity="(1 1)"
	 */
	private JMenuItem mProperties;

	/**
	 * 
	 * @uml.property name="mExport"
	 * @uml.associationEnd 
	 * @uml.property name="mExport" multiplicity="(1 1)"
	 */
	private UserMenuItemExport mExport;

	/**
	 * 
	 * @uml.property name="mPrintPreview"
	 * @uml.associationEnd 
	 * @uml.property name="mPrintPreview" multiplicity="(1 1)"
	 */
	private UserMenuItemPrintPreview mPrintPreview;

	/**
	 * 
	 * @uml.property name="mPrint"
	 * @uml.associationEnd 
	 * @uml.property name="mPrint" multiplicity="(1 1)"
	 */
	private UserMenuItemPrint mPrint;

	/**
	 * 
	 * @uml.property name="mSaveAll"
	 * @uml.associationEnd 
	 * @uml.property name="mSaveAll" multiplicity="(1 1)"
	 */
	private UserMenuItemSaveAll mSaveAll;

	/**
	 * 
	 * @uml.property name="mSaveAs"
	 * @uml.associationEnd 
	 * @uml.property name="mSaveAs" multiplicity="(1 1)"
	 */
	private UserMenuItemSaveAs mSaveAs;

	/**
	 * 
	 * @uml.property name="mSave"
	 * @uml.associationEnd 
	 * @uml.property name="mSave" multiplicity="(1 1)"
	 */
	private UserMenuItemSave mSave;

	/**
	 * 
	 * @uml.property name="mCloseAll"
	 * @uml.associationEnd 
	 * @uml.property name="mCloseAll" multiplicity="(1 1)"
	 */
	private UserMenuItemCloseAll mCloseAll;

	/**
	 * 
	 * @uml.property name="mClose"
	 * @uml.associationEnd 
	 * @uml.property name="mClose" multiplicity="(1 1)"
	 */
	private UserMenuItemClose mClose;

	/**
	 * 
	 * @uml.property name="mOpen"
	 * @uml.associationEnd 
	 * @uml.property name="mOpen" multiplicity="(1 1)"
	 */
	private JMenuItem mOpen;

	/**
	 * 
	 * @uml.property name="mNew"
	 * @uml.associationEnd 
	 * @uml.property name="mNew" multiplicity="(1 1)"
	 */
	private UserMenuItemNew mNew;

	/**
	 * 
	 * @uml.property name="mUserFrame"
	 * @uml.associationEnd 
	 * @uml.property name="mUserFrame" multiplicity="(1 1)"
	 */
	private UserFrame mUserFrame;

	/**
	 * @param pString The string containing the text for this menu 
	 * @param pUserFrame The UserFrame for this instance of TreeForm.
	 */
	public UserMenuFile(String pString, UserFrame pUserFrame) {
		super(pString);
		mUserFrame = pUserFrame;
		mNew = new UserMenuItemNew((String) mUserFrame.getI18n().getObject("NEW_LABEL"), (ImageIcon) mUserFrame.getI18n().getObject("NEW_ICON_SMALL"));
		mNew.addActionListener(new ListenerNew(mUserFrame));
		//mNew.setMnemonic('N');
		mNew.setAccelerator(KeyStroke.getKeyStroke(
							   KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));					   
		this.add(mNew);
		
		mOpen = new JMenuItem((String) mUserFrame.getI18n().getObject("OPEN_LABEL"), (ImageIcon) mUserFrame.getI18n().getObject("OPEN_ICON_SMALL"));
		mOpen.addActionListener(new ListenerLoad(mUserFrame));
		//mOpen.setMnemonic('I');
		mOpen.setAccelerator(KeyStroke.getKeyStroke(
							   KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		this.add(mOpen);
		
		mClose = new UserMenuItemClose((String) mUserFrame.getI18n().getObject("CLOSE_LABEL"),mUserFrame.getObservableNew());
		mClose.addActionListener(new ListenerClose(mUserFrame));
		//mClose.setMnemonic('C');
		mClose.setAccelerator(KeyStroke.getKeyStroke(
				   KeyEvent.VK_W, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

		mClose.setEnabled(false);
		mUserFrame.getObservableNew().addObserver(mClose);	
		this.add(mClose);
		
		mCloseAll = new UserMenuItemCloseAll((String) mUserFrame.getI18n().getObject("CLOSEALL_LABEL"), mUserFrame.getObservableNew());
		mCloseAll.addActionListener(new ListenerCloseAll(mUserFrame));
		//mCloseAll.setMnemonic('A');
		mCloseAll.setEnabled(false);
		mUserFrame.getObservableNew().addObserver(mCloseAll);
		this.add(mCloseAll);	
			
		this.addSeparator();
					
		mSave = new UserMenuItemSave((String) mUserFrame.getI18n().getObject("SAVE_LABEL"), (ImageIcon) mUserFrame.getI18n().getObject("SAVE_ICON_SMALL"), mUserFrame.getObservableNew());
		mSave.addActionListener(new ListenerSave(mUserFrame));
		//mSave.setMnemonic('S');
		mSave.setAccelerator(KeyStroke.getKeyStroke(
							   KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		mSave.setEnabled(false);
		mUserFrame.getObservableNew().addObserver(mSave);					   
		this.add(mSave);
		mSaveAs = new UserMenuItemSaveAs((String) mUserFrame.getI18n().getObject("SAVEAS_LABEL"), (ImageIcon) mUserFrame.getI18n().getObject("SAVEAS_ICON_SMALL"), mUserFrame.getObservableNew());
		mSaveAs.addActionListener(new ListenerSaveAs(mUserFrame));
		mSaveAs.setAccelerator(KeyStroke.getKeyStroke(
				   KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask() + KeyEvent.SHIFT_DOWN_MASK));

		mSaveAs.setEnabled(false);
		mUserFrame.getObservableNew().addObserver(mSaveAs);
		this.add(mSaveAs);
		mSaveAll = new UserMenuItemSaveAll((String) mUserFrame.getI18n().getObject("SAVEALL_LABEL"), (ImageIcon) mUserFrame.getI18n().getObject("SAVEALL_ICON_SMALL"), mUserFrame.getObservableNew());
		mSaveAll.addActionListener(new ListenerSaveAll(mUserFrame));
		mSaveAll.setEnabled(false);
		mUserFrame.getObservableNew().addObserver(mSaveAll);
		this.add(mSaveAll);
		
		this.addSeparator();	
		mProperties = new JMenuItem((String) mUserFrame.getI18n().getObject("PROPERTIES_LABEL"), (ImageIcon) mUserFrame.getI18n().getObject("PROPERTIES_ICON_SMALL"));
		mProperties.addActionListener(new ListenerPropertiesTree(mUserFrame));
		this.add(mProperties);	
		
		this.addSeparator();
		
		
		mPrint = new UserMenuItemPrint((String) mUserFrame.getI18n().getObject("PRINT_LABEL"), (ImageIcon) mUserFrame.getI18n().getObject("PRINT_ICON_SMALL"), mUserFrame.getObservableNew());
		mPrint.addActionListener(new ListenerPrintTree(mUserFrame));
		//mPrint.setMnemonic('P');
		mPrint.setAccelerator(KeyStroke.getKeyStroke(
							   KeyEvent.VK_P, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		mPrint.setEnabled(false);
		mUserFrame.getObservableNew().addObserver(mPrint);
		this.add(mPrint);
		
//		mPrintPreview = new UserMenuItemPrintPreview((String) mUserFrame.getI18n().getObject("PRINTPREVIEW_LABEL"), (ImageIcon) mUserFrame.getI18n().getObject("PRINTPREVIEW_ICON_SMALL"), mUserFrame.getObservableNew());
//		mPrintPreview.addActionListener(new ListenerPrintPreview(mUserFrame));
//		mPrintPreview.setMnemonic('V');
//		mPrintPreview.setEnabled(false);
//		mUserFrame.getObservableNew().addObserver(mPrintPreview);
//		this.add(mPrintPreview);
		
		this.addSeparator();
		
		mExport = new UserMenuItemExport((String) mUserFrame.getI18n().getObject("EXPORT_LABEL"), (ImageIcon) mUserFrame.getI18n().getObject("EXPORT_ICON_SMALL"), mUserFrame.getObservableNew());
		mExport.addActionListener(new ListenerExportTree(mUserFrame));
//		mExport.setMnemonic('E');
		mExport.setAccelerator(KeyStroke.getKeyStroke(
							   KeyEvent.VK_E, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		mExport.setEnabled(false);
		mUserFrame.getObservableNew().addObserver(mExport);
		this.add(mExport);	
		
		this.addSeparator();
		
		mExit = new JMenuItem((String) mUserFrame.getI18n().getObject("EXIT_LABEL"));
		mExit.addActionListener(new ListenerExitTree(mUserFrame));
//		mExit.setMnemonic('X');
		this.add(mExit);		
	}
	protected JMenuItem getExit()
	{
		return mExit;
	}
	protected JMenuItem getProperties()
	{
		return mProperties;
	}
	protected JMenuItem getExport()
	{
		return mExport;
	}
	protected JMenuItem getPrintPreview()
	{
		return mPrintPreview;
	}
	protected JMenuItem getPrint()
	{
		return mPrint;
	}
	protected JMenuItem getSaveAll()
	{
		return mSaveAll;
	}
	protected JMenuItem getSaveAs()
	{
		return mSaveAs;
	}
	protected JMenuItem getSave()
	{
		return mSave;
	}
	protected JMenuItem getCloseAll()
	{
		return mCloseAll;
	}
	protected JMenuItem getClose()
	{
		return mClose;
	}
	protected JMenuItem getOpen()
	{
		return mOpen;
	}
	protected JMenuItem getNew()
	{
		return mNew;
	}
}
