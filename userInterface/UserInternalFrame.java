//TreeForm Syntax Tree Drawing Software
//Copyright (C) 2006  Donald Derrick

//This program is free software; you can redistribute it and/or
//modify it under the terms of the GNU General Public License
//as published by the Free Software Foundation; either version 2
//of the License, or (at your option) any later version.

//This program is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.

//You should have received a copy of the GNU General Public License
//along with this program; if not, write to the Free Software
//Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
//package userInterface;

package userInterface;

import java.awt.Color;
import java.util.Map;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;

import syntaxTree.Properties;
import syntaxTree.SyntacticStructure;
import syntaxTree.SyntaxFacade;
import syntaxTree.TraceComponent;
import enumerators.SaveFileType;
import enumerators.SyntacticViewLayout;

/**
 * 
 * @author Donald Derrick
 * @version 0.1 <br>
 *          date: 19-Aug-2004 <br>
 *          <br>
 * 
 */
public class UserInternalFrame extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int mMinWidth;

	private int mMinHeight;

	/**
	 * 
	 * @uml.property name="mSaveFileType"
	 * @uml.associationEnd
	 * @uml.property name="mSaveFileType" multiplicity="(1 1)"
	 */
	private SaveFileType mSaveFileType;

	/**
	 * 
	 * @uml.property name="mSyntaxFacade"
	 * @uml.associationEnd
	 * @uml.property name="mSyntaxFacade" multiplicity="(1 1)"
	 *               inverse="mUIF:syntaxTree.SyntaxFacade"
	 */
	private SyntaxFacade mSyntaxFacade;

	/**
	 * 
	 * @uml.property name="mSyntacticViewLayout"
	 * @uml.associationEnd
	 * @uml.property name="mSyntacticViewLayout" multiplicity="(1 1)"
	 */
	private SyntacticViewLayout mSyntacticViewLayout;

	/**
	 * 
	 * @uml.property name="mUserFrame"
	 * @uml.associationEnd
	 * @uml.property name="mUserFrame" multiplicity="(1 1)"
	 */
	private UserFrame mUserFrame;

	private float mScale;

	private TraceComponent mTrace;
	
	private Properties mProperties;

	/**
	 * 
	 * @param pUserFrame
	 * @param pCount
	 * 
	 * Sets up the UserInternalFrame - including the default document name and
	 * the UserFrame. Please note that the UserInternalFrame acts as the Facade
	 * interface to the SyntaxFacade. While not strictly the correct way to do
	 * things, it's not bad because the UserInternalFrame doesn't do much - so
	 * using it as a Facade is not overly confusing.
	 */
	public UserInternalFrame(UserFrame pUserFrame, int pCount) {
		super("Document #" + pCount, true, // resizable
				true, // closable
				true, // maximizable
				true);// iconifiable
		mUserFrame = pUserFrame;
		mProperties = new Properties();
		mSyntaxFacade = new SyntaxFacade(this);
		mSaveFileType = SaveFileType.XML;
		// ...Create the GUI and put it in the window...

		// ...Then set the window size or call pack...
		setSize(mUserFrame.getDesktopPane().getSize());
		mSyntacticViewLayout = SyntacticViewLayout.D_STRUCTURE;
		mTrace = new TraceComponent(this);
		this.getContentPane().setBackground(new Color(255,255,255));
		this.getContentPane().add(mTrace);
		this.setScale(1.0F);
		setMinHeight(this.getHeight());
		setMinWidth(this.getWidth());
		
	}

	/**
	 * 
	 * @return returns the map of all the attributes of a given character at the
	 *         position of the selected index of the selected syntactic object.
	 *         These attributes include font information, underline, and
	 *         sub/superscript information.
	 */
	public Map getAttributes() {
		return mUserFrame.getUserControl().getAttributes();
	}

	/**
	 * 
	 * @return returns the SyntacticViewlayout (code not complete)
	 */
	public SyntacticViewLayout getSyntacticViewLayout() {
		return mSyntacticViewLayout;
	}

	/**
	 * 
	 * @param pSvl
	 *            sets the SyntacticViewLayout.
	 */
	public void setSyntacticViewLayout(SyntacticViewLayout pSvl) {
		mSyntacticViewLayout = pSvl;
	}

	/**
	 * 
	 * @return Returns the SyntaxFacade
	 */
	public SyntaxFacade getSyntaxFacade() {
		return mSyntaxFacade;
	}

	/**
	 * @return Returns the SaveFileType
	 */
	public SaveFileType getSaveFileType() {
		return mSaveFileType;
	}

	/**
	 * 
	 * @param pSaveFileType
	 *            sets the SaveFileType
	 */
	public void setSaveFileType(SaveFileType pSaveFileType) {
		mSaveFileType = pSaveFileType;
	}

	/**
	 * 
	 * @param pScale
	 *            set the Zoom scale
	 */
	public void setScale(float pScale) {
		mScale = pScale;
	}

	/**
	 * 
	 * @return Returns the Zoom Scale
	 */
	public float getScale() {
		return mScale;
	}

	/**
	 * 
	 * @return Returns the ObservableClipboard containing the selected syntactic
	 *         object (NOT the same as the selected text!)
	 */
	public ObservableClipboard getObservableClipboard() {
		return mUserFrame.getObservableClipboard();
	}

	/**
	 * 
	 * @return Returns the ObservableFont containint the selected font
	 */
	public ObservableFont getObservableFont() {
		return mUserFrame.getObservableFont();
	}

	/**
	 * 
	 * @return Returns the ObservableFontSize containing the font size
	 */
	public ObservableFontSize getObservableFontSize() {
		return mUserFrame.getObservableFontSize();
	}

	/**
	 * 
	 * @return Returns the ObservableFontBold containing the font style
	 */
	public ObservableFontBold getObservableFontBold() {
		return mUserFrame.getObservableFontBold();
	}

	/**
	 * 
	 * @return Returns the ObservableFontBold containing the font style
	 */
	public ObservableFontItalic getObservableFontItalic() {
		return mUserFrame.getObservableFontItalic();
	}

	/**
	 * 
	 * @return Returns the ObservableFontBold containing the font underline
	 *         feature
	 */
	public ObservableFontUnderline getObservableFontUnderline() {
		return mUserFrame.getObservableFontUnderline();
	}

	/**
	 * 
	 * @return Returns the ObservableFontBold containing the font subscript
	 *         feature
	 */
	public ObservableFontSubscript getObservableFontSubscript() {
		return mUserFrame.getObservableSubscript();
	}

	public ObservableFontStrikethrough getObservableFontStrikethrough() {
		return mUserFrame.getObservableFontStrikethrough();
	}
	/**
	 * 
	 * @return Returns the ObservableFontBold containing the font superscript
	 *         feature
	 */
	public ObservableFontSuperscript getObservableFontSuperscript() {
		return mUserFrame.getObservableSuperscript();
	}

	/**
	 * 
	 * @param pSS
	 *            the parent
	 * @param pSSChild
	 *            the subtree <br>
	 *            <br>
	 *            activates the glass pane - needed for repositioning trees
	 */
	public void activateGlassPane(SyntacticStructure pSS,
			SyntacticStructure pSSChild) {
		UserGlassPane lUGP = new UserGlassPane(mUserFrame, pSS, pSSChild);
		lUGP.setSyntacticStructure(pSS);
		ListenerGlassPane lListenerGlassPane = new ListenerGlassPane(mUserFrame);
		lUGP.addMouseListener(lListenerGlassPane);
		lUGP.addMouseMotionListener(lListenerGlassPane);
		this.setGlassPane(lUGP);
		this.getGlassPane().setVisible(true);
	}
	public void activateBezierPane(SyntacticStructure pSS)
	{
		UserBezierPane lUBP = new UserBezierPane(mUserFrame,pSS);
		ListenerBezierPane listenerBezierPane = new ListenerBezierPane(mUserFrame);
		lUBP.addMouseListener(listenerBezierPane);
		lUBP.addMouseMotionListener(listenerBezierPane);
		this.setGlassPane(lUBP);
		this.getGlassPane().setVisible(true);
	}

	public void deactivateGlassPane() {
		this.setGlassPane(new JLabel());
		this.getGlassPane().setVisible(false);
	}

	public void deactivateBezierPane(){
		this.setGlassPane(new JLabel());
		this.getGlassPane().setVisible(false);
	}
	/**
	 * 
	 * @return Returns the User Frame
	 */
	public UserFrame getUserFrame() {
		return mUserFrame;
	}

	/**
	 * 
	 * @param mMinWidth
	 *            sets the minimum width - needed for auto-sizing
	 */
	public void setMinWidth(int mMinWidth) {
		this.mMinWidth = mMinWidth;
	}

	/**
	 * 
	 * @return gets the minimum width - needed for auto-sizing
	 */
	public int getMinWidth() {
		return mMinWidth;
	}

	/**
	 * 
	 * @param mMinHeight
	 *            sets the minimum height - needed for auto-sizing.
	 */
	public void setMinHeight(int mMinHeight) {
		this.mMinHeight = mMinHeight;
	}

	/**
	 * 
	 * @return sets the minimum height - needed for auto-sizing.
	 */
	public int getMinHeight() {
		return mMinHeight;
	}
	public TraceComponent getTrace()
	{
		return mTrace;
	}
	public Properties getProperties()
	{
		return mProperties;
	}
}
