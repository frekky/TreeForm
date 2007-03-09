
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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import staticFunctions.Sizer;
import syntaxTree.SyntaxFacade;

/**
 * @author Donald Derrick
 * @version 0.1
 * <br>
 * <br>
 * The main JFrame containing, well, the entire project.  This is the
 * storage place for the Observables, i18n bundles, and GUI
 * 
 *
 */
public class UserFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @uml.property name="mObservableSuperscript"
	 * @uml.associationEnd 
	 * @uml.property name="mObservableSuperscript" multiplicity="(1 1)"
	 */
	private ObservableFontSuperscript mObservableSuperscript;

	/**
	 * 
	 * @uml.property name="mObservableSubscript"
	 * @uml.associationEnd 
	 * @uml.property name="mObservableSubscript" multiplicity="(1 1)"
	 */
	private ObservableFontSubscript mObservableSubscript;

	/**
	 * 
	 * @uml.property name="mObservableUnderline"
	 * @uml.associationEnd 
	 * @uml.property name="mObservableUnderline" multiplicity="(1 1)"
	 */
	private ObservableFontUnderline mObservableUnderline;

	/**
	 * 
	 * @uml.property name="mObservableItalic"
	 * @uml.associationEnd 
	 * @uml.property name="mObservableItalic" multiplicity="(1 1)"
	 */
	private ObservableFontItalic mObservableItalic;

	/**
	 * 
	 * @uml.property name="mObservableBold"
	 * @uml.associationEnd 
	 * @uml.property name="mObservableBold" multiplicity="(1 1)"
	 */
	private ObservableFontBold mObservableBold;

	/**
	 * 
	 * @uml.property name="mObservableSize"
	 * @uml.associationEnd 
	 * @uml.property name="mObservableSize" multiplicity="(1 1)"
	 */
	private ObservableFontSize mObservableSize;

	/**
	 * 
	 * @uml.property name="mObservableFont"
	 * @uml.associationEnd 
	 * @uml.property name="mObservableFont" multiplicity="(1 1)"
	 */
	private ObservableFont mObservableFont;

	/**
	 * 
	 * @uml.property name="mClipboard"
	 * @uml.associationEnd 
	 * @uml.property name="mClipboard" multiplicity="(1 1)"
	 */
	private ObservableClipboard mClipboard;

	/**
	 * 
	 * @uml.property name="mZoom"
	 * @uml.associationEnd 
	 * @uml.property name="mZoom" multiplicity="(1 1)"
	 */
	private ObservableZoom mZoom;

	/**
	 * 
	 * @uml.property name="mObservableStack"
	 * @uml.associationEnd 
	 * @uml.property name="mObservableStack" multiplicity="(1 1)"
	 */
	private ObservableStack mObservableStack;

	/**
	 * 
	 * @uml.property name="mObservableNew"
	 * @uml.associationEnd 
	 * @uml.property name="mObservableNew" multiplicity="(1 1)"
	 */
	private ObservableNew mObservableNew;

	private ResourceBundle mI18n;
	private Locale mCurrentLocale;
	private Locale[] mSupportedLocales = {Locale.CANADA, Locale.CANADA_FRENCH};

	/**
	 * 
	 * @uml.property name="mDesktopPane"
	 * @uml.associationEnd 
	 * @uml.property name="mDesktopPane" multiplicity="(1 1)" inverse="mUserFrame:userInterface.UserDesktopPane"
	 */
	private UserDesktopPane mDesktopPane;

	/**
	 * 
	 * @uml.property name="mSplitPane"
	 * @uml.associationEnd 
	 * @uml.property name="mSplitPane" multiplicity="(1 1)"
	 */
	private JSplitPane mSplitPane;

	/**
	 * 
	 * @uml.property name="mPanelCenter"
	 * @uml.associationEnd 
	 * @uml.property name="mPanelCenter" multiplicity="(1 1)"
	 */
	private JPanel mPanelCenter;

	/**
	 * 
	 * @uml.property name="mBoxLayout"
	 * @uml.associationEnd 
	 * @uml.property name="mBoxLayout" multiplicity="(1 1)"
	 */
	private BoxLayout mBoxLayout;

	/**
	 * 
	 * @uml.property name="mPanelNorth"
	 * @uml.associationEnd 
	 * @uml.property name="mPanelNorth" multiplicity="(1 1)"
	 */
	private JPanel mPanelNorth;

	/**
	 * 
	 * @uml.property name="mToolBarFile"
	 * @uml.associationEnd 
	 * @uml.property name="mToolBarFile" multiplicity="(1 1)" inverse="mUserFrame:userInterface.UserToolBar"
	 */
	private UserToolBar mToolBarFile;

	/**
	 * 
	 * @uml.property name="mToolBarFonts"
	 * @uml.associationEnd 
	 * @uml.property name="mToolBarFonts" multiplicity="(1 1)"
	 */
	private UserToolBar mToolBarFonts;

	/**
	 * 
	 * @uml.property name="mMenuBar"
	 * @uml.associationEnd 
	 * @uml.property name="mMenuBar" multiplicity="(1 1)"
	 */
	private JMenuBar mMenuBar;

	/**
	 * 
	 * @uml.property name="mObjectBrowser"
	 * @uml.associationEnd 
	 * @uml.property name="mObjectBrowser" multiplicity="(1 1)"
	 */
	private JPanel mObjectBrowser;

	/**
	 * 
	 * @uml.property name="mUserControl"
	 * @uml.associationEnd 
	 * @uml.property name="mUserControl" multiplicity="(1 1)" inverse="mUserFrame:userInterface.UserControl"
	 */
	private UserControl mUserControl;

	private ObservableFontStrikethrough mObservableStrikethrough;

/**
 * Sets the environment for TreeForm
 *
 */
	public UserFrame()
	{
		this.setVisible(false);
		setObservableZoom(new ObservableZoom(1.0F));		
		mCurrentLocale = mSupportedLocales[0];
		seti18n(mCurrentLocale);
		setDefaults();
		// open and set up menu
		// open and set up toolbars
		setObservableNew(new ObservableNew(0));
		setObservableStack(new ObservableStack(null));
		setObservableClipboard(new ObservableClipboard(null));
		setObservableFont(new ObservableFont("Times New Roman"));
		setObservableFontSize(new ObservableFontSize(10));
		setObservableFontBold(new ObservableFontBold(false));
		setObservableFontItalic(new ObservableFontItalic(false));
		setObservableFontUnderline(new ObservableFontUnderline(false));
		setObservableFontStrikethrough(new ObservableFontStrikethrough(false));
		setObservableSubscript(new ObservableFontSubscript(false));
		setObservableSuperscript(new ObservableFontSuperscript(false));
		mToolBarFile = new UserToolBar(this,UserToolBar.LOAD + UserToolBar.PRINT + UserToolBar.CUT_AND_PASTE + UserToolBar.VIEW_AND_HELP);
		mToolBarFonts = new UserToolBar(this,  UserToolBar.ADD_FONT_EFFECTS);
		// open and set 
		mObjectBrowser = new UserObjectBrowser(this);
		mDesktopPane = new UserDesktopPane(this);
		// add the control commands
		mUserControl = new UserControl(this);
		mPanelNorth = new JPanel();
		mPanelCenter = new JPanel();
		mPanelCenter.validate();
		mMenuBar = new UserMenuBar(this);
		// make a pretty picture! (SNAP!)
		setLayout();
		// resize that picture.	
		pack();
		resize();
		this.setVisible(true);
		getUserControl().createNewTree();
		getObservableNew().setValue(getDesktopPane().getAllFrames().length + 1);
	}

/**
 * 
 * @param pUnderline Sets the ObservableFontUnderline Object.
 */	
	private void setObservableFontUnderline(ObservableFontUnderline pUnderline) {
		mObservableUnderline = pUnderline;
		
	}
/**
 * 
 * @return returns the ObservableFontUnderline for underline
 */
	protected ObservableFontUnderline getObservableFontUnderline()
	{
		return mObservableUnderline;
	}
/**
 * 
 * @param pItalic sets the ObservableFontItalic Object.
 */
	private void setObservableFontItalic(ObservableFontItalic pItalic) {
		mObservableItalic = pItalic;
		
	}
	private void setObservableFontStrikethrough(ObservableFontStrikethrough pStrikethrough) {
		mObservableStrikethrough = pStrikethrough;
	}
/**
 * @return Returns the ObservableFontItalic for setting italics
 */
	protected ObservableFontItalic getObservableFontItalic()
	{
		return mObservableItalic;
	}
/**
 * 
 * @param pBold sets the ObservableFontBold Object.
 */
	private void setObservableFontBold(ObservableFontBold pBold) {
		mObservableBold = pBold;	
	}

/**
 * 
 * @return returns the ObservableFontBold for setting font bold style.
 */
	protected ObservableFontBold getObservableFontBold()
	{
		return mObservableBold;
	}
/**
 * 
 * @param pSize Sets the ObservableFontSize Object.
 */
	private void setObservableFontSize(ObservableFontSize pSize) {
		mObservableSize = pSize;
		
	}
/**
 * 
 * @return returns the ObservableFontSize Object for setting font sizes.
 */
	protected ObservableFontSize getObservableFontSize()
	{
		return mObservableSize;
	}

/**
 * 
 * @param pObservableClipboard sets the ObservableFontClipboard for copy/paste 
 * operations.
 */
	private void setObservableClipboard(ObservableClipboard pObservableClipboard) {
		mClipboard = pObservableClipboard;
		
	}
/**
 * 
 * @param zoom sets the ObservableZoom clipboard to zoom operations.
 */
	private void setObservableZoom(ObservableZoom zoom) {
		mZoom = zoom;
		
	}
/**
 * 
 * @return returns the ObservableZoom Object for changing zoom.
 */
	protected ObservableZoom getObservableZoom() {
		return mZoom;
	}
/**
 * 
 * @return gets the Clipboard containing the selected syntactic object.
 */
	protected ObservableClipboard getObservableClipboard() {
		return mClipboard;
	}

/**
 * 
 * @param pCurrentLocale sets the current Locale for i18n operations.
 */

	protected void seti18n(Locale pCurrentLocale) {
		mI18n = ResourceBundle.getBundle("TreeFormBundle", pCurrentLocale);
	}

/**
 * 
 * @return returns the i18n bundle.
 */
	protected ResourceBundle getI18n()
	{
		return mI18n;
	}

/**
 * sets standard defaults, including the TreeForm LOGO.
 *
 */

	private void setDefaults() {
		
		this.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				if (getObservableNew().getValue() != 0)
				{
					int I = JOptionPane.showConfirmDialog(null,"Clicking NO exit the program without saving any trees.","Save trees first?",JOptionPane.YES_NO_OPTION);
					if (I == JOptionPane.YES_OPTION)
					{
						getUserControl().saveAllTrees(getDesktopPane());
					}
					else
					{
						System.exit(0);
					}
				}
				else
				{
				System.exit(0);
				}
			}
		});
		this.setTitle("TreeForm");
		ImageIcon treeFormIcon =  (ImageIcon) mI18n.getObject("TREEFORM_ICON_SMALL");
		this.setIconImage(treeFormIcon.getImage());
	}

	/**
	 * gets the all powerful UserControl
	 */
	protected UserControl getUserControl()
	{
		return mUserControl;
	}
/**
 * Resizes the Frame to screen resolution.
 *
 */
	private void resize() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize((int)dim.getWidth(), (int)dim.getHeight()-30);
	}
/**
 * sets the layout of the JFrame
 *
 */
	private void setLayout() {
		this.setJMenuBar(mMenuBar);
		mBoxLayout = new BoxLayout(mPanelNorth,BoxLayout.PAGE_AXIS);
		mPanelNorth.setLayout(mBoxLayout);
		mPanelNorth.add(mToolBarFile);
		mPanelNorth.add(mToolBarFonts);
		JScrollPane jsp = new JScrollPane(mDesktopPane);
		mSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,mObjectBrowser,jsp);
		mSplitPane.setDividerLocation((Sizer.scaledButtonSize().width * 2) + 14);
		this.getContentPane().add(mPanelNorth,BorderLayout.NORTH);
		this.getContentPane().add(mSplitPane,BorderLayout.CENTER);		
	}
	
/**
 * 
 * @return return the DesktopPane - sometimes needed for mass close/save operations.
 */
	public UserDesktopPane getDesktopPane()
	{
		return mDesktopPane;
	}
/**
 * 
 * @return returns the selected InternalFrame - used to get at the sentences -
 * a convenience Facade function.
 */
	public UserInternalFrame getInternalFrame()
	{
		return getDesktopPane().getInternalFrame();
	}
/**
 * 
 * @return returns the SyntaxFacade - a convenience Facade function.
 */
	public SyntaxFacade getSyntaxFacade()
	{
		return getDesktopPane().getInternalFrame().getSyntaxFacade();
	}
/**
 * 
 * @return get the ObjectBrowser
 */
	protected JPanel getObjectBrowser()
	{
		return mObjectBrowser;
	}
/**
 * 
 * @return get the Top JToolbar
 */
	protected UserToolBar getToolBarFile()
	{
		return mToolBarFile;
	}
/**
 * 
 * @return get the bottom JToolbar
 */
	protected UserToolBar getToolBarFonts()
	{
		return mToolBarFonts;
	}
/**
 * 
 * @param pObservableNew sets ObservableNew - the Observable that counts InternalFrames
 */
	private void setObservableNew(ObservableNew pObservableNew) {
		mObservableNew = pObservableNew;
	}
/**
 * 
 * @return Returns the ObservableNew Object for changing InternalFrame counts.
 */
	protected ObservableNew getObservableNew() {
		return mObservableNew;
	}

	/**
	 * @returns the ObservableStack for undo/redo operations.
	 */
	protected ObservableStack getObservableStack() 
	{
		return mObservableStack;
	}
	/**
	 * 
	 * @param pObservableStack sets the ObservableStack used for undo/redo operations.
	 */
	private void setObservableStack(ObservableStack pObservableStack)
	{
		mObservableStack = pObservableStack;
	}
/**
 * 
 * @param pObservableFont Sets the ObservableFont Object for observing the selected
 * font
 */
	private void setObservableFont(ObservableFont pObservableFont) {
		mObservableFont = pObservableFont;
	}
/**
 * 
 * @return Returns the ObservableFont object containing the most recently selected font.
 */
	protected ObservableFont getObservableFont() {
		return mObservableFont;
	}
/**
 * 
 * @param mObservableSuperscript Sets the ObservableSuperscript Object for observing
 * selected text with superscript on/off.
 */
	protected void setObservableSuperscript(ObservableFontSuperscript mObservableSuperscript) {
		this.mObservableSuperscript = mObservableSuperscript;
	}
/**
 * 
 * @return Returns the ObservableSuperscript Object containing superscript information.
 */
	protected ObservableFontSuperscript getObservableSuperscript() {
		return mObservableSuperscript;
	}
/**
 * 
 * @param mObservableSubscript Sets the ObservableSubscript Object for observing
 * selected text with subscript on/off.
 */
	protected void setObservableSubscript(ObservableFontSubscript mObservableSubscript) {
		this.mObservableSubscript = mObservableSubscript;
	}
/**
 * 
 * @return Returns the ObservableSubscript Object containing subscript information.
 */
	protected ObservableFontSubscript getObservableSubscript() {
		return mObservableSubscript;
	}

	protected ObservableFontStrikethrough getObservableFontStrikethrough() {
		// TODO Auto-generated method stub
		return mObservableStrikethrough;
	}
}

