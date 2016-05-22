package com.zetravelcloud.webapp.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A OfferedServiceType.
 */
@Entity
@Table(name = "offered_service_type")
public class OfferedServiceType implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OfferedServiceType offeredServiceType = (OfferedServiceType) o;
        if(offeredServiceType.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, offeredServiceType.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "OfferedServiceType{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
