package userInterface;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Arc2D;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;

import staticFunctions.Sizer;

public class UserButtonLineColor extends JButton implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ObservableLineColor mObservableFontColor;
	private ObservableNew mObservableNew;
	private Color mColor;
	private boolean mIconSize;
	public UserButtonLineColor(UserFrame userFrame, boolean iconSize, ObservableLineColor observableFontColor, ObservableNew observableNew) {
		super();
		//this.setUI(null);
		mObservableFontColor = observableFontColor;
		mObservableNew = observableNew;
		mColor = Color.black;
		mIconSize = iconSize;
		
		if (iconSize)
		{
			this.setPreferredSize(new Dimension(32,32));
		}
		else
		{
			this.setPreferredSize(new Dimension(24,24));
		}
	}

	public void update(Observable arg0, Object arg1) {
		if(arg0 == mObservableFontColor)
		{
			this.setForeground(((ObservableLineColor) arg0).getValue());
			this.setColor(this.getForeground());
		}
		if (arg0 == mObservableNew)
		{
			if (mObservableNew.getValue() == 0)
			  {
				this.setEnabled(false);
			  }
			  else
			  {
				this.setEnabled(true);
			  }
		}
	}

	public void setColor(Color foreground) {
		mColor = foreground;
		repaint();
	}
	public Color getColor()
	{
		return mColor;
	}
	public void paintComponent(Graphics pG) {
		super.paintComponent(pG);
		Graphics2D mGraphics2D = (Graphics2D) pG;
		mGraphics2D.setColor(Color.BLACK);
		mGraphics2D.setRenderingHint(
			RenderingHints.KEY_ANTIALIASING,
			RenderingHints.VALUE_ANTIALIAS_ON);
		mGraphics2D.setRenderingHint(
			RenderingHints.KEY_RENDERING,
			RenderingHints.VALUE_RENDER_QUALITY);
		if (mIconSize)
		{
			mGraphics2D.scale(32/24, 32/24);
		}
		mGraphics2D.setStroke(new BasicStroke(0.5f));
		Arc2D arc2D;
		arc2D = new Arc2D.Double(3, 3, 7, 7, 0, 360,Arc2D.PIE);
		mGraphics2D.setColor(Color.BLUE);
		mGraphics2D.fill(arc2D);
		mGraphics2D.setColor(Sizer.BROWN1);
		mGraphics2D.draw(arc2D);
		arc2D = new Arc2D.Double(3, 3, 7, 7, 0, 240,Arc2D.PIE);
		mGraphics2D.setColor(Color.GREEN);
		mGraphics2D.fill(arc2D);
		mGraphics2D.setColor(Sizer.BROWN1);
		mGraphics2D.draw(arc2D);
		arc2D = new Arc2D.Double(3, 3, 7, 7, 0, 120,Arc2D.PIE);
		mGraphics2D.setColor(Color.RED);
		mGraphics2D.fill(arc2D);
		mGraphics2D.setColor(Sizer.BROWN1);
		mGraphics2D.draw(arc2D);
		mGraphics2D.setColor(mColor);
		mGraphics2D.setStroke(new BasicStroke(1.5f));
		mGraphics2D.drawLine(12, 10, 20, 20);
		mGraphics2D.drawLine(12,10,8,14);
		mGraphics2D.drawLine(16,16,12,20);
	}
}