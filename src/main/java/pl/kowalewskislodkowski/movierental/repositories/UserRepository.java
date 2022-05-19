package pl.kowalewskislodkowski.movierental.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.kowalewskislodkowski.movierental.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    @EntityGraph(attributePaths = {"role"})
    List<User> findAll();
    @EntityGraph(attributePaths = {"role"})
    User findByUsername(String username);
    boolean existsByEmail(String email);
    @EntityGraph(attributePaths = {"role"})
    List<User> findByRoleName(String roleName);
}
