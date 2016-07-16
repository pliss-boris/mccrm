package com.bd.mc.crm.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.bd.mc.crm.domain.enumeration.CustomerType;

/**
 * A Customer.
 */
@Entity
@Table(name = "customer", schema = "crm")
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 64)
    @Column(name = "first_name", length = 64)
    private String firstName;

    @Size(max = 64)
    @Column(name = "last_name", length = 64)
    private String lastName;

    @Size(max = 64)
    @Column(name = "middle_name", length = 64)
    private String middleName;

    @Enumerated(EnumType.STRING)
    @Column(name = "customer_type")
    private CustomerType customerType;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @ManyToOne
    private Lre lre;

    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    private Set<CustomerAddres> customerAddres = new HashSet<>();

    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    private Set<CustomerContact> customerContacts = new HashSet<>();

    @OneToMany(mappedBy = "parentCustomer")
    @JsonIgnore
    private Set<Customer> customers = new HashSet<>();

    @ManyToOne
    private Customer parentCustomer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Lre getLre() {
        return lre;
    }

    public void setLre(Lre lre) {
        this.lre = lre;
    }

    public Set<CustomerAddres> getCustomerAddres() {
        return customerAddres;
    }

    public void setCustomerAddres(Set<CustomerAddres> customerAddres) {
        this.customerAddres = customerAddres;
    }

    public Set<CustomerContact> getCustomerContacts() {
        return customerContacts;
    }

    public void setCustomerContacts(Set<CustomerContact> customerContacts) {
        this.customerContacts = customerContacts;
    }

    public Set<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(Set<Customer> customers) {
        this.customers = customers;
    }

    public Customer getParentCustomer() {
        return parentCustomer;
    }

    public void setParentCustomer(Customer customer) {
        this.parentCustomer = customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Customer customer = (Customer) o;
        if(customer.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Customer{" +
            "id=" + id +
            ", firstName='" + firstName + "'" +
            ", lastName='" + lastName + "'" +
            ", middleName='" + middleName + "'" +
            ", customerType='" + customerType + "'" +
            ", creationDate='" + creationDate + "'" +
            '}';
    }
}
