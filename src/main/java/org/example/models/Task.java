package org.example.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
@Table(name = "Task")
public class Task {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;

    @Column(name = "is_done")
    private Boolean isDone;

    @NotEmpty(message = "Name should not be empty")
    @Column(name = "name")
    private String name;

    @Column(name = "date_start")
    private Date dateStart;

    @Column(name = "date_end")
    private Date dateEnd;

    @Column(name = "date_implementation")
    private Date dateImplementation;

    public Task() {
    }

    public Task(Employee employee, Boolean isDone, String name, Date dateStart, Date dateEnd, Date dateImplementation) {
        this.employee = employee;
        this.isDone = isDone;
        this.name = name;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.dateImplementation = dateImplementation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Boolean getDone() {
        return isDone;
    }

    public void setDone(Boolean done) {
        isDone = done;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Date getDateImplementation() {
        return dateImplementation;
    }

    public void setDateImplementation(Date dateImplementation) {
        this.dateImplementation = dateImplementation;
    }
}
