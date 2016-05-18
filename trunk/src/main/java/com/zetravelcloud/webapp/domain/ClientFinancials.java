package com.zetravelcloud.webapp.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ClientFinancials.
 */
@Entity
@Table(name = "client_financials")
public class ClientFinancials implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "credit_limit")
    private Long creditLimit;
    
    @Column(name = "balance")
    private Long balance;
    
    @OneToOne
    private Client client;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreditLimit() {
        return creditLimit;
    }
    
    public void setCreditLimit(Long creditLimit) {
        this.creditLimit = creditLimit;
    }

    public Long getBalance() {
        return balance;
    }
    
    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ClientFinancials clientFinancials = (ClientFinancials) o;
        if(clientFinancials.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, clientFinancials.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ClientFinancials{" +
            "id=" + id +
            ", creditLimit='" + creditLimit + "'" +
            ", balance='" + balance + "'" +
            '}';
    }
}
