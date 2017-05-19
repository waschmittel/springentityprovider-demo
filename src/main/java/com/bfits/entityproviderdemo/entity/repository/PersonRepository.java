package com.bfits.entityproviderdemo.entity.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bfits.entityproviderdemo.entity.Person;
import com.bfits.util.vaadin.spring.SpringEntityProvider;

/**
 * example repository
 *
 * @author FlassakD
 *
 */
public interface PersonRepository extends JpaRepository<Person, Serializable> {
    @Repository
    public class PersonProvider extends SpringEntityProvider<Person> {
    }
}
