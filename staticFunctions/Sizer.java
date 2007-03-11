
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
/**
 * Constructor
 *
 */	
	private Sizer()
	{
	}
/**
 * 
 * @return base button size for ObjectBrowser buttons
 */
	public static Dimension buttonSize(){
		Dimension lDim = new Dimension(69,55);
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
