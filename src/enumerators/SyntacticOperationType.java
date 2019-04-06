
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
 * @author Donald Derrick
 * @version 0.1
 * <br>
 * date: 20-Aug-2004
 * <br>
 * <br>
 * A list of syntacticStructureTypes for the builder.
 *
 * @uml.stereotype name="tagged" isDefined="true"
 */

public final class SyntacticOperationType {
    private SyntacticOperationType(){}

    /**
     *
     * @uml.property name="bINARY" changeability="frozen"
     */
    public static final SyntacticOperationType MOVEMENT = new SyntacticOperationType();
    public static final SyntacticOperationType ERASE = new SyntacticOperationType();
    public static final SyntacticOperationType ASSOCIATION = new SyntacticOperationType();
    public static final SyntacticOperationType ADD = new SyntacticOperationType();


}
