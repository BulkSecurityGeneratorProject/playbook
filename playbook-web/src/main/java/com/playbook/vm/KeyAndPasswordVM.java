package com.playbook.vm;

/**
 * View Model object for storing the user's key and password.
 */
public class KeyAndPasswordVM {

    private String key;

    private String password;

    private String confirmation;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(String confirmation) {
        this.confirmation = confirmation;
    }
}
