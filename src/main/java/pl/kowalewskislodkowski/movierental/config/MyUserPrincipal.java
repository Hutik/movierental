package pl.kowalewskislodkowski.movierental.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import pl.kowalewskislodkowski.movierental.entities.Film;
import pl.kowalewskislodkowski.movierental.entities.Role;
import pl.kowalewskislodkowski.movierental.entities.User;

public class MyUserPrincipal implements UserDetails{
    private User user;

    public MyUserPrincipal(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> roles = user.getRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
         
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getName()));
        }
         
        return authorities;
    }

    public Long getId(){
        return user.getId();
    }

    public boolean addBorrowFilm(Film film){
        return user.addBorrowFilm(film);
    }

    public boolean removeBorrowFilm(Film film){
        return user.removeBorrowFilm(film);
    }

    public List<Film> getBorroFilms(){
        return user.getBorrowFilms();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
