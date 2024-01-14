DROP TABLE IF EXISTS Task;
DROP TABLE IF EXISTS Employee;

CREATE TABLE Employee (
    id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL
);

CREATE TABLE Task (
    id INT AUTO_INCREMENT PRIMARY KEY,
    employee_id INT,
    is_done BOOLEAN,
    name VARCHAR NOT NULL,
    date_start DATE,
    date_end DATE,
    date_implementation DATE,
    FOREIGN KEY (employee_id) REFERENCES Employee(id) ON DELETE CASCADE
);

INSERT INTO Employee(full_name) VALUES ('Иванов И.И.'), ('Петров П.П.');
INSERT INTO Task(employee_id, is_done, name, date_start, date_end, date_implementation) VALUES
(1, false, 'Task1', '2024-01-12', '2024-02-12', '2024-02-11'),
(1, true, 'Task2', '2024-01-13', '2024-02-12', '2024-02-11'),
(2, true, 'Task3', '2024-01-14', '2024-02-01', '2024-02-01'),
(2, false, 'Task4', '2024-01-15', '2024-01-22', '2024-01-21');