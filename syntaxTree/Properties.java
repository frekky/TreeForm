package syntaxTree;

import java.awt.Color;

public class Properties {

	private int mMinTextWidth = 30;
	private int mFontSize = 10;
	private int mLineLength = 10;
	private int mLeftTranslate = 20;
	private int mTopTranslate = 20;
	private Color mLineColor = new Color(0,0,0);
	private Color mBackgroundColor = new Color(255,255,255,0);
	private Color mFontColor = new Color(0,0,0);
	
	public int getDefaultFontSize()
	{
		return mFontSize;
	}
	public int getMinLineLength()
	{
		return mLineLength;
	}
	public int getMinLineWidth()
	{
		return mMinTextWidth;
	}
	public void setDefaultFontSize(int fontSize)
	{
		mFontSize = fontSize;
	}
	public void setMinLineWidth(int minTextWidth)
	{
		mMinTextWidth = minTextWidth;
	}
	public void setLineLength(int lineLength)
	{
		mLineLength = lineLength;
	}
	public int getLeftTranslate() {
		return mLeftTranslate;
	}
	public int getTopTranslate() {
		return mTopTranslate;
	}
	public void setLeftTranslate(int leftTranslate)
	{
		mLeftTranslate = leftTranslate;
	}
	public void setTopTranslate(int topTranslate)
	{
		mTopTranslate = topTranslate;
	}
	public void restoreDefaults() {
		setLineLength(10);
		setMinLineWidth(30);
		setDefaultFontSize(10);
		setLeftTranslate(20);
		setTopTranslate(20);
		setFontColor(new Color(0,0,0));
		setBackgroundColor(new Color(255,255,255,0));
		setLineColor(new Color(0,0,0));
	}
	public void setLineColor(Color color) {
		mLineColor = color;
		
	}
	public void setBackgroundColor(Color color) {
		if (color.getRed() == 255 && color.getGreen() == 255 && color.getBlue() == 255)
		{
			color = new Color(255,255,255,0);
		}
		mBackgroundColor = color;
	}
	public void setFontColor(Color color) {
		mFontColor = color;
		
	}
	public Color getLineColor()
	{
		return mLineColor;
	}
	public Color getBackgroundColor()
	{
		return mBackgroundColor;
	}
	public Color getFontColor()
	{
		return mFontColor;
	}
}
