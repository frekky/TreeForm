package userInterface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;

public class UserButtonBackgroundColor extends JButton implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ObservableBackgroundColor mObservableBackgroundColor;
	private ObservableNew mObservableNew;
	private Color mColor;

	public UserButtonBackgroundColor(UserFrame userFrame, boolean iconSize, ObservableBackgroundColor observableFontColor, ObservableNew observableNew) {
		super();
		mObservableBackgroundColor = observableFontColor;
		mObservableNew = observableNew;
		this.setForeground(new Color(255,255,255));
		this.setText("B");
		if (iconSize)
		{
			this.setFont(new Font("Doulos SIL", Font.BOLD, 20));
			this.setPreferredSize(new Dimension(32,32));
		}
		else
		{
			this.setFont(new Font("Doulos SIL", Font.BOLD, 16));
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
}