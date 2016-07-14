package com.techdegree.hibernate.model;

import javax.persistence.*;

@Entity
@Table(name = "COUNTRY")
public class Country {
    @Id
    @Column(name = "CODE", columnDefinition = "VARCHAR(3)")
    private String mCode;
    @Column(name = "NAME", columnDefinition = "VARCHAR(32)")
    private String mName;
    @Column(name = "INTERNETUSERS", columnDefinition = "DECIMAL(11,8)")
    private Double mInternetUsers;
    @Column(name = "ADULTLITERACYRATE", columnDefinition = "DECIMAL(11,8)")
    private Double mAdultLiteracyRate;

    public String getCode() {
        return mCode;
    }

    public void setCode(String code) {
        this.mCode = code;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public Double getInternetUsers() {
        return mInternetUsers;
    }

    public void setInternetUsers(Double internetUsers) {
        this.mInternetUsers = internetUsers;
    }

    public Double getAdultLiteracyRate() {
        return mAdultLiteracyRate;
    }

    public void setAdultLiteracyRate(Double adultLiteracyRate) {
        this.mAdultLiteracyRate = adultLiteracyRate;
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
                "id = " + mCode +
                ", name = '" + mName + '\'' +
                ", internetUsers = " + mInternetUsers +
                ", adultLiteracyRate = " + mAdultLiteracyRate +
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
