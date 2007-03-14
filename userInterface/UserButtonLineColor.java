package userInterface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;

public class UserButtonLineColor extends JButton implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ObservableLineColor mObservableFontColor;
	private ObservableNew mObservableNew;
	private Color mColor;

	public UserButtonLineColor(UserFrame userFrame, boolean iconSize, ObservableLineColor observableFontColor, ObservableNew observableNew) {
		super();
		mObservableFontColor = observableFontColor;
		mObservableNew = observableNew;
		this.setText("L");
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

	private void setColor(Color foreground) {
		mColor = foreground;
	}
	public Color getColor()
	{
		return mColor;
	}
}