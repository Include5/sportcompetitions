package ru.strelkov.sportcompetitions.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.strelkov.sportcompetitions.models.Discipline;
import ru.strelkov.sportcompetitions.repositories.DisciplineRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class DisciplineService {

    private DisciplineRepository disciplineRepository;


@Autowired
    public DisciplineService(DisciplineRepository disciplineRepository) {
        this.disciplineRepository = disciplineRepository;
    }

    public List<Discipline> findAll() {
    return disciplineRepository.findAll();
    }

    public Discipline findOne(int id) {
        Optional<Discipline> foundDiscipline = disciplineRepository.findById(id);

        return foundDiscipline.orElse(null);
    }

    @Transactional(readOnly = false)
    public void save(Discipline discipline) {
    disciplineRepository.save(discipline);
    }

    @Transactional(readOnly = false)
    public void update(int id, Discipline updatedDiscipline) {
    Discipline disciplineToBeUpdated = disciplineRepository.findById(id).get();
    updatedDiscipline.setId(id);

    disciplineRepository.save(updatedDiscipline);
    }

    @Transactional(readOnly = false)
    public void delete(int id) {
    disciplineRepository.deleteById(id);
    }

}
