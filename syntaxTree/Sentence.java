
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

import java.util.Collection;
import java.util.LinkedList;

import javax.swing.JMenuItem;

import userInterface.UserFrame;

/**
 * @author Donald Derrick * @version 0.1 * <br> * date: 20-Aug-2004 * <br> * <br> * A simple class that I probably shouldn't have made to hold the top of sentences * <br> * In retrospect, I should have just used a composite for this task. * <br> * Ahh, well, live and learn. * <br>
 * 
 * @uml.stereotype name="tagged" isDefined="true" 
 * @uml.stereotype name="entity" 
 */

public class Sentence implements RepositionTree{

	/**
	 * 
	 * @uml.property name="mMoveSubtree"
	 * @uml.associationEnd 
	 * @uml.property name="mMoveSubtree" multiplicity="(0 1)"
	 */
	private JMenuItem mMoveSubtree;

	private int childCount;

	/**
	 * 
	 * @uml.property name="userUI"
	 * @uml.associationEnd 
	 * @uml.property name="userUI" multiplicity="(0 1)"
	 */
	private UserFrame userUI;

	/**
	 * 
	 * @uml.property name="syntacticStructure" 
	 */
	private Collection syntacticStructure;

	private String mName;

	/**
	 * 
	 * @uml.property name="syntacticStructureCount" 
	 */
	private int syntacticStructureCount;

	/**
	 * 
	 * @uml.property name="children"
	 * @uml.associationEnd 
	 * @uml.property name="children" multiplicity="(0 -1)" elementType="syntaxTree.SyntacticStructure"
	 */
	private LinkedList children;

	/**
	 * 
	 * @uml.property name="syntacticNote" 
	 */
	private Collection syntacticNote;

	/**
	 * 
	 * @uml.property name="mDeleteSubtree"
	 * @uml.associationEnd 
	 * @uml.property name="mDeleteSubtree" multiplicity="(0 1)"
	 */
	private JMenuItem mDeleteSubtree;

	public Sentence() {
		super();
		mName = "";
		children = new LinkedList();
	}

	public String getName() {
		return mName;
	}
	public void setName(String pName) {
		mName = pName;
	}
	public void addChild(SyntacticStructure child) {
		if (children.size() < 1)
		{
			children.add(child);
		}
	}

	/**
	 * 
	 * @uml.property name="children"
	 */
	public void setChildren(LinkedList children) {
		this.children = children;
	}

	/**
	 * 
	 * @uml.property name="children"
	 */
	public LinkedList getChildren() {
		return children;
	}

	public void removeChild(SyntacticStructure child) {
		children.remove(child);
	}

}
