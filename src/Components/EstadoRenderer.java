package Components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

public class EstadoRenderer extends JLabel implements TableCellRenderer
{
    public EstadoRenderer()
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
            case "Suficiente":
                setBackground(new Color(40, 167, 69));
                break;

            case "Bajo":
                setBackground(new Color(255, 193, 7));
                break;

            case "Agotado":
                setBackground(new Color(220, 53, 69));
                break;

            default:
                setBackground(Color.GRAY);
        }

        return this;
    }
}
