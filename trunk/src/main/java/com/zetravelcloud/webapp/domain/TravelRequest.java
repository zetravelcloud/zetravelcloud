package com.zetravelcloud.webapp.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * A TravelRequest.
 */
@Entity
@Table(name = "travel_request")
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class TravelRequest implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "title")
    private String title;
    
    @Column(name = "description")
    private String description;
    
    @NotNull
    @Column(name = "checkin", nullable = false)
    private LocalDate checkin;
    
    @NotNull
    @Column(name = "checkout", nullable = false)
    private LocalDate checkout;
    
    @Column(name = "date")
    private ZonedDateTime date;
    
    @Column(name = "file_id")
    private String fileId;
    
    @Column(name = "date_sent_to_accounting")
    private ZonedDateTime dateSentToAccounting;
    
    @Column(name = "status")
    private String status;
    
    @Column(name = "payment_type")
    private String paymentType;
    
    @Column(name = "num_of_adults")
    private Integer numOfAdults;
    
    @Column(name = "num_ofchildren")
    private Integer numOfchildren;
    
    @Column(name = "destination")
    private String destination;
    
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "created_by_id")
    private User createdBy;

    @ManyToMany
    @JoinTable(
        name="travel_req_traveler",
        joinColumns=@JoinColumn(name="travel_req_id", referencedColumnName="ID"),
        inverseJoinColumns=@JoinColumn(name="traveler_id", referencedColumnName="ID"))
    private Set<Traveler> travelers;

    @OneToMany(mappedBy = "travelRequest")
    private Set<OfferedService> offeredServices;

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

    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
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

    public ZonedDateTime getDate() {
        return date;
    }
    
    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public String getFileId() {
        return fileId;
    }
    
    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public ZonedDateTime getDateSentToAccounting() {
        return dateSentToAccounting;
    }
    
    public void setDateSentToAccounting(ZonedDateTime dateSentToAccounting) {
        this.dateSentToAccounting = dateSentToAccounting;
    }

    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentType() {
        return paymentType;
    }
    
    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public Integer getNumOfAdults() {
        return numOfAdults;
    }
    
    public void setNumOfAdults(Integer numOfAdults) {
        this.numOfAdults = numOfAdults;
    }

    public Integer getNumOfchildren() {
        return numOfchildren;
    }
    
    public void setNumOfchildren(Integer numOfchildren) {
        this.numOfchildren = numOfchildren;
    }

    public String getDestination() {
        return destination;
    }
    
    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User user) {
        this.createdBy = user;
    }

    public Set<Traveler> getTravelers() {
		return travelers;
	}

	public void setTravelers(Set<Traveler> travelers) {
		this.travelers = travelers;
	}

	public Set<OfferedService> getOfferedServices() {
		return offeredServices;
	}

	public void setOfferedServices(Set<OfferedService> offeredServices) {
		this.offeredServices = offeredServices;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TravelRequest travelRequest = (TravelRequest) o;
        if(travelRequest.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, travelRequest.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TravelRequest{" +
            "id=" + id +
            ", title='" + title + "'" +
            ", description='" + description + "'" +
            ", checkin='" + checkin + "'" +
            ", checkout='" + checkout + "'" +
            ", date='" + date + "'" +
            ", fileId='" + fileId + "'" +
            ", dateSentToAccounting='" + dateSentToAccounting + "'" +
            ", status='" + status + "'" +
            ", paymentType='" + paymentType + "'" +
            ", numOfAdults='" + numOfAdults + "'" +
            ", numOfchildren='" + numOfchildren + "'" +
            ", destination='" + destination + "'" +
            '}';
    }
}
