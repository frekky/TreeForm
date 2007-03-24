package userInterface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;

public class UserButtonPulldownForeground extends JButton implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ObservableFontColor mObservableFontColor;
	private ObservableNew mObservableNew;
	private Color mColor;
	private boolean mIconSize;

	public UserButtonPulldownForeground(UserFrame userFrame, boolean iconSize, ObservableFontColor color, ObservableNew observableNew) {
		super();
		mColor = Color.yellow;
		mIconSize = iconSize;
		mObservableFontColor = color;
		mObservableNew = observableNew;
		if (iconSize)
		{
			this.setPreferredSize(new Dimension(12,32));
		}
		else
		{
			this.setPreferredSize(new Dimension(9,24));
		}
	}

	public void update(Observable arg0, Object arg1) {
		if(arg0 == mObservableFontColor)
		{
			this.setForeground(((ObservableFontColor) arg0).getValue());
			this.setColor(((ObservableFontColor) arg0).getValue());
			//System.out.println("yes");
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

	private void setColor(Color background) {
		mColor = background;
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
			mGraphics2D.scale(32f/24f, 32f/24f);
		}
		GeneralPath polly = new GeneralPath();
		polly.moveTo(2, 10);
		polly.lineTo(7, 10);
		polly.lineTo(4.5f,14);
		polly.closePath();
		mGraphics2D.fill(polly);
	}
}