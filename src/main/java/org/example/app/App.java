package org.example.app;

import org.example.dao.EmployeeDao;
import org.example.models.Employee;
import org.example.models.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

@Component
public class App {
    private final EmployeeDao employeeDao;
    private int curEmpId = 1;
    private int curMonth = 0;
    private JTable tasksTable;
    private JTable planTable;

    @Autowired
    public App(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    public void start() {
        JFrame frame = new JFrame("Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel label = new JLabel("Сотрудник: ");
        panel.add(label);

        String[] employees = employeeDao.index().stream()
                .map(Employee::getFullName)
                .toArray(String[]::new);
        JComboBox<String> comboBox = new JComboBox<>(employees);
        panel.add(comboBox);

        JPanel panel2 = createPlanPanel();
        tasksTable = new JTable(createTasksTableModel());
        TableCellRenderer checkboxRenderer = new DefaultTableCellRenderer() {
            private final JCheckBox checkbox = new JCheckBox();

            @Override
            public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                checkbox.setSelected((Boolean) value);
                checkbox.setHorizontalAlignment(JCheckBox.CENTER);
                return checkbox;
            }
        };
        tasksTable.getColumnModel().getColumn(0).setCellRenderer(checkboxRenderer);

        JTabbedPane tabbedPane = new JTabbedPane();
        JScrollPane scrollPane = new JScrollPane(tasksTable);
        tabbedPane.addTab("Задачи", scrollPane);
        tabbedPane.addTab("План", panel2);
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox<String> selectedComboBox = (JComboBox<String>) e.getSource();
                curEmpId = selectedComboBox.getSelectedIndex() + 1;
                tasksTable.setModel(createTasksTableModel());
                planTable.setModel(createPlanTableModel());
                planTable.getColumnModel().getColumn(0).setPreferredWidth(150);
                tasksTable.getColumnModel().getColumn(0).setCellRenderer(checkboxRenderer);
                highlightCells();
            }
        });

        frame.add(panel, BorderLayout.NORTH);
        frame.add(tabbedPane, BorderLayout.CENTER);

        frame.setSize(1200, 600);
        frame.setVisible(true);
    }

    private JPanel createPlanPanel() {
        JComboBox<String> comboBox = new JComboBox<>(new String[]{"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь",
                "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"});
        JPanel panel = new JPanel(new BorderLayout());
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        topPanel.add(new JLabel("Месяц: "));
        topPanel.add(comboBox);
        panel.add(topPanel, BorderLayout.NORTH);

        DefaultTableModel tableModel = createPlanTableModel();
        planTable = new JTable(tableModel);
        planTable.getColumnModel().getColumn(0).setPreferredWidth(150);
        highlightCells();
        JScrollPane scrollPane = new JScrollPane(planTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox<String> selectedComboBox = (JComboBox<String>) e.getSource();
                curMonth = selectedComboBox.getSelectedIndex();
                DefaultTableModel newTableModel = createPlanTableModel();
                planTable.setModel(newTableModel);
                planTable.getColumnModel().getColumn(0).setPreferredWidth(150);
                highlightCells();
            }
        });
        return panel;
    }

    private DefaultTableModel createPlanTableModel() {
        List<String> columnNames = new ArrayList<>();
        columnNames.add("Задача");
        YearMonth yearMonth = YearMonth.of(2000, curMonth + 1);
        int daysInMonth = yearMonth.lengthOfMonth();
        for (int i = 1; i <= daysInMonth; i++) {
            columnNames.add(String.valueOf(i));
        }

        List<Task> tasks = employeeDao.get(curEmpId).getTasks();
        int rows = 0;
        for (Task task : tasks) {
            if (task.getDateStart().getMonth() == curMonth || task.getDateEnd().getMonth() == curMonth)
                rows++;
        }

        Object[][] data = new Object[rows][daysInMonth];
        for (int i = 0, j = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getDateStart().getMonth() == curMonth || tasks.get(i).getDateEnd().getMonth() == curMonth) {
                data[j][0] = tasks.get(i).getName();
                j++;
            }
        }
        return new DefaultTableModel(data, columnNames.toArray());
    }

    private void highlightCells() {
        List<Task> tasks = employeeDao.get(curEmpId).getTasks();
        Calendar calendar = Calendar.getInstance();
        GrayCellRenderer cellRenderer = null;
        for (int i = 0; i < tasks.size(); i++) {
            Date dateStart = tasks.get(i).getDateStart();
            Date dateEnd = tasks.get(i).getDateEnd();
            int start = 1;
            int end = planTable.getColumnCount() - 1;
            if (dateStart.getMonth() == curMonth) {
                calendar.setTime(dateStart);
                start = calendar.get(Calendar.DAY_OF_MONTH);
                calendar.setTime(dateEnd);
                if (calendar.get(Calendar.DAY_OF_MONTH) == curMonth) {
                    end = calendar.get(Calendar.DAY_OF_MONTH);
                }
            } else if (dateEnd.getMonth() == curMonth) {
                calendar.setTime(dateEnd);
                end = calendar.get(Calendar.DAY_OF_MONTH);
            }
            if (dateStart.getMonth() == curMonth || dateEnd.getMonth() == curMonth) {
                if (cellRenderer == null)
                    cellRenderer = new GrayCellRenderer(i, start, end);
                else
                    cellRenderer.add(i, start, end);
            }
        }
        planTable.setDefaultRenderer(Object.class, cellRenderer);
    }

    private DefaultTableModel createTasksTableModel() {
        String[] columnNames = {"Выполнена", "Задача", "Дата начала", "Дата окончания", "Дата выполнения"};
        List<Task> tasks = employeeDao.get(curEmpId).getTasks();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        List<Object[]> dataList = new ArrayList<>();
        for (Task task : tasks) {
            Object[] values = new Object[5];
            values[0] = task.getDone();
            values[1] = task.getName();
            values[2] = formatter.format(task.getDateStart());
            values[3] = formatter.format(task.getDateEnd());
            values[4] = formatter.format(task.getDateImplementation());
            dataList.add(values);
        }
        Object[][] data = dataList.toArray(new Object[0][]);
        return new DefaultTableModel(data, columnNames);
    }
}
