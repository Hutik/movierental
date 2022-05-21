package pl.kowalewskislodkowski.movierental.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pl.kowalewskislodkowski.movierental.config.MyUserPrincipal;
import pl.kowalewskislodkowski.movierental.entities.Role;
import pl.kowalewskislodkowski.movierental.entities.User;
import pl.kowalewskislodkowski.movierental.entities.DTO.ClientDTO;
import pl.kowalewskislodkowski.movierental.repositories.RoleRepository;
import pl.kowalewskislodkowski.movierental.repositories.UserRepository;

@Service
@RestController
@RequestMapping("/users")
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    Logger logger = LoggerFactory.getLogger(UserService.class);

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

    @PutMapping
    public ResponseEntity<Optional<User>> register(User user){
        userRepository.existsByEmail(user.getEmail());

        logger.info(user.getUsername());

        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @GetMapping("/clients")
    public ResponseEntity<List<ClientDTO>> getClients(){
        List<User> users = userRepository.findByRoleName("CLIENT");

        List<ClientDTO> clients = new ArrayList<ClientDTO>();

        users.forEach(user -> {clients.add(new ClientDTO(user));});

        return ResponseEntity.ok(clients);
    }

    @PutMapping("/clients")
    public HttpStatus registerClient(@RequestParam Map<String, String> paramMap){

        logger.info(paramMap.toString());

        User u = new User();
        u.setUsername(paramMap.get("username"));
        u.setPassword(BCrypt.hashpw(paramMap.get("password"), BCrypt.gensalt()));
        u.setName(paramMap.get("name"));
        u.setLastName(paramMap.get("lastName"));
        u.setEmail(paramMap.get("email"));
        Calendar date = Calendar.getInstance();
        String[] dateS = paramMap.get("dateOfBirth").split("-");

        date.set(Integer.valueOf(dateS[0]), Integer.valueOf(dateS[1]), Integer.valueOf(dateS[2]), 0, 0, 0);
        u.setDateOfBirth(date.getTime());
        
        Set<Role> roles = new HashSet<Role>();
        roles.add(roleRepository.getById(2));

        u.setRoles(roles);

        userRepository.save(u);

        return HttpStatus.OK;
    }
    
    @DeleteMapping("/clients/{id}")
    public HttpStatus deleteClient(@PathVariable Long id){
        Optional<User> user = userRepository.findById(id);
        user.ifPresent((u) -> {userRepository.delete(u);});

        return HttpStatus.OK;
    }

}
