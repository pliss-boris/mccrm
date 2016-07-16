package com.bd.mc.crm.domain;

import com.bd.mc.crm.domain.config.SchemasConfig;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Lre.
 */
@Entity
@Table(name = "lre", schema = SchemasConfig.CRM)
public class Lre implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @OneToMany(mappedBy = "lre")
    @JsonIgnore
    private Set<Customer> customers = new HashSet<>();

    @OneToMany(mappedBy = "lre")
    @JsonIgnore
    private Set<ClassOfService> classOfServices = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(Set<Customer> customers) {
        this.customers = customers;
    }

    public Set<ClassOfService> getClassOfServices() {
        return classOfServices;
    }

    public void setClassOfServices(Set<ClassOfService> classOfServices) {
        this.classOfServices = classOfServices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Lre lre = (Lre) o;
        if(lre.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, lre.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Lre{" +
            "id=" + id +
            ", description='" + description + "'" +
            '}';
    }
}
