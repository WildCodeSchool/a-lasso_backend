package com.back_alasso.User;

import com.back_alasso.Geolocalisation.Geolocalisation;
import com.back_alasso.Message.Message;
import com.back_alasso.Preferences.Preferences;
import com.back_alasso.Report.Report;
import com.back_alasso.core.BaseEntity;
import jakarta.persistence.*;

import java.util.List;

@Inheritance(strategy = InheritanceType.JOINED) // Use separate tables for each entity
@Entity
public class User extends BaseEntity {

    public static final int EMAIL_MAX_LENGTH = 320;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserEnumType user_type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountEnumType account_status;

    @Column(nullable = false)
    private String hashed_password;

    @Column(nullable = false, length = EMAIL_MAX_LENGTH)
    private String email;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Preferences preferences;

    @OneToMany(mappedBy = "user")
    private List<Message> messages;

    @OneToMany(mappedBy = "user_reporter")
    private List<Report> reportsMade;  // reports where the user is the reporter

    @OneToMany(mappedBy = "user_reported")
    private List<Report> reportsReceived;  // reports where the user is the reported

    @OneToMany(mappedBy = "user_admin")
    private List<Report> reportsHandled;  // reports where the user is the admin


    @ManyToOne
    @JoinColumn(name = "geolocalisation_id")
    private Geolocalisation geolocalisation;

    public User(UserEnumType user_type,
                AccountEnumType account_status,
                String hashed_password,
                String email,
                Preferences preferences) {
        this.user_type = user_type;
        this.account_status = account_status;
        this.hashed_password = hashed_password;
        this.email = email;
        this.preferences = preferences;
    }

    public UserEnumType getUser_type() {
        return user_type;
    }

    public void setUser_type(UserEnumType user_type) {
        this.user_type = user_type;
    }

    public AccountEnumType getAccount_status() {
        return account_status;
    }

    public void setAccount_status(AccountEnumType account_status) {
        this.account_status = account_status;
    }

    public String getHashed_password() {
        return hashed_password;
    }

    public void setHashed_password(String hashed_password) {
        this.hashed_password = hashed_password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Preferences getPreferences() {
        return preferences;
    }

    public void setPreferences(Preferences preferences) {
        this.preferences = preferences;
    }

    public List<Message> getMessage() {
        return messages;
    }

    public void setMessage(List<Message> messages) {
        this.messages = messages;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public List<Report> getReportsMade() {
        return reportsMade;
    }

    public void setReportsMade(List<Report> reportsMade) {
        this.reportsMade = reportsMade;
    }

    public List<Report> getReportsReceived() {
        return reportsReceived;
    }

    public void setReportsReceived(List<Report> reportsReceived) {
        this.reportsReceived = reportsReceived;
    }

    public List<Report> getReportsHandled() {
        return reportsHandled;
    }

    public void setReportsHandled(List<Report> reportsHandled) {
        this.reportsHandled = reportsHandled;
    }

    public Geolocalisation getGeolocalisation() {
        return geolocalisation;
    }

    public void setGeolocalisation(Geolocalisation geolocalisation) {
        this.geolocalisation = geolocalisation;
    }
}
