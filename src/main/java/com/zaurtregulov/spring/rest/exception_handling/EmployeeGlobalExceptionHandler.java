package com.zaurtregulov.spring.rest.exception_handling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


// Класс помеченный аннотацией @ControllerAdvice отвечает за глобальную поимку исключений, выброшенных контроллерами.
// Методы класса(см. ниже), отлавливают и обрабатывают конкретные исключения, которые создаются в контроллерах с переданным
// в них сообщениями. Возвращает в Http ответе ResponseEntity в теле которого содержатся сообщения парсированные в формат Json.
@ControllerAdvice
public class EmployeeGlobalExceptionHandler {


    // Метод срабатывает при выбрасывании исключения NoSuchEmployeeException, т.к. принимает в свои параметры это исключение
    // Возвращает ResponseEntity, который параметризован типом EmployeeIncorrectData (это класс который предназначен для парсирования
    // в Json, чтобы в Http ответе отправить сообщение об ошибке)
    @ExceptionHandler
    public ResponseEntity<EmployeeIncorrectData> handleException(NoSuchEmployeeException exception){
        // создается объект класса, чтобы передать ему сообщение об ошибке(далее он будет преобразован в Json для отправки в Http ответе)
        EmployeeIncorrectData data = new EmployeeIncorrectData();
        // Объекту класса передается сообщение
        data.setInfo(exception.getMessage());

        // возвращается ResponseEntity, в параметры которого передается объект класса(который преобразуется в Json, и статус Http ответа)
        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }


    // Тот же метод, но он срабатывает при любом Exception, который так же принимает в свои параметры.(например если вместо ID ввести буквы)
    @ExceptionHandler
    public ResponseEntity<EmployeeIncorrectData> handleException(Exception exception){
        EmployeeIncorrectData data = new EmployeeIncorrectData();
        data.setInfo(exception.getMessage());

        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }

}
