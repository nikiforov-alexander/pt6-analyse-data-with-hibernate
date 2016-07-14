package com.techdegree.hibernate.model;

import javax.persistence.*;
import javax.xml.stream.StreamFilter;

@Entity
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mId;
    @Column
    private String mName;
    @Column
    private Double mInternetUsers;
    @Column
    private Double mAdultLiteracyRate;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Double getInternetUsers() {
        return mInternetUsers;
    }

    public void setInternetUsers(Double internetUsers) {
        mInternetUsers = internetUsers;
    }

    public Double getAdultLiteracyRate() {
        return mAdultLiteracyRate;
    }

    public void setAdultLiteracyRate(Double adultLiteracyRate) {
        mAdultLiteracyRate = adultLiteracyRate;
    }


    // default constructor for JPA
    public Country() {
    }

    public Country(CountryBuilder countryBuilder) {
        mName = countryBuilder.mName;
        mInternetUsers = countryBuilder.mInternetUsers;
        mAdultLiteracyRate = countryBuilder.mAdultLiteracyRate;
    }

    @Override
    public String toString() {
        return "Country { " +
                "mId = " + mId +
                ", mName = '" + mName + '\'' +
                ", mInternetUsers = " + mInternetUsers +
                ", mAdultLiteracyRate = " + mAdultLiteracyRate +
                " }";
    }
    // builder object
    public static class CountryBuilder {
        private String mName;
        private Double mInternetUsers;
        private Double mAdultLiteracyRate;
        public CountryBuilder(String name) {
            mName = name;
        }
        public CountryBuilder withInternetUsers(Double internetUsers) {
            mInternetUsers = internetUsers;
            return this;
        }
        public CountryBuilder withAdultLiteracyRate(Double adultLiteracyRate) {
            mAdultLiteracyRate = adultLiteracyRate;
            return this;
        }
        public Country build() {
            return new Country(this);
        }
    }
}
