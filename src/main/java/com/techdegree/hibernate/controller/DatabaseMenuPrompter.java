package com.techdegree.hibernate.controller;

import com.techdegree.hibernate.dao.CountriesDaoImplementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class DatabaseMenuPrompter extends Prompter {
    private BufferedReader mBufferedReader;
    private Logger mLogger;
    private CountriesDaoImplementation mCountriesDaoImplementation;
    private HashMap<Integer,String> mMenu;

    private void fillMenuMapWithOptions() {
        mMenu.put(0, "Exit menu");
        mMenu.put(1, "Add");
        mMenu.put(2, "Delete");
        mMenu.put(3, "Choose");
        mMenu.put(4, "Show all");
    }
    // hopefully will be used in testing
    protected DatabaseMenuPrompter(
            BufferedReader bufferedReader,
            Logger logger,
            CountriesDaoImplementation countriesDaoImplementation) {
        mBufferedReader = bufferedReader;
        mLogger = logger;
        mCountriesDaoImplementation = countriesDaoImplementation;
        fillMenuMapWithOptions();
    }
    // default constructor used in real app with new Reader and Logger
    public DatabaseMenuPrompter(
            CountriesDaoImplementation countriesDaoImplementation) {
        this(new BufferedReader(new InputStreamReader(System.in)),
                new Logger(),
                countriesDaoImplementation);
    }

    // is executed upon "Add" choice: 1
    private void addNewCountry() {
    }

    // method processing user choice through switch
    private void processUserChoice(int userChoice) throws IOException {
        switch (userChoice) {
            case 0:
                mLogger.setSuccessMessage("Exiting...");
                break;
            case 1:
                addNewCountry();
                break;
            default:
                mLogger.setErrorMessage("Unknown choice or no teams: '" +
                        userChoice + "'. Try again.");
                break;
        }
    }

    // main while loop that prompts for options, used in Main
    public void presentMenuWithPossibleOptions() throws IOException {
        int userChoice;
        do {
            printMenuItems("Countries Database");
            userChoice = promptUserForId();
            processUserChoice(userChoice);
        } while (userChoice != 0);
    }

}
