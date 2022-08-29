package ru.strelkov.sportcompetitions.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.strelkov.sportcompetitions.models.Team;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Integer> {

    List<Team> findByNameStartingWith(String name);
}
