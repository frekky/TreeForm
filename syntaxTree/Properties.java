package syntaxTree;

import java.awt.Color;

public class Properties {

	private int mMinTextWidth = 30;
	private int mFontSize = 10;
	private int mLineLength = 10;
	private int mLeftTranslate = 20;
	private int mTopTranslate = 20;
	private Color mLineColor = new Color(0,0,0);
	private Color mBackgroundColor = new Color(255,255,255);
	private Color mFontColor = new Color(0,0,0);
	
	public int fontSize()
	{
		return mFontSize;
	}
	public int lineLength()
	{
		return mLineLength;
	}
	public int minTextWidth()
	{
		return mMinTextWidth;
	}
	public void setFontSize(int fontSize)
	{
		mFontSize = fontSize;
	}
	public void setMinTextWidth(int minTextWidth)
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
		setMinTextWidth(30);
		setFontSize(10);
		setLeftTranslate(20);
		setTopTranslate(20);
		setFontColor(new Color(0,0,0));
		setBackgroundColor(new Color(255,255,255));
		setLineColor(new Color(0,0,0));
	}
	public void setLineColor(Color color) {
		mLineColor = color;
		
	}
	public void setBackgroundColor(Color color) {
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
