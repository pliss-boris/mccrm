package com.bd.mc.crm.domain;


import com.bd.mc.crm.domain.config.SchemasConfig;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Remark.
 */
@Entity
@Table(name = "remark", schema = SchemasConfig.CRM)
public class Remark implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 2048)
    @Column(name = "remark", length = 2048)
    private String remark;

    @Column(name = "type")
    private String type;

    @ManyToOne
    private Communication communication;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Communication getCommunication() {
        return communication;
    }

    public void setCommunication(Communication communication) {
        this.communication = communication;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Remark remark = (Remark) o;
        if(remark.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, remark.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Remark{" +
            "id=" + id +
            ", remark='" + remark + "'" +
            ", type='" + type + "'" +
            '}';
    }
}
