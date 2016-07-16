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
 * A Communication.
 */
@Entity
@Table(name = "communication", schema = SchemasConfig.CRM)
public class Communication implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "contact_number")
    private String contactNumber;

    @Column(name = "contact_person")
    private String contactPerson;

    @Column(name = "contact_descripton")
    private String contactDescripton;

    @Column(name = "created")
    private ZonedDateTime created;

    @ManyToOne
    private Customer customer;

    @OneToMany(mappedBy = "communication")
    @JsonIgnore
    private Set<Remark> remarks = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactDescripton() {
        return contactDescripton;
    }

    public void setContactDescripton(String contactDescripton) {
        this.contactDescripton = contactDescripton;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Set<Remark> getRemarks() {
        return remarks;
    }

    public void setRemarks(Set<Remark> remarks) {
        this.remarks = remarks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Communication communication = (Communication) o;
        if(communication.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, communication.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Communication{" +
            "id=" + id +
            ", title='" + title + "'" +
            ", contactNumber='" + contactNumber + "'" +
            ", contactPerson='" + contactPerson + "'" +
            ", contactDescripton='" + contactDescripton + "'" +
            ", created='" + created + "'" +
            '}';
    }
}
