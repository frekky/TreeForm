
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

import java.util.Observable;
import java.util.Observer;

import javax.swing.JComboBox;

/**
 * @author Donald Derrick
 * @version 0.1
 * <br>
 * A simple class for holding all of the system fonts.
 *
 */
public class UserComboFont extends JComboBox implements Observer {

	/**
	 * 
	 * @uml.property name="mObservableFont"
	 * @uml.associationEnd 
	 * @uml.property name="mObservableFont" multiplicity="(1 1)"
	 */
	private ObservableFont mObservableFont;

/**
 * 
 * @param pObservableFont Contains the obervable that holds font information
 * 
 */
	public UserComboFont(ObservableFont pObservableFont)
	{
		mObservableFont = pObservableFont;
	}
/**
 * @param pO the passed observable (in case an object uses more than one)
 * @param pObject the passed object (the value)
 * TODO: go back and change the observable values back to Objects, and convert info out.
 * 
 */
	public void update(Observable pO, Object pObject) {
		if (pO == mObservableFont)
		{		
			this.setSelectedItem(mObservableFont.getValue());
		}
	}

}
