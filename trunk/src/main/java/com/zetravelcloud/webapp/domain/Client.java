package com.zetravelcloud.webapp.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Client.
 */
@Entity
@Table(name = "client")
public class Client implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "mobile")
    private String mobile;
    
    @Column(name = "fax")
    private String fax;
    
    @Lob
    @Column(name = "image")
    private byte[] image;
    
    @Column(name = "image_content_type")        private String imageContentType;
    @Column(name = "client_type")
    private String clientType;
    
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

    public String getMobile() {
        return mobile;
    }
    
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFax() {
        return fax;
    }
    
    public void setFax(String fax) {
        this.fax = fax;
    }

    public byte[] getImage() {
        return image;
    }
    
    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public String getClientType() {
        return clientType;
    }
    
    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Client client = (Client) o;
        if(client.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, client.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Client{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", email='" + email + "'" +
            ", mobile='" + mobile + "'" +
            ", fax='" + fax + "'" +
            ", image='" + image + "'" +
            ", imageContentType='" + imageContentType + "'" +
            ", clientType='" + clientType + "'" +
            '}';
    }
}
