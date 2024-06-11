package com.zaurtregulov.spring.rest.exception_handling;


// Класс парсируется в Json для отправки в Http ответе. Предназначается для обработки исключений
public class EmployeeIncorrectData {

    private String info;

    public EmployeeIncorrectData() {
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
