
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

import java.awt.Toolkit;
import java.io.IOException;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLEditorKit;

/**
 * @author Donald Derrick
 * @version 0.1
 *
 * This class holds the contents of the window that opens
 * when one selected the "About" option. 
 *
 */
public class HelpFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @uml.property name="mEditorScrollPane"
	 * @uml.associationEnd 
	 * @uml.property name="mEditorScrollPane" multiplicity="(1 1)"
	 */
	private JScrollPane mEditorScrollPane;

	/**
	 * 
	 * @uml.property name="mEditorPane"
	 * @uml.associationEnd 
	 * @uml.property name="mEditorPane" multiplicity="(1 1)"
	 */
	private JEditorPane mEditorPane;

	/**
	 * Constructor for the About Frame.
	 * Pre: about.htm file must be in the relative file path
	 * Post: about frame opens with about.htm text inside the JEditorPane.
	 * 
	 */
	public HelpFrame() {
		
		
		mEditorPane = new JEditorPane();
		mEditorPane.setEditorKit(new HTMLEditorKit());
		mEditorPane.setEditable(false);
		/**
		 * 
		 * @version 0.1 
		 * 
		 * Inner class containing the Hyperlink Listener - instead of navigating
		 * to a hyperlink, this listener displays the hyperlink text so it can be
		 * copied and pasted into a real web browser.
		 * 
		 * @version 1.0
		 * 
		 * This should be changed to something that forces open the default browser
		 * and navigates to the correct page.
		 * 
		 */
		class EditorPaneHyperlinkListener implements HyperlinkListener {
		
			/**
			 * 
			 * @param pHe
			 * 
			 * This method grabs the selected hyperlink and opens a small JFrame with 
			 * a JLabel containing the hyperlink text.  
			 * 
			 */
			public void hyperlinkUpdate(HyperlinkEvent pHe) {
			
			
			
				if (pHe.getEventType().toString().equals("ACTIVATED")) {
					JFrame lFrame = new JFrame();
					JTextField lLabel = new JTextField(pHe.getURL().toString());
					lFrame.getContentPane().add(lLabel);
					lFrame.setBounds(
						(int) (Toolkit
							.getDefaultToolkit()
							.getScreenSize()
							.width
							* .4d),
						(int) (Toolkit
							.getDefaultToolkit()
							.getScreenSize()
							.height
							* .4d),
						(int) (Toolkit
							.getDefaultToolkit()
							.getScreenSize()
							.width
							* .2d),
						(int) (Toolkit
							.getDefaultToolkit()
							.getScreenSize()
							.height
							* .2d));
					lFrame.pack();
					lFrame.setVisible(true);
				}
			}
		}
		try {
			mEditorPane.setPage(
				"file:"
					+ System.getProperty("user.dir")
					+ System.getProperty("file.separator")
					+ "help/help.htm");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/**
		 * Simple commands to add the listener to the JEditorPane, add the JEditorPane
		 * to the JFrame, and open the JFrame centered in the screen.
		 */
		
		mEditorPane.addHyperlinkListener(new EditorPaneHyperlinkListener());
		mEditorScrollPane = new JScrollPane(mEditorPane);
		mEditorScrollPane.setVerticalScrollBarPolicy(
			JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.getContentPane().add(mEditorScrollPane);

		this.setBounds(
			(int) (Toolkit.getDefaultToolkit().getScreenSize().width * .2d),
			(int) (Toolkit.getDefaultToolkit().getScreenSize().height * .2d),
			(int) (Toolkit.getDefaultToolkit().getScreenSize().width * .6d),
			(int) (Toolkit.getDefaultToolkit().getScreenSize().height * .6d));
		this.setVisible(true);
	}
}
