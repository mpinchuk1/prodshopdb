package org.appMain.entities.dto;


import javax.validation.constraints.Email;
import javax.validation.constraints.Size;


public class UserDTO {

    @Email(message = "Please provide a valid e-mail")
    private String email;
    @Size(min = 8, message = "Password must be at least 8 characters in length")
    private String password;
    private String firstName;
    private String lastName;
    private String login;

    public UserDTO() {
    }

    public UserDTO(String email, String password, String firstName, String lastName, String login) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
