package com.techdegree.hibernate;

import com.techdegree.hibernate.controller.DatabaseMenuPrompter;
import com.techdegree.hibernate.dao.CountriesDao;
import com.techdegree.hibernate.dao.CountriesDaoImplementation;

import java.io.IOException;

public class Main {
    //  create DAO object with all save, find and view methods and pass
    // created session factory there
    protected static CountriesDao countriesDao =
            new CountriesDaoImplementation("hibernate.cfg.xml");

    public static void main(String[] args) throws IOException {
        // create our main menu object, and pass DAO with database there
        DatabaseMenuPrompter databaseMenuPrompter =
                new DatabaseMenuPrompter(countriesDao);
        // run the menu
        databaseMenuPrompter.presentMenuWithPossibleOptions();
        // close session factory
        countriesDao.close();
    }
}
