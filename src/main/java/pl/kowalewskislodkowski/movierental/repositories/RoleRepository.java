package pl.kowalewskislodkowski.movierental.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.kowalewskislodkowski.movierental.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{
    List<Role> findAll();
    Optional<Role> findById(Integer id);
}
