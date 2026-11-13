package Components;
import javax.swing.text.*;

public class TextLimit extends DocumentFilter {
    private int limit;

    public TextLimit(int limit) {
        this.limit = limit;
    }

    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException
    {
        if ((fb.getDocument().getLength() + string.length()) <= limit)
        {
            super.insertString(fb, offset, string, attr);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException
    {
        if ((fb.getDocument().getLength() - length + text.length()) <= limit)
        {
            super.replace(fb, offset, length, text, attrs);
        }
    }
}
