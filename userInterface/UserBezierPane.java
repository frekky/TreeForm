
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
import java.awt.event.MouseEvent;
import java.awt.geom.GeneralPath;

import javax.swing.JComponent;

import staticFunctions.Sizer;
import syntaxTree.SyntacticStructure;


public class UserBezierPane extends JComponent {

	private static final long serialVersionUID = 1L;

	private SyntacticStructure mSS;

	private UserFrame mUserFrame;

	public UserBezierPane(
		UserFrame pUserFrame, SyntacticStructure pSS
		) {
		mUserFrame = pUserFrame;
		mSS = pSS;
		
	}

	public void paintComponent(Graphics g) {
		Graphics2D lGraphics2D = (Graphics2D) g;
		lGraphics2D.setRenderingHint(
			RenderingHints.KEY_ANTIALIASING,
			RenderingHints.VALUE_ANTIALIAS_ON);
		lGraphics2D.setRenderingHint(
			RenderingHints.KEY_RENDERING,
			RenderingHints.VALUE_RENDER_QUALITY);
		lGraphics2D.scale(
				Sizer.scaleWidth()
					* mUserFrame.getDesktopPane().getInternalFrame().getScale(),
				Sizer.scaleHeight()
					* mUserFrame.getDesktopPane().getInternalFrame().getScale());
		drawSquare(lGraphics2D, mSS.getStartX(),mSS.getStartY());
		drawSquare(lGraphics2D, mSS.getEndX(),mSS.getEndY());
		drawSquare(lGraphics2D, mSS.getControlStartX(),mSS.getControlStartY());
		drawSquare(lGraphics2D, mSS.getControlEndX(),mSS.getControlEndY());
	}

	public void setSyntacticStructure(SyntacticStructure pSS) {
		mSS = pSS;
	}

	private void drawSquare(
		Graphics2D lGraphics2D,
		int pX,
		int pY) {
		
			Color lColor = new Color(0,0,0);
			lGraphics2D.setColor(lColor);
			GeneralPath polly = new GeneralPath();
			polly.moveTo(pX-3, pY-3);
			polly.lineTo(pX+3, pY-3);
			polly.lineTo(pX+3, pY+3);
			polly.lineTo(pX-3, pY+3);
			polly.closePath();
			lGraphics2D.fill(polly);
		
	}

	public void setPosition(MouseEvent e) {
		System.out.println(e.getX() + " : " + e.getY());
	}

	public void exit() {
		mUserFrame.getDesktopPane().getInternalFrame().deactivateBezierPane();
	}

}