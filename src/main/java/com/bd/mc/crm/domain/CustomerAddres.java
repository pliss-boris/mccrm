package com.bd.mc.crm.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import com.bd.mc.crm.domain.enumeration.AdressType;

/**
 * A CustomerAddres.
 */
@Entity
@Table(name = "customer_addres")
public class CustomerAddres implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "adress_type", nullable = false)
    private AdressType adressType;

    @NotNull
    @Size(max = 64)
    @Column(name = "country", length = 64, nullable = false)
    private String country;

    @Size(max = 64)
    @Column(name = "city", length = 64)
    private String city;

    @Size(max = 64)
    @Column(name = "home", length = 64)
    private String home;

    @Size(max = 16)
    @Column(name = "home_letter", length = 16)
    private String homeLetter;

    @Size(max = 64)
    @Column(name = "flat", length = 64)
    private String flat;

    @Size(max = 64)
    @Column(name = "zip", length = 64)
    private String zip;

    @Column(name = "is_active")
    private Boolean isActive;

    @ManyToOne
    private Customer customer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AdressType getAdressType() {
        return adressType;
    }

    public void setAdressType(AdressType adressType) {
        this.adressType = adressType;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getHomeLetter() {
        return homeLetter;
    }

    public void setHomeLetter(String homeLetter) {
        this.homeLetter = homeLetter;
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public Boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CustomerAddres customerAddres = (CustomerAddres) o;
        if(customerAddres.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, customerAddres.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CustomerAddres{" +
            "id=" + id +
            ", adressType='" + adressType + "'" +
            ", country='" + country + "'" +
            ", city='" + city + "'" +
            ", home='" + home + "'" +
            ", homeLetter='" + homeLetter + "'" +
            ", flat='" + flat + "'" +
            ", zip='" + zip + "'" +
            ", isActive='" + isActive + "'" +
            '}';
    }
}
