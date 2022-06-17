package pl.kowalewskislodkowski.movierental.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.kowalewskislodkowski.movierental.entities.Film;
import pl.kowalewskislodkowski.movierental.repositories.CategoryRepository;
import pl.kowalewskislodkowski.movierental.repositories.FilmRepository;

@Service
@RestController
@RequestMapping("/films")
public class FilmServices {
    
    @Autowired
    FilmRepository repo;
    @Autowired
    CategoryRepository categoryRepo;

    Logger logger = LoggerFactory.getLogger(FilmServices.class);

    @GetMapping
    private ResponseEntity<List<Film>> getAllFilms(){
        return ResponseEntity.ok(repo.findAll());
    }

    @GetMapping("/{id}")
    private ResponseEntity<Optional<Film>> getFilmById(@PathVariable Integer id){
        return ResponseEntity.ok(repo.findById(id));
    }

    @GetMapping(params = {"new"})
    private ResponseEntity<List<Film>> getNewFilms(){
        return ResponseEntity.ok(repo.findByNewest(true));
    }

    @PostMapping("/{id}")
    private ResponseEntity<Film> updateFilm(@PathVariable Integer id, @RequestBody MultiValueMap<String, String> paramMap){

        logger.info(paramMap.toString());

        Film film = repo.getById(id);

        film.setTitle(paramMap.get("title").get(0));
        film.setDescription(paramMap.get("description").get(0));
        
        List<Integer> categoriesId = new ArrayList<Integer>();

        paramMap.get("categories").forEach(categoryId -> {
            categoriesId.add(Integer.parseInt(categoryId));
        });

        film.setCategories(categoryRepo.findByIdIn(categoriesId));

        film.setNewest(paramMap.get("new").get(0).matches("on"));

        film.setPrice(Float.valueOf(paramMap.get("price").get(0)));

        return ResponseEntity.ok(repo.saveAndFlush(film));
    }

    @PostMapping
    private ResponseEntity<Film> addFilm(@RequestBody MultiValueMap<String, String> paramMap){

        logger.info(paramMap.toString());

        Film film = new Film();

        film.setTitle(paramMap.get("title").get(0));
        film.setDescription(paramMap.get("description").get(0));
        
        List<Integer> categoriesId = new ArrayList<Integer>();

        paramMap.get("categories").forEach(categoryId -> {
            categoriesId.add(Integer.parseInt(categoryId));
        });

        film.setCategories(categoryRepo.findByIdIn(categoriesId));
        try{
            film.setNewest(paramMap.get("new").get(0).matches("on"));
        }catch(NullPointerException ex){
            film.setNewest(false);
        }
        film.setPrice(Float.valueOf(paramMap.get("price").get(0)));

        return ResponseEntity.ok(repo.saveAndFlush(film));
    }

    @DeleteMapping("/{id}")
    private HttpStatus deleteFilm(@PathVariable Integer id){
        repo.deleteById(id);

        return HttpStatus.OK;
    }

}
