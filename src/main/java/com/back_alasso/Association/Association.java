package com.back_alasso.Association;

import com.back_alasso.Address.Address;
import com.back_alasso.AssociationImage.AssociationImage;
import com.back_alasso.User.User;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Association extends User {

    public static final int DESC_MAX_LENGTH = 500;
    public static final int FOUNDER_MAX_LENGTH = 50;
    public static final int NAME_MAX_LENGTH = 50;

    @Column(nullable = true, length = DESC_MAX_LENGTH)
    private String description;

    @Column(nullable = true, length = FOUNDER_MAX_LENGTH)
    private String founder;

    @Column(nullable = true)
    private Date foundationDate;

    @Column(nullable = false, length = NAME_MAX_LENGTH)
    private String name;

    @ManyToOne
    @JoinColumn(name = "adress_id")
    private Address address;

    @OneToMany(mappedBy = "association")
    private List<AssociationImage> associationImages;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFounder() {
        return founder;
    }

    public void setFounder(String founder) {
        this.founder = founder;
    }

    public Date getFoundationDate() {
        return foundationDate;
    }

    public void setFoundationDate(Date foundationDate) {
        this.foundationDate = foundationDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAdress() {
        return address;
    }

    public void setAdress(Address address) {
        this.address = address;
    }

    public List<AssociationImage> getAssociationImages() {
        return associationImages;
    }

    public void setAssociationImages(List<AssociationImage> associationImages) {
        this.associationImages = associationImages;
    }
}
