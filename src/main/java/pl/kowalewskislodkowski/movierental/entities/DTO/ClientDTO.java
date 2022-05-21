package pl.kowalewskislodkowski.movierental.entities.DTO;

import java.util.Date;
import pl.kowalewskislodkowski.movierental.entities.User;

public class ClientDTO{
    Long id;
    String username;
    String name;
    String lastName;
    String email;
    Date dateOfBirth;

    public ClientDTO(User user){
        id=user.getId();
        username=user.getUsername();
        name=user.getName();
        lastName=user.getLastName();
        email=user.getEmail();
        dateOfBirth=user.getDateOfBirth();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
}
