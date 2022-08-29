package ru.strelkov.sportcompetitions.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.strelkov.sportcompetitions.models.Discipline;

public interface DisciplineRepository extends JpaRepository<Discipline, Integer> {
}
