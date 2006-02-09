
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
 * Created on 6-Aug-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package userInterface;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JComboBox;

/**
 * @author Donald Derrick
 * @version 0.1
 * <br>
 * An object containing a list of standard zoom sizes.
 *
 */
public class UserComboZoom extends JComboBox implements Observer {

	/**
	 * 
	 * @uml.property name="mObservableZoom"
	 * @uml.associationEnd 
	 * @uml.property name="mObservableZoom" multiplicity="(1 1)"
	 */
	private ObservableZoom mObservableZoom;

	public UserComboZoom(ObservableZoom pObservableZoom)
	{
		mObservableZoom = pObservableZoom;
	}
	public void update(Observable arg0, Object arg1) {
		if (arg0 == mObservableZoom)
		{
			int lZoom = (int) (100 * mObservableZoom.getValue());
			this.setSelectedItem(lZoom + "%");
		}
	}

}
