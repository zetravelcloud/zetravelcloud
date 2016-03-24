package com.zetravelcloud.webapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Proposal.
 */
@Entity
@Table(name = "proposal")
public class Proposal implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "checkin")
    private LocalDate checkin;
    
    @Column(name = "checkout")
    private LocalDate checkout;
    
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne
    @JoinColumn(name = "travel_request_id")
    private TravelRequest travelRequest;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name = "proposal_id")
    private Set<ProposedRoom> proposedRooms = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCheckin() {
        return checkin;
    }
    
    public void setCheckin(LocalDate checkin) {
        this.checkin = checkin;
    }

    public LocalDate getCheckout() {
        return checkout;
    }
    
    public void setCheckout(LocalDate checkout) {
        this.checkout = checkout;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public TravelRequest getTravelRequest() {
        return travelRequest;
    }

    public void setTravelRequest(TravelRequest travelRequest) {
        this.travelRequest = travelRequest;
    }

    public Set<ProposedRoom> getProposedRooms() {
        return proposedRooms;
    }

    public void setProposedRooms(Set<ProposedRoom> proposedRooms) {
        this.proposedRooms = proposedRooms;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Proposal proposal = (Proposal) o;
        if(proposal.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, proposal.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Proposal{" +
            "id=" + id +
            ", checkin='" + checkin + "'" +
            ", checkout='" + checkout + "'" +
            '}';
    }
}
