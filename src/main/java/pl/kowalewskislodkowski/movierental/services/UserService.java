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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pl.kowalewskislodkowski.movierental.config.MyUserPrincipal;
import pl.kowalewskislodkowski.movierental.entities.Film;
import pl.kowalewskislodkowski.movierental.entities.Role;
import pl.kowalewskislodkowski.movierental.entities.User;
import pl.kowalewskislodkowski.movierental.entities.DTO.ClientDTO;
import pl.kowalewskislodkowski.movierental.repositories.FilmRepository;
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
    @Autowired
    private FilmRepository filmRepository;

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

    @GetMapping("/currentUser")
    public ResponseEntity<ClientDTO> getCurrentUser(Authentication auth){
        ClientDTO client = new ClientDTO(userRepository.findByUsername(auth.getName()));
        return ResponseEntity.ok(client);
    }

    @PostMapping("/{id}")
    public ResponseEntity<ClientDTO> updateUser(@PathVariable Long id,@RequestBody MultiValueMap<String, String> paramMap){

        logger.info(paramMap.toString());

        User u = userRepository.getById(id);

        u.setName(paramMap.get("name").get(0));
        u.setLastName(paramMap.get("lastName").get(0));
        u.setUsername(paramMap.get("username").get(0));
        u.setEmail(paramMap.get("email").get(0));
        
        String[] d = paramMap.get("dateOfBirth").get(0).split("-");

        Calendar cal = Calendar.getInstance();
        cal.set(Integer.valueOf(d[0]), Integer.valueOf(d[1]), Integer.valueOf(d[2]), 0, 0, 1);

        u.setDateOfBirth(cal.getTime());

        return ResponseEntity.ok(new ClientDTO(userRepository.saveAndFlush(u)));
    }

    @PostMapping("/currentUser/borrow_films")
    public HttpStatus addBorrowFilm(@RequestBody MultiValueMap<String, String> filmId, Authentication auth){
        Film film = filmRepository.getById(Integer.valueOf(filmId.get("id").get(0)));

        User u = userRepository.getById(((MyUserPrincipal)auth.getPrincipal()).getId());

        List<Film> films = u.getBorrowFilms();

        HttpStatus status = HttpStatus.OK;

        for(int i=0;i<films.size();i++){
            if(films.get(0).getId()==film.getId()) {
                status=HttpStatus.ALREADY_REPORTED;
                logger.info("Wykryło");
            }
        }

        if(!status.toString().equals(HttpStatus.ALREADY_REPORTED.toString())){
            u.addBorrowFilm(film);
            userRepository.saveAndFlush(u);
        }
        return status;
    }

    @DeleteMapping("/currentUser/borrow_films/{filmId}")
    public HttpStatus removeBorrowFilm(@PathVariable Integer filmId, Authentication auth){

        Film film = filmRepository.getById(filmId);

        User u = userRepository.getById(((MyUserPrincipal)auth.getPrincipal()).getId());

        u.removeBorrowFilm(film);

        userRepository.saveAndFlush(u);
        
        return HttpStatus.OK;
    }

    @GetMapping("/currentUser/borrow_films")
    public ResponseEntity<List<Film>> getBorrowFilms(Authentication auth){
        User u = userRepository.getById(((MyUserPrincipal)auth.getPrincipal()).getId());

        List<Film> list = new ArrayList<Film>(u.getBorrowFilms());
        return ResponseEntity.ok(list);
    }

}
