package pl.kowalewskislodkowski.movierental.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.kowalewskislodkowski.movierental.entities.Category;
import pl.kowalewskislodkowski.movierental.repositories.CategoryRepository;

@Service
@RestController
@RequestMapping("/categories")
public class CategoryService {    
    @Autowired
    CategoryRepository repo;

    @GetMapping
    private ResponseEntity<List<Category>> getAllCategories(){
        return ResponseEntity.ok(repo.findAll());
    }

    @GetMapping("/{id}")
    private ResponseEntity<Optional<Category>> getFilmById(@PathVariable Integer id){
        return ResponseEntity.ok(repo.findById(id));
    }

}
