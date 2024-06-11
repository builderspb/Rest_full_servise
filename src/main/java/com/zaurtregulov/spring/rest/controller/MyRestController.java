package com.zaurtregulov.spring.rest.controller;

import com.zaurtregulov.spring.rest.dto.EmployeeDTO;
import com.zaurtregulov.spring.rest.entity.Employee;
import com.zaurtregulov.spring.rest.exception_handling.NoSuchEmployeeException;
import com.zaurtregulov.spring.rest.mapper.EmployeeMapper;
import com.zaurtregulov.spring.rest.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class MyRestController {
    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;

    @Autowired
    public MyRestController(EmployeeService employeeService, EmployeeMapper employeeMapper) {
        this.employeeService = employeeService;
        this.employeeMapper = employeeMapper;
    }


    // Метод возвращает Json списка EmployeeDTO
    @GetMapping("/employees")
    public List<EmployeeDTO> showAllEmployees() {

        return employeeService.getAllEmployees().stream()
                .map(employeeMapper::convertToEmployeeDTO)
                .collect(Collectors.toList());
    }


    // Метод возвращает Employee с конкретным ID, который передал клиент в теле Http запроса
    @GetMapping("/employees/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable int id) {
        Employee employee = employeeService.getEmployee(id);
        EmployeeDTO employeeDTO = employeeMapper.convertToEmployeeDTO(employee);
        // Условие для обработки исключительных ситуаций, когда работника с указанным ID не существует
        // При срабатывании условия, создается и выбрасывается исключение NoSuchEmployeeException, которое содержит в себе сообщение
        if (employee == null) {
            throw new NoSuchEmployeeException("There is no employee with ID = " + id + " int DataBase");
        }
        return new ResponseEntity<>(employeeDTO, HttpStatus.OK);
    }

    // Метод cохраняет нового сотрудника в БД. Принимает в Json формате запись. Аннотация @RequestBody связывает тело Http метода с параметром метода контроллера.
    // Jackson парсирует Json в объект, метод сервиса saveEmployee(employee) принимает парсированный объект и сохраняет его в БД.
    // Возвращает ResponseEntity, который содержит в себе тело ответа(тело содержит объект вместе с назначенным ему ID) и Http статус.
    // Можно вернуть просто Employee, без заголовка ответа и явного указания статуса.
    // Добавил dto
    @PostMapping("/employees")
    public ResponseEntity<EmployeeDTO> addNewEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = employeeMapper.convertToEmployee(employeeDTO);
        employeeService.saveOrUpdateEmployee(employee);
        EmployeeDTO responseDTO = employeeMapper.convertToEmployeeDTO(employee);

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }


    @PutMapping("/employees")
    public ResponseEntity<EmployeeDTO> updateEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = employeeMapper.convertToEmployee(employeeDTO);
        employeeService.saveOrUpdateEmployee(employee);
        EmployeeDTO responseDTO = employeeMapper.convertToEmployeeDTO(employee);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }


    @DeleteMapping("/employees/{id}")
    public String deleteEmployeeBuId(@PathVariable int id) {
        Employee employee = employeeService.getEmployee(id);
        if (employee == null) {
            throw new NoSuchEmployeeException("There is no employee with ID = " + id + " int DataBase");
        }

        employeeService.deleteEmployee(id);
        return "Employee with = " + id + " was deleted";
    }


}
