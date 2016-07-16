package com.techdegree.hibernate.dao;

import com.techdegree.hibernate.model.Country;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.OptionalDouble;

public class CountriesDaoImplementation implements CountriesDao {
    // session factory is created outside of this class, so that we can
    // have control which database to use: testing or real one
    // It could be done better probably, I just didn't found how.
    private SessionFactory mSessionFactory;

    // constructor
    public CountriesDaoImplementation(SessionFactory sessionFactory) {
        mSessionFactory = sessionFactory;
    }
    @Override
    @SuppressWarnings("unchecked")
    public List<Country> findAll() {
        // Open a session
        Session session = mSessionFactory.openSession();
        // Create criteria
        Criteria criteria = session.createCriteria(Country.class);
        // Use the criteria to fetch list of all countries
        List<Country> countries  = criteria.list();
        // Close session
        session.close();
        // return list of all countries in database
        return countries;
    }

    @Override
    public String save(Country country) {
        // Open a session
        Session session = mSessionFactory.openSession();
        // Begin a transaction
        session.beginTransaction();
        // Use the session to save
        String code = (String) session.save(country);
        // Commit transaction
        session.getTransaction().commit();
        // Close session
        session.close();
        // return code of newly created object in database
        return code;
    }

    @Override
    public Country findCountryByCode(String code) {
        // Open a session
        Session session = mSessionFactory.openSession();
        // Use the session to get by code
        Country country = session.get(Country.class, code);
        // Close session
        session.close();
        // return found country
        return country;
    }

    @Override
    public void update(Country country) {
        // Open a session
        Session session = mSessionFactory.openSession();
        // Begin a transaction
        session.beginTransaction();
        // Use the session to update
        session.update(country);
        // Commit transaction
        session.getTransaction().commit();
        // Close session
        session.close();
    }

    @Override
    public void delete(Country country) {
        // Open a session
        Session session = mSessionFactory.openSession();
        // Begin a transaction
        session.beginTransaction();
        // Use the session to delete
        session.delete(country);
        // Commit transaction
        session.getTransaction().commit();
        // Close session
        session.close();
    }

    // The rest 5 methods are used to calculate statistics, they are subject
    // to be moved to other layer, because this is database access layer
    // but for now I'll leave them here, to have working prototype

    public Double getMinimumAdultLiteracy() {
        OptionalDouble minimumAdultLiteracyRate = findAll()
                .stream()
                .filter(country -> country.getAdultLiteracyRate() != null)
                .mapToDouble(Country::getAdultLiteracyRate)
                .min();
        if (minimumAdultLiteracyRate.isPresent()) {
            return minimumAdultLiteracyRate.getAsDouble();
        } else {
            return null;
        }
    }
    public Double getMaximumAdultLiteracy() {
        OptionalDouble maximumAdultLiteracyRate = findAll()
                .stream()
                .filter(country -> country.getAdultLiteracyRate() != null)
                .mapToDouble(Country::getAdultLiteracyRate)
                .max();
        if (maximumAdultLiteracyRate.isPresent()) {
            return maximumAdultLiteracyRate.getAsDouble();
        } else {
            return null;
        }
    }
    public Double getMinimumInternetUsers() {
        OptionalDouble minimumInternetUsers = findAll()
                .stream()
                .filter(country -> country.getInternetUsers() != null)
                .mapToDouble(Country::getInternetUsers)
                .min();
        if (minimumInternetUsers.isPresent()) {
            return minimumInternetUsers.getAsDouble();
        } else {
            return null;
        }
    }
    public Double getMaximumInternetUsers() {
        OptionalDouble maximumInternetUsers = findAll()
                .stream()
                .filter(country -> country.getInternetUsers() != null)
                .mapToDouble(Country::getInternetUsers)
                .max();
        if (maximumInternetUsers.isPresent()) {
            return maximumInternetUsers.getAsDouble();
        } else {
            return null;
        }
    }
    // get correlation coefficient using apache.math library and
    // PearsonsCorrelation method.
    public Double getCorrelationCoefficient() {
        // create double arrays as input for correlation calculator
        // by filtering only non-null values in both decimals
        double[] internetUsers = findAll()
                .stream()
                .filter(country -> country.getInternetUsers() != null)
                .filter(country -> country.getAdultLiteracyRate() != null)
                .mapToDouble(Country::getInternetUsers)
                .toArray();
        double[] adultLiteracyRate = findAll()
                .stream()
                .filter(country -> country.getInternetUsers() != null)
                .filter(country -> country.getAdultLiteracyRate() != null)
                .mapToDouble(Country::getAdultLiteracyRate)
                .toArray();
        // actual correlation calculation
        return new PearsonsCorrelation()
                .correlation(internetUsers, adultLiteracyRate);
    }

}
