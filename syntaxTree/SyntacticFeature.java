
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

import java.text.AttributedString;
import java.util.LinkedList;

import userInterface.UserInternalFrame;

/**
 * @author Donald Derrick * @version 0.1 * <br> * date: 20-Aug-2004 * <br> * <br> * The SyntacticFeature subclass of EditableComponent
 * 
 * @uml.stereotype name="tagged" isDefined="true" 
 * @uml.stereotype name="entity" 
 */

public class SyntacticFeature extends EditableComponent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @uml.property name="mSyntacticAssociation"
	 * @uml.associationEnd 
	 * @uml.property name="mSyntacticAssociation" multiplicity="(0 -1)" inverse="mSyntacticFeature:syntaxTree.SyntacticAssociation"
	 */
//	@SuppressWarnings("unchecked")
	private LinkedList mSyntacticAssociation;

	/**
	 * 
	 * @uml.property name="syntacticFeatureSet"
	 * @uml.associationEnd 
	 * @uml.property name="syntacticFeatureSet" multiplicity="(0 1)"
	 */
	private SyntacticFeatureSet syntacticFeatureSet;

/**
 * 
 * @param pUserInternalFrame The InternalFrame holding the SyntacticFeature
 */
//	@SuppressWarnings("unchecked")
	public SyntacticFeature(UserInternalFrame pUserInternalFrame) {
		super(pUserInternalFrame);
		setSyntacticAssociation(new LinkedList());
	}
/**
 * 
 * @return Returns a LinkedList of SyntacticAssociations.
 */
//	@SuppressWarnings("unchecked")
	public LinkedList getSyntacticAssociation() {
		return mSyntacticAssociation;
	}
/**
 * 
 * @param pSyntacticAssociation Sets the LinkedList of SyntacticAssociations
 */
//	@SuppressWarnings("unchecked")
	public void setSyntacticAssociation( LinkedList pSyntacticAssociation) {
		mSyntacticAssociation = pSyntacticAssociation;
	}

	/**
	 * @return Accessor for the holding SyntacticFeatureSet
	 * 
	 * @uml.property name="syntacticFeatureSet"
	 */
	public SyntacticFeatureSet getSyntacticFeatureSet() {
		return syntacticFeatureSet;
	}

	/**
	 * @param syntacticFeatureSet sets the holding SyntacticFeatureSet
	 * 
	 * @uml.property name="syntacticFeatureSet"
	 */
	public void setSyntacticFeatureSet(SyntacticFeatureSet syntacticFeatureSet) {
		this.syntacticFeatureSet = syntacticFeatureSet;
	}

/**
 * Sets the head text for ALL associations as well as for itself.  Text syncro.
 */
	public void setHead(AttributedString pAT)
	{
		super.setHead(pAT);
		for (int i = 0; i < getSyntacticAssociation().size(); i++)
		{
			((SyntacticAssociation)getSyntacticAssociation().get(i)).setHead(pAT);
		}
		getSyntacticFeatureSet().testXY();
	}
	public void setHeadWithoutUpdate(AttributedString pAT)
	{
		super.setHead(pAT);
	}
/**
 * @param pAT The attributedString to reset.
 * 
 */	
	public void insertHead(AttributedString pAT, int pI)
	{
		super.insertHead(pAT, pI);
		setHead(getHead());
	}
/**
 * Overrides deleteHead to facilitate synchronization
 */
	public void deleteHead()
	{
		super.deleteHead();
		setHead(getHead());
	}
/**
 * Overrides setHighlight to facilitate synchronization
 */
//	public void setHighlight(Map map) {
//		super.setHighlight(map);
//		setHead(getHead());	
//	}
}
