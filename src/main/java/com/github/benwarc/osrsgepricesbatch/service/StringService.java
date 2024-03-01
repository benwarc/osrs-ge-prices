package com.github.benwarc.osrsgepricesbatch.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StringService {

    private boolean readHelloWorld;

    public String getHelloWorld() {
        if (!readHelloWorld) {
            readHelloWorld = true;
            return "Hello World";
        } else {
            readHelloWorld = false;
            // Adheres to the ItemReader.read contract such that null is returned when all data is exhausted
            return null;
        }
    }

    public String stringToUpperCase(String string) {
        return string != null ? string.toUpperCase() : null;
    }

    public void logString(String string) {
        log.info("{}", string);
    }
}
