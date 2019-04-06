
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

import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.DefaultDesktopManager;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

/**
 * @author Donald Derrick
 * @version 0.1 
 * <br>
 * This is the class that extends the DefauldDesktopManager.  It is used to change 
 * desktop resizing routines.
 *  
 */
public class MDIDesktopManager extends DefaultDesktopManager {

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
/**
 * 
 * @uml.property name="pDesktop"
 * @uml.associationEnd 
 * @uml.property name="pDesktop" multiplicity="(1 1)" inverse="mManager:userInterface.UserDesktopPane"
 */
private UserDesktopPane pDesktop;

	public MDIDesktopManager(UserDesktopPane pDesktop) {
		this.pDesktop = pDesktop;
	}

	public void endResizingFrame(JComponent pF) {
		super.endResizingFrame(pF);
		resizeDesktop();
	}

	public void endDraggingFrame(JComponent pF) {
		super.endDraggingFrame(pF);
		resizeDesktop();
	}

	public void setNormalSize() {
		JScrollPane scrollPane=getScrollPane();
		int x = 0;
		int y = 0;
		Insets scrollInsets = getScrollPaneInsets();

		if (scrollPane != null) {
			Dimension d = scrollPane.getVisibleRect().getSize();
			if (scrollPane.getBorder() != null) {
			   d.setSize(d.getWidth() - scrollInsets.left - scrollInsets.right,
						 d.getHeight() - scrollInsets.top - scrollInsets.bottom);
			}

			d.setSize(d.getWidth() - 20, d.getHeight() - 20);
			pDesktop.setAllSize(x,y);
			scrollPane.invalidate();
			scrollPane.validate();
		}
	}

	private Insets getScrollPaneInsets() {
		JScrollPane scrollPane=getScrollPane();
		if (scrollPane==null) return new Insets(0,0,0,0);
		else return getScrollPane().getBorder().getBorderInsets(scrollPane);
	}

	private JScrollPane getScrollPane() {
		if (pDesktop.getParent() instanceof JViewport) {
			JViewport viewPort = (JViewport)pDesktop.getParent();
			if (viewPort.getParent() instanceof JScrollPane)
				return (JScrollPane)viewPort.getParent();
		}
		return null;
	}

	protected void resizeDesktop() {
		int x = 0;
		int y = 0;
		JScrollPane pScrollPane = getScrollPane();
		Insets pScrollInsets = getScrollPaneInsets();

		if (pScrollPane != null) {
			JInternalFrame allFrames[] = pDesktop.getAllFrames();
			for (int i = 0; i < allFrames.length; i++) {
				if (allFrames[i].getX()+allFrames[i].getWidth()>x) {
					x = allFrames[i].getX() + allFrames[i].getWidth();
				}
				if (allFrames[i].getY()+allFrames[i].getHeight()>y) {
					y = allFrames[i].getY() + allFrames[i].getHeight();
				}
			}
			Dimension pD=pScrollPane.getVisibleRect().getSize();
			if (pScrollPane.getBorder() != null) {
			   pD.setSize(pD.getWidth() - pScrollInsets.left - pScrollInsets.right,
						 pD.getHeight() - pScrollInsets.top - pScrollInsets.bottom);
			}

			if (x <= pD.getWidth()) x = ((int)pD.getWidth()) - 20;
			if (y <= pD.getHeight()) y = ((int)pD.getHeight()) - 20;
			pDesktop.setAllSize(x,y);
			pScrollPane.invalidate();
			pScrollPane.validate();
		}
	}
}