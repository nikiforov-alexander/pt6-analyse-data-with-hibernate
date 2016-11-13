package com.techdegree.hibernate.dao;

import com.techdegree.hibernate.model.Country;
import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import java.util.List;
import java.util.Optional;
import java.util.function.*;

public class CountriesDaoImplementation implements CountriesDao {
    // final session factory
    private final SessionFactory mSessionFactory;
    // database configuration file, sets in constructor
    private final String mHibernateConfigurationFile;

    // build session factory, used in constructor
    private SessionFactory buildSessionFactory() {
        // Create a standard service registry object
        final ServiceRegistry serviceRegistry =
                new StandardServiceRegistryBuilder()
                        .configure(mHibernateConfigurationFile)
                        .build();
        return new MetadataSources(serviceRegistry)
                .buildMetadata()
                .buildSessionFactory();
    }
    // constructor
    public CountriesDaoImplementation(String hibernateConfigurationFile) {
        mHibernateConfigurationFile = hibernateConfigurationFile;
        mSessionFactory = buildSessionFactory();
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

    /**
     * finds minimum value from all countries {@code findAll}
     * method, using Java 8 Functional Interfaces and streams.
     * It is private, but is used in all simple getters, see
     * below, to hide complexity for user. It is just like
     * its brother @see #getMaximumValueFor, but I don't know how
     * to generalize them.
     * @param countryPropertyGetterFunction : this is function that
     *                                      is simply getter of the parameter
     *                                      minimum of which we seek
     * @param countryFilterPredicate : this is filter, checking that getter
     *                               values are not {@code null}. It is constant
     *                               for all methods, but I don't know how
     *                               to generalize it.
     * @return minimum {@code Double} value, or {@code null} otherwise
     */
    private Double getMinimumValueFor(
            Function<Country, Double> countryPropertyGetterFunction,
            Predicate<Country> countryFilterPredicate
    ) {
        Optional minimum = findAll()
                .stream()
                .filter(
                        countryFilterPredicate
                )
                .map(
                        countryPropertyGetterFunction
                )
                .min(Double::compareTo);
        if (minimum.isPresent()) {
            return (Double) minimum.get();
        } else {
            return null;
        }
    }

    /**
     * finds maximum value from all countries {@code findAll}
     * method, using Java 8 Functional Interfaces and streams.
     * It is private, but is used in all simple getters, see
     * below, to hide complexity for user. It is just like
     * its brother @see #getMinimumValueFor, but I don't know how
     * to generalize them.
     * @param countryPropertyGetterFunction : this is function that
     *                                      is simply getter of the parameter
     *                                      minimum of which we seek
     * @param countryFilterPredicate : this is filter, checking that getter
     *                               values are not {@code null}. It is constant
     *                               for all methods, but I don't know how
     *                               to generalize it.
     * @return maximum {@code Double} value, or {@code null} otherwise
     */
    private Double getMaximumValueFor(
            Function<Country, Double> countryPropertyGetterFunction,
            Predicate<Country> countryFilterPredicate
    ) {
        Optional maximum = findAll()
                .stream()
                .filter(countryFilterPredicate)
                .map(
                        countryPropertyGetterFunction
                )
                .max(Double::compareTo);
        if (maximum.isPresent()) {
            return (Double) maximum.get();
        } else {
            return null;
        }
    }

    /**
     * gets minimum adult literacy rate, re-uses generic
     * @see #getMinimumValueFor(Function, Predicate) method.
     * @return {@code Double} minimum adult literacy rate or
     * {@code null}
     */
    @Override
    public Double getMinimumAdultLiteracy() {
        return getMinimumValueFor(
                Country::getAdultLiteracyRate,
                country -> country.getAdultLiteracyRate() != null
        );
    }

    /**
     * gets maximum adult literacy rate, re-uses generic
     * @see #getMaximumValueFor(Function, Predicate) method.
     * @return {@code Double} maximum adult literacy rate or
     * {@code null}
     */
    @Override
    public Double getMaximumAdultLiteracy() {
        return getMaximumValueFor(
                Country::getAdultLiteracyRate,
                country -> country.getAdultLiteracyRate() != null
        );
    }

    /**
     * gets minimum internet users, re-uses generic
     * @see #getMinimumValueFor(Function, Predicate) method.
     * @return {@code Double} minimum internet users or
     * {@code null}
     */
    @Override
    public Double getMinimumInternetUsers() {
        return getMinimumValueFor(
                Country::getInternetUsers,
                country -> country.getInternetUsers() != null
        );
    }

    /**
     * gets maximum internet users, re-uses generic
     * @see #getMaximumValueFor(Function, Predicate) method.
     * @return {@code Double} maximum internet users or
     * {@code null}
     */
    @Override
    public Double getMaximumInternetUsers() {
        return getMaximumValueFor(
                Country::getInternetUsers,
                country -> country.getInternetUsers() != null
        );
    }


    // get correlation coefficient using apache.math library and
    // PearsonsCorrelation method.
    @Override
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
        Double correlation;
        try {
            correlation = new PearsonsCorrelation()
                    .correlation(internetUsers, adultLiteracyRate);
        } catch (MathIllegalArgumentException iae) {
            iae.printStackTrace();
            System.out.println("Adult literacy rate and internet user arrays" +
                    "are too small for the correlation to be calculated");
            return null;
        }
        return correlation;
    }

    @Override
    public void close() {
        mSessionFactory.close();
    }
}
