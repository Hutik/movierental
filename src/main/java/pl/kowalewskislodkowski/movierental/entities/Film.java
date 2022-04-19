package pl.kowalewskislodkowski.movierental.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Films")
public class Film {
    @Id
    Integer id;
}
