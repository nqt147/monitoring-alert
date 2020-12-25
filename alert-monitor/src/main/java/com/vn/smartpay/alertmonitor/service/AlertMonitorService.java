package com.vn.smartpay.alertmonitor.service;

import com.google.gson.JsonObject;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface AlertMonitorService {

    ResponseEntity.BodyBuilder alertMonitorCall() throws IOException;

    ResponseEntity.BodyBuilder alertMonitorCallSpeakText(JsonObject joData) throws IOException;

    void reloadConfig();

    void alertApplication(String data);

    void verifyFileIPAT();
}