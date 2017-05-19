package com.bfits.entityproviderdemo.entity.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bfits.entityproviderdemo.entity.Superhero;
import com.bfits.util.vaadin.spring.SpringEntityProvider;

/**
 * example repository
 *
 * @author FlassakD
 *
 */
public interface SuperheroRepository extends JpaRepository<Superhero, Serializable> {
    // List<Superhero> findAllByUid(String uid, Pageable pageable);

    // Superhero findOneBySuperheroName(String superheroName);

    @Repository
    public class SuperheroProvider extends SpringEntityProvider<Superhero> {
    }
}
