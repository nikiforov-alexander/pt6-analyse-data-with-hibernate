package com.techdegree.hibernate;

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
                new StandardServiceRegistryBuilder().configure().build();
        return new MetadataSources(serviceRegistry)
                .buildMetadata().buildSessionFactory();
    }

    public static void main(String[] args) {

    }
}
