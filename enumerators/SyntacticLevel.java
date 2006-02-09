
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

package enumerators;

/**
 * @author Donald Derrick * @version 0.1 * <br> * date: 20-Aug-2004 * <br> * <br> * A list of syntactic levels
 * 
 * @uml.stereotype name="tagged" isDefined="true" 
 */

public final class SyntacticLevel {
	private String mString;
	private SyntacticLevel(String pString){mString = pString;}

	/**
	 * 
	 * @uml.property name="dOUBLE_BAR" changeability="frozen" 
	 */
	public static final SyntacticLevel DOUBLE_BAR = new SyntacticLevel(
		"DOUBLE_BAR");

	/**
	 * 
	 * @uml.property name="bAR" changeability="frozen" 
	 */
	public static final SyntacticLevel BAR = new SyntacticLevel("BAR");

	/**
	 * 
	 * @uml.property name="hEAD" changeability="frozen" 
	 */
	public static final SyntacticLevel HEAD = new SyntacticLevel("HEAD");

	public static final SyntacticLevel MORPH = new SyntacticLevel("MORPH");
	public static final SyntacticLevel NULL = new SyntacticLevel("NULL");
	public static final SyntacticLevel TRIANGLE = new SyntacticLevel("TRIANGLE");
	public String toString()
	{
		return mString;
	}
}
