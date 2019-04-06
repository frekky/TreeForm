
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

package staticFunctions;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

/**
 *
 * @author Donald Derrick
 * @version 0.1
 * <br>
 * date: 20-Aug-2004
 * <br>
 * <br>
 * Static functions for scaling and default button sizes
 */
public final class Sizer {
    public static final Color BLUE1 = new Color(36,139,192);
    public static final Color BLUE2 = new Color(129,173,214);
    public static final Color BLUE3 = new Color(187,210,237);
    public static final Color BLUE4 = new Color(224,238,255);

    public static final Color ORANGE1 = new Color(254,151,41);
    public static final Color ORANGE2 = new Color(255,176,98);
    public static final Color ORANGE3 = new Color(255,199,138);
    public static final Color ORANGE4 = new Color(255,228,207);

    public static final Color GREEN1 = new Color(50,171,90);
    public static final Color GREEN2 = new Color(118,199,123);
    public static final Color GREEN3 = new Color(176,218,174);
    public static final Color GREEN4 = new Color(217,239,207);

    public static final Color RED1 = new Color(223,61,51);
    public static final Color RED2 = new Color(243,125,113);
    public static final Color RED3 = new Color(255,170,165);
    public static final Color RED4 = new Color(255,214,223);

    public static final Color PURPLE1 = new Color(166,127,190);
    public static final Color PURPLE2 = new Color(189,160,207);
    public static final Color PURPLE3 = new Color(208,191,220);
    public static final Color PURPLE4 = new Color(240,223,251);

    public static final Color BROWN1 = new Color(157,106,93);
    public static final Color BROWN2 = new Color(183,139,127);
    public static final Color BROWN3 = new Color(207,172,163);
    public static final Color BROWN4 = new Color(249,215,206);

    public static final Color PINK1 = new Color(232,151,199);
    public static final Color PINK2 = new Color(240,170,210);
    public static final Color PINK3 = new Color(249,196,218);
    public static final Color PINK4 = new Color(255,222,242);

    public static final Color GRAY1 = new Color(145,145,144);
    public static final Color GRAY2 = new Color(177,178,178);
    public static final Color GRAY3 = new Color(208,208,208);
    public static final Color GRAY4 = new Color(244,244,244);

    public static final Color GOLD1 = new Color(198,197,42);
    public static final Color GOLD2 = new Color(211,210,110);
    public static final Color GOLD3 = new Color(224,223,157);
    public static final Color GOLD4 = new Color(249,244,191);

    public static final Color TEAL1 = new Color(5,199,215);
    public static final Color TEAL2 = new Color(122,211,223);
    public static final Color TEAL3 = new Color(171,224,232);
    public static final Color TEAL4 = new Color(213,244,252);

    private Sizer()
    {
    }
    /**
     *
     * @return base button size for ObjectBrowser buttons
     */
    public static Dimension buttonSize(){
        Dimension lDim = new Dimension(64,52);
        return lDim;
    }
    /**
     *
     * @return the scaled button size for Objectbrowser buttons.
     */
    public static Dimension scaledButtonSize(){
        Dimension lDim = buttonSize();
        Dimension lReturnDim = new Dimension((int)(lDim.width * scaleWidth()), (int)(lDim.height * scaleHeight()));
        return lReturnDim;
    }

    public static float scaleWidth()
    {
        return scale();
    }
    /**
     *
     * @return the scaled height based on screen resolution
     */
    public static float scaleHeight()
    {
        return scale();
    }
    /**
     * @return the recommended icon size based on screen width.
     */
    private static float scale()
    {
        float height = Toolkit.getDefaultToolkit().getScreenSize().height;
        float width = Toolkit.getDefaultToolkit().getScreenSize().width;
        if(height < width)
        {
            return (float) height/600;
        }
        else
        {
            return (float) width/800;
        }
    }
    public static boolean iconSize() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        if (dim.width < 1280)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    public static int UILineLength()
    {
        return 10;
    }
    public static int UIFontSize()
    {
        return 10;
    }
}
