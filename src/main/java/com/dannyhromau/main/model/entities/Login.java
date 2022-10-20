package com.dannyhromau.main.model.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "logins")
@NoArgsConstructor
public class Login {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String application;
    @Column(name = "app_acc_name")
    private String accountName;
    @Column(name = "is_active")
    private String isActive;
    @Column(name = "job_title")
    private String jobTitle;
    private String department;

    public Login(String application, String accountName, String isActive, String jobTitle, String department) {
        this.application = application;
        this.accountName = accountName;
        this.isActive = isActive;
        this.jobTitle = jobTitle;
        this.department = department;
    }

}
