
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

import userInterface.UserInternalFrame;

/**
 * @author Donald Derrick * @version 0.1 * <br> * date: 20-Aug-2004 * <br> * <br> * The SyntacticAssociation subclass of EditableComponent.
 *
 * @uml.stereotype name="tagged" isDefined="true"
 * @uml.stereotype name="entity"
 */

public class SyntacticAssociation extends EditableComponent{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     * @uml.property name="mSyntacticFeature"
     * @uml.associationEnd
     * @uml.property name="mSyntacticFeature" multiplicity="(0 1)" inverse="mSyntacticAssociation:syntaxTree.SyntacticFeature"
     */
    private SyntacticFeature mSyntacticFeature;

    /**
     *
     * @uml.property name="mSyntacticStructure"
     * @uml.associationEnd
     * @uml.property name="mSyntacticStructure" multiplicity="(0 1)" inverse="mSyntacticAssociation:syntaxTree.SyntacticStructure"
     */
    private SyntacticStructure mSyntacticStructure;

    /**
     *
     * @param pUserInternalFrame The InternalFrame holding this SyntacticAssociation
     */
    public SyntacticAssociation(UserInternalFrame pUserInternalFrame) {
        super(pUserInternalFrame);
    }
    /**
     *
     * @return Returns the SyntacticStructure holding this association
     */
    public SyntacticStructure getSyntacticStructure() {
        return mSyntacticStructure;
    }
    /**
     *
     * @param pSyntacticStructure The SyntacticStructure that will hold this association
     */
    public void setSyntacticStructure(SyntacticStructure pSyntacticStructure) {
        mSyntacticStructure = pSyntacticStructure;
    }
    /**
     *
     * @return The SyntacticFeature producing this association
     */
    public SyntacticFeature getSyntacticFeature() {
        return mSyntacticFeature;
    }
    /**
     *
     * @param pSyntacticFeature The SyntacticFeature that is used to produce this
     * association.
     */
    public void setSyntacticFeature(SyntacticFeature pSyntacticFeature) {
        mSyntacticFeature = pSyntacticFeature;
    }
    /**
     * @param pAS The attributedString for this association.  This command
     * overrides the superclass command of same name, and makes sure all
     * matched associations and features have the same text.
     */
    public void setHead(AttributedString pAS)
    {
        super.setHead(pAS);
        if (getHead() != getSyntacticFeature().getHead())
        {
            getSyntacticFeature().setHead(getHead());
        }
        getSyntacticStructure().testXY();
    }
    public void setHeadWithoutUpdate(AttributedString pAS)
    {
        super.setHead(pAS);
    }
}
