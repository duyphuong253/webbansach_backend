package vn.fun.webbansach_backend.service;

public interface EmailService {

    public void sendMessage(String string, String email, String subject, String text);

}
