package com.techdegree.hibernate.dao;

import com.techdegree.hibernate.model.Country;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class CountriesDaoImplementation implements CountriesDao {
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
        // return id of newly created object in database
        return countries;
    }

    @Override
    public String add(Country country) {
        // Open a session
        Session session = mSessionFactory.openSession();
        // Begin a transaction
        session.beginTransaction();
        // Use the session to add
        String code = (String) session.save(country);
        // Commit transaction
        session.getTransaction().commit();
        // Close session
        session.close();
        // return id of newly created object in database
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


}
