package com.playbook.error;

public class ErrorUtil {

    public static void check(boolean condition, RuntimeException exception) {
        if (condition) {
            throw exception;
        }
    }



}
