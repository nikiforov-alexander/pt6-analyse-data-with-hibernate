package com.techdegree.hibernate.dao;

import com.techdegree.hibernate.model.Country;
import com.techdegree.hibernate.model.Country.CountryBuilder;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

import org.hibernate.exception.DataException;

public class CountriesDaoImplementationTest {
    // test configuration file
    private static final String TEST_CONFIGURATION_FILE = "hibernate-test.cfg.xml";
    // static session factory initialized in @BeforeClass
    private static SessionFactory mSessionFactory;
    // Dao with all CRUDs we test
    private CountriesDaoImplementation mCountriesDaoImplementation;
    // Test country with ABC code
    private Country mTestCountryWithAbcCode;
//    @BeforeClass
//    public static void setUpSessionFactory() {
//        // setting up session factory
//        final ServiceRegistry serviceRegistry =
//                new StandardServiceRegistryBuilder()
//                        .configure(TEST_CONFIGURATION_FILE)
//                        .build();
//        mSessionFactory =
//                new MetadataSources(serviceRegistry)
//                .buildMetadata()
//                .buildSessionFactory();
//    }
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
    }

    @After
    public void tearDown() throws Exception {
        mSessionFactory.close();
    }

    private void addTestCountryToDatabase() {
        mTestCountryWithAbcCode = new CountryBuilder("ABC")
                .withName("Country")
                .withAdultLiteracyRate(null)
                .withInternetUsers(1.0)
                .build();
       mCountriesDaoImplementation.save(mTestCountryWithAbcCode);
    }
    @Test
    public void findAllReturnsOneTestCountryWhenWeAddIt() throws Exception {
        // Given database with one country
        addTestCountryToDatabase();
        // When we fetch all countries
        List<Country> listOfAllCountries =
                mCountriesDaoImplementation.findAll();
        // Then size of list should be one
        assertEquals(1, listOfAllCountries.size());
    }

    @Test
    public void savingCountryReturnsCountryCode() throws Exception {
        // Given empty testing database and test country with code "ABC"
        Country testCountryWithAbcCode = new CountryBuilder("ABC")
                .withName("Country")
                .withAdultLiteracyRate(null)
                .withInternetUsers(1.0)
                .build();
        // When we save a country
        String codeFromSave = mCountriesDaoImplementation
                .save(testCountryWithAbcCode);
        // Then code of country should be returned
        assertEquals("ABC", codeFromSave);
    }

    @Test
    public void getCountryByCodeReturnsCorrectCountry() throws Exception {
        // Given testing database with ABC country
        addTestCountryToDatabase();
        // When we try to find country by code
        Country foundCountry = mCountriesDaoImplementation
                .findCountryByCode("ABC");
        // Then obtained country should be equal to our test country
        assertEquals(mTestCountryWithAbcCode, foundCountry);
    }

    @Test
    public void updateCountryByChangingCodeUpdatesTheCountry() throws Exception {
        // Given testing database with ABC country,
        addTestCountryToDatabase();
        // When we set new name and update database
        mTestCountryWithAbcCode.setName("New Country Name");
        mCountriesDaoImplementation.update(mTestCountryWithAbcCode);
        // Then country's name fetched from database should be equal to
        // new name
        Country testCountryFromDataBase =
                mCountriesDaoImplementation.findCountryByCode("ABC");
        assertEquals("New Country Name", testCountryFromDataBase.getName());
    }

    @Test
    public void deleteCountrySetsSizeToZero() throws Exception {
        // Given testing database with ABC country
        addTestCountryToDatabase();
        // When we delete only entry from database
        mCountriesDaoImplementation.delete(mTestCountryWithAbcCode);
        // Then size of the list of all countries should be zero
        int sizeOfListOfCountriesInDataBase =
                mCountriesDaoImplementation.findAll().size();
        assertEquals(0, sizeOfListOfCountriesInDataBase);
    }

    @Test(expected = DataException.class)
    public void
    decimalsWithMoreThanThreeNumberInIntegerPartWithoutTrailingZerosAreNotAcceptedByDatabaseSave()
            throws Exception {
        // Given testing database with no countries
        // When we add decimal with 4 numbers in integer part
        Country country = new Country.CountryBuilder("ABC")
                .withName("Country")
                .withAdultLiteracyRate(1234.)
                .withInternetUsers(null)
                .build();
        mCountriesDaoImplementation.save(country);
        // Then DataException is thrown
    }

    @Test
    public void decimalsWithRightIntegerPartAndManyDecimalNumbersAreAcceptedBySave()
            throws Exception {
        // Given testing database with no countries
        // When we add decimal with 3 numbers in integer part, and more than
        // eight numbers in fractional part
        Country country = new Country.CountryBuilder("ABC")
                .withName("Country")
                .withAdultLiteracyRate(123.4567890123345)
                .withInternetUsers(null)
                .build();
        mCountriesDaoImplementation.save(country);
        // Then these decimals are accepted by truncating
        assertEquals(1, mCountriesDaoImplementation.findAll().size());
    }
}