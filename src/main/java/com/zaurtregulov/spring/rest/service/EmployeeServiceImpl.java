package com.zaurtregulov.spring.rest.service;


import com.zaurtregulov.spring.rest.dao.EmployeeDAO;
import com.zaurtregulov.spring.rest.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class EmployeeServiceImpl implements EmployeeService {


    private final EmployeeDAO employeeDAO;

    @Autowired
    public EmployeeServiceImpl(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    @Override
    public List<Employee> getAllEmployees() {

        return employeeDAO.getAllEmployees();
    }


    @Override
    @Transactional
    public void saveOrUpdateEmployee(Employee employee) {
        enrichEmployee(employee);
        employeeDAO.saveOrUpdateEmployee(employee);
    }

    @Override
    public Employee getEmployee(int id) {

        return employeeDAO.getEmployee(id);
    }


    @Override
    @Transactional
    public void deleteEmployee(int id) {
        employeeDAO.deleteEmployee(id);
    }


    private void enrichEmployee(Employee employee) {
        LocalDateTime currentTime = LocalDateTime.now();
        if (employee.getId() == 0) {
            employee.setCreatedAt(currentTime);
            employee.setUpdateAt(null);
        } else {
            // Получаем существующую запись из базы данных
            Employee existingEmployee = employeeDAO.getEmployee(employee.getId());
            if (existingEmployee != null) {
                // Сохраняем прежнее значение createdAt
                employee.setCreatedAt(existingEmployee.getCreatedAt());
            }
            // Обновляем дату обновления
            employee.setUpdateAt(currentTime);
        }
    }
}







