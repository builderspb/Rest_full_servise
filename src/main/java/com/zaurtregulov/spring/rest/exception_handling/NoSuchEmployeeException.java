package com.zaurtregulov.spring.rest.exception_handling;


// Новое исключение NoSuchEmployeeException наследуется от RuntimeException. Служит для обработки ситуации когда Employee отсутствует в БД
public class NoSuchEmployeeException extends RuntimeException {
    // Конструктор принимающий в параметры сообщение
    public NoSuchEmployeeException(String message) {
        super(message);
    }
}
