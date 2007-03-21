
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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;

import javax.swing.JComponent;

/**
 * @author Donald Derrick
 * @version 0.1 
 * 
 * This is one of several Object Browser buttons designed to contain the necessary
 * information to drive sentence generation using the GUI.  
 *  
 */
public class ButtonUIIndex extends UserBrowserButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ButtonUIIndex(UserFrame pUserFrame, Object pButtonType) {
		super(pUserFrame, pButtonType);
		// TODO Auto-generated constructor stub
	}

	public void paintComponent(Graphics pG) {
		super.paintComponent(pG);
		this.prepaint(pG);	
		// set the string (internationalize later!)		
		AttributedString ats = new AttributedString("Index");
		ats.addAttribute(TextAttribute.FONT, mFont);
		AttributedCharacterIterator iter = ats.getIterator();
		// create a textlayout from the font, string, and font render context.
		TextLayout tl = new TextLayout(iter, mFrc);
		// draw the font				
		tl.draw(
			mGraphics2D,
			(float) ((mDim.getWidth() - tl.getBounds().getWidth()) / 2),
			(float) (mDim.getHeight() - 2));

		// repeat for the SPEC
		ats = new AttributedString("[Xi,Yi]");
		ats.addAttribute(TextAttribute.FONT, mFont);
		mFont = mFont.deriveFont((float)(mFont.getSize()*2/3));
		AffineTransform aft = new AffineTransform();
		aft.translate(0,1);
		mFont= mFont.deriveFont(aft);
		ats.addAttribute(TextAttribute.FONT, mFont,2,3);
		ats.addAttribute(TextAttribute.FONT,mFont,5,6);
		
				
		iter = ats.getIterator();
		tl = new TextLayout(iter, mFrc);
		Dimension positionCenter =
			new Dimension(
				(int) ((mDim.getWidth() - tl.getBounds().getWidth()) / 2),
				(int) ((mDim.getHeight() + tl.getBounds().getHeight())/2) );
		tl.draw(mGraphics2D, positionCenter.width, positionCenter.height);
		


		// surround in a gray rectangle.
		mGraphics2D.setColor(Color.GRAY);
		mGraphics2D.drawRect(0, 0, (int) mDim.getWidth() - 1, (int) mDim.getHeight() - 1);
		this.postpaint();
	}
}
