package com.techdegree.hibernate.controller;

import com.techdegree.hibernate.dao.CountriesDaoImplementation;
import com.techdegree.hibernate.model.Country;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import java.io.BufferedReader;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class DatabaseMenuPrompterTest {
    // test configuration file
    private static final String TEST_CONFIGURATION_FILE = "hibernate-test.cfg.xml";
    // static session factory initialized in @BeforeClass
    private static SessionFactory mSessionFactory;
    // Dao with all CRUDs we test
    private CountriesDaoImplementation mCountriesDaoImplementation;
    // Test country with ABC code
    private Country mTestCountryWithAbcCode = new Country.CountryBuilder("ABC")
            .withName("Country")
            .withAdultLiteracyRate(1.0)
            .withInternetUsers(1.0)
            .build();
    // buffered reader to be mocked and passed to menu prompter, to control
    // user input
    private BufferedReader mMockedBufferedReader;
    // Logger to be mocked and passed to menu prompter to control user
    // output
    private Logger mMockedLogger;
    // main database menu prompter object, created in setUp with DAO
    private DatabaseMenuPrompter mDatabaseMenuPrompter;

    // adding some decoration before each test
    @Rule
    public TestRule watcher = new TestWatcher() {
        protected void starting(Description description) {
            System.out.printf("%n --------- Test: %s %n",
                    description.getMethodName());
        }
    };

    @Before
    public void setUp() throws Exception {
        // setting up session factory
        final ServiceRegistry serviceRegistry =
                new StandardServiceRegistryBuilder()
                        .configure(TEST_CONFIGURATION_FILE)
                        .build();
        mSessionFactory =
                new MetadataSources(serviceRegistry)
                        .buildMetadata()
                        .buildSessionFactory();
        // setting up DAO with our session factory
        mCountriesDaoImplementation = new
                CountriesDaoImplementation(mSessionFactory);
        // create mocked BufferedReader
        mMockedBufferedReader = mock(BufferedReader.class);
        // create mocked Logger
        mMockedLogger = mock(Logger.class);
        // creating DatabaseMenuPrompter with DAO, mocked Logger and
        // mocked BufferedReader to control user input and output
        mDatabaseMenuPrompter =
                new DatabaseMenuPrompter(
                        mMockedBufferedReader,
                        mMockedLogger,
                        mCountriesDaoImplementation);
        //
    }

    @After
    public void tearDown() throws Exception {
        mSessionFactory.close();
    }

    private void addTestCountryToDatabase() {
        mTestCountryWithAbcCode = new Country.CountryBuilder("ABC")
                .withName("Country")
                .withAdultLiteracyRate(null)
                .withInternetUsers(1.0)
                .build();
        mCountriesDaoImplementation.save(mTestCountryWithAbcCode);
    }

    // Country addition tests
    @Test
    public void addingNewCountryThroughPrompterWorks() throws Exception {
        // Given menu with DAO, pointing to empty test database
        // When user is asked to add new Country and all valid fields
        // are entered
        when(mMockedBufferedReader.readLine())
                .thenReturn("1")
                .thenReturn("BDC")
                .thenReturn("Country")
                .thenReturn("1.0")
                .thenReturn("null")
                .thenReturn("0");
        Country country = new Country.CountryBuilder("BDC")
                .withName("Country")
                .withInternetUsers(1.0)
                .withAdultLiteracyRate(null)
                .build();
        // actual menu call
        mDatabaseMenuPrompter.presentMenuWithPossibleOptions();
        // Then new country should be in database
        Country countryFromDataBase =
                mCountriesDaoImplementation.findCountryByCode("BDC");
        assertEquals(country, countryFromDataBase);
    }
    @Test
    public void addingNewCountryWithThreeErrorsInvokesErrorsFiveTimes()
            throws Exception {
        // Given menu with DAO, pointing to empty test database
        // When user is asked to add new Country and makes one mistake on
        // every prompt,
        when(mMockedBufferedReader.readLine())
                .thenReturn("a")
                .thenReturn("1")
                .thenReturn("adsfdss")
                .thenReturn("BDC")
                .thenReturn("1234")
                .thenReturn("Country")
                .thenReturn("123.143333333333333333")
                .thenReturn("1.0")
                .thenReturn("zero")
                .thenReturn("null")
                .thenReturn("0");
        // actual menu call
        mDatabaseMenuPrompter.presentMenuWithPossibleOptions();
        // Then Logger with error message should be invoked 5 times
        verify(mMockedLogger, times(5)).setErrorMessage(anyString());
    }
    @Test
    public void addingCountryWithExistingCodeGivesLoggerError()
            throws Exception {
        // Given menu with DAO, pointing to test database w "ABC" country
        addTestCountryToDatabase();
        // When user is asked to add new Country and all valid fields
        // are entered
        when(mMockedBufferedReader.readLine())
                .thenReturn("1")
                .thenReturn("ABC")
                .thenReturn("0");
        // actual menu call
        mDatabaseMenuPrompter.presentMenuWithPossibleOptions();
        // Then Logger should return error
        verify(mMockedLogger).setErrorMessage(contains("already exists"));
    }

    // Country deletion tests

    @Test
    public void deletingCountryFromDatabaseActuallyDeletesCountry()
            throws Exception {
        // Given menu with DAO, pointing to test database with "ABC" country
        addTestCountryToDatabase();
        // When user is trying to delete country by code
        when(mMockedBufferedReader.readLine())
                .thenReturn("2")
                .thenReturn("ABC")
                .thenReturn("0");
        // actual menu call
        mDatabaseMenuPrompter.presentMenuWithPossibleOptions();
        // Then no country with ABC should be found
        assertNull(mCountriesDaoImplementation.findCountryByCode("ABC"));
    }
    @Test
    public void deletingNotExistingCountryFromDatabaseGivesError()
            throws Exception {
        // Given menu with DAO, pointing to test database with "ABC" country
        addTestCountryToDatabase();
        // When user is trying to delete non-existing country by code
        when(mMockedBufferedReader.readLine())
                .thenReturn("2")
                .thenReturn("CBA")
                .thenReturn("0");
        // actual menu call
        mDatabaseMenuPrompter.presentMenuWithPossibleOptions();
        // Then Logger should return error message "not found"
        verify(mMockedLogger).setErrorMessage(contains("not found"));
    }

    // Edit country tests


    @Test
    public void editingCountryChangesTestEntry() throws Exception {
        // Given menu with DAO, pointing to test database with "ABC" country
        addTestCountryToDatabase();
        // When user is trying to edit country "ABC"
        when(mMockedBufferedReader.readLine())
                .thenReturn("3")
                .thenReturn("ABC")
                .thenReturn("New Name")
                .thenReturn("null")
                .thenReturn("2.0")
                .thenReturn("0");
        // actual menu call
        mDatabaseMenuPrompter.presentMenuWithPossibleOptions();
        // Then foundCountry should be equal to our test country
        mTestCountryWithAbcCode.setName("New Name");
        mTestCountryWithAbcCode.setInternetUsers(null);
        mTestCountryWithAbcCode.setAdultLiteracyRate(2.0);
        Country foundCountry =
                mCountriesDaoImplementation.findCountryByCode("ABC");
        assertEquals(mTestCountryWithAbcCode, foundCountry);
        assertEquals(mTestCountryWithAbcCode.getInternetUsers(),
                foundCountry.getInternetUsers());
        assertEquals(mTestCountryWithAbcCode.getAdultLiteracyRate(),
                foundCountry.getAdultLiteracyRate());
    }
    @Test
    public void editingNonExistingCountryReturnsErrorMessage()
            throws Exception {
        // Given menu with DAO, pointing to test database with "ABC" country
        addTestCountryToDatabase();
        // When user is trying to edit country "ABC"
        when(mMockedBufferedReader.readLine())
                .thenReturn("3")
                .thenReturn("BAC")
                .thenReturn("0");
        // actual menu call
        mDatabaseMenuPrompter.presentMenuWithPossibleOptions();
        // Then Logger should be invoked with not found message
        verify(mMockedLogger).setErrorMessage(contains("not found"));
    }

    //  Prompting for name, code and decimal tests


    // testing error in code input
    @Test
    public void promptForCodeWorksOnlyWithThreeLetter() throws Exception {
        // Given menu with DAO, pointing to empty Database
        // When promptForCode method is called with following error cases
        when(mMockedBufferedReader.readLine())
                .thenReturn("A")
                .thenReturn("AB")
                .thenReturn("a")
                .thenReturn("ab")
                .thenReturn(" ")
                .thenReturn("")
                .thenReturn("123")
                .thenReturn("ABC ab")
                .thenReturn(" ABCS ")
                .thenReturn("ABC");
        // actual method call
        String name = mDatabaseMenuPrompter.promptForCode();
        // Then only last right one should be accepted
        assertEquals("ABC", name);
    }
    // testing valid code inputs
    @Test
    public void validCountryCodeValuesAreAccepted() throws Exception {
        // Given menu with DAO, pointing to empty Database
        // When promptForCode method is called with following valid
        // country names
        String[] arrayOfValidCountryCodes = new String[]{
                "ABC",
                "abc",
                " abc ",
                " ABC "
        };
        for (String validCountryCode : arrayOfValidCountryCodes) {
            when(mMockedBufferedReader.readLine())
                    .thenReturn(validCountryCode);
            // actual method call
            String acceptedCode = mDatabaseMenuPrompter.promptForCode();
            // Then valid country code is accepted
            assertEquals(validCountryCode.trim(), acceptedCode);
        }
    }

    // testing error in country name input
    @Test
    public void promptNameDoesNotWorkWithBadInput() throws Exception {
        // Given menu with DAO, pointing to empty Database
        // When promptForName method is called with following error cases
        when(mMockedBufferedReader.readLine())
                .thenReturn("CountryWithMoreThan32LettersVeryBigCountry")
                .thenReturn(" ")
                .thenReturn("")
                .thenReturn("123")
                .thenReturn("Some Country");
        // actual method call
        String name = mDatabaseMenuPrompter.promptForName();
        // Then only last right one should be accepted
        assertEquals("Some Country", name);
    }
    // testing good values in country name
    @Test
    public void validCountryNameValuesAreAccepted() throws Exception {
        // Given menu with DAO, pointing to empty Database
        // When promptForName method is called with following valid
        // country names
        String[] arrayOfValidCountryNames = new String[]{
                "Country",
                "New Country",
                "country",
                " country w trail and lead spaces "
        };
        for (String validCountryName : arrayOfValidCountryNames) {
            when(mMockedBufferedReader.readLine())
                    .thenReturn(validCountryName);
            // actual method call
            String acceptedName = mDatabaseMenuPrompter.promptForName();
            // Then valid country name is accepted
            assertEquals(validCountryName.trim(), acceptedName);
        }
    }
    // testing error in decimal input, for both decimal fields
    @Test
    public void promptForDecimalWorksOnlyWithThreeLetter() throws Exception {
        // Given menu with DAO, pointing to empty Database
        // When promptForDecimal method is called with following error cases
        when(mMockedBufferedReader.readLine())
                .thenReturn("String")
                .thenReturn(" ")
                .thenReturn("")
                .thenReturn("12345678901") // number with 11 digits no dots
                .thenReturn("1.234567890") // decimal with 10 digits one dot
                .thenReturn("1.0");
        // actual method call
        Double decimal = mDatabaseMenuPrompter.promptForDecimal("decimal name");
        // Then only last right one should be accepted
        assertEquals(Double.valueOf(1.0), decimal);
    }
    @Test
    public void validDecimalValuesAreAccepted() throws Exception {
        // Given menu with DAO, pointing to empty Database
        // When promptForDecimal method is called with following valid
        // country names
        String[] arrayOfValidCountryDecimals = new String[]{
                "1",
                "1.",
                "1.2",
                "1234567890",
                "1.23456789",
                " 1.0 "
        };
        for (String validCountryDecimal : arrayOfValidCountryDecimals) {
            when(mMockedBufferedReader.readLine())
                    .thenReturn(validCountryDecimal);
            // actual method call
            Double acceptedDecimal =
                    mDatabaseMenuPrompter.promptForDecimal("some Decimal");
            // Then valid decimal is accepted
            assertEquals(
                    Double.valueOf(validCountryDecimal),
                    acceptedDecimal);
        }
    }
}