
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
 * Created on Jul 17, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package userInterface;

import java.awt.Component;
import java.awt.Dimension;
import java.beans.PropertyVetoException;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

/**
 * @author Donald Derrick
 * @version 0.1
 *
 * Well, and also the author is JavaWorld Magazine! (for the window cascade
 * and tile methods)
 * 
 * http://www.javaworld.com/javaworld/jw-05-2001/jw-0525-mdi.html?#resources
 * 
 * 
 */
public class UserDesktopPane extends JDesktopPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @uml.property name="mUserFrame"
	 * @uml.associationEnd 
	 * @uml.property name="mUserFrame" multiplicity="(1 1)" inverse="mDesktopPane:userInterface.UserFrame"
	 */
	private UserFrame mUserFrame;

	private static int FRAME_OFFSET=20;

	/**
	 * 
	 * @uml.property name="mManager"
	 * @uml.associationEnd 
	 * @uml.property name="mManager" multiplicity="(1 1)" inverse="pDesktop:userInterface.MDIDesktopManager"
	 */
	private MDIDesktopManager mManager;

/**
 * 
 * @param pUserFrame The UserFrame for this instance of TreeForm.
 */	
	public UserDesktopPane(UserFrame pUserFrame) {
		super();
		mUserFrame = pUserFrame;
		mManager= new MDIDesktopManager(this);
		setDesktopManager(mManager);
		setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
		
	}
/**
 * Adds a new InternalFrame
 *
 */
	public void addInternalFrame() {
		UserInternalFrame internalFrame =
			new UserInternalFrame(
				mUserFrame,
				mUserFrame.getDesktopPane().getAllFrames().length + 1);
		internalFrame.setVisible(true); //necessary as of 1.3
		internalFrame.addInternalFrameListener(new ListenerInternalFrame(mUserFrame));
		internalFrame.getContentPane().setLayout(null);
		//internalFrame.setAutoscrolls(true);
		this.add(internalFrame);
		try {
			internalFrame.setSelected(true);
		} catch (java.beans.PropertyVetoException e) {
		}
	}
/**
 * Closes InternalFrames
 *
 */
	public void closeInternalFrame() {
		if (this.getSelectedFrame() != null) {
			this.remove(this.getSelectedFrame());
			selecteNewFrame();
		}
		this.repaint();
	}

/**
 * Selects a new frame when one was closed.
 *
 */

	public void selecteNewFrame() {
		JInternalFrame[] jif = this.getAllFrames();
		if (jif.length > 0) {
			this.setSelectedFrame(jif[0]);
			try {
				jif[0].setSelected(true);
			} catch (PropertyVetoException e) {
				e.printStackTrace();
			}
			jif[0].repaint();
		}
		
	}
/**
 * 
 * @return returns the selected InternalFrame
 */
	public UserInternalFrame getInternalFrame()
	{
		return (UserInternalFrame) this.getSelectedFrame();
	}
/**
 * 
 * Closes all the internal frames
 */

	public void closeAllInternalFrames() {
		JInternalFrame[] jif = this.getAllFrames();
		for (int i = 0; i < jif.length; i++) {
			this.remove(jif[i]);
		}
		this.repaint();
	}

		public void setBounds(int x, int y, int w, int h) {
			super.setBounds(x,y,w,h);
			checkDesktopSize();
		}
		
		public void remove(Component c) {
			super.remove(c);
			checkDesktopSize();
		}

		/**
		 * Cascade all internal frames
		 */
		public void cascadeFrames() {
			int x = 0;
			int y = 0;
			JInternalFrame allFrames[] = getAllFrames();

			mManager.setNormalSize();
			int frameHeight = (getBounds().height - 5) - allFrames.length * FRAME_OFFSET;
			int frameWidth = (getBounds().width - 5) - allFrames.length * FRAME_OFFSET;
			for (int i = allFrames.length - 1; i >= 0; i--) {
				allFrames[i].setSize(frameWidth,frameHeight);
				allFrames[i].setLocation(x,y);
				x = x + FRAME_OFFSET;
				y = y + FRAME_OFFSET;
			}
		}

		/**
		 * Tile all internal frames
		 */
		public void tileFrames() {
			java.awt.Component allFrames[] = getAllFrames();
			mManager.setNormalSize();
			int frameHeight = getBounds().height/allFrames.length;
			int y = 0;
			for (int i = 0; i < allFrames.length; i++) {
				allFrames[i].setSize(getBounds().width,frameHeight);
				allFrames[i].setLocation(0,y);
				y = y + frameHeight;
			}
		}

		/**
		 * Sets all component size properties ( maximum, minimum, preferred)
		 * to the given dimension.
		 */
		public void setAllSize(Dimension d){
			setMinimumSize(d);
			setMaximumSize(d);
			setPreferredSize(d);
		}

		/**
		 * Sets all component size properties ( maximum, minimum, preferred)
		 * to the given width and height.
		 */
		public void setAllSize(int width, int height){
			setAllSize(new Dimension(width,height));
		}

		private void checkDesktopSize() {
			if (getParent()!=null&&isVisible()) mManager.resizeDesktop();
		}
	}
