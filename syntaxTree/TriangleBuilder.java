
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
 * A builder class for constructing triangles
 */
public class TriangleBuilder extends AbstractStructureBuilder {

	/**
	 * @param pInternalFrame The internal Frame that this structure will be attached to.
	 * @return Returns a constructed SyntacticStructure
	 * <br>
	 * <br>
	 * This structure contains a tree with "XP" text only.
	 * <br><br>
	 * This is a triangle, which displays lines differently from other
	 * SyntacticStructures
	 */
	public SyntacticStructure buildSentence(UserInternalFrame pInternalFrame) {
		SyntacticStructure lSyntacticStructureTop = new SyntacticStructure(pInternalFrame,null);
		AttributedString lAttributedString = new AttributedString("XP");
		Font lFont = new Font("Doulos SIL", Font.PLAIN, pInternalFrame.getProperties().fontSize());
		lAttributedString.addAttribute(TextAttribute.FONT, lFont);
		lSyntacticStructureTop.setHead(lAttributedString);
		lSyntacticStructureTop.setSyntacticLevel(SyntacticLevel.TRIANGLE);
		pInternalFrame.getContentPane().add(lSyntacticStructureTop);
		pInternalFrame.getContentPane().add(lSyntacticStructureTop.getSyntacticStructureLines());			
		return lSyntacticStructureTop;
	}
	
}
