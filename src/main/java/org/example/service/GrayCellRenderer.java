package org.example.service;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GrayCellRenderer extends DefaultTableCellRenderer {

    private List<Integer> rows = new ArrayList<>();
    private List<Integer> startColumns = new ArrayList<>();
    private List<Integer> endColumns = new ArrayList<>();

    public GrayCellRenderer(int row, int startColumn, int endColumn) {
        rows.add(row);
        startColumns.add(startColumn);
        endColumns.add(endColumn);
    }

    public void add(int row, int startColumn, int endColumn) {
        rows.add(row);
        startColumns.add(startColumn);
        endColumns.add(endColumn);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (isHighlightedCell(row, column)) {
            cellComponent.setBackground(Color.LIGHT_GRAY);
        } else {
            cellComponent.setBackground(table.getBackground());
        }
        return cellComponent;
    }

    private boolean isHighlightedCell(int row, int column) {
        for (int i = 0; i < rows.size(); i++) {
            if (this.rows.get(i) == row && column >= startColumns.get(i) && column <= endColumns.get(i))
                return true;
        }
        return false;
    }
}
