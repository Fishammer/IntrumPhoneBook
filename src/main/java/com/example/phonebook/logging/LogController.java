package com.example.phonebook.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface LogController {

    default Logger getLogger() {
        return LoggerFactory.getLogger(getLoggerClassName());
    }
    String getLoggerClassName();
}

