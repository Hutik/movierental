package pl.kowalewskislodkowski.movierental.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.kowalewskislodkowski.movierental.config.MyUserPrincipal;
import pl.kowalewskislodkowski.movierental.entities.User;
import pl.kowalewskislodkowski.movierental.repositories.UserRepository;

@Service
@RestController
@RequestMapping("/users")
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new MyUserPrincipal(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers(){

        List<User> users = userRepository.findAll();

        users.forEach( u -> {u.setPassword(null);});

        return ResponseEntity.ok(users);
    }

}
