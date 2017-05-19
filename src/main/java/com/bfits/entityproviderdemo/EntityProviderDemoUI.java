package com.bfits.entityproviderdemo;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bfits.entityproviderdemo.entity.Person;
import com.bfits.entityproviderdemo.entity.Superhero;
import com.bfits.entityproviderdemo.entity.repository.PersonRepository.PersonProvider;
import com.bfits.entityproviderdemo.entity.repository.SuperheroRepository.SuperheroProvider;
import com.bfits.util.vaadin.spring.SpringEntityProvider.ExampleFilter;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Push
@Theme("mytheme")
@SpringUI
public class EntityProviderDemoUI extends UI {

    @Autowired
    SuperheroProvider superheroProvider;

    @Autowired
    PersonProvider employeeProvider;

    @Autowired
    JpaRepository<Person, Serializable> personRepository;

    @Autowired
    JpaRepository<Superhero, Serializable> heroRepository;

    Grid<Superhero> superheroGrid = new Grid<>(Superhero.class);
    Grid<Person>    personGrid    = new Grid<>(Person.class);

    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout layout = new VerticalLayout();
        setContent(layout);

        Button sampleDataButton = new Button("create sample data", (event) -> {
            createSampleData();
        });
        layout.addComponent(sampleDataButton);

        HorizontalLayout gridLayout = new HorizontalLayout();
        layout.addComponent(gridLayout);

        VerticalLayout heroLayout = new VerticalLayout();
        gridLayout.addComponent(heroLayout);

        heroLayout.addComponent(new Label("Superheroes"));

        superheroGrid.setDataProvider(superheroProvider);
        superheroGrid.removeColumn("id");
        heroLayout.addComponent(superheroGrid);

        TextField heroFilter = new TextField("name filter", (event) -> {
            Superhero filterHero = new Superhero();
            filterHero.setSuperheroName(event.getValue());
            superheroGrid.setDataProvider(superheroProvider.withFilter(new ExampleFilter<>(filterHero)));
        });
        heroLayout.addComponent(heroFilter);

        VerticalLayout personLayout = new VerticalLayout();
        gridLayout.addComponent(personLayout);

        personLayout.addComponent(new Label("Persons"));

        personGrid.setDataProvider(employeeProvider);
        personGrid.removeColumn("id");
        personLayout.addComponent(personGrid);

        TextField employeeFilter = new TextField("name filter", (event) -> {
            Person filterEmployee = new Person();
            filterEmployee.setName(event.getValue());
            personGrid.setDataProvider(employeeProvider.withFilter(new ExampleFilter<>(filterEmployee)));
        });
        personLayout.addComponent(employeeFilter);
    }

    public void createSampleData() {
        for (int i = 1; i <= 500; i++) {
            Person person = new Person();
            person.setAge(i);
            person.setDescription("Person no. " + i);
            person.setName("The" + i + " " + i + "sson");
            person = personRepository.saveAndFlush(person);

            Superhero hero = new Superhero();
            hero.setSuperheroName("Super" + i + "sson");
            hero.setBoringIdentity(person);
            heroRepository.saveAndFlush(hero);

            personGrid.setDataProvider(personGrid.getDataProvider());
        }

    }
}
