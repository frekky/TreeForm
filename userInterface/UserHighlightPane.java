
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

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.GeneralPath;

import javax.swing.JComponent;

public class UserHighlightPane extends JComponent {

	private static final long serialVersionUID = 1L;

	private UserFrame mUserFrame;

	private int mEndX;

	private int mEndY;

	private int mStartX;

	private int mStartY;

	private boolean mHighlight;


	public UserHighlightPane(
		UserFrame pUserFrame
		) {
		mUserFrame = pUserFrame;
	}

	public void paint(Graphics g) {
		Graphics2D lGraphics2D = (Graphics2D) g;
		lGraphics2D.setRenderingHint(
			RenderingHints.KEY_ANTIALIASING,
			RenderingHints.VALUE_ANTIALIAS_ON);
		lGraphics2D.setRenderingHint(
			RenderingHints.KEY_RENDERING,
			RenderingHints.VALUE_RENDER_QUALITY);
		
		GeneralPath polly = new GeneralPath();
		// move the pollygon to the middle and bottom
		polly.moveTo(mStartX,mStartY);
		polly.lineTo(mStartX,mEndY);
		polly.lineTo(mEndX,mEndY);
		polly.lineTo(mEndX,mStartY);
		polly.closePath();
		if (mHighlight)
		{
		lGraphics2D.setColor(new Color(0,0,255,90));
		}
		else
		{
			lGraphics2D.setColor(new Color(0,0,0,90));
		}
		lGraphics2D.draw(polly);
		if (mHighlight)
		{
			lGraphics2D.setColor(new Color(0,0,255,30));
			}
			else
			{
				lGraphics2D.setColor(new Color(0,0,0,30));
			}
		lGraphics2D.fill(polly);
		//System.out.println(mStartX + " : " + mStartY + " : " + mEndX + " : " + mEndY);
	}

	

	public void exit() {
		mUserFrame.getDesktopPane().getInternalFrame().deactivateHighlightPane();
	}

	public void setEndPosition(int x, int y) {
		mEndX = x;
		mEndY = y;
		repaint();
	}

	public void copyImage() {
		exit();
	}

	public void setStartPosition(int x, int y) {
		mStartX = x;
		mStartY = y;	
	}

	public void setHighlightType(MouseEvent e) {
		if ((e.getModifiersEx() & InputEvent.SHIFT_DOWN_MASK) !=0)  
		{
			mHighlight = false;
		}
		else
		{
			mHighlight = true;
		}
		
	}
}