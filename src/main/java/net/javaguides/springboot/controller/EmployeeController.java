package net.javaguides.springboot.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.repository.EmployeeRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	//get all employees
	@GetMapping("/employees")
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}
	
	@PostMapping("/employees")
	public Employee createEmployee(@RequestBody Employee employee) {
		return employeeRepository.save(employee);
	}
	
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployee(@PathVariable Long id) {
		Employee employee= employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("employee doesnt exist with id "+id));
		return ResponseEntity.ok(employee);
	}
	
	@PutMapping("/employees/{id}") 
	public ResponseEntity<Employee> updateEmployee(@RequestBody Employee myEmployee, @PathVariable Long id) {
		Employee employee= employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("employee doesnt exist with id "+id));
		employee.setEmailId(myEmployee.getEmailId());
		employee.setFirstName(myEmployee.getFirstName());
		employee.setLastName(myEmployee.getLastName());
		employeeRepository.save(employee);
		return ResponseEntity.ok(employee);
	}
	
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
		Employee employee= employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("employee doesnt exist with id "+id));
		employeeRepository.delete(employee);
		return ResponseEntity.ok("true");
		
	}
}
