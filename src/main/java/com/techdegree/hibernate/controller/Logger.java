package com.techdegree.hibernate.controller;

// This class is used as mocked class when all other methods
// sent different messages. It is also executes almost all System.out.print
// calls
// e.g.: it is invoked in protected constructor with any method
// than mocked in class. And used with verify(logger).setMessage
// to check message
// Also used for decoration purposes
public class Logger {
    protected static final String ANSI_RED = "\u001B[31m";
    protected static final String ANSI_RESET = "\u001B[0m";
    protected static final String ANSI_GREEN = "\u001B[32m";
    protected static final String ANSI_CYAN = "\u001B[36m";

    public void setErrorMessage(String errorMessage) {
        System.out.println(ANSI_RED + "[Error]: " + errorMessage + ANSI_RESET);
    }
    public void setSuccessMessage(String successMessage) {
        System.out.println(ANSI_GREEN + "[Success]: " + successMessage + ANSI_RESET);
    }
    public void setChoiceMessage(String choiceMessage) {
        System.out.println(ANSI_CYAN + "[Input]: " + choiceMessage + ANSI_RESET);
    }
    // default constructor: nothing is here
    public Logger() {
    }
}
