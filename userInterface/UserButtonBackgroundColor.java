package userInterface;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.Arc2D;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;

import staticFunctions.Sizer;

public class UserButtonBackgroundColor extends JButton implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ObservableBackgroundColor mObservableBackgroundColor;
	private ObservableNew mObservableNew;
	private Color mColor;
	private boolean mIconSize;

	public UserButtonBackgroundColor(UserFrame userFrame, boolean iconSize, ObservableBackgroundColor observableFontColor, ObservableNew observableNew) {
		super();
		mColor = Color.yellow;
		mIconSize = iconSize;
		mObservableBackgroundColor = observableFontColor;
		mObservableNew = observableNew;
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
		if(arg0 == mObservableBackgroundColor)
		{
			this.setForeground(((ObservableBackgroundColor) arg0).getValue());
			this.setColor(this.getForeground());
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
		AttributedString ats;
		AttributedCharacterIterator iter;
		TextLayout tl;
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
		Font mFont = new Font("Doulos SIL", Font.BOLD,7);
		FontRenderContext mFrc = mGraphics2D.getFontRenderContext();
		
		ats = new AttributedString("ABC");
		ats.addAttribute(TextAttribute.FONT, mFont);
		ats.addAttribute(TextAttribute.FOREGROUND, Color.BLACK);
		ats.addAttribute(TextAttribute.BACKGROUND, Sizer.BLUE3);
		iter = ats.getIterator();
		tl = new TextLayout(iter, mFrc);				
		tl.draw(mGraphics2D,5,17);
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
		mGraphics2D.fillRect(5, 17, 15, 4);		
	}
}