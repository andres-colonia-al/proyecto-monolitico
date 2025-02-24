package com.acolonia.spring.service.IServices;

public interface EmailService {

    //Método para el envío de correos electrónicos
    void sendEmail(String toUser, String subject, String mesage);

}
