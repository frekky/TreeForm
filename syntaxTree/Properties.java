package syntaxTree;

public class Properties {

	private int mMinTextWidth = 30;
	private int mFontSize = 10;
	private int mLineLength = 10;
	private int mLeftTranslate = 20;
	private int mTopTranslate = 20;
	
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
}
