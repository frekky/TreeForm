
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
import java.awt.Rectangle;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;

import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

import staticFunctions.Sizer;

/**
 * @author Donald Derrick
 * @version 0.1 
 * 
 * This is one of several Object Browser buttons designed to contain the necessary
 * information to drive sentence generation using the GUI.  
 *  
 */
public class ButtonUIXBar extends ButtonUIAbstract {


	static ButtonUIXBar b = new ButtonUIXBar();
	public ButtonUIXBar() {
		super();
	}
	public static ComponentUI createUI(JComponent c) {
		return b;
	}
	public void installUI(JComponent c) {
		//Since we know this is a JButton it is safe to cast as an AbstractButton
		AbstractButton b = (AbstractButton) c;
		super.installListeners(b);
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


	public void paint(Graphics pG, JComponent pC) {

	this.prepaint(pG,pC);
			
		mGraphics2D.translate(-Sizer.lineLength(),0);
		// set the string (internationalize later!)		
		AttributedString ats = new AttributedString("X-Bar");
		ats.addAttribute(TextAttribute.FONT, mFont);
		AttributedCharacterIterator iter = ats.getIterator();
		// create a textlayout from the font, string, and font render context.
		TextLayout tl = new TextLayout(iter, mFrc);
		// draw the font				
		tl.draw(
			mGraphics2D,
			(float) ((mDim.getWidth() - tl.getBounds().getWidth()) / 2),
			(float) (mDim.getHeight() - 2));

		// repeat for the title X-double-bar

		ats = new AttributedString("X''");
		mFont = mFont.deriveFont(mFont.getStyle(), mFont.getSize() - 2);
		ats.addAttribute(TextAttribute.FONT, mFont);
		iter = ats.getIterator();
		tl = new TextLayout(iter, mFrc);
		tl.draw(
			mGraphics2D,
			(float) ((mDim.getWidth() - tl.getBounds().getWidth()) / 2),
			(float) (tl.getBounds().getHeight() * 1.5));

		// draw the lines below the X-double-bar
		Rectangle drawRight =
			new Rectangle(
				(int) ((mDim.getWidth()) / 2),
				(int) ((tl.getBounds().getHeight() * 1.5) + 2),
				(int) ((mDim.getWidth()) / 2)
					+ 13,
				(int) (tl.getBounds().getHeight() * 1.5)
					+ Sizer.lineLength()
					+ 2);
		Rectangle drawLeft =
			new Rectangle(
				(int) ((mDim.getWidth()) / 2),
				(int) ((tl.getBounds().getHeight() * 1.5) + 2),
				(int) ((mDim.getWidth()) / 2)
					- 13,
				(int) (tl.getBounds().getHeight() * 1.5)
					+ Sizer.lineLength()
					+ 2);

		mGraphics2D.drawLine(
			drawRight.width,
			drawRight.height,
			drawRight.x,
			drawRight.y);
		mGraphics2D.drawLine(drawLeft.width, drawLeft.height, drawLeft.x, drawLeft.y);

		// repeat for the SPEC
		ats = new AttributedString("spec");
		ats.addAttribute(TextAttribute.FONT, mFont);
		iter = ats.getIterator();
		tl = new TextLayout(iter, mFrc);
		Dimension positionLeft =
			new Dimension(
				(int) (drawLeft.width - tl.getBounds().getWidth() / 2),
				(int) (drawLeft.height + tl.getBounds().getHeight() + 2));
		tl.draw(mGraphics2D, positionLeft.width, positionLeft.height);

		// repeat for the title X-bar
		ats = new AttributedString("X'");
		ats.addAttribute(TextAttribute.FONT, mFont);
		iter = ats.getIterator();
		tl = new TextLayout(iter, mFrc);
		Dimension positionRight =
			new Dimension(
				(int) (drawRight.width - tl.getBounds().getWidth() / 2),
				(int) (drawRight.height + tl.getBounds().getHeight() + 2));
		tl.draw(mGraphics2D, positionRight.width, positionRight.height);

		// draw the lines below the X-bar

		Rectangle drawRightBar =
			new Rectangle(
				(int) (drawRight.width),
				(int) (drawRight.height + (tl.getBounds().getHeight() * 1.5)),
				(int) (drawRight.width) + 13,
				(int) (drawRight.height + (tl.getBounds().getHeight() * 1.5))
					+ Sizer.lineLength());
		Rectangle drawLeftBar =
			new Rectangle(
				(int) (drawRight.width),
				(int) (drawRight.height + (tl.getBounds().getHeight() * 1.5)),
				(int) (drawRight.width) - 13,
				(int) (drawRight.height + (tl.getBounds().getHeight() * 1.5))
					+ Sizer.lineLength());

		mGraphics2D.drawLine(
			drawRightBar.width,
			drawRightBar.height,
			drawRightBar.x,
			drawRightBar.y);
		mGraphics2D.drawLine(
			drawLeftBar.width,
			drawLeftBar.height,
			drawLeftBar.x,
			drawLeftBar.y);

		// repeat for the X
		ats = new AttributedString("X");
		ats.addAttribute(TextAttribute.FONT, mFont);
		iter = ats.getIterator();
		tl = new TextLayout(iter, mFrc);
		Dimension positionLeftBar =
			new Dimension(
				(int) (drawLeftBar.width - tl.getBounds().getWidth() / 2),
				(int) (drawLeftBar.height + tl.getBounds().getHeight()+1));
		tl.draw(mGraphics2D, positionLeftBar.width, positionLeftBar.height);

		// repeat for the title Compl
		ats = new AttributedString("compl");
		ats.addAttribute(TextAttribute.FONT, mFont);
		iter = ats.getIterator();
		tl = new TextLayout(iter, mFrc);
		Dimension positionRightBar =
			new Dimension(
				(int) (drawRightBar.width - tl.getBounds().getWidth() / 2),
				(int) (drawRightBar.height + tl.getBounds().getHeight() - 2));
		tl.draw(mGraphics2D, positionRightBar.width, positionRightBar.height);
		

		// surround in a gray rectangle.
		mGraphics2D.translate(Sizer.lineLength(),0);
		this.postpaint();
	}
}
