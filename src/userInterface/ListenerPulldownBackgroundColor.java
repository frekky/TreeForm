package userInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ListenerPulldownBackgroundColor implements ActionListener {

	private UserFrame frame;
	private UserButtonPulldownBackground mBPB;
	private UserButtonBackgroundColor mUBB;

	public ListenerPulldownBackgroundColor(UserFrame userFrame, UserButtonPulldownBackground button, UserButtonBackgroundColor UBB) {
		super();
		frame = userFrame;
		mBPB = button;
		mUBB = UBB;
	}

	public void actionPerformed(ActionEvent e) {
		JFrame textFrame = new JFrame();
		JPanel textColorPanel = new JPanel();
		UserColorChooser textColor = new UserColorChooser(frame.getInternalFrame().getProperties());
		textColor.setColor(frame.getInternalFrame().getProperties().getFontColor());
		textColor.getSelectionModel().addChangeListener(new ListenerHighlightBackgroundColor(mUBB,textFrame));
		textColorPanel.add(textColor);
		textFrame.getContentPane().add(textColorPanel);
		textFrame.pack();
		textFrame.setBounds(mBPB.getX() + mBPB.getWidth(),mBPB.getY() + mBPB.getHeight(),
				textFrame.getWidth(),textFrame.getHeight());
		textFrame.setVisible(true);
	}
}
