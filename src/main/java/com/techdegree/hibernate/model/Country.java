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
        mCode = countryBuilder.mCode;
        mName = countryBuilder.mName;
        mInternetUsers = countryBuilder.mInternetUsers;
        mAdultLiteracyRate = countryBuilder.mAdultLiteracyRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Country country = (Country) o;

        if (mCode != null ? !mCode.equals(country.mCode) : country.mCode != null)
            return false;
        return mName != null ? mName.equals(country.mName) : country.mName == null;

    }

    @Override
    public int hashCode() {
        int result = mCode != null ? mCode.hashCode() : 0;
        result = 31 * result + (mName != null ? mName.hashCode() : 0);
        return result;
    }

    private String getStringFromDecimalToPrint(Double decimal) {
        if (decimal == null) {
            return "--";
        } else {
            return String.format("%15.2f", decimal);
        }
    }

    @Override
    public String toString() {
        return String.format("%7s %40s %15s %15s%n",
                mCode,
                mName,
                getStringFromDecimalToPrint(mInternetUsers),
                getStringFromDecimalToPrint(mAdultLiteracyRate));
    }
    // builder object
    public static class CountryBuilder {
        private String mCode;
        private String mName;
        private Double mInternetUsers;
        private Double mAdultLiteracyRate;
        public CountryBuilder(String code) {
            mCode = code;
        }
        public CountryBuilder withName(String name) {
            mName = name;
            return this;
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
