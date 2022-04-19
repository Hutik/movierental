package pl.kowalewskislodkowski.movierental.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import pl.kowalewskislodkowski.movierental.entities.User;

@Repository
@RestController
public interface UserRepository extends JpaRepository<User, Long>{
    User findByUsername(String username);
}
