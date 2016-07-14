package com.techdegree.hibernate.dao;

import com.techdegree.hibernate.model.Country;
import com.techdegree.hibernate.model.Country.CountryBuilder;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class CountriesDaoImplementationTest {
    // test configuration file
    private static final String TEST_CONFIGURATION_FILE = "hibernate-test.cfg.xml";
    // static session factory initialized in @BeforeClass
    private static SessionFactory mSessionFactory;
    // Dao with all CRUDs we test
    private CountriesDaoImplementation mCountriesDaoImplementation;

    @BeforeClass
    public static void setUpSessionFactory() {
        // setting up session factory
        final ServiceRegistry serviceRegistry =
                new StandardServiceRegistryBuilder()
                        .configure(TEST_CONFIGURATION_FILE)
                        .build();
        mSessionFactory =
                new MetadataSources(serviceRegistry)
                .buildMetadata()
                .buildSessionFactory();
    }
    @Before
    public void setUp() throws Exception {
       // setting up DAO with our session factory
       mCountriesDaoImplementation = new
               CountriesDaoImplementation(mSessionFactory);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void savingCountryWorks() throws Exception {
        // Given empty testing database and test country with code "ABC"
        String countryCode = "ABC";
        Country country = new CountryBuilder(countryCode)
                .withName("Country")
                .withAdultLiteracyRate(1.0)
                .withInternetUsers(1.0)
                .build();
        // When we save a country
        String codeFromSave = mCountriesDaoImplementation.save(country);
        // Then code of country should be returned
        assertEquals(countryCode, codeFromSave);
    }
}