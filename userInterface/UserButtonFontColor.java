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

public class UserButtonFontColor extends JButton implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ObservableFontColor mObservableFontColor;
	private ObservableNew mObservableNew;
	private Color mColor;
	private boolean mIconSize;
	private UserFrame mUserFrame;

	public UserButtonFontColor(UserFrame userFrame, boolean iconSize, ObservableFontColor observableFontColor, ObservableNew observableNew) {
		super();
		mUserFrame = userFrame;
		mObservableFontColor = observableFontColor;
		mObservableNew = observableNew;
		mIconSize = iconSize;
		mColor = Color.red;
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
			this.setForeground(((ObservableFontColor) arg0).getValue());
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
		mUserFrame.getSyntaxFacade().changeAttributes(TextAttribute.FOREGROUND, mColor);
		repaint();
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
			mGraphics2D.scale(32f/24f, 32f/24f);
		}
		Font mFont = new Font("Doulos SIL", Font.BOLD,12);
		FontRenderContext mFrc = mGraphics2D.getFontRenderContext();	
		ats = new AttributedString("A");
		ats.addAttribute(TextAttribute.FONT, mFont);
		ats.addAttribute(TextAttribute.FOREGROUND, Color.BLACK);
		iter = ats.getIterator();
		tl = new TextLayout(iter, mFrc);				
		tl.draw(mGraphics2D,10,17);
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
		mGraphics2D.fillRect(8, 17, 12, 4);		
	}
}
