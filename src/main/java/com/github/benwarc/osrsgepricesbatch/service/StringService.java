package com.github.benwarc.osrsgepricesbatch.service;

import org.springframework.stereotype.Service;

@Service
public class StringService {

    public String stringToUpperCase(String string) {
        return string != null ? string.toUpperCase() : null;
    }
}
