
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

import java.awt.Font;
import java.awt.font.TextAttribute;
import java.text.AttributedString;

import userInterface.UserInternalFrame;
import enumerators.SyntacticLevel;

/**
 *
 * @author Donald Derrick
 * @version 0.1
 * <br>
 * date: 19-Aug-2004
 * <br>
 * <br>
 *  A builder class for constructing X-Bar structures,
 * the most complex builder in the project to date.
 */
public class XBarBuilder extends AbstractStructureBuilder {

    /**
     * @param pInternalFrame The internal Frame that this structure will be attached to.
     * @return Returns a constructed SyntacticStructure
     * <br>
     * <br>
     * This structure contains a tree with "XP" text, and two subtrees.  One is
     * a NULL heads(which gets automatically deleted when an object is placed on it)
     * and the other is a BAR level syntactic structure.  That BAR structure has
     * two more subtrees, one a NULL,and the other a HEAD SyntacticLevel.
     * <br><br>
     * Note that the enforcement of SyntacticLevels has not been implemented.
     */
    //@SupressWarnings("unchecked")
    public SyntacticStructure buildSentence(UserInternalFrame pInternalFrame) {
        SyntacticStructure lSyntacticStructureTop = new SyntacticStructure(pInternalFrame,null);
        lSyntacticStructureTop.setLineColor(pInternalFrame.getProperties().getLineColor());
        AttributedString lAttributedString = new AttributedString("XP");
        Font lFont = new Font("Doulos SIL", Font.PLAIN, pInternalFrame.getProperties().getDefaultFontSize());
        lAttributedString.addAttribute(TextAttribute.FONT, lFont);
        lAttributedString.addAttribute(TextAttribute.FOREGROUND, pInternalFrame.getProperties().getFontColor());
        lAttributedString.addAttribute(TextAttribute.BACKGROUND,pInternalFrame.getProperties().getBackgroundColor());
        lSyntacticStructureTop.setHead(lAttributedString);
        lSyntacticStructureTop.setSyntacticLevel(SyntacticLevel.DOUBLE_BAR);
        pInternalFrame.getContentPane().add(lSyntacticStructureTop);
        pInternalFrame.getContentPane().add(lSyntacticStructureTop.getSyntacticStructureLines());

        SyntacticStructure lSyntacticStructureBarLeft = new SyntacticStructure(pInternalFrame,lSyntacticStructureTop);
        lSyntacticStructureBarLeft.setLineColor(pInternalFrame.getProperties().getLineColor());

        lAttributedString = new AttributedString("∅");
        lFont = new Font("Doulos SIL", Font.PLAIN, pInternalFrame.getProperties().getDefaultFontSize());
        lAttributedString.addAttribute(TextAttribute.FONT, lFont);
        lAttributedString.addAttribute(TextAttribute.FOREGROUND, pInternalFrame.getProperties().getFontColor());
        lAttributedString.addAttribute(TextAttribute.BACKGROUND,pInternalFrame.getProperties().getBackgroundColor());
        lSyntacticStructureBarLeft.setHead(lAttributedString);
        lSyntacticStructureBarLeft.setSyntacticLevel(SyntacticLevel.NULL);
        pInternalFrame.getContentPane().add(lSyntacticStructureBarLeft);
        pInternalFrame.getContentPane().add(lSyntacticStructureBarLeft.getSyntacticStructureLines());

        SyntacticStructure lSyntacticStructureBarRight = new SyntacticStructure(pInternalFrame,lSyntacticStructureTop);
        lSyntacticStructureBarRight.setLineColor(pInternalFrame.getProperties().getLineColor());

        lAttributedString = new AttributedString("X'");
        lFont = new Font("Doulos SIL", Font.PLAIN, pInternalFrame.getProperties().getDefaultFontSize());
        lAttributedString.addAttribute(TextAttribute.FONT, lFont);
        lAttributedString.addAttribute(TextAttribute.FOREGROUND, pInternalFrame.getProperties().getFontColor());
        lAttributedString.addAttribute(TextAttribute.BACKGROUND,pInternalFrame.getProperties().getBackgroundColor());
        lSyntacticStructureBarRight.setHead(lAttributedString);
        lSyntacticStructureBarRight.setSyntacticLevel(SyntacticLevel.BAR);
        pInternalFrame.getContentPane().add(lSyntacticStructureBarRight);
        pInternalFrame.getContentPane().add(lSyntacticStructureBarRight.getSyntacticStructureLines());

        lSyntacticStructureTop.getChildren().add(lSyntacticStructureBarLeft);
        lSyntacticStructureTop.getChildren().add(lSyntacticStructureBarRight);

        SyntacticStructure lSyntacticStructureLeft = new SyntacticStructure(pInternalFrame, lSyntacticStructureBarRight);
        lSyntacticStructureLeft.setLineColor(pInternalFrame.getProperties().getLineColor());
        lAttributedString = new AttributedString("X");
        lFont = new Font("Doulos SIL", Font.PLAIN, pInternalFrame.getProperties().getDefaultFontSize());
        lAttributedString.addAttribute(TextAttribute.FONT, lFont);
        lAttributedString.addAttribute(TextAttribute.FOREGROUND, pInternalFrame.getProperties().getFontColor());
        lAttributedString.addAttribute(TextAttribute.BACKGROUND,pInternalFrame.getProperties().getBackgroundColor());
        lSyntacticStructureLeft.setHead(lAttributedString);
        lSyntacticStructureLeft.setSyntacticLevel(SyntacticLevel.HEAD);
        pInternalFrame.getContentPane().add(lSyntacticStructureLeft);
        pInternalFrame.getContentPane().add(lSyntacticStructureLeft.getSyntacticStructureLines());


        SyntacticStructure lSyntacticStructureRight = new SyntacticStructure(pInternalFrame, lSyntacticStructureBarRight);
        lSyntacticStructureRight.setLineColor(pInternalFrame.getProperties().getLineColor());
        lAttributedString = new AttributedString("∅");
        lFont = new Font("Doulos SIL", Font.PLAIN, pInternalFrame.getProperties().getDefaultFontSize());
        lAttributedString.addAttribute(TextAttribute.FONT, lFont);
        lAttributedString.addAttribute(TextAttribute.FOREGROUND, pInternalFrame.getProperties().getFontColor());
        lAttributedString.addAttribute(TextAttribute.BACKGROUND,pInternalFrame.getProperties().getBackgroundColor());
        lSyntacticStructureRight.setHead(lAttributedString);
        lSyntacticStructureRight.setSyntacticLevel(SyntacticLevel.NULL);
        pInternalFrame.getContentPane().add(lSyntacticStructureRight);
        pInternalFrame.getContentPane().add(lSyntacticStructureRight.getSyntacticStructureLines());


        lSyntacticStructureBarRight.getChildren().add(lSyntacticStructureLeft);
        lSyntacticStructureBarRight.getChildren().add(lSyntacticStructureRight);


        return lSyntacticStructureTop;

    }

}
