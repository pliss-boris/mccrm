package com.bd.mc.crm.domain;


import com.bd.mc.crm.domain.config.SchemasConfig;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A SubscriberWalletProperty.
 */
@Entity
@Table(name = "subscriber_wallet_property", schema = SchemasConfig.CRM)
public class SubscriberWalletProperty implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "balance")
    private Double balance;

    @Column(name = "created")
    private ZonedDateTime created;

    @ManyToOne
    private SubscriberWallet subscriberWallet;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public SubscriberWallet getSubscriberWallet() {
        return subscriberWallet;
    }

    public void setSubscriberWallet(SubscriberWallet subscriberWallet) {
        this.subscriberWallet = subscriberWallet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SubscriberWalletProperty subscriberWalletProperty = (SubscriberWalletProperty) o;
        if(subscriberWalletProperty.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, subscriberWalletProperty.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SubscriberWalletProperty{" +
            "id=" + id +
            ", balance='" + balance + "'" +
            ", created='" + created + "'" +
            '}';
    }
}
