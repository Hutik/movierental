package pl.kowalewskislodkowski.movierental.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

@Entity
@Table
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Basic(optional = false)
    @Column(name = "id")
    Long id;
    @Column(unique=true)
    String username;
    String password;
    @ManyToMany
    @JoinTable(name="user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    Set<Role> role = new HashSet<Role>();
    @Column(unique=true)
    String email;
    String name;
    String lastName;
    Date dateOfBirth;
    @ManyToMany
    @JoinTable(name="user_films",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "film_id"))
    List<Film> borrowFilms = new ArrayList<Film>();
    
    public List<Film> getBorrowFilms() {
        return borrowFilms;
    }
    public void setBorrowFilms(List<Film> borrowFilms) {
        this.borrowFilms = borrowFilms;
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
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Set<Role> getRoles() {
        return role;
    }
    public void setRoles(Set<Role> roles) {
        this.role = roles;
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
    public boolean addBorrowFilm(Film film){
        return borrowFilms.add(film);
    }
    public boolean removeBorrowFilm(Film film){
        return borrowFilms.remove(film);
    }
}
