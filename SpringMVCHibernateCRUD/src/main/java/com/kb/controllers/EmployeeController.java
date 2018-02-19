package com.kb.controllers;
import com.kb.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.kb.model.Employee;
import com.kb.service.EmployeeService;

@Controller
public class EmployeeController {
@Autowired
private EmployeeService employeeService;
@RequestMapping(value = "/employees", method = RequestMethod.GET)
public String listemployees(Model model) {
model.addAttribute("employee", new Employee());
model.addAttribute("employeeList", employeeService.listEmployees());
System.out.print(model.addAttribute("employeeList", employeeService.listEmployees()));
return "employee";
}
@RequestMapping(value = "/employee/add", method = RequestMethod.POST)
public String addemployee(@ModelAttribute("employee") Employee employee) {
if (employee.getEmployeeId()==null || employee.getEmployeeId() == 0) {
// new employee, add it
employeeService.addEmployee(employee);
}
else {employeeService.updateEmployee(employee);}
return "redirect:/employees";
}
@RequestMapping("/employee/remove/{id}")
public String removeemployee(@PathVariable("id") int id) {
employeeService.removeEmployee(id);
return "redirect:/employees";
}
@RequestMapping("/employee/edit/{id}")
public String editemployee(@PathVariable("id") int id, Model model) {
model.addAttribute("employee", employeeService.getEmployeeById(id));
model.addAttribute("employeeList", employeeService.listEmployees());
return "employee";
}
}