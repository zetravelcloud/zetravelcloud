package com.zetravelcloud.webapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Traveler.
 */
@Entity
@Table(name = "traveler")
public class Traveler implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "phone")
    private String phone;
    
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    
    @OneToMany(mappedBy = "traveler")
    @JsonIgnore
    private Set<TravelerFile> travelerFiles = new HashSet<>();

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

    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Set<TravelerFile> getTravelerFiles() {
        return travelerFiles;
    }

    public void setTravelerFiles(Set<TravelerFile> travelerFiles) {
        this.travelerFiles = travelerFiles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Traveler traveler = (Traveler) o;
        if(traveler.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, traveler.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Traveler{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", email='" + email + "'" +
            ", phone='" + phone + "'" +
            ", dateOfBirth='" + dateOfBirth + "'" +
            '}';
    }
}
