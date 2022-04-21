package pl.kowalewskislodkowski.movierental.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.kowalewskislodkowski.movierental.entities.Film;

@Repository
public interface FilmRepository extends JpaRepository<Film, Integer>{
    List<Film> findAll();
    Optional<Film> findById(Integer id);
}