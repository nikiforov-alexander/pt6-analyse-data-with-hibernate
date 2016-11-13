package com.techdegree.hibernate.dao;

import com.techdegree.hibernate.model.Country;
import com.techdegree.hibernate.model.Country.CountryBuilder;
import org.hibernate.exception.DataException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CountriesDaoImplTest {

    // test configuration file
    private static final String TEST_CONFIGURATION_FILE = "hibernate-test.cfg.xml";

    // Dao with all CRUDs we test
    private CountriesDao countriesDao;

    // Test country with ABC code
    private Country mTestCountryWithAbcCode;

    // before each test we set up database, by opening session factory
    @Before
    public void setUp() throws Exception {
       // setting up DAO with our session factory
       countriesDao = new
               CountriesDaoImplementation(TEST_CONFIGURATION_FILE);
    }
    // and after each test we close session factory
    @After
    public void tearDown() throws Exception {
        countriesDao.close();
    }

    private void addTestCountryToDatabase() {
        mTestCountryWithAbcCode = new CountryBuilder("ABC")
                .withName("Country")
                .withAdultLiteracyRate(null)
                .withInternetUsers(1.0)
                .build();
       countriesDao.save(mTestCountryWithAbcCode);
    }

    @Test
    public void findAllReturnsOneTestCountryWhenWeAddIt() throws Exception {
        // Given database with one country
        addTestCountryToDatabase();

        // When we fetch all countries
        List<Country> listOfAllCountries =
                countriesDao.findAll();

        // Then size of list should be one
        assertThat(listOfAllCountries.size())
                .isEqualTo(1);
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
        String codeFromSave = countriesDao
                .save(testCountryWithAbcCode);
        // Then code of country should be returned
        assertThat(codeFromSave).isEqualTo("ABC");
    }

    @Test
    public void getCountryByCodeReturnsCorrectCountry() throws Exception {
        // Given testing database with ABC country
        addTestCountryToDatabase();

        // When we try to find country by code
        Country foundCountry = countriesDao
                .findCountryByCode("ABC");

        // Then obtained country should be equal to our test country
        assertThat(mTestCountryWithAbcCode)
                .isEqualTo(foundCountry);
    }

    @Test
    public void updateCountryByChangingCodeUpdatesTheCountry() throws Exception {

        // Given testing database with ABC country,
        addTestCountryToDatabase();

        // When we set new name and update database
        mTestCountryWithAbcCode.setName("New Country Name");
        countriesDao.update(mTestCountryWithAbcCode);

        // Then country's name fetched from database should be equal to
        // new name
        assertThat(
                countriesDao.findCountryByCode("ABC")
                )
                .hasFieldOrPropertyWithValue("name", "New Country Name");
    }

    @Test
    public void deleteCountrySetsSizeToZero() throws Exception {
        // Given testing database with ABC country
        addTestCountryToDatabase();

        // When we delete only entry from database
        countriesDao.delete(mTestCountryWithAbcCode);

        // Then size of the list of all countries should be zero
        assertThat(
                countriesDao.findAll().size()
        ).isEqualTo(0);
    }

    @Test(expected = DataException.class)
    public void
    decimalsWithMoreThanThreeNumberInIntegerPartWithoutTrailingZerosAreNotAcceptedByDatabaseSave()
            throws Exception {
        // Given testing database with no countries

        // When we add decimal with 4 numbers in integer part
        // and save country
        Country country = new Country.CountryBuilder("ABC")
                .withName("Country")
                .withAdultLiteracyRate(1234.)
                .withInternetUsers(null)
                .build();

        countriesDao.save(country);
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
        countriesDao.save(country);

        // Then these decimals are accepted by truncating
        assertThat(
                countriesDao.findAll().size()
        ).isEqualTo(1);
    }

    @Test
    public void allStatMethodsReturnNullWithEmptyDao()
            throws Exception {
        // Given empty db and DAO
        // When we get
        // - MinimumAdultLiteracy
        // - MaximumAdultLiteracy
        // - MinimumInternetUsers
        // - MaximumInternetUsers
        // - CorrelationCoefficient
        // Then null should be returned

        assertThat(
                countriesDao.getMinimumAdultLiteracy()
        ).isNull();
        assertThat(
                countriesDao.getMinimumAdultLiteracy()
        ).isNull();

        assertThat(
                countriesDao.getMinimumInternetUsers()
        ).isNull();
        assertThat(
                countriesDao.getMaximumInternetUsers()
        ).isNull();

        assertThat(
                countriesDao.getCorrelationCoefficient()
        ).isNull();
    }

    @Test
    public void properMinAndMaxValuesAreReturnedForAdultLiteracyAndInternetUsers()
            throws Exception {
        // Given dao with three countries that have
        // 1.00 as adultLiteracy, and internetUsers
        // null as adultLiteracy, and internetUsers
        // 2.00 as adultLiteracy, and internetUsers
        Country firstCountry =
                new Country.CountryBuilder("AAA")
                .withAdultLiteracyRate(1.00)
                .withInternetUsers(1.00)
                .withName("first country")
                .build();
        Country secondCountry =
                new Country.CountryBuilder("BBB")
                        .withAdultLiteracyRate(null)
                        .withInternetUsers(null)
                        .withName("second country")
                        .build();
        Country thirdCountry =
                new Country.CountryBuilder("CCC")
                        .withAdultLiteracyRate(2.0)
                        .withInternetUsers(2.0)
                        .withName("second country")
                        .build();
        countriesDao.save(firstCountry);
        countriesDao.save(secondCountry);
        countriesDao.save(thirdCountry);

        // When we get minimum adultLiteracyRate and internetUsers
        // Then min adultLiteracyRate and internetUsers should be 1.0
        // and max adultLiteracyRate and internetUsers should be 2.0
        assertThat(
                countriesDao.getMinimumAdultLiteracy()
        ).isEqualTo(1.00);
        assertThat(
                countriesDao.getMaximumAdultLiteracy()
        ).isEqualTo(2.00);
        assertThat(
                countriesDao.getMinimumInternetUsers()
        ).isEqualTo(1.00);
        assertThat(
                countriesDao.getMaximumInternetUsers()
        ).isEqualTo(2.00);
    }
}