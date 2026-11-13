package Button;

public class ButtonState
{
    private volatile boolean wasPressed = false;
    
    public boolean WasPressed()
    {
        return wasPressed;
    }

    public void SetWasPressed(boolean wasPressed)
    {
        this.wasPressed = wasPressed;
    }
}
