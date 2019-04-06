
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
/*
 * Created on Jul 17, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package userInterface;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import staticFunctions.Sizer;

/**
 * @author Donald Derrick
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class UserButtonCopyTree extends JButton implements Observer{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     * @uml.property name="mObservableNew"
     * @uml.associationEnd
     * @uml.property name="mObservableNew" multiplicity="(1 1)"
     */
    private ObservableNew mObservableNew;



    private boolean mSize;
    public UserButtonCopyTree(UserFrame pUserFrame,
        ImageIcon pEnableIcon,
        ImageIcon pDisableIcon,
        ImageIcon pActiveIcon,
        ImageIcon pEnableIconSmall,
        ImageIcon pDisableIconSmall,
        /**
         *
         * @param pUserFrame The UserFrame for this instance of TreeForm
         * @param pEnableIcon The large icon for an enabled button
         * @param pDisableIcon The large icon for a disabled button
         * @param pActiveIcon The large icon for an active button
         * @param pEnableIconSmall The small icon for an enabled button
         * @param pDisableIconSmall The small icon for a disabled button
         * @param pActiveIconSmall The small icon for an active button
         * @param pSize The size of the button (as dictated by screen resolution)
         * @param pObservableNew The shared observable used by all buttons that
         * should be disabled when there is nothing in the UserInternalFrame.
         *
         */
        ImageIcon pActiveIconSmall, boolean pSize, ObservableNew pObservableNew) {
        super();
        mSize = pSize;
        mObservableNew = pObservableNew;
        if (mSize)
        {
            this.setPreferredSize(new Dimension(32,32));
        }
        else
        {
            this.setPreferredSize(new Dimension(24,24));
        }
    }

    public void activate()
    {

    }
    public void deactivate()
    {
        //		this.setIcon(mHoldIcon);
    }
    public void makeSmall()
    {
        mSize = false;
        //		setIcons();
    }
    public void makeBig()
    {
        mSize = true;
        //		setIcons();
    }

    public void update(Observable pObservable, Object arg1) {
        if (pObservable == mObservableNew)
        {
            if (mObservableNew.getValue() == 0)
            {
                this.setEnabled(false);
            }
            else
            {
                this.setEnabled(true);
            }
        }

    }
    public void paintComponent(Graphics pG) {
        super.paintComponent(pG);
        Graphics2D mGraphics2D = (Graphics2D) pG;
        mGraphics2D.setColor(Color.BLACK);
        mGraphics2D.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        mGraphics2D.setRenderingHint(
            RenderingHints.KEY_RENDERING,
            RenderingHints.VALUE_RENDER_QUALITY);
        if (mSize)
        {
            mGraphics2D.scale(32f/24f, 32f/24f);
        }


        float dash[] = {2.0f, 2.0f};
        mGraphics2D.setStroke(new BasicStroke(.5f, BasicStroke.CAP_BUTT,
            BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f));
        mGraphics2D.drawRect(4, 4, 16, 16);
        mGraphics2D.setStroke(new BasicStroke(.5f));
        mGraphics2D.setColor(Color.gray);
        mGraphics2D.fillRect(6,6,11,11);
        //mGraphics2D.setStroke(new BasicStroke(1f));
        mGraphics2D.setColor(Color.black);
        mGraphics2D.drawRect(6,6,11,11);
        mGraphics2D.setColor(Sizer.BLUE1);
        mGraphics2D.fillRect(8,8,2,1);
        mGraphics2D.setColor(Sizer.BLUE2);
        mGraphics2D.fillRect(12, 8, 2, 1);
        mGraphics2D.setColor(Color.black);
        Ellipse2D ellipse2D = new Ellipse2D.Float(8.5f,10,6,6);
        mGraphics2D.fill(ellipse2D);
    }
}
