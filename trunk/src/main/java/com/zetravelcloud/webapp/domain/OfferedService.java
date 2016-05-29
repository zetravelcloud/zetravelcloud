package com.zetravelcloud.webapp.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * A OfferedService.
 */
@Entity
@Table(name = "offered_service")
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class OfferedService implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "selling_price", precision=10, scale=2)
    private BigDecimal sellingPrice;
    
    @Column(name = "cost", precision=10, scale=2)
    private BigDecimal cost;
    
    @Column(name = "details_id")
    private Long detailsId;
    
    @Column(name = "confirmation_date")
    private ZonedDateTime confirmationDate;
    
    @ManyToOne
    @JoinColumn(name = "currency_id")
    private Currency currency;

    @ManyToOne
    @JoinColumn(name = "offered_service_type_id")
    private OfferedServiceType offeredServiceType;

    @ManyToOne
    @JoinColumn(name = "service_provider_id")
    private ServiceProvider serviceProvider;

    @ManyToOne
    @JoinColumn(name="travel_request_id")
    private TravelRequest travelRequest;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }
    
    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public BigDecimal getCost() {
        return cost;
    }
    
    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Long getDetailsId() {
        return detailsId;
    }
    
    public void setDetailsId(Long detailsId) {
        this.detailsId = detailsId;
    }

    public ZonedDateTime getConfirmationDate() {
        return confirmationDate;
    }
    
    public void setConfirmationDate(ZonedDateTime confirmationDate) {
        this.confirmationDate = confirmationDate;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public OfferedServiceType getOfferedServiceType() {
        return offeredServiceType;
    }

    public void setOfferedServiceType(OfferedServiceType offeredServiceType) {
        this.offeredServiceType = offeredServiceType;
    }

    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public TravelRequest getTravelRequest() {
        return travelRequest;
    }

    public void setTravelRequest(TravelRequest travelRequest) {
        this.travelRequest = travelRequest;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OfferedService offeredService = (OfferedService) o;
        if(offeredService.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, offeredService.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "OfferedService{" +
            "id=" + id +
            ", sellingPrice='" + sellingPrice + "'" +
            ", cost='" + cost + "'" +
            ", detailsId='" + detailsId + "'" +
            ", confirmationDate='" + confirmationDate + "'" +
            '}';
    }
}
