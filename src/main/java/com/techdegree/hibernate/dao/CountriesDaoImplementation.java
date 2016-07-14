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
    public List<Country> findAll() {
        // Open a session
        Session session = mSessionFactory.openSession();
        // Create criteria
        Criteria criteria = session.createCriteria(Country.class);
        // Use the session to save
        List<Country> countries  = criteria.list();
        // Close session
        session.close();
        // return id of newly created object in database
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
        // return id of newly created object in database
        return code;
    }
}
