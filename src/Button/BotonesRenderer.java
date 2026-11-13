package Button;

import java.awt.Component;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class BotonesRenderer extends JPanel implements TableCellRenderer
{
    JButton ver = new JButton("Ver");
    JButton editar = new JButton("Editar");
    JButton eliminar = new JButton("Eliminar");

    public BotonesRenderer()
    {
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        add(ver);
        add(editar);
        add(eliminar);
    }

    @Override
    public Component getTableCellRendererComponent(
            JTable table,
            Object value,
            boolean isSelected,
            boolean hasFocus,
            int row,
            int column)
    {
        return this;
    }
}
