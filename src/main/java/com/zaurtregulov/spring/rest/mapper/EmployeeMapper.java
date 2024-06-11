package com.zaurtregulov.spring.rest.mapper;


import com.zaurtregulov.spring.rest.dto.EmployeeDTO;
import com.zaurtregulov.spring.rest.entity.Employee;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public EmployeeMapper(ModelMapper modelMapper) {

        this.modelMapper = modelMapper;
    }


    public Employee convertToEmployee(EmployeeDTO employeeDTO) {

        return modelMapper.map(employeeDTO, Employee.class);
    }


    public EmployeeDTO convertToEmployeeDTO(Employee employee) {

        return modelMapper.map(employee, EmployeeDTO.class);

    }
}

