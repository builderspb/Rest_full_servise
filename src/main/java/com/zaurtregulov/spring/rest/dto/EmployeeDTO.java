package com.zaurtregulov.spring.rest.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

public class EmployeeDTO {
    // Аннотация исключает поле из сериализации
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int id;
    private String name;
    private String surname;
    private String department;
    private int salary;




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
