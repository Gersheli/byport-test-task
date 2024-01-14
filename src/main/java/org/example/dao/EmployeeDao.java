package org.example.dao;

import org.example.models.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class EmployeeDao {
    private final SessionFactory sessionFactory;

    @Autowired
    public EmployeeDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<Employee> index() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select e from Employee e", Employee.class)
                .getResultList();
    }

    @Transactional(readOnly = true)
    public Employee get(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Employee.class, id);
    }
}
