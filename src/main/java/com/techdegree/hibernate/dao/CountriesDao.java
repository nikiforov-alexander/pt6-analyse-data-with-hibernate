package com.techdegree.hibernate.dao;

import com.techdegree.hibernate.model.Country;

import java.util.List;

public interface CountriesDao {
    List<Country> findAll();
    String save(Country country);
    Country findCountryByCode(String code);
    void update(Country country);
    void delete(Country country);
    void close();
}
