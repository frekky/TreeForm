package syntaxTree;

import javax.swing.colorchooser.ColorSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ListenerUpdateLineColor implements ChangeListener {

	private SyntacticStructure mSS;
	private SyntaxFacade mSF;

	public ListenerUpdateLineColor(SyntacticStructure pSS, SyntaxFacade pSF) {
		mSS = pSS;
		mSF = pSF;
	}

	public void stateChanged(ChangeEvent e) {
		mSS.setLineColor(((ColorSelectionModel) e.getSource()).getSelectedColor());
		mSF.displayTree();

	}

}
