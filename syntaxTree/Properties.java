package syntaxTree;

public class Properties {

	private int mMinTextWidth = 30;
	private int mFontSize = 10;
	private int mLineLength = 10;
	
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
	public double getLeftTranslate() {
		// TODO Auto-generated method stub
		return 20;
	}
	
}
