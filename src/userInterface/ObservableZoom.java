
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
/**
 * @author Donald Derrick
 * @version 0.1 
 * <br>
 * This is one of several Observable classes (part of Java's implementation of the
 * Observer design pattern).  These classes are needed to easily add objects
 * to a big list of objects that care about changes to a particular variable.  This
 * program has a LOT of these types of variables.
 *  
 */
import java.util.Observable;

public class ObservableZoom extends Observable
{
   private float mValue = 1.0F;
   /**
	* Constructor
	* @param pValue Passes a zoom float to the ObservableZoom value.
	* <br>
	* 
	*/
   public ObservableZoom(float pValue)
   {
	  mValue = pValue;
   }
   /**
	 * 
	 * @param pValue The float value of the zoom ratio.
	 * <br>
	 * Once this command is invoked, the superclass change notification is sent,
	 * and all the subscribed observers are told about the event.
	 */
   public void setValue(float pValue)
   {
	 mValue = pValue;

	  setChanged();
	  notifyObservers();
   }
   /**
	* 
	* @return getValue - passes a value from the Observable, in this case the float
	* value of the zoom ratio.
	* <br>
	* A convenience function for returning the observable value.
	*/
   public float getValue()
   {
	  return mValue;
   }

}
