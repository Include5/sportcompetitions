package ru.strelkov.sportcompetitions.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.strelkov.sportcompetitions.models.Competition;

import java.util.List;

public interface CompetitionRepository extends JpaRepository<Competition, Integer> {

    List<Competition> findByTitleStartingWith(String title);
}
