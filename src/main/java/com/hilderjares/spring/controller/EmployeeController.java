package com.hilderjares.spring.controller;

import java.util.List;

import com.hilderjares.spring.entity.Employee;
import com.hilderjares.spring.security.Response;
import com.hilderjares.spring.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
@RequestMapping({"/api"})
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@RequestMapping(value = "/employee", method = RequestMethod.GET)
	public ResponseEntity<List<Employee>> index() {

		List<Employee> employees = employeeService.getAll();

		if (employees.isEmpty() || employees == null) {
			return new ResponseEntity(new Response("Employees not founds", 404), HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<List<Employee>>(employees, HttpStatus.OK);
	}
}
