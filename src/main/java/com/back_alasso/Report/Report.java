package com.back_alasso.Report;

import com.back_alasso.User.User;
import com.back_alasso.core.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Report extends BaseEntity {

    public static final int MESSAGE_REPORTER_MAX_LENGTH = 700;
    public static final int COMMENTARY_ADMIN_MAX_LENGTH = 1000;

    @Column(length = MESSAGE_REPORTER_MAX_LENGTH)
    private String message_reporter;

    @Column(length = COMMENTARY_ADMIN_MAX_LENGTH)
    private String commentary_admin;

    @Column(nullable = false)
    private StatusReportEnumType status;

    @Column(nullable = false)
    private ReasonReportEnumType reason;

    @ManyToOne
    @JoinColumn(name = "user_reported_id")
    private User user_reported;

    @ManyToOne
    @JoinColumn(name = "user_reporter_id")
    private User user_reporter;

    @ManyToOne
    @JoinColumn(name = "user_admin_id")
    private User user_admin;

    public Report(StatusReportEnumType status, ReasonReportEnumType reason, User user_reported, User user_reporter, User user_admin) {
        this.status = status;
        this.reason = reason;
        this.user_reported = user_reported;
        this.user_reporter = user_reporter;
        this.user_admin = user_admin;
    }

    public String getMessage_reporter() {
        return message_reporter;
    }

    public void setMessage_reporter(String message_reporter) {
        this.message_reporter = message_reporter;
    }

    public String getCommentary_admin() {
        return commentary_admin;
    }

    public void setCommentary_admin(String commentary_admin) {
        this.commentary_admin = commentary_admin;
    }

    public StatusReportEnumType getStatus() {
        return status;
    }

    public void setStatus(StatusReportEnumType status) {
        this.status = status;
    }

    public ReasonReportEnumType getReason() {
        return reason;
    }

    public void setReason(ReasonReportEnumType reason) {
        this.reason = reason;
    }

    public User getUser_reported() {
        return user_reported;
    }

    public void setUser_reported(User user_reported) {
        this.user_reported = user_reported;
    }

    public User getUser_reporter() {
        return user_reporter;
    }

    public void setUser_reporter(User user_reporter) {
        this.user_reporter = user_reporter;
    }

    public User getUser_admin() {
        return user_admin;
    }

    public void setUser_admin(User user_admin) {
        this.user_admin = user_admin;
    }
}
