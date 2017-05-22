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

    Button sampleDataButton;

    TextField heroFilter     = new TextField("name filter");
    TextField employeeFilter = new TextField("name filter");

    /**
     * this should be in a Design class that is used by a view ... but since this is only a demo, it's okay
     *
     * @return
     */
    private void makeLayout() {
        VerticalLayout layout = new VerticalLayout();
        setContent(layout);
        sampleDataButton = new Button("create sample data");
        layout.addComponent(sampleDataButton);

        HorizontalLayout gridLayout = new HorizontalLayout();
        layout.addComponent(gridLayout);

        VerticalLayout heroLayout = new VerticalLayout();
        heroLayout.addComponent(new Label("Superheroes"));
        heroLayout.addComponent(superheroGrid);
        heroLayout.addComponent(heroFilter);
        gridLayout.addComponent(heroLayout);

        VerticalLayout personLayout = new VerticalLayout();
        personLayout.addComponent(new Label("Persons"));
        personLayout.addComponent(personGrid);
        personLayout.addComponent(employeeFilter);
        gridLayout.addComponent(personLayout);
    }

    @Override
    protected void init(VaadinRequest request) {
        makeLayout();

        superheroGrid.setDataProvider(superheroProvider);
        superheroGrid.removeColumn("id");
        personGrid.setDataProvider(employeeProvider);
        personGrid.removeColumn("id");

        sampleDataButton.addClickListener((event) -> {
            createSampleData();
        });

        heroFilter.addValueChangeListener((event) -> {
            filterSuperheroes(event.getValue());
        });

        employeeFilter.addValueChangeListener((event) -> {
            filterPersons(event.getValue());
        });
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

    public void filterPersons(String filter) {
        Person filterPerson = new Person();
        filterPerson.setName(filter);
        personGrid.setDataProvider(employeeProvider.withFilter(new ExampleFilter<>(filterPerson)));
    }

    public void filterSuperheroes(String filter) {
        Superhero filterHero = new Superhero();
        filterHero.setSuperheroName(filter);
        superheroGrid.setDataProvider(superheroProvider.withFilter(new ExampleFilter<>(filterHero)));
    }
}
