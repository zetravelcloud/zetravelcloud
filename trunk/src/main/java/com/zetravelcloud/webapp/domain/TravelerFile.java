package com.zetravelcloud.webapp.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A TravelerFile.
 */
@Entity
@Table(name = "traveler_file")
public class TravelerFile implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "file_name")
    private String fileName;
    
    @Lob
    @Column(name = "file")
    private byte[] file;
    
    @Column(name = "file_content_type")        private String fileContentType;
    @ManyToOne
    @JoinColumn(name = "traveler_id")
    private Traveler traveler;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }
    
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getFile() {
        return file;
    }
    
    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public Traveler getTraveler() {
        return traveler;
    }

    public void setTraveler(Traveler traveler) {
        this.traveler = traveler;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TravelerFile travelerFile = (TravelerFile) o;
        if(travelerFile.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, travelerFile.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TravelerFile{" +
            "id=" + id +
            ", fileName='" + fileName + "'" +
            ", file='" + file + "'" +
            ", fileContentType='" + fileContentType + "'" +
            '}';
    }
}
