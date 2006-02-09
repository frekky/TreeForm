
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

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.text.CharacterIterator;

/**
 * 
 * @author Donald Derrick
 * @version 0.1
 * <br>
 * date: 19-Aug-2004
 * <br>
 * <br>
 * The clipboard transferrable object, used to pasting text, unicode, and AttributedStrings
 */
public class UserTransferable implements Transferable {

	protected static DataFlavor attributedStringFlavor = new DataFlavor(AttributedString.class,"Attributed String");
	protected static DataFlavor[] mDataFlavors = {
	   attributedStringFlavor,       // Transfer as an AttributedString object
	   DataFlavor.stringFlavor,      // Transfer as a String object
	   DataFlavor.plainTextFlavor,   // Transfer as a stream of Unicode text
	 };
	 AttributedString mAT;
/**
 * 
 * @param lAT Sets the attributed string in the clipboard
 */
	public UserTransferable(AttributedString lAT)
	{
		mAT = lAT;
	}
/**
 * gets the list of transferrable data flavors
 */
	public DataFlavor[] getTransferDataFlavors() {
		return mDataFlavors;
	}
/**
 * checks to see if a "flavor" is supported.  For this program, Unicode Text, Strings,
 * and AttributedStrings are exported to the system clipboard.
 */
	public boolean isDataFlavorSupported(DataFlavor pFlavor) {
		if (pFlavor.equals(attributedStringFlavor) || 
		pFlavor.equals(DataFlavor.stringFlavor) ||
		pFlavor.equals(DataFlavor.plainTextFlavor)) return true;
	return false;
	}
/**
 * gets the transfer data for the given data flavor
 */

	public Object getTransferData(DataFlavor flavor)
	throws UnsupportedFlavorException, IOException 
	{
		if (flavor.equals(attributedStringFlavor)) 
		{
			return mAT;
		} 
		else 
		{
			String lString = "";
			AttributedCharacterIterator lIterator = mAT.getIterator();
			for(char c = lIterator.first(); c != CharacterIterator.DONE; c = lIterator.next()) {
					 lString += c;
				 }		
			if (flavor.equals(DataFlavor.stringFlavor)) 
		   {
			return lString;
		   	} 
		   else if (flavor.equals(DataFlavor.plainTextFlavor))
		   {
			 return new ByteArrayInputStream(lString.getBytes("Unicode"));
		   }
		   else throw new UnsupportedFlavorException(flavor);
		   }			
	}
}
