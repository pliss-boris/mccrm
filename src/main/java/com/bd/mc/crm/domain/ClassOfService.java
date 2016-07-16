package com.bd.mc.crm.domain;

import com.bd.mc.crm.domain.config.SchemasConfig;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ClassOfService.
 */
@Entity
@Table(name = "class_of_service", schema = SchemasConfig.BILLING)
public class ClassOfService implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 256)
    @Column(name = "description", length = 256)
    private String description;

    @Column(name = "created")
    private ZonedDateTime created;

    @ManyToOne
    private Lre lre;

    @OneToMany(mappedBy = "classOfService")
    @JsonIgnore
    private Set<Subscriber> subscribers = new HashSet<>();

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

    public ZonedDateTime getCreated() {
        return created;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public Lre getLre() {
        return lre;
    }

    public void setLre(Lre lre) {
        this.lre = lre;
    }

    public Set<Subscriber> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(Set<Subscriber> subscribers) {
        this.subscribers = subscribers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ClassOfService classOfService = (ClassOfService) o;
        if(classOfService.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, classOfService.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ClassOfService{" +
            "id=" + id +
            ", description='" + description + "'" +
            ", created='" + created + "'" +
            '}';
    }
}
