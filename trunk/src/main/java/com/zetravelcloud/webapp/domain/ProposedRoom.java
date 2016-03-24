package com.zetravelcloud.webapp.domain;


import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ProposedRoom.
 */
@Entity
@Table(name = "proposed_room")
public class ProposedRoom implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "hotel_name")
    private String hotelName;
    
    @Column(name = "type")
    private String type;
    
    @Column(name = "url")
    private String url;
    
    @Column(name = "price", precision=10, scale=2)
    private BigDecimal price;
    
    @ManyToOne
    @JoinColumn(name = "currency_id")
    private Currency currency;

    @ManyToOne
    @JoinColumn(name = "proposal_id")
    @JsonIgnore
    private Proposal proposal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHotelName() {
        return hotelName;
    }
    
    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }

    public BigDecimal getPrice() {
        return price;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Proposal getProposal() {
        return proposal;
    }

    public void setProposal(Proposal proposal) {
        this.proposal = proposal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProposedRoom proposedRoom = (ProposedRoom) o;
        if(proposedRoom.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, proposedRoom.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ProposedRoom{" +
            "id=" + id +
            ", hotelName='" + hotelName + "'" +
            ", type='" + type + "'" +
            ", url='" + url + "'" +
            ", price='" + price + "'" +
            '}';
    }
}
