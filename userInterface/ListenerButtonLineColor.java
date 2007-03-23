package userInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ListenerButtonLineColor implements ActionListener {

	private UserButtonLineColor mLineColor;

	public ListenerButtonLineColor(UserButtonLineColor lineColor) {
		mLineColor = lineColor;
	}

	public void actionPerformed(ActionEvent arg0) {
		mLineColor.setHighlight();

	}

}
