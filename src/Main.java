import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel label = new JLabel("Сотрудник: ");
        panel.add(label);
        String[] items = {"Опция 1", "Опция 2", "Опция 3"}; //
        JComboBox<String> comboBox = new JComboBox<>(items);
        panel.add(comboBox);

        JTabbedPane tabbedPane = new JTabbedPane();

        JTable table = createTasksTable();
        JScrollPane scrollPane = new JScrollPane(table);
        tabbedPane.addTab("Задачи", scrollPane);

        JPanel panel2 = createPlanPanel();
        tabbedPane.addTab("План", panel2);

        frame.add(panel, BorderLayout.NORTH);
        frame.add(tabbedPane, BorderLayout.CENTER);

        frame.setSize(1200, 600);
        frame.setVisible(true);
    }

    private static JPanel createPlanPanel() {
        List<String> months = new ArrayList<>();
        months.add("Январь");
        months.add("Февраль");
        months.add("Март");
        months.add("Апрель");
        months.add("Май");
        months.add("Июнь");
        months.add("Июль");
        months.add("Август");
        months.add("Сентябрь");
        months.add("Октябрь");
        months.add("Ноябрь");
        months.add("Декабрь");

        JComboBox<String> comboBox = new JComboBox<>(months.toArray(new String[0]));
        JPanel panel = new JPanel(new BorderLayout());
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        topPanel.add(new JLabel("Месяц: "));
        topPanel.add(comboBox);
        panel.add(topPanel, BorderLayout.NORTH);

        DefaultTableModel tableModel = createPlanTableModel(0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox<String> selectedComboBox = (JComboBox<String>) e.getSource();
                int selectedMonth = selectedComboBox.getSelectedIndex();
                DefaultTableModel newTableModel = createPlanTableModel(selectedMonth);
                table.setModel(newTableModel);
                table.getColumnModel().getColumn(0).setPreferredWidth(150);
            }
        });
        return panel;
    }

    private static DefaultTableModel createPlanTableModel(int monthNumber) {
        List<String> columnNames = getColumnNames(monthNumber);
        Object[][] data = {}; //
        return new DefaultTableModel(data, columnNames.toArray());
    }

    private static List<String> getColumnNames(int monthNumber) {
        List<String> columnNames = new ArrayList<>();
        columnNames.add("Задача");
        YearMonth yearMonth = YearMonth.of(2000, monthNumber + 1);
        int daysInMonth = yearMonth.lengthOfMonth();
        for (int i = 1; i <= daysInMonth; i++) {
            columnNames.add(String.valueOf(i));
        }
        return columnNames;
    }

    private static JTable createTasksTable() {
        String[] columnNames = {"Выполнена", "Задача", "Дата начала", "Дата окончания", "Дата выполнения"};
        Object[][] data = {}; //
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        return new JTable(model);
    }
}