package com.techdegree.hibernate;

import com.techdegree.hibernate.dao.CountriesDaoImplementation;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

public class Main {
    // Hold a reusable reference to a session factory
    private static final SessionFactory sessionFactory =
            buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        // Create a standard service registry object
        final ServiceRegistry serviceRegistry =
                new StandardServiceRegistryBuilder()
                        .configure()
                        .build();
        return new MetadataSources(serviceRegistry)
                .buildMetadata()
                .buildSessionFactory();
    }
    //  create DAO object with all save, find and view methods and pass
    // created session factory there
    protected static CountriesDaoImplementation mCountriesDaoImplementation =
            new CountriesDaoImplementation(sessionFactory);

    public static void main(String[] args) {
        mCountriesDaoImplementation.findAll();
    }
}
