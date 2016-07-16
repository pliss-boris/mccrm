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
 * A SubscriberWallet.
 */
@Entity
@Table(name = "subscriber_wallet", schema = SchemasConfig.CRM)
public class SubscriberWallet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "activation_date")
    private ZonedDateTime activationDate;

    @Column(name = "expired")
    private Boolean expired;

    @Column(name = "created")
    private ZonedDateTime created;

    @ManyToOne
    private Subscriber subscriber;

    @OneToMany(mappedBy = "subscriberWallet")
    @JsonIgnore
    private Set<SubscriberWalletProperty> subscriberWalletProperties = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getActivationDate() {
        return activationDate;
    }

    public void setActivationDate(ZonedDateTime activationDate) {
        this.activationDate = activationDate;
    }

    public Boolean isExpired() {
        return expired;
    }

    public void setExpired(Boolean expired) {
        this.expired = expired;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public Subscriber getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
    }

    public Set<SubscriberWalletProperty> getSubscriberWalletProperties() {
        return subscriberWalletProperties;
    }

    public void setSubscriberWalletProperties(Set<SubscriberWalletProperty> subscriberWalletProperties) {
        this.subscriberWalletProperties = subscriberWalletProperties;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SubscriberWallet subscriberWallet = (SubscriberWallet) o;
        if(subscriberWallet.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, subscriberWallet.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SubscriberWallet{" +
            "id=" + id +
            ", activationDate='" + activationDate + "'" +
            ", expired='" + expired + "'" +
            ", created='" + created + "'" +
            '}';
    }
}
