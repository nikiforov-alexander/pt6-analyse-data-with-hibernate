package com.techdegree.hibernate.controller;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// This is super class for both MainMenu- and TeamChangePrompters
public class Prompter {
    // constant to check how many while cycles can be executed, see
    // throwNewRunTimeExceptionIfNumberOfWhileCallsIsBig() for details
    public static final int MAX_NUMBER_OF_WHILE_CALLS = 10000;

    // used to read input from user
    // everything is protected, so that it can be used by child Classes
    // IntellijIdea is complaining
    protected BufferedReader mBufferedReader;

    // Logger is used to print specific messages, and test afterwards:
    // e.g. success message, error message, hint message, etc.
    protected Logger mLogger;

    // Map containing Menu Item -> Description
    protected Map<Integer, String> mMenu;

    // seems to be an overkill. But I've had a lot of tests ran into
    // endless while loop, so I decided to check that number of calls
    // is less that MAX_NUMBER_OF_WHILE_CALLS, to throw Runtime exception.
    // To hide this check as much as possible, I create new member of
    // Prompter class, and throwNewRunTimeExceptionIfNumberOfWhileCallsIsBig()
    // method, see below, that I put in all while loops
    private int mNumberOfWhileCalls;

    // constructor with Buffered Reader and Logger, so that we can test
    // the methods.
    private Prompter(BufferedReader bufferedReader, Logger logger) {
        mBufferedReader = bufferedReader;
        mLogger = logger;
        mMenu = new HashMap<>();
    }

    // default no args constructor
    public Prompter() {
        this(new BufferedReader(new InputStreamReader(System.in)),
                new Logger());
    }

    // prompts user for String, without any checks, returns it
    // @return - returns user inputted String without modification
    // @throws IOException if its impossible to read user input
    protected String promptForStringAndReturnInput() throws IOException {
        String userInputString = "";
        try {
            userInputString = mBufferedReader.readLine();
        } catch (IOException ioe) {
            // It may seem strange here to rethrow exception, but
            // I found it to be the only way to test this part of the code.
            // Otherwise, expected exception is not found in test, but
            // caught here ...
            throw new IOException();
        }
        return userInputString;
    }
    // used in promptForStringWithPatternUntilUserInputMatchingOne to
    // check user input for pattern.
    // @param string - String to be checked for pattern
    // @param patternString - String with regexp, to be passed to Pattern
    // @return boolean - true if matches, false if does not
    private boolean checkStringForPattern(String string, String patternString) {
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }
    // is used in all prompter while loops, increments mNumberOfWhileCalls
    // and checks if this number is more than MAX_NUMBER_OF_WHILE_CALLS
    // @throws new RuntimeException("Too many while calls")
    public void throwNewRunTimeExceptionIfNumberOfWhileCallsIsBig()
            throws RuntimeException {
        mNumberOfWhileCalls++;
        if (mNumberOfWhileCalls > MAX_NUMBER_OF_WHILE_CALLS) {
            throw new RuntimeException("Too many while calls");
        }
    }
    // method prompting user and checking input for a given pattern,
    // in a while loop, until valid input is given.
    // If user input is not accepted, error message is thrown,
    // while is executed again. If user input is accepted, it is
    // returned.
    // Choice message is displayed before each user input
    // @param pattern - regexp, that has to be checked upon in
    //                  checkStringForPattern method
    // @param choiceMessage - String printed every time before user is
    //                        asked for input
    // @param errorMessage - String printed in case user input does not match
    //                       pattern, regexp.
    // @return checked user input String , trimmed
    // @throws RuntimeException("Too many while calls"), see
    //         throwNewRunTimeExceptionIfNumberOfWhileCallsIsBig()
    protected String promptForStringWithPatternUntilUserInputMatchingOne(
            String pattern,
            String choiceMessage,
            String errorMessage) throws IOException {
        boolean userInputIsAccepted = false;
        String userInput = "";
        do {
            // choice message is printed
            mLogger.setChoiceMessage(choiceMessage);
            // userInput is trimmed. See no reason why not ...
            userInput = promptForStringAndReturnInput().trim();
            // input is checked for pattern
            userInputIsAccepted =
                    checkStringForPattern(userInput,pattern);
            // if not accepted, error message is printed
            if (!userInputIsAccepted) {
                mLogger.setErrorMessage(errorMessage);
            }
            // checks for a while not to be infinite
            throwNewRunTimeExceptionIfNumberOfWhileCallsIsBig();
        } while (!userInputIsAccepted);
        return userInput;
    }

    // used to print Menu map of possible options
    // @param menuName - name of Menu to print in parentheses before listing
    //                   actual menu items. Used for separating different Menus
    protected void printMenuItems(String menuName) throws IOException {
        System.out.println("[" + menuName + "]: Here are your options:");
        for (Map.Entry<Integer,String> option: mMenu.entrySet()) {
            System.out.printf("     '%s' - %s %n",
                    option.getKey(),option.getValue());
        }
        System.out.println("[" + menuName + "]: What do you want to do?");
    }

    // prompts user for id, using
    // promptForStringWithPatternUntilUserInputMatchingOne() method.
    // @return int - parsed value
    public int promptUserForId() throws IOException {
        String id =
                promptForStringWithPatternUntilUserInputMatchingOne(
                        "^\\d+$",
                        "Please enter id: " +
                                "(One integer more than 0, like '4', " +
                                "trailing and leading space will be removed)",
                        "Invalid id");
        // We make sure no NumberFormatException is thrown, by prompt function
        // with pattern above
        return Integer.parseInt(id);
    }

    // helpful print method
    public void printEightyHyphensWithoutNewLine() {
        for (int i = 1; i <= 80 ; i++) {
            System.out.printf("-");
        }
    }
    // helpful print method replacing null with "--"
    // and converting double to String
    public String convertDoubleToStringReplacingNull(
            Double decimal, String format) {
        if (decimal == null) {
            return "--";
        } else {
            return String.format(format, decimal);
        }
    }
}
