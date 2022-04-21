package pl.kowalewskislodkowski.movierental.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.kowalewskislodkowski.movierental.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    @EntityGraph(attributePaths = {"role"})
    User findByUsername(String username);
}