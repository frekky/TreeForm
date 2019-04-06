
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

import staticFunctions.Sizer;

/**
 * @author Donald Derrick
 * @version 0.1
 *
 * This is one of several Object Browser buttons designed to contain the necessary
 * information to drive sentence generation using the GUI.
 *
 */
public class ButtonUITernary extends UserBrowserButton {



    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public ButtonUITernary(UserFrame pUserFrame, Object pButtonType) {
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
            ats = new AttributedString("F10");
            ats.addAttribute(TextAttribute.FONT, mFont);
            iter = ats.getIterator();
            // create a textlayout from the font, string, and font render context.
            tl = new TextLayout(iter, mFrc);
            // draw the font
            tl.draw(
                mGraphics2D,4,11);
        }

        ats = new AttributedString((String) getResourceBundle().getObject("TERNARY_TEXT"));
        ats.addAttribute(TextAttribute.FONT, mFont);
        iter = ats.getIterator();
        // create a textlayout from the font, string, and font render context.
        tl = new TextLayout(iter, mFrc);
        // draw the font
        tl.draw(
            mGraphics2D,
            (float) ((mDim.getWidth() - tl.getBounds().getWidth()) / 2),
            (float) (mDim.getHeight() - 2));

        // repeat for the title X-double-bar

        ats = new AttributedString("XP");
        mFont = mFont.deriveFont(mFont.getStyle(), mFont.getSize() - 2);
        ats.addAttribute(TextAttribute.FONT, mFont);
        iter = ats.getIterator();
        tl = new TextLayout(iter, mFrc);
        tl.draw(
            mGraphics2D,
            (float) ((mDim.getWidth() - tl.getBounds().getWidth()) / 2),
            (float) ((mDim.getHeight() /4) + tl.getBounds().getHeight() * 1.5));

        // draw the lines below the X-double-bar
        Rectangle drawRight =
            new Rectangle(
                (int) ((mDim.getWidth()) / 2),
                (int) ((mDim.getHeight() /4) + (tl.getBounds().getHeight() * 1.5) + 2),
                (int) ((mDim.getWidth()) / 2)
                + 13,
                (int) ((mDim.getHeight() /4) + tl.getBounds().getHeight() * 1.5)
                + Sizer.UILineLength()
                + 2);

        Rectangle drawCenter =
            new Rectangle(
                (int) ((mDim.getWidth()) / 2),
                (int) ((mDim.getHeight() /4) + (tl.getBounds().getHeight() * 1.5) + 2),
                (int) ((mDim.getWidth()) / 2),
                (int) ((mDim.getHeight() /4) + tl.getBounds().getHeight() * 1.5)
                + Sizer.UILineLength()
                + 2);

        Rectangle drawLeft =
            new Rectangle(
                (int) ((mDim.getWidth()) / 2),
                (int) ((mDim.getHeight() /4) + (tl.getBounds().getHeight() * 1.5) + 2),
                (int) ((mDim.getWidth()) / 2)
                - 13,
                (int) ((mDim.getHeight() /4) + tl.getBounds().getHeight() * 1.5)
                + Sizer.UILineLength()
                + 2);

        mGraphics2D.drawLine(
            drawRight.width,
            drawRight.height,
            drawRight.x,
            drawRight.y);
        mGraphics2D.drawLine(
            drawCenter.width,
            drawCenter.height,
            drawCenter.x,
            drawCenter.y);
        mGraphics2D.drawLine(drawLeft.width, drawLeft.height, drawLeft.x, drawLeft.y);

        // repeat for the X
        ats = new AttributedString("X");
        ats.addAttribute(TextAttribute.FONT, mFont);
        iter = ats.getIterator();
        tl = new TextLayout(iter, mFrc);
        Dimension positionLeft =
            new Dimension(
                (int) (drawLeft.width - tl.getBounds().getWidth() / 2),
                (int) (drawLeft.height + tl.getBounds().getHeight() + 3));
        tl.draw(mGraphics2D, positionLeft.width, positionLeft.height);

        //		repeat for the X
        ats = new AttributedString("Y");
        ats.addAttribute(TextAttribute.FONT, mFont);
        iter = ats.getIterator();
        tl = new TextLayout(iter, mFrc);
        Dimension positionCenter =
            new Dimension(
                (int) (drawCenter.width - tl.getBounds().getWidth() / 2),
                (int) (drawCenter.height + tl.getBounds().getHeight() + 3));
        tl.draw(mGraphics2D, positionCenter.width, positionCenter.height);

        // repeat for the title X
        ats = new AttributedString("Z");
        ats.addAttribute(TextAttribute.FONT, mFont);
        iter = ats.getIterator();
        tl = new TextLayout(iter, mFrc);
        Dimension positionRight =
            new Dimension(
                (int) (drawRight.width - tl.getBounds().getWidth() / 2),
                (int) (drawRight.height + tl.getBounds().getHeight() + 3));
        tl.draw(mGraphics2D, positionRight.width, positionRight.height);
        this.postpaint();
    }
}
