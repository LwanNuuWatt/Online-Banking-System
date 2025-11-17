package Button;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class btnRenderer extends JButton implements TableCellRenderer {
    public btnRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        setText((value == null) ? "Details" : value.toString());
        return this;
    }
}