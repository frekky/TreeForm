
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

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;

/**
 * @author Donald Derrick
 * @version 0.1 
 * 
 * This is one of several Object Browser buttons designed to contain the necessary
 * information to drive sentence generation using the GUI.  
 *  
 */
public class ButtonUICase extends UserBrowserButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ButtonUICase(UserFrame pUserFrame, Object pButtonType) {
		super(pUserFrame, pButtonType);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param pG  This parameter is the Graphics (and 2DGraphics) from the component
	 * @param pC  This parameter is the component to be painted.  repaint() sends
	 * the component holding this UI to paint(a,b), but a programmer may send
	 * any component they wish.
	 * 
	 * Like all the painting tasks for Object Broser buttons, this draws the text
	 * you see in the button, and resizes it according to your screen resolution.
	 * 
	 */

	public void paintComponent(Graphics pG) {
		super.paintComponent(pG);
		this.prepaint(pG);	
		
		AttributedString ats;
		AttributedCharacterIterator iter;
		TextLayout tl;
		if (!mDrag)
		{
		ats = new AttributedString("F5");
		ats.addAttribute(TextAttribute.FONT, mFont);
		iter = ats.getIterator();
		// create a textlayout from the font, string, and font render context.
		tl = new TextLayout(iter, mFrc);
		// draw the font				
		tl.draw(
			mGraphics2D,4,11);
		}
		AttributedString lAts = new AttributedString((String) getResourceBundle().getObject("CASE_TEXT"));
		lAts.addAttribute(TextAttribute.FONT, mFont);
		AttributedCharacterIterator lIter = lAts.getIterator();
		// create a textlayout from the font, string, and font render context.
		TextLayout lTl = new TextLayout(lIter, mFrc);
		// draw the font				
		lTl.draw(
			mGraphics2D,
			(float) ((mDim.getWidth() - lTl.getBounds().getWidth()) / 2),
			(float) (mDim.getHeight() - 2));

		// repeat for the SPEC
		lAts = new AttributedString("[+CASE]");
		lAts.addAttribute(TextAttribute.UNDERLINE,TextAttribute.UNDERLINE_ON,1,2);
		lAts.addAttribute(TextAttribute.FONT, mFont);

		
		lIter = lAts.getIterator();
		lTl = new TextLayout(lIter, mFrc);
		Dimension positionCenter =
			new Dimension(
				(int) ((mDim.getWidth() - lTl.getBounds().getWidth()) / 2),
				(int) ((mDim.getHeight() + lTl.getBounds().getHeight())/2) );
		lTl.draw(mGraphics2D, positionCenter.width, positionCenter.height);
		
		this.postpaint();
		
	}
}
