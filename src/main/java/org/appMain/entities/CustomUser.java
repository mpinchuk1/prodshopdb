package org.appMain.entities;

import org.appMain.utils.UserRole;

import javax.persistence.*;
import java.util.Date;

@Entity
public class CustomUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private Date registrationDate;
    private String login;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    private String email;

    public CustomUser() {
    }

    public CustomUser(String firstName, String lastName, String login, String password,
                      String email, UserRole role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.registrationDate = new Date();
        this.login = login;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "CustomUser{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", registrationDate=" + registrationDate +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + role +
                ", email='" + email + '\'' +
                '}';
    }
}
