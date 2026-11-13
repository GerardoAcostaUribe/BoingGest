package Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionEvents implements ActionListener
{
    private final ButtonState state;
    
    public ActionEvents(ButtonState state)
    {
        this.state = state;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        System.out.println("Presionado");
        state.SetWasPressed(true);
    }
}
