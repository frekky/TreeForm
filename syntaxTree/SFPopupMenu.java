
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

package syntaxTree;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
/**
 * 
 * @author Donald Derrick
 * @version 0.1
 * <br>
 * date: 20-Aug-2004
 * <br>
 * <br>
 * A class containing the popup menu for SyntacticFeatures
 */
public class SFPopupMenu extends JPopupMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @uml.property name="mAddFeature"
	 * @uml.associationEnd 
	 * @uml.property name="mAddFeature" multiplicity="(1 1)"
	 */
	private JMenuItem mAddFeature;

	/**
	 * 
	 * @uml.property name="mDeleteFeatureSet"
	 * @uml.associationEnd 
	 * @uml.property name="mDeleteFeatureSet" multiplicity="(1 1)"
	 */
	private JMenuItem mDeleteFeatureSet;

	/**
	 * 
	 * @uml.property name="mDeleteFeature"
	 * @uml.associationEnd 
	 * @uml.property name="mDeleteFeature" multiplicity="(1 1)"
	 */
	private JMenuItem mDeleteFeature;

	/**
	 * 
	 * @uml.property name="mFacade"
	 * @uml.associationEnd 
	 * @uml.property name="mFacade" multiplicity="(1 1)"
	 */
	private SyntaxFacade mFacade;

	/**
	 * 
	 * @uml.property name="mSF"
	 * @uml.associationEnd 
	 * @uml.property name="mSF" multiplicity="(1 1)"
	 */
	private SyntacticFeature mSF;

	/**
	 * 
	 * @param pF The SyntaxFacade for this menu
	 * @param pSF The SyntacticStructure associated with this menu
	 * <br>
	 * This menu includes Delete Feature, Delete Feature Set, and Add Feature
	 * commands.
	 * TODO: Add i18n
	 */
	public SFPopupMenu(SyntaxFacade pF, SyntacticFeature pSF)
	{
		mSF = pSF;
		mFacade = pF;
		mDeleteFeature = new JMenuItem("Delete Feature");
		mDeleteFeature.addActionListener(new ListenerDeleteFeature(mFacade, mSF));
		add(mDeleteFeature);
		mDeleteFeatureSet = new JMenuItem("Delete Feature Set");
		mDeleteFeatureSet.addActionListener(new ListenerDeleteFeatureSet(mFacade, mSF));
		add(mDeleteFeatureSet);
		mAddFeature = new JMenuItem("Add Feature");
		mAddFeature.addActionListener(new ListenerAddFeature(mFacade, mSF));
		add(mAddFeature);
	}
}
