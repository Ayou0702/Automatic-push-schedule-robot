package com.enterprise.service;

public interface SendMessageService {

    void pushCourse(String title, String message);

    public void sendTextMsg(String message);

    public void sendNewsMsg(String title, String message);

}
