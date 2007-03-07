
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

import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JToolBar;

import staticFunctions.Sizer;
import staticFunctions.StringManipulator;
import enumerators.SyntacticViewLayout;

/**
 * 
 * @author Donald Derrick
 * @version 0.1
 * <br>
 * date: 19-Aug-2004
 * <br>
 * <br>
 * Toolbar - can display different parts of the toolbar based on creational parameters.
 * I'm not sure if that is the right way to do it, but oh well.
 */
public class UserToolBar extends JToolBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @uml.property name="mSuperscript"
	 * @uml.associationEnd 
	 * @uml.property name="mSuperscript" multiplicity="(1 1)"
	 */
	private UserToggleFontSuperscript mSuperscript;

	/**
	 * 
	 * @uml.property name="mSubscript"
	 * @uml.associationEnd 
	 * @uml.property name="mSubscript" multiplicity="(1 1)"
	 */
	private UserToggleFontSubscript mSubscript;

	/**
	 * 
	 * @uml.property name="mModelZoom"
	 * @uml.associationEnd 
	 * @uml.property name="mModelZoom" multiplicity="(1 1)"
	 */
	private DefaultComboBoxModel mModelZoom;

	/**
	 * 
	 * @uml.property name="mZoom"
	 * @uml.associationEnd 
	 * @uml.property name="mZoom" multiplicity="(1 1)"
	 */
	private UserComboZoom mZoom;

	public final static int LOAD = 1;
	public final static int PRINT = 2;
	public final static int CUT_AND_PASTE = 4;
	public final static int VIEW_AND_HELP = 8;
	public final static int ADD_FONT_EFFECTS = 16;
	private boolean mIconSize;

	/**
	 * 
	 * @uml.property name="mSizeButton"
	 * @uml.associationEnd 
	 * @uml.property name="mSizeButton" multiplicity="(0 1)"
	 */
	private UserButton mSizeButton;

	/**
	 * 
	 * @uml.property name="mNew"
	 * @uml.associationEnd 
	 * @uml.property name="mNew" multiplicity="(1 1)"
	 */
	private UserButton mNew;

	/**
	 * 
	 * @uml.property name="mModelFontSize"
	 * @uml.associationEnd 
	 * @uml.property name="mModelFontSize" multiplicity="(1 1)"
	 */
	private DefaultComboBoxModel mModelFontSize;

	/**
	 * 
	 * @uml.property name="mFontSize"
	 * @uml.associationEnd 
	 * @uml.property name="mFontSize" multiplicity="(1 1)"
	 */
	private UserComboFontSize mFontSize;

	/**
	 * 
	 * @uml.property name="mModelFont"
	 * @uml.associationEnd 
	 * @uml.property name="mModelFont" multiplicity="(1 1)"
	 */
	private DefaultComboBoxModel mModelFont;

	/**
	 * 
	 * @uml.property name="mFont"
	 * @uml.associationEnd 
	 * @uml.property name="mFont" multiplicity="(1 1)"
	 */
	private UserComboFont mFont;

	/**
	 * 
	 * @uml.property name="mUnderline"
	 * @uml.associationEnd 
	 * @uml.property name="mUnderline" multiplicity="(1 1)"
	 */
	private UserToggleFontUnderline mUnderline;

	/**
	 * 
	 * @uml.property name="mItalics"
	 * @uml.associationEnd 
	 * @uml.property name="mItalics" multiplicity="(1 1)"
	 */
	private UserToggleFontItalic mItalics;

	/**
	 * 
	 * @uml.property name="mBold"
	 * @uml.associationEnd 
	 * @uml.property name="mBold" multiplicity="(1 1)"
	 */
	private UserToggleFontBold mBold;

	/**
	 * 
	 * @uml.property name="mExport"
	 * @uml.associationEnd 
	 * @uml.property name="mExport" multiplicity="(1 1)"
	 */
	private UserButtonExport mExport;

	/**
	 * 
	 * @uml.property name="mProperties"
	 * @uml.associationEnd 
	 * @uml.property name="mProperties" multiplicity="(1 1)"
	 */
	private UserButton mProperties;

	/**
	 * 
	 * @uml.property name="mLabelledBracket"
	 * @uml.associationEnd 
	 * @uml.property name="mLabelledBracket" multiplicity="(1 1)"
	 */
	private UserButton mLabelledBracket;

	/**
	 * 
	 * @uml.property name="mDStructure"
	 * @uml.associationEnd 
	 * @uml.property name="mDStructure" multiplicity="(1 1)"
	 */
	private UserButton mDStructure;

	/**
	 * 
	 * @uml.property name="mSurfaceStructure"
	 * @uml.associationEnd 
	 * @uml.property name="mSurfaceStructure" multiplicity="(1 1)"
	 */
	private UserButton mSurfaceStructure;

	/**
	 * 
	 * @uml.property name="mLogicalForm"
	 * @uml.associationEnd 
	 * @uml.property name="mLogicalForm" multiplicity="(1 1)"
	 */
	private UserButton mLogicalForm;

	/**
	 * 
	 * @uml.property name="mDelete"
	 * @uml.associationEnd 
	 * @uml.property name="mDelete" multiplicity="(1 1)"
	 */
	private UserButtonCopyTree mCopyTree;

	/**
	 * 
	 * @uml.property name="mSaveAll"
	 * @uml.associationEnd 
	 * @uml.property name="mSaveAll" multiplicity="(1 1)"
	 */
	private UserButtonSaveAll mSaveAll;

	/**
	 * 
	 * @uml.property name="mRedo"
	 * @uml.associationEnd 
	 * @uml.property name="mRedo" multiplicity="(1 1)"
	 */
	private UserButtonRedo mRedo;

	/**
	 * 
	 * @uml.property name="mUndo"
	 * @uml.associationEnd 
	 * @uml.property name="mUndo" multiplicity="(1 1)"
	 */
	private UserButtonUndo mUndo;

	/**
	 * 
	 * @uml.property name="mPaste"
	 * @uml.associationEnd 
	 * @uml.property name="mPaste" multiplicity="(1 1)"
	 */
	private UserButtonPaste mPaste;

	/**
	 * 
	 * @uml.property name="mCopy"
	 * @uml.associationEnd 
	 * @uml.property name="mCopy" multiplicity="(1 1)"
	 */
	private UserButtonCopy mCopy;

	/**
	 * 
	 * @uml.property name="mCut"
	 * @uml.associationEnd 
	 * @uml.property name="mCut" multiplicity="(1 1)"
	 */
	private UserButtonCut mCut;

	/**
	 * 
	 * @uml.property name="mPrintPreview"
	 * @uml.associationEnd 
	 * @uml.property name="mPrintPreview" multiplicity="(1 1)"
	 */
	private UserButtonPrintPreview mPrintPreview;

	/**
	 * 
	 * @uml.property name="mZoomMinus"
	 * @uml.associationEnd 
	 * @uml.property name="mZoomMinus" multiplicity="(1 1)"
	 */
	private UserButtonZoomMinus mZoomMinus;

	/**
	 * 
	 * @uml.property name="mZoomPlus"
	 * @uml.associationEnd 
	 * @uml.property name="mZoomPlus" multiplicity="(1 1)"
	 */
	private UserButtonZoomPlus mZoomPlus;

	/**
	 * 
	 * @uml.property name="mPrint"
	 * @uml.associationEnd 
	 * @uml.property name="mPrint" multiplicity="(1 1)"
	 */
	private UserButtonPrint mPrint;

	/**
	 * 
	 * @uml.property name="mSaveAs"
	 * @uml.associationEnd 
	 * @uml.property name="mSaveAs" multiplicity="(1 1)"
	 */
	private UserButtonSaveAs mSaveAs;

	/**
	 * 
	 * @uml.property name="mSave"
	 * @uml.associationEnd 
	 * @uml.property name="mSave" multiplicity="(1 1)"
	 */
	private UserButtonSave mSave;

	/**
	 * 
	 * @uml.property name="mOpen"
	 * @uml.associationEnd 
	 * @uml.property name="mOpen" multiplicity="(1 1)"
	 */
	private UserButton mOpen;

	/**
	 * 
	 * @uml.property name="mUserFrame"
	 * @uml.associationEnd 
	 * @uml.property name="mUserFrame" multiplicity="(1 1)" inverse="mToolBarFile:userInterface.UserFrame"
	 */
	private UserFrame mUserFrame;

/**
 * 
 * @param pUserFrame The UserFrame for this instance of TreeForm 
 * @param toolBars The bitwise set of toolbars to be added (kinda hokey, but
 * hey, I was having fun!)
 */
	public UserToolBar(UserFrame pUserFrame, int toolBars) {
		super();
		// change this for the default load-up value stored in the defaults.
		mIconSize = Sizer.iconSize();
		mUserFrame = pUserFrame;
		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setVgap(1);
		flowLayout.setHgap(1);
		flowLayout.setAlignment(FlowLayout.LEADING);
		this.setLayout(flowLayout);	
		if ((toolBars & LOAD) == LOAD)
			{
			addLoad();
			this.add(new JToolBar.Separator());
		}
		if ((toolBars & PRINT) == PRINT)
		{
			addPrint();
			this.add(new JToolBar.Separator());
		}
		if ((toolBars & CUT_AND_PASTE) == CUT_AND_PASTE)
		{
			addCutAndPaste();
			this.add(new JToolBar.Separator());
			addStack();
			this.add(new JToolBar.Separator());
		}
		if ((toolBars & VIEW_AND_HELP)  == VIEW_AND_HELP)
		{
			addViewAndHelp();
			this.add(new JToolBar.Separator());
		}
		if ((toolBars & ADD_FONT_EFFECTS) == ADD_FONT_EFFECTS)
		{
			addFontEffects();
		}
	}
/**
 * 
 * add stack options (undo, redo), fully i18n
 */
	private void addStack() {
		mUndo = new UserButtonUndo(mUserFrame,
		(ImageIcon) mUserFrame.getI18n().getObject("UNDO_ICON_LARGE"),
		(ImageIcon) mUserFrame.getI18n().getObject("UNDO_ICON_LARGE_GREY"),
		(ImageIcon) mUserFrame.getI18n().getObject("UNDO_ICON_LARGE_HIGHLIGHT"),
		(ImageIcon) mUserFrame.getI18n().getObject("UNDO_ICON_SMALL"),
		(ImageIcon) mUserFrame.getI18n().getObject("UNDO_ICON_SMALL_GREY"),
		(ImageIcon) mUserFrame.getI18n().getObject("UNDO_ICON_SMALL_HIGHLIGHT"),
		mIconSize, mUserFrame.getObservableStack(), mUserFrame.getObservableNew());
		mUndo.addActionListener(new ListenerUndo(mUserFrame));
		mUndo.setToolTipText((String) mUserFrame.getI18n().getObject("UNDO_TOOLTIP"));
		mUndo.setEnabled(false);
		mUndo.setFocusable(false);
		mUserFrame.getObservableStack().addObserver(mUndo);
		mUserFrame.getObservableNew().addObserver(mUndo);
		this.add(mUndo);
		mRedo = new UserButtonRedo(mUserFrame,
		(ImageIcon) mUserFrame.getI18n().getObject("REDO_ICON_LARGE"),
		(ImageIcon) mUserFrame.getI18n().getObject("REDO_ICON_LARGE_GREY"),
		(ImageIcon) mUserFrame.getI18n().getObject("REDO_ICON_LARGE_HIGHLIGHT"),
		(ImageIcon) mUserFrame.getI18n().getObject("REDO_ICON_SMALL"),
		(ImageIcon) mUserFrame.getI18n().getObject("REDO_ICON_SMALL_GREY"),
		(ImageIcon) mUserFrame.getI18n().getObject("REDO_ICON_SMALL_HIGHLIGHT"),
		mIconSize,mUserFrame.getObservableStack(), mUserFrame.getObservableNew());
		mRedo.addActionListener(new ListenerRedo(mUserFrame));
		mRedo.setToolTipText((String) mUserFrame.getI18n().getObject("REDO_TOOLTIP"));
		mRedo.setEnabled(false);
		mRedo.setFocusable(false);
		mUserFrame.getObservableStack().addObserver(mRedo);	
		mUserFrame.getObservableNew().addObserver(mRedo);
		this.add(mRedo);
	}
/**
 * add font effect options, fully i18n
 */
	private void addFontEffects() {
		mFont = new UserComboFont(mUserFrame.getObservableFont());
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		String fontNames[] = ge.getAvailableFontFamilyNames();
		mModelFont = new DefaultComboBoxModel();
		for (int i = 0; i < fontNames.length; i++)
		{
			mModelFont.addElement(StringManipulator.lrtrim(fontNames[i]));
		}
		mFont.setModel(mModelFont);
		mFont.addActionListener(new ListenerFontName(mUserFrame));
		mUserFrame.getObservableFont().addObserver(mFont);
		mFont.setFocusable(false);
		this.add(mFont);
		mFontSize = new UserComboFontSize(mUserFrame.getObservableFontSize());
		String fontSize[] = {"8","9","10","11","12","14","16","18"};
		mModelFontSize = new DefaultComboBoxModel();
		for (int i = 0; i < fontSize.length; i++)
		{
			mModelFontSize.addElement(fontSize[i]);
		}
		mFontSize.setModel(mModelFontSize);
		mFontSize.setEditable(true);
		mFontSize.setFocusable(false);
		mFontSize.addActionListener(new ListenerFontSize(mUserFrame));
		mUserFrame.getObservableFontSize().addObserver(mFontSize);
		this.add(mFontSize);		
		mBold = new UserToggleFontBold(mUserFrame,
		(ImageIcon) mUserFrame.getI18n().getObject("BOLD_ICON_LARGE"),
		(ImageIcon) mUserFrame.getI18n().getObject("BOLD_ICON_LARGE_GREY"),
		(ImageIcon) mUserFrame.getI18n().getObject("BOLD_ICON_LARGE_HIGHLIGHT"),
		(ImageIcon) mUserFrame.getI18n().getObject("BOLD_ICON_SMALL"),
		(ImageIcon) mUserFrame.getI18n().getObject("BOLD_ICON_SMALL_GREY"),
		(ImageIcon) mUserFrame.getI18n().getObject("BOLD_ICON_SMALL_HIGHLIGHT"),
		mIconSize, mUserFrame.getObservableFontBold(),mUserFrame.getObservableNew());
		mBold.addActionListener(new ListenerBold(mUserFrame));
		mBold.setToolTipText((String) mUserFrame.getI18n().getObject("BOLD_TOOLTIP"));
		mUserFrame.getObservableFontBold().addObserver(mBold);
		mUserFrame.getObservableNew().addObserver(mBold);
		mBold.setFocusable(false);
		this.add(mBold);
		mItalics = new UserToggleFontItalic(mUserFrame,
		(ImageIcon) mUserFrame.getI18n().getObject("ITALICS_ICON_LARGE"),
		(ImageIcon) mUserFrame.getI18n().getObject("ITALICS_ICON_LARGE_GREY"),
		(ImageIcon) mUserFrame.getI18n().getObject("ITALICS_ICON_LARGE_HIGHLIGHT"),
		(ImageIcon) mUserFrame.getI18n().getObject("ITALICS_ICON_SMALL"),
		(ImageIcon) mUserFrame.getI18n().getObject("ITALICS_ICON_SMALL_GREY"),
		(ImageIcon) mUserFrame.getI18n().getObject("ITALICS_ICON_SMALL_HIGHLIGHT"),
		mIconSize, mUserFrame.getObservableFontItalic(), mUserFrame.getObservableNew());
		mItalics.addActionListener(new ListenerItalics(mUserFrame));
		mItalics.setToolTipText((String) mUserFrame.getI18n().getObject("ITALICS_TOOLTIP"));
		mUserFrame.getObservableFontItalic().addObserver(mItalics);
		mUserFrame.getObservableNew().addObserver(mItalics);
		mItalics.setFocusable(false);
		this.add(mItalics);
		mUnderline = new UserToggleFontUnderline(mUserFrame,
		(ImageIcon) mUserFrame.getI18n().getObject("UNDERLINE_ICON_LARGE"),
		(ImageIcon) mUserFrame.getI18n().getObject("UNDERLINE_ICON_LARGE_GREY"),
		(ImageIcon) mUserFrame.getI18n().getObject("UNDERLINE_ICON_LARGE_HIGHLIGHT"),
		(ImageIcon) mUserFrame.getI18n().getObject("UNDERLINE_ICON_SMALL"),
		(ImageIcon) mUserFrame.getI18n().getObject("UNDERLINE_ICON_SMALL_GREY"),
		(ImageIcon) mUserFrame.getI18n().getObject("UNDERLINE_ICON_SMALL_HIGHLIGHT"),
		mIconSize, mUserFrame.getObservableFontUnderline(), mUserFrame.getObservableNew());
		mUnderline.addActionListener(new ListenerUnderline(mUserFrame));
		mUnderline.setToolTipText((String) mUserFrame.getI18n().getObject("UNDERLINE_TOOLTIP"));
		mUserFrame.getObservableFontUnderline().addObserver(mUnderline);
		mUserFrame.getObservableNew().addObserver(mUnderline);
		mUnderline.setFocusable(false);
		this.add(mUnderline);
		
		mSubscript = new UserToggleFontSubscript(mUserFrame,
		(ImageIcon) mUserFrame.getI18n().getObject("SUBSCRIPT_ICON_LARGE"),
		(ImageIcon) mUserFrame.getI18n().getObject("SUBSCRIPT_ICON_LARGE_GREY"),
		(ImageIcon) mUserFrame.getI18n().getObject("SUBSCRIPT_ICON_LARGE_HIGHLIGHT"),
		(ImageIcon) mUserFrame.getI18n().getObject("SUBSCRIPT_ICON_SMALL"),
		(ImageIcon) mUserFrame.getI18n().getObject("SUBSCRIPT_ICON_SMALL_GREY"),
		(ImageIcon) mUserFrame.getI18n().getObject("SUBSCRIPT_ICON_SMALL_HIGHLIGHT"),
		mIconSize, mUserFrame.getObservableSubscript(), mUserFrame.getObservableNew());
		mSubscript.addActionListener(new ListenerSubscript(mUserFrame));
		mSubscript.setToolTipText((String) mUserFrame.getI18n().getObject("SUBSCRIPT_TOOLTIP"));
		mUserFrame.getObservableSubscript().addObserver(mSubscript);
		mUserFrame.getObservableNew().addObserver(mSubscript);
		mSubscript.setFocusable(false);
		this.add(mSubscript);
				
		mSuperscript = new UserToggleFontSuperscript(mUserFrame,
		(ImageIcon) mUserFrame.getI18n().getObject("SUPERSCRIPT_ICON_LARGE"),
		(ImageIcon) mUserFrame.getI18n().getObject("SUPERSCRIPT_ICON_LARGE_GREY"),
		(ImageIcon) mUserFrame.getI18n().getObject("SUPERSCRIPT_ICON_LARGE_HIGHLIGHT"),
		(ImageIcon) mUserFrame.getI18n().getObject("SUPERSCRIPT_ICON_SMALL"),
		(ImageIcon) mUserFrame.getI18n().getObject("SUPERSCRIPT_ICON_SMALL_GREY"),
		(ImageIcon) mUserFrame.getI18n().getObject("SUPERSCRIPT_ICON_SMALL_HIGHLIGHT"),
		mIconSize, mUserFrame.getObservableSuperscript(), mUserFrame.getObservableNew());
		mSuperscript.addActionListener(new ListenerSuperscript(mUserFrame));
		mSuperscript.setToolTipText((String) mUserFrame.getI18n().getObject("SUPERSCRIPT_TOOLTIP"));
		mUserFrame.getObservableSuperscript().addObserver(mSuperscript);
		mUserFrame.getObservableNew().addObserver(mSuperscript);
		mSuperscript.setFocusable(false);
		this.add(mSuperscript);

	}
/**
 *  add view and help options, fully i18n
 *
 */
	private void addViewAndHelp() {
		mZoomPlus = new UserButtonZoomPlus(mUserFrame,
		(ImageIcon) mUserFrame.getI18n().getObject("ZOOMPLUS_ICON_LARGE"),
		(ImageIcon) mUserFrame.getI18n().getObject("ZOOMPLUS_ICON_LARGE_GREY"),
		(ImageIcon) mUserFrame.getI18n().getObject("ZOOMPLUS_ICON_LARGE_HIGHLIGHT"),
		(ImageIcon) mUserFrame.getI18n().getObject("ZOOMPLUS_ICON_SMALL"),
		(ImageIcon) mUserFrame.getI18n().getObject("ZOOMPLUS_ICON_SMALL_GREY"),
		(ImageIcon) mUserFrame.getI18n().getObject("ZOOMPLUS_ICON_SMALL_HIGHLIGHT"),
		mIconSize,mUserFrame.getObservableNew());
		mZoomPlus.addActionListener(new ListenerZoomPlus(mUserFrame));
		mZoomPlus.setToolTipText((String) mUserFrame.getI18n().getObject("ZOOMPLUS_TOOLTIP"));
		mZoomPlus.setEnabled(false);
		mZoomPlus.setFocusable(false);
		mUserFrame.getObservableNew().addObserver(mZoomPlus);

		this.add(mZoomPlus);
		mZoomMinus = new UserButtonZoomMinus(mUserFrame,
		(ImageIcon) mUserFrame.getI18n().getObject("ZOOMMINUS_ICON_LARGE"),
		(ImageIcon) mUserFrame.getI18n().getObject("ZOOMMINUS_ICON_LARGE_GREY"),
		(ImageIcon) mUserFrame.getI18n().getObject("ZOOMMINUS_ICON_LARGE_HIGHLIGHT"),
		(ImageIcon) mUserFrame.getI18n().getObject("ZOOMMINUS_ICON_SMALL"),
		(ImageIcon) mUserFrame.getI18n().getObject("ZOOMMINUS_ICON_SMALL_GREY"),
		(ImageIcon) mUserFrame.getI18n().getObject("ZOOMMINUS_ICON_SMALL_HIGHLIGHT"),
		mIconSize,mUserFrame.getObservableNew());
		mZoomMinus.addActionListener(new ListenerZoomMinus(mUserFrame));
		mZoomMinus.setToolTipText((String) mUserFrame.getI18n().getObject("ZOOMMINUS_TOOLTIP"));
		mZoomMinus.setEnabled(false);
		mZoomMinus.setFocusable(false);
		mUserFrame.getObservableNew().addObserver(mZoomMinus);

		this.add(mZoomMinus);
		mZoom = new UserComboZoom(mUserFrame.getObservableZoom());
		String Zoom[] = {"25%","50%","75%","100%","150%","200%","500%","1000%"};
		mModelZoom = new DefaultComboBoxModel();
		for (int i = 0; i < Zoom.length; i++)
		{
			mModelZoom.addElement(Zoom[i]);
		}
		mZoom.setModel(mModelZoom);
		mZoom.setEditable(true);
		mZoom.setFocusable(false);
		mZoom.addActionListener(new ListenerZoom(mUserFrame));
		mUserFrame.getObservableZoom().addObserver(mZoom);
		this.add(mZoom);	
//		
//		mLogicalForm = new UserButton(mUserFrame,
//		(ImageIcon) mUserFrame.getI18n().getObject("LOGICALFORM_ICON_LARGE"),
//		(ImageIcon) mUserFrame.getI18n().getObject("LOGICALFORM_ICON_LARGE_GREY"),
//		(ImageIcon) mUserFrame.getI18n().getObject("LOGICALFORM_ICON_LARGE_HIGHLIGHT"),
//		(ImageIcon) mUserFrame.getI18n().getObject("LOGICALFORM_ICON_SMALL"),
//		(ImageIcon) mUserFrame.getI18n().getObject("LOGICALFORM_ICON_SMALL_GREY"),
//		(ImageIcon) mUserFrame.getI18n().getObject("LOGICALFORM_ICON_SMALL_HIGHLIGHT"),
//		mIconSize);
//		mLogicalForm.addActionListener(new ListenerChangeViewLayout(mUserFrame, SyntacticViewLayout.L_STRUCTURE));
//		mLogicalForm.setToolTipText((String) mUserFrame.getI18n().getObject("LOGICALFORM_TOOLTIP"));
//		mLogicalForm.setFocusable(false);
//		this.add(mLogicalForm);
//		mSurfaceStructure = new UserButton(mUserFrame,
//		(ImageIcon) mUserFrame.getI18n().getObject("SURFACESTRUCTURE_ICON_LARGE"),
//		(ImageIcon) mUserFrame.getI18n().getObject("SURFACESTRUCTURE_ICON_LARGE_GREY"),
//		(ImageIcon) mUserFrame.getI18n().getObject("SURFACESTRUCTURE_ICON_LARGE_HIGHLIGHT"),
//		(ImageIcon) mUserFrame.getI18n().getObject("SURFACESTRUCTURE_ICON_SMALL"),
//		(ImageIcon) mUserFrame.getI18n().getObject("SURFACESTRUCTURE_ICON_SMALL_GREY"),
//		(ImageIcon) mUserFrame.getI18n().getObject("SURFACESTRUCTURE_ICON_SMALL_HIGHLIGHT"),
//		mIconSize);
//		mSurfaceStructure.addActionListener(new ListenerChangeViewLayout(mUserFrame, SyntacticViewLayout.S_STRUCTURE));
//		mSurfaceStructure.setToolTipText((String) mUserFrame.getI18n().getObject("SURFACESTRUCTURE_TOOLTIP"));
//		mSurfaceStructure.setFocusable(false);
//		this.add(mSurfaceStructure);
//		mDStructure = new UserButton(mUserFrame,
//		(ImageIcon) mUserFrame.getI18n().getObject("DEEPSTRUCTURE_ICON_LARGE"),
//		(ImageIcon) mUserFrame.getI18n().getObject("DEEPSTRUCTURE_ICON_LARGE_GREY"),
//		(ImageIcon) mUserFrame.getI18n().getObject("DEEPSTRUCTURE_ICON_LARGE_HIGHLIGHT"),
//		(ImageIcon) mUserFrame.getI18n().getObject("DEEPSTRUCTURE_ICON_SMALL"),
//		(ImageIcon) mUserFrame.getI18n().getObject("DEEPSTRUCTURE_ICON_SMALL_GREY"),
//		(ImageIcon) mUserFrame.getI18n().getObject("DEEPSTRUCTURE_ICON_SMALL_HIGHLIGHT"),
//		mIconSize);
//		mDStructure.addActionListener(new ListenerChangeViewLayout(mUserFrame, SyntacticViewLayout.D_STRUCTURE));
//		mDStructure.setToolTipText((String) mUserFrame.getI18n().getObject("DEEPSTRUCTURE_TOOLTIP"));
//		mDStructure.setFocusable(false);
//		this.add(mDStructure);	
//		mLabelledBracket = new UserButton(mUserFrame,
//		(ImageIcon) mUserFrame.getI18n().getObject("LABELLEDBRACKET_ICON_LARGE"),
//		(ImageIcon) mUserFrame.getI18n().getObject("LABELLEDBRACKET_ICON_LARGE_GREY"),
//		(ImageIcon) mUserFrame.getI18n().getObject("LABELLEDBRACKET_ICON_LARGE_HIGHLIGHT"),
//		(ImageIcon) mUserFrame.getI18n().getObject("LABELLEDBRACKET_ICON_SMALL"),
//		(ImageIcon) mUserFrame.getI18n().getObject("LABELLEDBRACKET_ICON_SMALL_GREY"),
//		(ImageIcon) mUserFrame.getI18n().getObject("LABELLEDBRACKET_ICON_SMALL_HIGHLIGHT"),
//		mIconSize);
//		mLabelledBracket.addActionListener(new ListenerChangeViewLayout(mUserFrame, SyntacticViewLayout.LABELLED_BRACKET));
//		mLabelledBracket.setToolTipText((String) mUserFrame.getI18n().getObject("LABELLEDBRACKET_TOOLTIP"));
//		mLabelledBracket.setFocusable(false);
//		this.add(mLabelledBracket);		
	}
/**
 * add cut and paste options, fully i18n
 */
	private void addCutAndPaste() {
//		mProperties = new UserButton(mUserFrame,
//		(ImageIcon) mUserFrame.getI18n().getObject("PROPERTIES_ICON_LARGE"),
//		(ImageIcon) mUserFrame.getI18n().getObject("PROPERTIES_ICON_LARGE_GREY"),
//		(ImageIcon) mUserFrame.getI18n().getObject("PROPERTIES_ICON_LARGE_HIGHLIGHT"),
//		(ImageIcon) mUserFrame.getI18n().getObject("PROPERTIES_ICON_SMALL"),
//		(ImageIcon) mUserFrame.getI18n().getObject("PROPERTIES_ICON_SMALL_GREY"),
//		(ImageIcon) mUserFrame.getI18n().getObject("PROPERTIES_ICON_SMALL_HIGHLIGHT"),
//		mIconSize);
//		mProperties.addActionListener(new ListenerPropertiesTree(mUserFrame));
//		mProperties.setToolTipText((String) mUserFrame.getI18n().getObject("PROPERTIES_TOOLTIP"));
//		mProperties.setFocusable(false);
//		this.add(mProperties);
		mCut = (new UserButtonCut(mUserFrame,
		(ImageIcon) mUserFrame.getI18n().getObject("CUT_ICON_LARGE"),
		(ImageIcon) mUserFrame.getI18n().getObject("CUT_ICON_LARGE_GREY"),
		(ImageIcon) mUserFrame.getI18n().getObject("CUT_ICON_LARGE_HIGHLIGHT"),
		(ImageIcon) mUserFrame.getI18n().getObject("CUT_ICON_SMALL"),
		(ImageIcon) mUserFrame.getI18n().getObject("CUT_ICON_SMALL_GREY"),
		(ImageIcon) mUserFrame.getI18n().getObject("CUT_ICON_SMALL_HIGHLIGHT"),
		mIconSize,mUserFrame.getObservableNew()));
		mCut.addActionListener(new ListenerCut(mUserFrame));
		mCut.setToolTipText((String) mUserFrame.getI18n().getObject("CUT_TOOLTIP"));
		mCut.setEnabled(false);
		mCut.setFocusable(false);
		mUserFrame.getObservableNew().addObserver(mCut);
		
		this.add(mCut);
		mCopy = (new UserButtonCopy(mUserFrame,
		(ImageIcon) mUserFrame.getI18n().getObject("COPY_ICON_LARGE"),
		(ImageIcon) mUserFrame.getI18n().getObject("COPY_ICON_LARGE_GREY"),
		(ImageIcon) mUserFrame.getI18n().getObject("COPY_ICON_LARGE_HIGHLIGHT"),
		(ImageIcon) mUserFrame.getI18n().getObject("COPY_ICON_SMALL"),
		(ImageIcon) mUserFrame.getI18n().getObject("COPY_ICON_SMALL_GREY"),
		(ImageIcon) mUserFrame.getI18n().getObject("COPY_ICON_SMALL_HIGHLIGHT"),
		mIconSize,mUserFrame.getObservableNew()));
		mCopy.addActionListener(new ListenerCopy(mUserFrame));
		mCopy.setToolTipText((String) mUserFrame.getI18n().getObject("COPY_TOOLTIP"));
		mCopy.setEnabled(false);
		mCopy.setFocusable(false);
		mUserFrame.getObservableNew().addObserver(mCopy);


		this.add(mCopy);
		mPaste = new UserButtonPaste(mUserFrame,
		(ImageIcon) mUserFrame.getI18n().getObject("PASTE_ICON_LARGE"),
		(ImageIcon) mUserFrame.getI18n().getObject("PASTE_ICON_LARGE_GREY"),
		(ImageIcon) mUserFrame.getI18n().getObject("PASTE_ICON_LARGE_HIGHLIGHT"),
		(ImageIcon) mUserFrame.getI18n().getObject("PASTE_ICON_SMALL"),
		(ImageIcon) mUserFrame.getI18n().getObject("PASTE_ICON_SMALL_GREY"),
		(ImageIcon) mUserFrame.getI18n().getObject("PASTE_ICON_SMALL_HIGHLIGHT"),
		mIconSize,mUserFrame.getObservableNew());
		mPaste.addActionListener(new ListenerPaste(mUserFrame));
		mPaste.setToolTipText((String) mUserFrame.getI18n().getObject("PASTE_TOOLTIP"));
		mPaste.setEnabled(false);
		mPaste.setFocusable(false);
		mUserFrame.getObservableNew().addObserver(mPaste);

		this.add(mPaste);
		mCopyTree = new UserButtonCopyTree(mUserFrame,
		(ImageIcon) mUserFrame.getI18n().getObject("COPY_TREE_ICON_LARGE"),
		(ImageIcon) mUserFrame.getI18n().getObject("COPY_TREE_ICON_LARGE_GREY"),
		(ImageIcon) mUserFrame.getI18n().getObject("COPY_TREE_ICON_LARGE_HIGHLIGHT"),
		(ImageIcon) mUserFrame.getI18n().getObject("COPY_TREE_ICON_SMALL"),
		(ImageIcon) mUserFrame.getI18n().getObject("COPY_TREE_ICON_SMALL_GREY"),
		(ImageIcon) mUserFrame.getI18n().getObject("COPY_TREE_ICON_SMALL_HIGHLIGHT"),
		mIconSize,mUserFrame.getObservableNew());
		mCopyTree.addActionListener(new ListenerCopyTree(mUserFrame));
		mCopyTree.setToolTipText((String) mUserFrame.getI18n().getObject("COPY_TREE_TOOLTIP"));
		mCopyTree.setEnabled(false);
		mCopyTree.setFocusable(false);
		mUserFrame.getObservableNew().addObserver(mCopyTree);
		this.add(mCopyTree);
	}
/**
 * add print options, fully i18n
 */
	private void addPrint() {
		mPrint = new UserButtonPrint(mUserFrame,
		(ImageIcon) mUserFrame.getI18n().getObject("PRINT_ICON_LARGE"),
		(ImageIcon) mUserFrame.getI18n().getObject("PRINT_ICON_LARGE_GREY"),
		(ImageIcon) mUserFrame.getI18n().getObject("PRINT_ICON_LARGE_HIGHLIGHT"),
		(ImageIcon) mUserFrame.getI18n().getObject("PRINT_ICON_SMALL"),
		(ImageIcon) mUserFrame.getI18n().getObject("PRINT_ICON_SMALL_GREY"),
		(ImageIcon) mUserFrame.getI18n().getObject("PRINT_ICON_SMALL_HIGHLIGHT"),
		mIconSize,mUserFrame.getObservableNew());
		mPrint.addActionListener(new ListenerPrintTree(mUserFrame));
		mPrint.setToolTipText((String) mUserFrame.getI18n().getObject("PRINT_TOOLTIP"));
		mPrint.setEnabled(false);
		mPrint.setFocusable(false);
		mUserFrame.getObservableNew().addObserver(mPrint);
		this.add(mPrint);
		mPrintPreview = new UserButtonPrintPreview(mUserFrame,
		(ImageIcon) mUserFrame.getI18n().getObject("PRINTPREVIEW_ICON_LARGE"),
		(ImageIcon) mUserFrame.getI18n().getObject("PRINTPREVIEW_ICON_LARGE_GREY"),
		(ImageIcon) mUserFrame.getI18n().getObject("PRINTPREVIEW_ICON_LARGE_HIGHLIGHT"),
		(ImageIcon) mUserFrame.getI18n().getObject("PRINTPREVIEW_ICON_SMALL"),
		(ImageIcon) mUserFrame.getI18n().getObject("PRINTPREVIEW_ICON_SMALL_GREY"),
		(ImageIcon) mUserFrame.getI18n().getObject("PRINTPREVIEW_ICON_SMALL_HIGHLIGHT"),
		mIconSize,mUserFrame.getObservableNew());
		mPrintPreview.addActionListener(new ListenerPrintPreview(mUserFrame));
		mPrintPreview.setToolTipText((String) mUserFrame.getI18n().getObject("PRINTPREVIEW_TOOLTIP"));
		mPrintPreview.setEnabled(false);
		mPrintPreview.setFocusable(false);
		mUserFrame.getObservableNew().addObserver(mPrintPreview);
		this.add(mPrintPreview);

	}
/**
 * Add load options, fully i18n
 */
	private void addLoad() {
		mNew = new UserButton(mUserFrame,
		(ImageIcon) mUserFrame.getI18n().getObject("NEW_ICON_LARGE"),
		(ImageIcon) mUserFrame.getI18n().getObject("NEW_ICON_LARGE_GREY"),
		(ImageIcon) mUserFrame.getI18n().getObject("NEW_ICON_LARGE_HIGHLIGHT"),
		(ImageIcon) mUserFrame.getI18n().getObject("NEW_ICON_SMALL"),
		(ImageIcon) mUserFrame.getI18n().getObject("NEW_ICON_SMALL_GREY"),
		(ImageIcon) mUserFrame.getI18n().getObject("NEW_ICON_SMALL_HIGHLIGHT"),
		mIconSize
		);
		mNew.addActionListener(new ListenerNew(mUserFrame));
		mNew.setToolTipText((String) mUserFrame.getI18n().getObject("NEW_TOOLTIP"));
		mNew.setFocusable(false);
		this.add(mNew);
		mOpen = new UserButton(mUserFrame,
		(ImageIcon) mUserFrame.getI18n().getObject("OPEN_ICON_LARGE"),
		(ImageIcon) mUserFrame.getI18n().getObject("OPEN_ICON_LARGE_GREY"),
		(ImageIcon) mUserFrame.getI18n().getObject("OPEN_ICON_LARGE_HIGHLIGHT"),
		(ImageIcon) mUserFrame.getI18n().getObject("OPEN_ICON_SMALL"),
		(ImageIcon) mUserFrame.getI18n().getObject("OPEN_ICON_SMALL_GREY"),
		(ImageIcon) mUserFrame.getI18n().getObject("OPEN_ICON_SMALL_HIGHLIGHT"),
		mIconSize);
		mOpen.addActionListener(new ListenerLoad(mUserFrame));
		mOpen.setToolTipText((String) mUserFrame.getI18n().getObject("OPEN_TOOLTIP"));
		mOpen.setFocusable(false);
		this.add(mOpen);
		mSave = new UserButtonSave(mUserFrame,
		(ImageIcon) mUserFrame.getI18n().getObject("SAVE_ICON_LARGE"),
		(ImageIcon) mUserFrame.getI18n().getObject("SAVE_ICON_LARGE_GREY"),
		(ImageIcon) mUserFrame.getI18n().getObject("SAVE_ICON_LARGE_HIGHLIGHT"),
		(ImageIcon) mUserFrame.getI18n().getObject("SAVE_ICON_SMALL"),
		(ImageIcon) mUserFrame.getI18n().getObject("SAVE_ICON_SMALL_GREY"),
		(ImageIcon) mUserFrame.getI18n().getObject("SAVE_ICON_SMALL_HIGHLIGHT"),
		mIconSize,mUserFrame.getObservableNew());
		mSave.addActionListener(new ListenerSave(mUserFrame));
		mSave.setToolTipText((String) mUserFrame.getI18n().getObject("SAVE_TOOLTIP"));
		mSave.setEnabled(false);
		mSave.setFocusable(false);
		mUserFrame.getObservableNew().addObserver(mSave);
		this.add(mSave);
		mSaveAs = new UserButtonSaveAs(mUserFrame,
		(ImageIcon) mUserFrame.getI18n().getObject("SAVEAS_ICON_LARGE"),
		(ImageIcon) mUserFrame.getI18n().getObject("SAVEAS_ICON_LARGE_GREY"),
		(ImageIcon) mUserFrame.getI18n().getObject("SAVEAS_ICON_LARGE_HIGHLIGHT"),
		(ImageIcon) mUserFrame.getI18n().getObject("SAVEAS_ICON_SMALL"),
		(ImageIcon) mUserFrame.getI18n().getObject("SAVEAS_ICON_SMALL_GREY"),
		(ImageIcon) mUserFrame.getI18n().getObject("SAVEAS_ICON_SMALL_HIGHLIGHT"),
		mIconSize,mUserFrame.getObservableNew());
		mSaveAs.addActionListener(new ListenerSaveAs(mUserFrame));
		mSaveAs.setToolTipText((String) mUserFrame.getI18n().getObject("SAVEAS_TOOLTIP"));
		mSaveAs.setEnabled(false);
		mSaveAs.setFocusable(false);
		mUserFrame.getObservableNew().addObserver(mSaveAs);
		this.add(mSaveAs);
		mSaveAll = new UserButtonSaveAll(mUserFrame,
		(ImageIcon) mUserFrame.getI18n().getObject("SAVEALL_ICON_LARGE"),
		(ImageIcon) mUserFrame.getI18n().getObject("SAVEALL_ICON_LARGE_GREY"),
		(ImageIcon) mUserFrame.getI18n().getObject("SAVEALL_ICON_LARGE_HIGHLIGHT"),
		(ImageIcon) mUserFrame.getI18n().getObject("SAVEALL_ICON_SMALL"),
		(ImageIcon) mUserFrame.getI18n().getObject("SAVEALL_ICON_SMALL_GREY"),
		(ImageIcon) mUserFrame.getI18n().getObject("SAVEALL_ICON_SMALL_HIGHLIGHT"),
		mIconSize,mUserFrame.getObservableNew());
		mSaveAll.addActionListener(new ListenerSaveAll(mUserFrame));
		mSaveAll.setToolTipText((String) mUserFrame.getI18n().getObject("SAVEALL_TOOLTIP"));
		mSaveAll.setEnabled(false);
		mSaveAll.setFocusable(false);
		mUserFrame.getObservableNew().addObserver(mSaveAll);
		this.add(mSaveAll);
		mExport = new UserButtonExport(mUserFrame,
		(ImageIcon) mUserFrame.getI18n().getObject("EXPORT_ICON_LARGE"),
		(ImageIcon) mUserFrame.getI18n().getObject("EXPORT_ICON_LARGE_GREY"),
		(ImageIcon) mUserFrame.getI18n().getObject("EXPORT_ICON_LARGE_HIGHLIGHT"),
		(ImageIcon) mUserFrame.getI18n().getObject("EXPORT_ICON_SMALL"),
		(ImageIcon) mUserFrame.getI18n().getObject("EXPORT_ICON_SMALL_GREY"),
		(ImageIcon) mUserFrame.getI18n().getObject("EXPORT_ICON_SMALL_HIGHLIGHT"),
		mIconSize,mUserFrame.getObservableNew());
		mExport.addActionListener(new ListenerExportTree(mUserFrame));
		mExport.setToolTipText((String) mUserFrame.getI18n().getObject("EXPORT_TOOLTIP"));
		mExport.setEnabled(false);
		mExport.setFocusable(false);
		mUserFrame.getObservableNew().addObserver(mExport);
		this.add(mExport);
	}

	/**
	 * 
	 * @return Accessors
	 */
		protected UserToggleFontBold getBold() {return mBold;}
	/**
	 * 
	 * @return Accessors
	 */
		protected UserButtonCopy getCopy() {return mCopy;}
	/**
	 * 
	 * @return Accessors
	 */
		protected UserButtonCut getCut() {return mCut;}
	/**
	 * 
	 * @return Accessors
	 */
		protected UserButton getSizeButton(){return mSizeButton;}
	/**
	 * 
	 * @return Accessors
	 */
		protected UserButton getNew(){return mNew;}
	/**
	 * 
	 * @return Accessors
	 */
		protected JComboBox getFontSize(){return mFontSize;}
	/**
	 * 
	 * @return Accessors
	 */
		protected JComboBox getComboFont(){return mFont;}
	/**
	 * 
	 * @return Accessors
	 */
		protected UserToggleFontUnderline getUnderline(){return mUnderline;}
	/**
	 * 
	 * @return Accessors
	 */
		protected UserToggleFontItalic getItalics(){return mItalics;}
	/**
	 * 
	 * @return Accessors
	 */
		protected UserButtonExport getExport(){return mExport;}
	/**
	 * 
	 * @return Accessors
	 */
		protected UserButton getProperties(){return mProperties;}
	/**
	 * 
	 * @return Accessors
	 */
		protected UserButton getLabelledBracket(){return mLabelledBracket;}
	/**
	 * 
	 * @return Accessors
	 */
		protected UserButton getDStructure(){return mDStructure;}
	/**
	 * 
	 * @return Accessors
	 */
		protected UserButton getSStructure(){return mSurfaceStructure;}
	/**
	 * 
	 * @return Accessors
	 */
		protected UserButton getLForm(){return mLogicalForm;}
	/**
	 * 
	 * @return Accessors
	 */
		protected UserButtonCopyTree getDelete(){return mCopyTree;}
	/**
	 * 
	 * @return Accessors
	 */
		protected UserButtonSaveAll getSaveAll(){return mSaveAll;}
	/**
	 * 
	 * @return Accessors
	 */
		protected UserButtonRedo getRedo(){return mRedo;}
	/**
	 * 
	 * @return Accessors
	 */
		protected UserButtonUndo getUndo(){return mUndo;}
	/**
	 * 
	 * @return Accessors
	 */
		protected UserButtonPaste getPaste(){return mPaste;}
	/**
	 * 
	 * @return Accessors
	 */
		protected UserButtonPrintPreview getPrintPreview(){return mPrintPreview;}
	/**
	 * 
	 * @return Accessors
	 */
		protected UserButtonZoomMinus getZoomMinus(){return mZoomMinus;}
	/**
	 * 
	 * @return Accessors
	 */
		protected UserButtonZoomPlus getZoomPlus(){return mZoomPlus;}
	/**
	 * 
	 * @return Accessors
	 */
		protected UserButtonPrint getPrint(){return mPrint;}
	/**
	 * 
	 * @return Accessors
	 */
		protected UserButtonSaveAs getSaveAs(){return mSaveAs;}
	/**
	 * 
	 * @return Accessors
	 */
		protected UserButtonSave getSave(){return mSave;}
	/**
	 * 
	 * @return Accessors
	 */
		protected UserButton getOpen(){return mOpen;}
	/**
	 * 
	 * @return Accessors
	 */
		protected JComboBox getZoom(){return mZoom;}
		
}
