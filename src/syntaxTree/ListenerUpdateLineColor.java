package syntaxTree;

import javax.swing.JFrame;
import javax.swing.colorchooser.ColorSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ListenerUpdateLineColor implements ChangeListener {

	private SyntacticStructure mSS;
	private SyntaxFacade mSF;
	private JFrame mFrame;

	public ListenerUpdateLineColor(SyntacticStructure pSS, SyntaxFacade pSF, JFrame textFrame) {
		mSS = pSS;
		mSF = pSF;
		mFrame = textFrame;
	}

	public void stateChanged(ChangeEvent e) {
		mSS.setLineColor(((ColorSelectionModel) e.getSource()).getSelectedColor());
		mSF.displayTree();
		mFrame.dispose();
	}

}
