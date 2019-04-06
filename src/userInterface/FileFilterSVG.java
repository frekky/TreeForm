
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
* Created on 20-Jul-2004
*
* To change the template for this generated file go to
* Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
*/
package userInterface;

import java.io.File;
import javax.swing.filechooser.*;

import staticFunctions.StringManipulator;

/**
* @author Donald Derrick
* @version 0.1 
* 
* This is one of several file filter objects used in a JFileChooser object
*  
* This class is used to save and load the xml file format for this software.
* 
*/
public class FileFilterSVG extends FileFilter {

	/**
	 * 
	 */
	public FileFilterSVG() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param pF - a File object for testing.
	 * @return accept - Returns whether the file is a valid type for display
	 * PRE: must pass a valid file object
	 * POST: returns a boolean indicating whether the file is accepted.
	 * This is based on whether the file is a directory or has a .xml extension.
	 * 
	 */
	public boolean accept(File pF) {
		if (pF.isDirectory()) {
	return true;
	}

	String extension = StringManipulator.getExtension(pF);

	if (extension != null) {
		if (extension.equals("svg"))
		{
				return true;
		} 
		else 
		{
			return false;
		}
	}
	return false;
	}

	/**
	 * @return String - Returns the file type description.
	 */
	public String getDescription() {
		return "SVG File";
	}

}