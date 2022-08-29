package ru.strelkov.sportcompetitions.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table
public class Discipline {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NotEmpty(message = "Название дисциплины не может быть пустым")
    @Size(min = 2, max = 30, message = "Название дисциплины должно содержать от 2 до 30 символов")
    private String name;

    @OneToMany(mappedBy = "discipline", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<Competition> competitionList;


    public Discipline() {

    }

    public Discipline(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Competition> getCompetitionList() {
        return competitionList;
    }

    public void setCompetitionList(List<Competition> competitionList) {
        this.competitionList = competitionList;
    }

}
