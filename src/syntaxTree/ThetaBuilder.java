
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
/**
 * 
 * @author Donald Derrick
 * @version 0.1
 * <br>
 * date: 19-Aug-2004
 * <br>
 * <br>
 * A builder class for the CaseFeatureSet
 */
public class ThetaBuilder extends AbstractFeatureBuilder {

	/**
	 * @param pInternalFrame The internal Frame that this structure will be attached to.
	 * @return Returns the SyntacticFeatureSet.
	 * <br><br>
	 * This FeatureSet contains two features by default, an "&lt;agent&gt;" and a "&lt;theme&gt;"
	 */
	//@SupressWarnings("unchecked")
	public SyntacticFeatureSet buildFeature(UserInternalFrame pInternalFrame) {
		SyntacticFeatureSet lSFS = new ThetaRoleFeatureSet();
		lSFS.setSyntacticStructure((SyntacticStructure) pInternalFrame.getSyntaxFacade().getContainer());
		SyntacticFeature lSF = new SyntacticFeature(pInternalFrame);
		AttributedString lAttributedString = new AttributedString("<θ>");
		Font lFont = new Font("Doulos SIL", Font.PLAIN, pInternalFrame.getProperties().getDefaultFontSize());
		lAttributedString.addAttribute(TextAttribute.FONT, lFont);
		lAttributedString.addAttribute(TextAttribute.UNDERLINE,TextAttribute.UNDERLINE_ON,1,2);
		lAttributedString.addAttribute(TextAttribute.FOREGROUND, pInternalFrame.getProperties().getFontColor());
		lAttributedString.addAttribute(TextAttribute.BACKGROUND,pInternalFrame.getProperties().getBackgroundColor());
		lSF.setSyntacticFeatureSet(lSFS);
		lSFS.getSyntacticFeature().add(lSF);
		lSF.setHead(lAttributedString);
		pInternalFrame.getContentPane().add(lSF);
		
		lSF = new SyntacticFeature(pInternalFrame);
		lAttributedString = new AttributedString("<θ>");
		lFont = new Font("Doulos SIL", Font.PLAIN, pInternalFrame.getProperties().getDefaultFontSize());
		lAttributedString.addAttribute(TextAttribute.FONT, lFont);
		lAttributedString.addAttribute(TextAttribute.FOREGROUND, pInternalFrame.getProperties().getFontColor());
		lAttributedString.addAttribute(TextAttribute.BACKGROUND,pInternalFrame.getProperties().getBackgroundColor());
		lSF.setSyntacticFeatureSet(lSFS);
		lSFS.getSyntacticFeature().add(lSF);
		lSF.setHead(lAttributedString);
		pInternalFrame.getContentPane().add(lSF);
		return lSFS;
	}
}
