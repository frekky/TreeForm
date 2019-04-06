
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

import java.io.File;
/**
 *
 * @author NOT Donald Derrick
 * @version 0.1
 * <br>
 * date: 20-Aug-2004
 * <br>
 * <br>
 * Source code obtained from http://www.rgagnon.com/javadetails/java-0352.html
 */
public final class StringManipulator {

    // Source code obtained from http://www.rgagnon.com/javadetails/java-0352.html

    /* remove leading whitespace */

    // cannot instantiate
    private StringManipulator()
    {
    }
    public static String ltrim(String source) {
        return source.replaceAll("^\\s+", "");
    }

    /* remove trailing whitespace */
    public static String rtrim(String source) {
        return source.replaceAll("\\s+$", "");
    }

    /* replace multiple whitespaces between words with single blank */
    public static String itrim(String source) {
        return source.replaceAll("\\b\\s{2,}\\b", " ");
    }

    /* remove all superfluous whitespaces in source string */
    public static String trim(String source) {
        return itrim(ltrim(rtrim(source)));
    }

    public static String lrtrim(String source){
        return ltrim(rtrim(source));
    }
    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }

}
