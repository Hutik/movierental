package pl.kowalewskislodkowski.movierental.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.kowalewskislodkowski.movierental.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{
    List<Category> findAll();
    Optional<Category> findById(Integer id);
}
