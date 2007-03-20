package userInterface;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Ellipse2D;

import javax.swing.JComponent;

import staticFunctions.Sizer;
import syntaxTree.SyntacticStructure;

public class UserMovementPane extends JComponent {

	private UserFrame mUserFrame;
	private SyntacticStructure mSS;
	private double mX;
	private double mY;

	public UserMovementPane(UserFrame userFrame, SyntacticStructure structure) {
		mUserFrame = userFrame;
		mSS = structure;
		mX = mSS.getButtonX() + (mSS.getButtonWidth()/2) + 10;
		mY = mSS.getButtonY() + mSS.getButtonHeight() + 10;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void setPosition(MouseEvent pme) {
		mX = pme.getX();
		mY = pme.getY();
		repaint();
		
	}

	public void setEnd(SyntacticStructure structure) {
		mUserFrame.getSyntaxFacade().addTrace(structure, mSS);
		
	}

	public void paintComponent(Graphics g) {
		Graphics2D lGraphics2D = (Graphics2D) g;
		lGraphics2D.setRenderingHint(
			RenderingHints.KEY_ANTIALIASING,
			RenderingHints.VALUE_ANTIALIAS_ON);
		lGraphics2D.setRenderingHint(
			RenderingHints.KEY_RENDERING,
			RenderingHints.VALUE_RENDER_QUALITY);
		Ellipse2D.Double ellipse = new Ellipse2D.Double(mX, mY, 10,10);
		lGraphics2D.fill(ellipse);
		double x = mSS.getButtonX() + (mSS.getButtonWidth()/2) + 10;
		double y = mSS.getButtonY() + mSS.getButtonHeight() + 10;
		
		ellipse = new Ellipse2D.Double(x,
				y, 10,10);
		lGraphics2D.fill(ellipse);
		 float dash[] = {2.0f, 2.0f};
		    lGraphics2D.setStroke(new BasicStroke(2f, BasicStroke.CAP_BUTT,
		                BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f)); 			//stroke.getDashPhase();
		CubicCurve2D bezier = new CubicCurve2D.Double(
				x + 5,
				y + 5,
				x + 5,
				((mY< y) ? y : mY) + 25,
				mX + 5,
				((mY< y) ? y : mY) + 25,
				mX + 5,
				mY + 5);
		lGraphics2D.draw(bezier);
		lGraphics2D.scale(
				Sizer.scaleWidth()
					* mUserFrame.getDesktopPane().getInternalFrame().getScale(),
				Sizer.scaleHeight()
					* mUserFrame.getDesktopPane().getInternalFrame().getScale());
		
		//Rectangle lRectangle = mSS.getBounds();
		
		
	}
	
}
