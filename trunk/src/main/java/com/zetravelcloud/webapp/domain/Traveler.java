package com.zetravelcloud.webapp.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

    @ManyToMany(mappedBy="travelers")
    private List<TravelRequest> travelRequests;

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

    public List<TravelRequest> getTravelRequests() {
		return travelRequests;
	}

	public void setTravelRequests(List<TravelRequest> travelRequests) {
		this.travelRequests = travelRequests;
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
