package userInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ListenerPulldownFontColor implements ActionListener {

	private UserFrame frame;
	private UserButtonPulldownForeground mButton;
	private UserButtonFontColor mBFC;

	public ListenerPulldownFontColor(UserFrame userFrame, UserButtonPulldownForeground pulldownFontColor, UserButtonFontColor BFC) {
		super();
		frame = userFrame;
		mButton = pulldownFontColor;
		mBFC = BFC;
	}

	public void actionPerformed(ActionEvent e) {
		JFrame textFrame = new JFrame();
		JPanel textColorPanel = new JPanel();
		UserColorChooser textColor = new UserColorChooser(frame.getInternalFrame().getProperties());
		textColor.setColor(frame.getInternalFrame().getProperties().getFontColor());
		textColor.getSelectionModel().addChangeListener(new ListenerHighlightFontColor(mBFC,textFrame));
		textColorPanel.add(textColor);
		textFrame.getContentPane().add(textColorPanel);
		textFrame.pack();
		textFrame.setBounds(mButton.getX() + mButton.getWidth(),mButton.getY() + mButton.getHeight(),
				textFrame.getWidth(),textFrame.getHeight());
		textFrame.setVisible(true);
	}
}
