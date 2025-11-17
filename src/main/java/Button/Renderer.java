package Button;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class Renderer extends JButton implements TableCellRenderer {

    public Renderer() {
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column)
    {
        setText((value == null) ? "" : value.toString());
        return this;
    }
}