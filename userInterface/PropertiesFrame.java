//TreeForm Syntax Tree Drawing Software
//Copyright (C) 2006  Donald Derrick
//
//This program is free software; you can redistribute it and/or
//modify it under the terms of the GNU General Public License
//as published by the Free Software Foundation; either version 2
//of the License, or (at your option) any later version.
//
//This program is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.
//
//You should have received a copy of the GNU General Public License
//along with this program; if not, write to the Free Software
//Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
package userInterface;

import java.awt.Color;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;

public class PropertiesFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JSlider lineLength;
	private JSlider lineWidth;
	private JSlider leftPad;
	private JSlider topPad;
	private JButton close;
	private JLabel lineLengthLabel;
	private JLabel lineWidthLabel;
	private JLabel leftPadLabel;
	private JLabel topPadLabel;
	private UserInternalFrame mFrame;
	private JSlider defaultFontSize;
	private JLabel defaultFontSizeLabel;
	private JButton restoreDefaults;
	private JColorChooser textColor;
	private JTabbedPane tabbedPane;
	private JPanel textColorPanel;
	private JPanel highlightColorPanel;
	private UserColorChooser highlightColor;
	private JPanel lineColorPanel;
	private UserColorChooser lineColor;
	public PropertiesFrame(UserInternalFrame frame) {
		mFrame = frame;
		lineLengthLabel = new JLabel((String) mFrame.getUserFrame().getI18n().getObject("LINE_HEIGHT_LABEL"));
		lineLength = new JSlider();
		lineLength.setMajorTickSpacing(5);
		lineLength.setMinorTickSpacing(1);
		lineLength.setMaximum(100);
		lineLength.setPaintTicks(true);
		lineLength.setPaintLabels(true);
		lineLength.setValue(frame.getProperties().getMinLineLength());
		lineLength.addChangeListener(new ListenerLineLength(frame.getProperties(),frame.getUserFrame().getInternalFrame().getSyntaxFacade()));
		lineWidthLabel = new JLabel((String) mFrame.getUserFrame().getI18n().getObject("LINE_WIDTH_LABEL"));
		lineWidth = new JSlider();
		lineWidth.setMajorTickSpacing(5);
		lineWidth.setMinorTickSpacing(1);
		lineWidth.setMaximum(100);
		lineWidth.setPaintTicks(true);
		lineWidth.setPaintLabels(true);
		lineWidth.setValue(frame.getProperties().getMinLineWidth());
		lineWidth.addChangeListener(new ListenerLineWidth(frame.getProperties(),frame.getUserFrame().getInternalFrame().getSyntaxFacade()));
		leftPadLabel = new JLabel((String) mFrame.getUserFrame().getI18n().getObject("LEFT_PAD_LABEL"));
		leftPad = new JSlider();
		leftPad.setMajorTickSpacing(20);
		//leftPad.setMinorTickSpacing(1);
		leftPad.setMaximum(200);
		leftPad.setPaintTicks(true);
		leftPad.setPaintLabels(true);
		leftPad.setValue(frame.getProperties().getLeftTranslate());
		leftPad.addChangeListener(new ListenerLeftPad(frame.getProperties(),frame.getUserFrame().getInternalFrame().getSyntaxFacade()));
		topPadLabel = new JLabel((String) mFrame.getUserFrame().getI18n().getObject("TOP_PAD_LABEL"));
		topPad = new JSlider();
		topPad.setMajorTickSpacing(20);
		//topPad.setMinorTickSpacing(1);
		topPad.setMaximum(200);
		topPad.setPaintTicks(true);
		topPad.setPaintLabels(true);
		topPad.setValue(frame.getProperties().getTopTranslate());
		topPad.addChangeListener(new ListenerTopPad(frame.getProperties(),frame.getUserFrame().getInternalFrame().getSyntaxFacade()));
		defaultFontSizeLabel = new JLabel((String) mFrame.getUserFrame().getI18n().getObject("FONT_SIZE_LABEL"));
		defaultFontSize = new JSlider();
		defaultFontSize.setMajorTickSpacing(1);
		defaultFontSize.setMinorTickSpacing(1);
		defaultFontSize.setMinimum(4);
		defaultFontSize.setMaximum(30);
		defaultFontSize.setPaintTicks(true);
		defaultFontSize.setPaintLabels(true);
		defaultFontSize.setValue(frame.getProperties().getDefaultFontSize());
		defaultFontSize.addChangeListener(new ListenerDefaultFontSize(frame.getProperties(),frame.getUserFrame().getInternalFrame().getSyntaxFacade()));
		tabbedPane = new JTabbedPane();
		textColorPanel = new JPanel();
		textColor = new UserColorChooser(frame.getProperties());
		textColor.setColor(frame.getProperties().getFontColor());
		textColor.getSelectionModel().addChangeListener(new ListenerFrameFontColor(frame.getProperties(),frame.getUserFrame()));
		textColorPanel.add(textColor);
		highlightColorPanel = new JPanel();
		highlightColor = new UserColorChooser(frame.getProperties());
		highlightColor.setColor(frame.getProperties().getBackgroundColor());
		highlightColor.getSelectionModel().addChangeListener(new ListenerFrameBackgroundColor(frame.getProperties(),frame.getUserFrame()));
		highlightColorPanel.add(highlightColor);
		lineColorPanel = new JPanel();
		lineColor = new UserColorChooser(frame.getProperties());
		lineColor.setColor(frame.getProperties().getLineColor());
		lineColor.getSelectionModel().addChangeListener(new ListenerFrameLineColor(frame.getProperties(),frame.getUserFrame()));

		lineColorPanel.add(lineColor);
		tabbedPane.addTab( "Text Color", textColorPanel );
		tabbedPane.addTab( "Text Highlight", highlightColorPanel );
		tabbedPane.addTab( "Line Color", lineColorPanel );
		close = new JButton((String) mFrame.getUserFrame().getI18n().getObject("CLOSE_PROPERTIES_LABEL"));
		close.addActionListener(new ListenerCloseProperties(this));
		restoreDefaults = new JButton((String) mFrame.getUserFrame().getI18n().getObject("RESTORE_PROPERTIES_LABEL"));
		restoreDefaults.addActionListener(new ListenerRestoreDefaults(frame.getProperties(),frame.getUserFrame().getInternalFrame().getSyntaxFacade(), this));
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
		this.getContentPane().add(lineLengthLabel);
		this.getContentPane().add(lineLength);
		this.getContentPane().add(lineWidthLabel);
		this.getContentPane().add(lineWidth);
		this.getContentPane().add(leftPadLabel);
		this.getContentPane().add(leftPad);
		this.getContentPane().add(topPadLabel);
		this.getContentPane().add(topPad);
		this.getContentPane().add(defaultFontSizeLabel);
		this.getContentPane().add(defaultFontSize);
		this.getContentPane().add(tabbedPane);
		this.getContentPane().add(restoreDefaults);
		this.getContentPane().add(close);
		this.setBounds(
			(int) (Toolkit.getDefaultToolkit().getScreenSize().width * .45d),
			(int) (Toolkit.getDefaultToolkit().getScreenSize().height * .0d),
			(int) (Toolkit.getDefaultToolkit().getScreenSize().width * .5d),
			(int) (Toolkit.getDefaultToolkit().getScreenSize().height * .95d));
		this.setVisible(true);
	}
	public UserInternalFrame getUIF()
	{
		return mFrame;
	}
	public void setMinTextWidth(int i) {
		lineWidth.setValue(i);
	}
	public void setLineLength(int i) {
		lineLength.setValue(i);
		
	}
	public void setLeftTranslate(int leftTranslate) {
		leftPad.setValue(leftTranslate);
		
	}
	public void setTopTranslate(int topTranslate) {
		topPad.setValue(topTranslate);
		
	}
	public void setFontSize(int i) {
		defaultFontSize.setValue(i);
		
	}
	public void setFontColor(Color fontColor) {
		textColor.setColor(fontColor);
		
	}
	public void setBackgroundColor(Color backgroundColor) {
		highlightColor.setColor(backgroundColor);
	}
	public void setLineColor(Color lineColor2) {
		lineColor.setColor(lineColor2);
		
	}
	
}