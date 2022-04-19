package pl.kowalewskislodkowski.movierental.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Role {
    @Id
    Integer id;
    String name;
}
