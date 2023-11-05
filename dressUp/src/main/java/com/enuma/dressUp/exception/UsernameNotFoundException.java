package com.enuma.dressUp.exception;

public class UsernameNotFoundException extends RuntimeException {
    public UsernameNotFoundException(String s) {
        System.out.println("No name");
    }
}
