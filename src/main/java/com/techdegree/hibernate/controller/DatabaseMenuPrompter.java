package com.techdegree.hibernate.controller;

import com.techdegree.hibernate.dao.CountriesDaoImplementation;
import com.techdegree.hibernate.model.Country;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.OptionalDouble;

public class DatabaseMenuPrompter extends Prompter {
    // reference to our DAO accessing database, testing or real one
    private CountriesDaoImplementation mCountriesDaoImplementation;

    // constructor is used in testing
    protected DatabaseMenuPrompter(
            BufferedReader bufferedReader,
            Logger logger,
            CountriesDaoImplementation countriesDaoImplementation) {
        mBufferedReader = bufferedReader;
        mLogger = logger;
        mCountriesDaoImplementation = countriesDaoImplementation;
        mMenu = new HashMap<>();
        fillMenuMapWithOptions();
    }
    // used in addNewCountry method
    // default constructor used in real app with new Reader and Logger
    // and real database DAO from Main
    public DatabaseMenuPrompter(
            CountriesDaoImplementation countriesDaoImplementation) {
        this(new BufferedReader(new InputStreamReader(System.in)),
                new Logger(),
                countriesDaoImplementation);
    }


    // used in addNewCountry, deleteCountryById methods
    // prompts for country code. code is primary key, so no null can be
    // accepted, and only 3 letter words are accepted
    // @throws IOException - see
    //      promptForStringWithPatternUntilUserInputMatchingOne.
    protected String promptForCode() throws IOException {
        return promptForStringWithPatternUntilUserInputMatchingOne(
                "^[a-zA-Z]{3}$",
                "Please type Code of the new country " +
                        "(three letters, case-insensitive, like 'abc')",
                "Wrong code"
        );
    }
    // used in addNewCountry, deleteCountryById methods
    // prompts for name. I don't accept null here, because there should be
    // some name.
    // @return String - valid country name to be inserted in database
    // @throws IOException - see
    //      promptForStringWithPatternUntilUserInputMatchingOne.
    protected String promptForName() throws IOException {
        return promptForStringWithPatternUntilUserInputMatchingOne(
                "^(?=([a-zA-Z]+)).{0,32}$",
                "Please type Name of the new country " +
                        "(0-32 letters, like 'Country')",
                "Wrong name"
        );
    }
    // used in addNewCountry method
    // @return correct Double or null - works for both decimal fields in
    //         database
    // @throws IOException - see
    //      promptForStringWithPatternUntilUserInputMatchingOne.
    protected Double promptForDecimal(String decimalName) throws IOException {
        // can be "1", "1.", "1.23456789" max, the last digit will be added by
        // me, also "1234567890", then I will save 1234567890.0
        // applied to both decimals
        // If user types 'null' then value will go to database as null,
        // or "--"
        String acceptedDecimalValue =
                promptForStringWithPatternUntilUserInputMatchingOne(
                        "^(?=[0-9]+\\.[0-9]+|[0-9]+|[0-9]+\\.|null).{0,10}$",
                        "Please type '" + decimalName + "' of the new country " +
                                "(decimal or integer with max 10 digits, " +
                                "like '1', '1.', '1.23456789' max or '1234567890' max " +
                                "or type 'null' for absent value",
                        "Wrong decimal"
                );
        if (acceptedDecimalValue.equals("null")) {
            return null;
        } else {
            return Double.valueOf(acceptedDecimalValue);
        }
    }

    // used in showStatistics method
    private void printStatisticDecimalNicely(String message,
                                             Double decimal) {
        System.out.printf("%40s %15s%n",
                message,
                convertDoubleToStringReplacingNull(decimal,"%15.2f"));
    }
    // is executed upon "5: Shows statistics of database
    private void showStatistics() {
        printStatisticDecimalNicely(
                "Min adult literacy rate is: ",
                mCountriesDaoImplementation.getMinimumAdultLiteracy()
        );
        printStatisticDecimalNicely(
                "Max adult literacy rate is: ",
                mCountriesDaoImplementation.getMaximumAdultLiteracy()
        );
        printStatisticDecimalNicely(
                "Min internet users is: ",
                mCountriesDaoImplementation.getMinimumInternetUsers()
        );
        printStatisticDecimalNicely(
                "Max internet users is: ",
                mCountriesDaoImplementation.getMaximumInternetUsers()
        );
        printStatisticDecimalNicely(
                "Correlation coefficient is: ",
                mCountriesDaoImplementation.getCorrelationCoefficient()
        );
    }

    // is executed upon "4" : Show all countries in database
    private void showAll() {
        // print header
        System.out.printf("%7s %40s %15s %15s%n",
                "Code",
                "Country Name",
                "Internet Users",
                "Literacy"
        );
        printEightHyphensWithoutNewLine();
        System.out.printf("%n");
        // print countries in database
        mCountriesDaoImplementation
                .findAll().forEach(System.out::println);
        printEightHyphensWithoutNewLine();
        // print header
        System.out.printf("%n%7s %40s %15s %15s%n",
                "Code",
                "Country Name",
                "Internet Users",
                "Literacy"
        );
    }

    // is executed upon "3" : Edit option in Main Menu
    private void editCountry() throws IOException {
        // prompt user for Code
        String code = promptForCode();
        // try to find country
        Country foundCountry =
                mCountriesDaoImplementation.findCountryByCode(code);
        // if country is found, update with all fields, else print error
        if (foundCountry != null) {
            // Since it was not specifically mentioned I will provide basic
            // functionality here, i.e. editing is possible only for all
            // all fields
            // prompt for name, internetUsers and adultLiteracyRate
            String name = promptForName();
            Double internetUsers = promptForDecimal("Internet Users");
            Double adultLiteracyRate = promptForDecimal("Adult Literacy Rate");
            // set fields using setters
            foundCountry.setName(name);
            foundCountry.setInternetUsers(internetUsers);
            foundCountry.setAdultLiteracyRate(adultLiteracyRate);
            // update found country
            mCountriesDaoImplementation.update(foundCountry);
        } else {
            mLogger.setErrorMessage("Country with this code is not found");
        }
    }
    // is executed upon "2": Delete option in Main Menu
    // @throws IOException because of prompt methods
    private void deleteCountryByCode() throws IOException {
        // prompt user for code
        String code = promptForCode();
        // try to find country by code
        Country foundCountry =
                mCountriesDaoImplementation.findCountryByCode(code);
        // if country is found, delete, if not print "Error"
        if (foundCountry != null) {
            mCountriesDaoImplementation.delete(foundCountry);
        } else {
            mLogger.setErrorMessage("Country with this code is not found");
        }
    }
    // is executed upon "Add" choice: 1
    // @throws IOException - see
    //      promptFor.. methods
    private void addNewCountry() throws IOException {
        // prompt for member variables
        String code = promptForCode();
        // if country with this code does not exist
        if (mCountriesDaoImplementation.findCountryByCode(code) == null) {
            String name = promptForName();
            Double internetUsers = promptForDecimal("Internet Users");
            Double adultLiteracyRate = promptForDecimal("Adult Literacy Rate");
            // create new country
            Country country = new Country.CountryBuilder(code)
                    .withName(name)
                    .withInternetUsers(internetUsers)
                    .withAdultLiteracyRate(adultLiteracyRate)
                    .build();
            // save to database
            mCountriesDaoImplementation.save(country);
        } else {
            mLogger.setErrorMessage("Country with this code already exists");
        }
    }

    // fill menu map with options, used in constructors, see also
    // method below
    private void fillMenuMapWithOptions() {
        mMenu.put(0, "Exit menu");
        mMenu.put(1, "Add");
        mMenu.put(2, "Delete");
        mMenu.put(3, "Edit");
        mMenu.put(4, "Show all");
        mMenu.put(5, "Show statistics");
    }
    // method processing user choice through switch
    private void processUserChoice(int userChoice) throws IOException {
        switch (userChoice) {
            case 0:
                mLogger.setSuccessMessage("Exiting...");
                break;
            case 1:
                showAll();
                addNewCountry();
                break;
            case 2:
                showAll();
                deleteCountryByCode();
                break;
            case 3:
                showAll();
                editCountry();
                break;
            case 4:
                showAll();
                break;
            case 5:
                showStatistics();
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
            // print options of menu
            printMenuItems("Countries Database");
            // get correct int from user
            userChoice = promptUserForId();
            // main processing switch, see higher
            processUserChoice(userChoice);
            // handy feature to prevent infinite looping
            throwNewRunTimeExceptionIfNumberOfWhileCallsIsBig();
        } while (userChoice != 0);
    }

}
