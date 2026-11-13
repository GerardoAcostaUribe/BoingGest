package Components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

public class TipoNotificacionRenderer extends JLabel implements TableCellRenderer
{
    public TipoNotificacionRenderer()
    {
        setOpaque(true);
        setHorizontalAlignment(SwingConstants.CENTER);
        setForeground(Color.WHITE);
        setFont(new Font("Arial", Font.BOLD, 14));
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
        String tipo = value.toString();

        setText(tipo);

        switch(tipo)
        {
            case "Pedido":
                setBackground(Color.BLUE);
                break;

            case "Inventario":
                setBackground(Color.ORANGE);
                break;

            case "Usuario":
                setBackground(new Color(40,167,69));
                break;

            case "Distribución":
                setBackground(Color.RED);
                break;

            default:
                setBackground(Color.GRAY);
        }

        return this;
    }
}
