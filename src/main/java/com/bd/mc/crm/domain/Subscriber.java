package com.bd.mc.crm.domain;

import com.bd.mc.crm.domain.config.SchemasConfig;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Subscriber.
 */
@Entity
@Table(name = "subscriber", schema = SchemasConfig.CRM)
public class Subscriber implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "subscriber_type")
    private String subscriberType;

    @Column(name = "subscriber_payment_class")
    private Integer subscriberPaymentClass;

    @Column(name = "creation_date")
    private ZonedDateTime creationDate;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private ClassOfService classOfService;

    @OneToMany(mappedBy = "subscriber")
    @JsonIgnore
    private Set<SubscriberWallet> subscriberWallets = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubscriberType() {
        return subscriberType;
    }

    public void setSubscriberType(String subscriberType) {
        this.subscriberType = subscriberType;
    }

    public Integer getSubscriberPaymentClass() {
        return subscriberPaymentClass;
    }

    public void setSubscriberPaymentClass(Integer subscriberPaymentClass) {
        this.subscriberPaymentClass = subscriberPaymentClass;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public ClassOfService getClassOfService() {
        return classOfService;
    }

    public void setClassOfService(ClassOfService classOfService) {
        this.classOfService = classOfService;
    }

    public Set<SubscriberWallet> getSubscriberWallets() {
        return subscriberWallets;
    }

    public void setSubscriberWallets(Set<SubscriberWallet> subscriberWallets) {
        this.subscriberWallets = subscriberWallets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Subscriber subscriber = (Subscriber) o;
        if(subscriber.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, subscriber.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Subscriber{" +
            "id=" + id +
            ", subscriberType='" + subscriberType + "'" +
            ", subscriberPaymentClass='" + subscriberPaymentClass + "'" +
            ", creationDate='" + creationDate + "'" +
            '}';
    }
}
