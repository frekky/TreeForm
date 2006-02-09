
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

import java.util.Observable;

import syntaxTree.EditableComponent;

/**
 * @author Donald Derrick
 * @version 0.1 
 * <br>
 * This is one of several Observable classes (part of Java's implementation of the
 * Observer design pattern).  These classes are needed to easily add objects
 * to a big list of objects that care about changes to a particular variable.  This
 * program has a LOT of these types of variables.
 *  
 */
public class ObservableClipboard extends Observable {

	private int mInsertionIndex;

	/**
	 * 
	 * @uml.property name="mSelected"
	 * @uml.associationEnd 
	 * @uml.property name="mSelected" multiplicity="(1 1)"
	 */
	private EditableComponent mSelected;

	/**
	 * Constructor
	 * @param pComponent - passes an EditableComponent to the clipboard.
	 * <br>
	 * 
	 */
	public ObservableClipboard(EditableComponent pComponent) {
		
		mSelected = pComponent;
	}
	/**
	 * 
	 * @param pObject - passes a value for the Observable, in this case, whatever is
	 * clicked on.
	 * <br>
	 * The program then checks to see if the new object is different from the old
	 * selected object.
	 * <br>
	 * If it is, the old object is checked to see if it is an editableComponent
	 * (and not a null value).  Then the highlights are reset, carat turned off, repaint.
	 * <br>
	 * If the new object is an editable component, the old component is repointed to
	 * the new component, and the new component has it's carat turned on.
	 * The change flag is set, and all observers notified.
	 * <br>
	 * TODO: chance to fix broken encapsulation
	 */
	public void setValue(Object pObject)
	 {
	 	if (pObject != mSelected)
	 	{
		 	if (mSelected instanceof EditableComponent)
		 	{
		 		((EditableComponent)mSelected).setCarat(false);
		 		((EditableComponent)mSelected).setHighlightBegin(0);
		 		((EditableComponent)mSelected).setHighlightEnd(0);
		 		((EditableComponent)mSelected).repaint();
		 	}
		 	if (pObject instanceof EditableComponent)
		 	{
		 		((EditableComponent)pObject).setCarat(true);
		 		((EditableComponent)pObject).repaint();
				mSelected = (EditableComponent) pObject;
				setChanged();
				notifyObservers();
		 	}
	 	}
	 	
		
	 }
	/**
	 * 
	 * @return EditableComponent - the value in the clipboard.
	 */
	 public EditableComponent getValue()
	 {
		return mSelected;
	 }
	/**
	 * @param pInsertionIndex The carat insertion index
	 */
	public void setIndex(int pInsertionIndex) {
		mInsertionIndex = pInsertionIndex;
		
	}
	/**
	 * 
	 * @return getIndex The carat insertion index
	 * 
	 */
	public int getIndex()
	{
		return mInsertionIndex;
	}
}
