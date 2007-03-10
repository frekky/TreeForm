package syntaxTree;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ListenerDeleteStartTrace implements ActionListener {

	private SyntaxFacade mSF;
	private SyntacticStructure mSS;

	public ListenerDeleteStartTrace(SyntaxFacade pSF, SyntacticStructure pSS) {
		mSF = pSF;
		mSS = pSS;
	}

	public void actionPerformed(ActionEvent arg0) {
		mSF.deleteStartTrace(mSS);
		mSF.displayTree();
	}

}
