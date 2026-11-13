package Components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

public class EstadoPedidoRenderer extends JLabel implements TableCellRenderer
{
    public EstadoPedidoRenderer()
    {
        setHorizontalAlignment(SwingConstants.CENTER);
        setOpaque(true);
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
        String estado = value.toString();

        setText(estado);

        switch (estado)
        {
            case "En preparación":
                setBackground(new Color(255, 193, 7));
                break;

            case "En distribución":
                setBackground(new Color(13, 110, 253));
                break;

            case "Entregado":
                setBackground(new Color(40, 167, 69));
                break;

            case "Pendiente":
                setBackground(new Color(255, 128, 0));
                break;

            default:
                setBackground(Color.GRAY);
        }

        return this;
    }
}
