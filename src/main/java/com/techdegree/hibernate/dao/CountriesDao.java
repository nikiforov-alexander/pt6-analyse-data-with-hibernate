package com.techdegree.hibernate.dao;

import com.techdegree.hibernate.model.Country;

import java.util.List;

public interface CountriesDao {
    public List<Country> findAll();
    public String add(Country country);
    public Country findCountryByCode(String code);
    public void update(Country country);
    public void delete(Country country);
}
