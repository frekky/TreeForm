package userInterface;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.colorchooser.ColorSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class ListenerHighlightFontColor implements ChangeListener {

	private JFrame mFrame;
	private Color color;
	private UserButtonFontColor mBFC;

	public ListenerHighlightFontColor(UserButtonFontColor BFC, JFrame frame) {
		mBFC = BFC;
		mFrame = frame;
	}

	public void stateChanged(ChangeEvent arg0) {
		color = ((ColorSelectionModel) arg0.getSource()).getSelectedColor();
		if (color.getRed() == 1 && color.getGreen() == 1 && color.getBlue() == 1)
		{
			((ColorSelectionModel)arg0.getSource()).setSelectedColor(Color.BLACK);
		}
		mBFC.setColor(color);

		mFrame.dispose();
	}
}