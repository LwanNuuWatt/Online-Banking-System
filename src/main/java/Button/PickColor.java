package Button;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class PickColor extends JButton implements TableCellRenderer {
    private final Color[] colors;

    public PickColor(Color[] colors)
    {
        setOpaque(true);
        this.colors = colors;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        setText((value == null) ? "" : value.toString());

        if (row < colors.length) {
            setForeground(Color.WHITE);
            setBackground(colors[row]);
        }

        return this;
    }

}
