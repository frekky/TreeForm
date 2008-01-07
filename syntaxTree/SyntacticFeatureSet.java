
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

import java.util.LinkedList;

/**
 * @author Donald Derrick * @version 0.1 * <br> * date: 20-Aug-2004 * <br> * <br>
 * 
 * @uml.stereotype name="tagged" isDefined="true" 
 * @uml.stereotype name="interface" 
 */

public interface SyntacticFeatureSet {
/**
 * 
 * @return Returns the visibility of the component
 */
	boolean getVisibility();
/**
 * 
 * @param visibility Sets the visibility of the component
 */
	void setVisibility(boolean visibility);

	/**
	 * @return Returns the list of associated SyntacticFeatures
	 * 
	 * @uml.property name="syntacticFeature"
	 */
	@SuppressWarnings("unchecked")
	LinkedList getSyntacticFeature();

	/**
	 * @param syntacticFeature sets the list of SyntacticFeatures
	 * 
	 * @uml.property name="syntacticFeature"
	 */
	@SuppressWarnings("unchecked")
	void setSyntacticFeature(LinkedList syntacticFeature);

	/**
	 * @return Returns the associated SyntacticStructure
	 * 
	 * @uml.property name="syntacticStructure"
	 * @uml.associationEnd 
	 * @uml.property name="syntacticStructure" multiplicity="(0 1)"
	 */
	SyntacticStructure getSyntacticStructure();

	/**
	 * @param syntacticStructure sets the associated SyntacticStructure
	 * 
	 * @uml.property name="syntacticStructure"
	 */
	void setSyntacticStructure(SyntacticStructure syntacticStructure);

	/**
	 * @return Returns the text Height
	 *
	 */
	int getHeight();
	/**
	 * @return Returns the text Width
	 */
	int getWidth();
	/**
	 * textXY command
	 */
	void testXY();

}
