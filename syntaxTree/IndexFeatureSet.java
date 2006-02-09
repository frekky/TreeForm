
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
 * @author Donald Derrick * @version 0.1 * <br> * date: 20-Aug-2004 * <br> * <br> * UNIMPLEMENTED
 * 
 * @uml.stereotype name="tagged" isDefined="true" 
 * @uml.stereotype name="entity" 
 */

public class IndexFeatureSet implements SyntacticFeatureSet {

	private LinkedList mSyntacticFeature;

	/**
	 * 
	 * @uml.property name="mSyntacticStructure"
	 * @uml.associationEnd 
	 * @uml.property name="mSyntacticStructure" multiplicity="(0 1)"
	 */
	private SyntacticStructure mSyntacticStructure;

	/**
	 * 
	 * @uml.property name="visibility" 
	 */
	private boolean visibility;

	public SyntacticStructure getSyntacticStructure() {
		return mSyntacticStructure;
	}

	public void setSyntacticStructure(SyntacticStructure pSyntacticStructure) {
		mSyntacticStructure = pSyntacticStructure;
	}

	public LinkedList getSyntacticFeature() {
		return mSyntacticFeature;
	}

	public void setSyntacticFeature(LinkedList pSyntacticFeature) {
		mSyntacticFeature = pSyntacticFeature;
	}

	/**
	 * 
	 * @uml.property name="visibility"
	 */
	public boolean getVisibility() {
		return visibility;
	}

	/**
	 * 
	 * @uml.property name="visibility"
	 */
	public void setVisibility(boolean lvisibility) {
		visibility = lvisibility;
	}

	public int getHeight() {

		return 0;
	}


	public int getWidth() {

		return 0;
	}

	public void testXY() {

		
	}
}