
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
public class SSPopupMenu extends JPopupMenu {

	/**
	 * 
	 * @uml.property name="mSF"
	 * @uml.associationEnd 
	 * @uml.property name="mSF" multiplicity="(1 1)"
	 */
	private SyntaxFacade mSF;

	/**
	 * 
	 * @uml.property name="mSS"
	 * @uml.associationEnd 
	 * @uml.property name="mSS" multiplicity="(1 1)"
	 */
	private SyntacticStructure mSS;

	/**
	 * 
	 * @uml.property name="mRepositionSubtree"
	 * @uml.associationEnd 
	 * @uml.property name="mRepositionSubtree" multiplicity="(1 1)"
	 */
	private JMenuItem mRepositionSubtree;

	/**
	 * 
	 * @uml.property name="mDeleteSubtree"
	 * @uml.associationEnd 
	 * @uml.property name="mDeleteSubtree" multiplicity="(1 1)"
	 */
	private JMenuItem mDeleteSubtree;

	/**
	 * 
	 * @param pSF The SyntaxFacade for this menu
	 * @param pSS The SyntacticStructure associated with this menu
	 * <br>
	 * This menu includes Delete Subtree, and Reposition Subtree
	 * commands.
	 * TODO: Add i18n
	 */
	public SSPopupMenu(SyntaxFacade pSF, SyntacticStructure pSS)
	{
		mSS = pSS;
		mSF = pSF;
		mDeleteSubtree = new JMenuItem("Delete Subtree");
		mDeleteSubtree.addActionListener(new ListenerDelete(mSF, mSS));
		add(mDeleteSubtree);
		mRepositionSubtree = new JMenuItem("Reposition Subtree");
		mRepositionSubtree.addActionListener(new ListenerReposition(mSF, mSS));
		add(mRepositionSubtree);
	}
}
