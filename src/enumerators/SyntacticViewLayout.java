
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
 * @author Donald Derrick * @version 0.1 * <br> * date: 20-Aug-2004 * <br> * <br> * Unused as of yet, a list of view layouts
 * 
 * @uml.stereotype name="tagged" isDefined="true" 
 */

public final class SyntacticViewLayout {
	private SyntacticViewLayout(){}

	/**
	 * 
	 * @uml.property name="d_STRUCTURE" changeability="frozen" 
	 */
	public static final SyntacticViewLayout D_STRUCTURE = new SyntacticViewLayout();

	/**
	 * 
	 * @uml.property name="s_STRUCTURE" changeability="frozen" 
	 */
	public static final SyntacticViewLayout S_STRUCTURE = new SyntacticViewLayout();

	/**
	 * 
	 * @uml.property name="l_STRUCTURE" changeability="frozen" 
	 */
	public static final SyntacticViewLayout L_STRUCTURE = new SyntacticViewLayout();

	/**
	 * 
	 * @uml.property name="lABELLED_BRACKET" changeability="frozen" 
	 */
	public static final SyntacticViewLayout LABELLED_BRACKET = new SyntacticViewLayout();

}
