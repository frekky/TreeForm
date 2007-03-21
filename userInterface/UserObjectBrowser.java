
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

import javax.swing.JPanel;
import enumerators.SyntacticFeatureType;
import enumerators.SyntacticOperationType;
import enumerators.SyntacticStructureType;

import staticFunctions.Sizer;

/**
 * 
 * @author Donald Derrick
 * @version 0.1
 * <br>
 * date: 19-Aug-2004
 * <br>
 * <br>
 * The ObjectBrowser is the class that contains all the draggable buttons used
 * to produce syntax structures and features in the GUI environment.
 */
public class UserObjectBrowser extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @uml.property name="mMorph"
	 * @uml.associationEnd 
	 * @uml.property name="mMorph" multiplicity="(1 1)"
	 */
	private UserBrowserButton mMorph;

	/**
	 * 
	 * @uml.property name="mFeature"
	 * @uml.associationEnd 
	 * @uml.property name="mFeature" multiplicity="(1 1)"
	 */
	private UserBrowserButton mFeature;


	/**
	 * 
	 * @uml.property name="mTheta"
	 * @uml.associationEnd 
	 * @uml.property name="mTheta" multiplicity="(1 1)"
	 */
	private UserBrowserButton mTheta;

	/**
	 * 
	 * @uml.property name="mCase"
	 * @uml.associationEnd 
	 * @uml.property name="mCase" multiplicity="(1 1)"
	 */
	private UserBrowserButton mCase;

	/**
	 * 
	 * @uml.property name="mTriangle"
	 * @uml.associationEnd 
	 * @uml.property name="mTriangle" multiplicity="(1 1)"
	 */
	private UserBrowserButton mTriangle;

	/**
	 * 
	 * @uml.property name="mXBar"
	 * @uml.associationEnd 
	 * @uml.property name="mXBar" multiplicity="(1 1)"
	 */
	private UserBrowserButton mXBar;

	/**
	 * 
	 * @uml.property name="mTrinary"
	 * @uml.associationEnd 
	 * @uml.property name="mTrinary" multiplicity="(1 1)"
	 */
	private UserBrowserButton mTrinary;

	/**
	 * 
	 * @uml.property name="mBinary"
	 * @uml.associationEnd 
	 * @uml.property name="mBinary" multiplicity="(1 1)"
	 */
	private UserBrowserButton mBinary;

	/**
	 * 
	 * @uml.property name="mUnary"
	 * @uml.associationEnd 
	 * @uml.property name="mUnary" multiplicity="(1 1)"
	 */
	private UserBrowserButton mUnary;

	/**
	 * 
	 * @uml.property name="mAdjunct"
	 * @uml.associationEnd 
	 * @uml.property name="mAdjunct" multiplicity="(1 1)"
	 */
	private UserBrowserButton mAdjunct;

	/**
	 * 
	 * @uml.property name="mUIObject"
	 * @uml.associationEnd 
	 * @uml.property name="mUIObject" multiplicity="(1 1)"
	 */
	private ButtonUIAbstract mUIObject;

	/**
	 * 
	 * @uml.property name="mHead"
	 * @uml.associationEnd 
	 * @uml.property name="mHead" multiplicity="(1 1)"
	 */
	private UserBrowserButton mHead;

	/**
	 * 
	 * @uml.property name="mUserFrame"
	 * @uml.associationEnd 
	 * @uml.property name="mUserFrame" multiplicity="(1 1)"
	 */
	private UserFrame mUserFrame;

	private UserBrowserButton mPhrase;

	private UserBrowserButton mMovement;

	private UserBrowserButton mErase;

/**
 * 
 * @param pUserFrame Yup, you guessed it, the UserFrame from this instance of TreeForm
 */
	public UserObjectBrowser(UserFrame pUserFrame) {
		super();
		mUserFrame = pUserFrame;
		this.setLayout(new FlowLayout());
		addObjects();
	}
/**
 * Simple, adds all the objects to this panel.  Note that the size is always
 * proportional to your screen resolution.
 */
	private void addObjects() {
		mHead = new UserBrowserButton(mUserFrame, SyntacticStructureType.HEAD);
		mUIObject = (ButtonUIAbstract) ButtonUIHead.createUI(mHead);
		mUIObject.setResourceBundle(mUserFrame.getI18n());
		mHead.setUI(mUIObject);
		mHead.setPreferredSize(Sizer.scaledButtonSize());
		mHead.setLabel();
		mHead.addMouseListener(new ListenerMouse(mUserFrame));
		mHead.addMouseMotionListener(new ListenerMouseMotion(mUserFrame));
		mHead.setToolTipText((String) mUserFrame.getI18n().getObject("NODE_UP"));
		this.add(mHead);
		
		mPhrase = new UserBrowserButton(mUserFrame, SyntacticStructureType.PHRASE);
		mUIObject = (ButtonUIAbstract) ButtonUIPhrase.createUI(mPhrase);
		mUIObject.setResourceBundle(mUserFrame.getI18n());
		mPhrase.setUI(mUIObject);
		mPhrase.setPreferredSize(Sizer.scaledButtonSize());
		mPhrase.setLabel();
		mPhrase.addMouseListener(new ListenerMouse(mUserFrame));
		mPhrase.addMouseMotionListener(new ListenerMouseMotion(mUserFrame));
		mPhrase.setToolTipText((String) mUserFrame.getI18n().getObject("NODE_DOWN"));
		this.add(mPhrase);
		
		mMorph = new UserBrowserButton(mUserFrame, SyntacticStructureType.MORPH);
		mUIObject = (ButtonUIAbstract) ButtonUIMorph.createUI(mMorph);
		mUIObject.setResourceBundle(mUserFrame.getI18n());
		mMorph.setUI(mUIObject);
		mMorph.setPreferredSize(Sizer.scaledButtonSize());
		mMorph.setLabel();
		mMorph.addMouseListener(new ListenerMouse(mUserFrame));
		mMorph.addMouseMotionListener(new ListenerMouseMotion(mUserFrame));
		mMorph.setToolTipText((String) mUserFrame.getI18n().getObject("MORPH"));
		this.add(mMorph);
		
		
		mTriangle = new UserBrowserButton(mUserFrame, SyntacticStructureType.TRIANGLE);
		mUIObject = (ButtonUIAbstract) ButtonUITriangle.createUI(mTriangle);
		mUIObject.setResourceBundle(mUserFrame.getI18n());
		mTriangle.setUI(mUIObject);
		mTriangle.setPreferredSize(Sizer.scaledButtonSize());
		mTriangle.setLabel();
		mTriangle.addMouseListener(new ListenerMouse(mUserFrame));
		mTriangle.addMouseMotionListener(
			new ListenerMouseMotion(mUserFrame));
		mTriangle.setToolTipText((String) mUserFrame.getI18n().getObject("TRIANGLE"));
		this.add(mTriangle);

		mCase = new UserBrowserButton(mUserFrame, SyntacticFeatureType.CASE);
		mUIObject = (ButtonUIAbstract) ButtonUICase.createUI(mCase);
		mUIObject.setResourceBundle(mUserFrame.getI18n());
		mCase.setUI(mUIObject);
		mCase.setPreferredSize(Sizer.scaledButtonSize());
		mCase.setLabel();
		mCase.addMouseListener(new ListenerMouse(mUserFrame));
		mCase.addMouseMotionListener(new ListenerMouseMotion(mUserFrame));
		mCase.setToolTipText((String) mUserFrame.getI18n().getObject("CASE"));
		this.add(mCase);

		mTheta = new UserBrowserButton(mUserFrame, SyntacticFeatureType.THETA);
		mUIObject = (ButtonUIAbstract) ButtonUITheta.createUI(mTheta);
		mUIObject.setResourceBundle(mUserFrame.getI18n());
		mTheta.setUI(mUIObject);
		mTheta.setPreferredSize(Sizer.scaledButtonSize());
		mTheta.setLabel();
		mTheta.addMouseListener(new ListenerMouse(mUserFrame));
		mTheta.addMouseMotionListener(new ListenerMouseMotion(mUserFrame));
		mTheta.setToolTipText((String) mUserFrame.getI18n().getObject("THETA"));
		this.add(mTheta);


		mFeature = new UserBrowserButton(mUserFrame,SyntacticFeatureType.FEATURE);
		mUIObject = (ButtonUIAbstract) ButtonUIFeature.createUI(mFeature);
		mUIObject.setResourceBundle(mUserFrame.getI18n());
		mFeature.setUI(mUIObject);
		mFeature.setPreferredSize(Sizer.scaledButtonSize());
		mFeature.setLabel();
		mFeature.addMouseListener(new ListenerMouse(mUserFrame));
		mFeature.addMouseMotionListener(
			new ListenerMouseMotion(mUserFrame));
		mFeature.setToolTipText((String) mUserFrame.getI18n().getObject("FEATURE"));
		this.add(mFeature);
		
		mUnary = new UserBrowserButton(mUserFrame, SyntacticStructureType.UNARY);
		mUIObject = (ButtonUIAbstract) ButtonUIUnary.createUI(mUnary);
		mUIObject.setResourceBundle(mUserFrame.getI18n());
		mUnary.setUI(mUIObject);
		mUnary.setPreferredSize(Sizer.scaledButtonSize());
		mUnary.setLabel();
		mUnary.addMouseListener(new ListenerMouse(mUserFrame));
		mUnary.addMouseMotionListener(new ListenerMouseMotion(mUserFrame));
		mUnary.setToolTipText((String) mUserFrame.getI18n().getObject("UNARY"));
		this.add(mUnary);
		
		mBinary = new UserBrowserButton(mUserFrame, SyntacticStructureType.BINARY);
		mUIObject = (ButtonUIAbstract) ButtonUIBinary.createUI(mBinary);
		mUIObject.setResourceBundle(mUserFrame.getI18n());
		mBinary.setUI(mUIObject);
		mBinary.setPreferredSize(Sizer.scaledButtonSize());
		mBinary.setLabel();
		mBinary.addMouseListener(new ListenerMouse(mUserFrame));
		mBinary.addMouseMotionListener(new ListenerMouseMotion(mUserFrame));
		mBinary.setToolTipText((String) mUserFrame.getI18n().getObject("BINARY"));
		this.add(mBinary);
		
		mTrinary = new UserBrowserButton(mUserFrame, SyntacticStructureType.TRINARY);
		mUIObject = (ButtonUIAbstract) ButtonUITernary.createUI(mTrinary);
		mUIObject.setResourceBundle(mUserFrame.getI18n());
		mTrinary.setUI(mUIObject);
		mTrinary.setPreferredSize(Sizer.scaledButtonSize());
		mTrinary.setLabel();
		mTrinary.addMouseListener(new ListenerMouse(mUserFrame));
		mTrinary.addMouseMotionListener(
			new ListenerMouseMotion(mUserFrame));
		mTrinary.setToolTipText((String) mUserFrame.getI18n().getObject("TERNARY"));
		this.add(mTrinary);
		
		mAdjunct = new UserBrowserButton(mUserFrame, SyntacticStructureType.ADJUNCT);
		mUIObject = (ButtonUIAbstract) ButtonUIAdjunct.createUI(mAdjunct);
		mUIObject.setResourceBundle(mUserFrame.getI18n());
		mAdjunct.setUI(mUIObject);
		mAdjunct.setPreferredSize(Sizer.scaledButtonSize());
		mAdjunct.setLabel();
		mAdjunct.addMouseListener(new ListenerMouse(mUserFrame));
		mAdjunct.addMouseMotionListener(
			new ListenerMouseMotion(mUserFrame));
		mAdjunct.setToolTipText((String) mUserFrame.getI18n().getObject("ADJUNCT"));
		this.add(mAdjunct);
		
		
		mXBar = new UserBrowserButton(mUserFrame, SyntacticStructureType.X_BAR);
		mUIObject = (ButtonUIAbstract) ButtonUIXBar.createUI(mXBar);
		mUIObject.setResourceBundle(mUserFrame.getI18n());
		mXBar.setUI(mUIObject);
		mXBar.setPreferredSize(Sizer.scaledButtonSize());
		mXBar.setLabel();
		mXBar.addMouseListener(new ListenerMouse(mUserFrame));
		mXBar.addMouseMotionListener(new ListenerMouseMotion(mUserFrame));
		mXBar.setToolTipText((String) mUserFrame.getI18n().getObject("X-BAR"));
		this.add(mXBar);

		mMovement = new UserBrowserButton(mUserFrame, SyntacticOperationType.MOVEMENT);
		mUIObject = (ButtonUIAbstract) ButtonUIMovement.createUI(mMovement);
		mUIObject.setResourceBundle(mUserFrame.getI18n());
		mMovement.setUI(mUIObject);
		mMovement.setPreferredSize(Sizer.scaledButtonSize());
		mMovement.setLabel();
		mMovement.addMouseListener(new ListenerMouse(mUserFrame));
		mMovement.addMouseMotionListener(new ListenerMouseMotion(mUserFrame));
		mMovement.setToolTipText((String) mUserFrame.getI18n().getObject("MOVEMENT"));
		this.add(mMovement);
		
		mErase = new UserBrowserButton(mUserFrame, SyntacticOperationType.ERASE);
		mUIObject = (ButtonUIAbstract) ButtonUIErase.createUI(mErase);
		mUIObject.setResourceBundle(mUserFrame.getI18n());
		mErase.setUI(mUIObject);
		mErase.setPreferredSize(Sizer.scaledButtonSize());
		mErase.setLabel();
		mErase.addMouseListener(new ListenerMouse(mUserFrame));
		mErase.addMouseMotionListener(new ListenerMouseMotion(mUserFrame));
		mErase.setToolTipText((String) mUserFrame.getI18n().getObject("ERASE"));
		this.add(mErase);
		
		
		
//		mBinary = new UserBrowserButton(mUserFrame, SyntacticStructureType.BINARY);
//		mUIObject = (ButtonUI) ButtonUIBinary.createUI(mBinary);
//		mBinary.setUI(mUIObject);
//		mBinary.setPreferredSize(Sizer.scaledButtonSize());
//		mBinary.setLabel();
//		mBinary.addMouseListener(new ListenerMouse(mUserFrame));
//		mBinary.addMouseMotionListener(new ListenerMouseMotion(mUserFrame));
//		mBinary.setToolTipText((String) mUserFrame.getI18n().getObject("BINARY"));
//		this.add(mBinary);
//		
//		mTrinary = new UserBrowserButton(mUserFrame, SyntacticStructureType.TRINARY);
//		mUIObject = (ButtonUI) ButtonUITernary.createUI(mTrinary);
//		mTrinary.setUI(mUIObject);
//		mTrinary.setPreferredSize(Sizer.scaledButtonSize());
//		mTrinary.setLabel();
//		mTrinary.addMouseListener(new ListenerMouse(mUserFrame));
//		mTrinary.addMouseMotionListener(
//			new ListenerMouseMotion(mUserFrame));
//		mTrinary.setToolTipText((String) mUserFrame.getI18n().getObject("TERNARY"));
//		this.add(mTrinary);
//		
//		mAdjunct = new UserBrowserButton(mUserFrame, SyntacticStructureType.ADJUNCT);
//		mUIObject = (ButtonUI) ButtonUIAdjunct.createUI(mAdjunct);
//		mAdjunct.setUI(mUIObject);
//		mAdjunct.setPreferredSize(Sizer.scaledButtonSize());
//		mAdjunct.setLabel();
//		mAdjunct.addMouseListener(new ListenerMouse(mUserFrame));
//		mAdjunct.addMouseMotionListener(
//			new ListenerMouseMotion(mUserFrame));
//		mAdjunct.setToolTipText((String) mUserFrame.getI18n().getObject("ADJUNCT"));
//		this.add(mAdjunct);
//		
//		
//		mXBar = new UserBrowserButton(mUserFrame, SyntacticStructureType.X_BAR);
//		mUIObject = (ButtonUI) ButtonUIXBar.createUI(mXBar);
//		mXBar.setUI(mUIObject);
//		mXBar.setPreferredSize(Sizer.scaledButtonSize());
//		mXBar.setLabel();
//		mXBar.addMouseListener(new ListenerMouse(mUserFrame));
//		mXBar.addMouseMotionListener(new ListenerMouseMotion(mUserFrame));
//		mXBar.setToolTipText((String) mUserFrame.getI18n().getObject("X-BAR"));
//		this.add(mXBar);
		
		
//		this.add(new JLabel((String) mUserFrame.getI18n().getObject("INSTRUCTIONS")));
//		JLabel moveTree = new JLabel((String) mUserFrame.getI18n().getObject("MOVE_TREE"));
//		JLabel moveTree2 = new JLabel((String) mUserFrame.getI18n().getObject("MOVE_TREE2"));
//		
//		this.add(moveTree);
//		this.add(moveTree2);
//	    String vers = System.getProperty("os.name").toLowerCase();
//
//	    JLabel addLines= new JLabel();
//	    JLabel addLines2 = new JLabel();
//	    if (vers.indexOf("mac") != -1) {
//	    	addLines.setText((String) mUserFrame.getI18n().getObject("ADD_LINES_MAC"));
//	    	addLines2.setText((String) mUserFrame.getI18n().getObject("ADD_LINES2"));
//	    }
//	    else
//	    {
//	    	addLines.setText((String) mUserFrame.getI18n().getObject("ADD_LINES"));
//	    	addLines2.setText((String) mUserFrame.getI18n().getObject("ADD_LINES2"));
//	    }
//		this.add(addLines);
//		this.add(addLines2);
	}

}
