package syntaxTree;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import userInterface.UserColorChooser;

public class ListenerChangeLineColor implements ActionListener {

	private SyntaxFacade mSF;
	private SyntacticStructure mSS;

	public ListenerChangeLineColor(SyntaxFacade pSF, SyntacticStructure pSS) {
		mSF = pSF;
		mSS = pSS;
	}

	public void actionPerformed(ActionEvent e) {
		JFrame textFrame = new JFrame();
		JPanel textColorPanel = new JPanel();
		UserColorChooser textColor = new UserColorChooser(mSF.getUIF().getProperties());
		textColor.setColor(mSS.getLineColor());
		textColor.getSelectionModel().addChangeListener(new ListenerUpdateLineColor(mSS,mSF));
		textColorPanel.add(textColor);
		textFrame.add(textColorPanel);
		textFrame.pack();
		textFrame.setVisible(true);
	}

}
