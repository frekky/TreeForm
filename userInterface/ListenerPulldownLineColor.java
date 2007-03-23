package userInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ListenerPulldownLineColor implements ActionListener {

	private UserFrame frame;
	private UserButtonPulldownLine mButton;
	private UserButtonLineColor mULC;

	public ListenerPulldownLineColor(UserFrame userFrame, UserButtonPulldownLine pulldownLineColor, UserButtonLineColor ULC) {
		super();
		frame = userFrame;
		mButton = pulldownLineColor;
		mULC = ULC;
	}

	public void actionPerformed(ActionEvent e) {
		JFrame textFrame = new JFrame();
		JPanel textColorPanel = new JPanel();
		UserColorChooser textColor = new UserColorChooser(frame.getInternalFrame().getProperties());
		textColor.setColor(frame.getInternalFrame().getProperties().getFontColor());
		textColor.getSelectionModel().addChangeListener(new ListenerHighlightLineColor(mULC,textFrame));	
		frame.getObservableLineColor().addObserver(textColor);
		textColorPanel.add(textColor);
		textFrame.add(textColorPanel);
		textFrame.pack();
		textFrame.setBounds(mButton.getX() + mButton.getWidth(),mButton.getY() + mButton.getHeight(),
				textFrame.getWidth(),textFrame.getHeight());

		textFrame.setVisible(true);
	}
}
