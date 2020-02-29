package com.hilderjares.spring.service;

import java.util.List;
import com.hilderjares.spring.entity.Employee;
import com.hilderjares.spring.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    
	public List<Employee> getAll() {
		return this.employeeRepository.findAll();
	}

    public Employee save(Employee employee) {
        return this.employeeRepository.save(employee);
    }
}
