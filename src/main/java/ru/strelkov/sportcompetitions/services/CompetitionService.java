package ru.strelkov.sportcompetitions.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.strelkov.sportcompetitions.models.Competition;
import ru.strelkov.sportcompetitions.models.Discipline;
import ru.strelkov.sportcompetitions.repositories.CompetitionRepository;
import ru.strelkov.sportcompetitions.repositories.TeamRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CompetitionService {

    private final CompetitionRepository competitionRepository;
    private final TeamRepository teamRepository;

    @Autowired
    public CompetitionService(CompetitionRepository competitionRepository, TeamRepository teamRepository) {
        this.competitionRepository = competitionRepository;
        this.teamRepository = teamRepository;
    }

    public List<Competition> findAll() {
        return competitionRepository.findAll();
    }

    public Competition findOne(int id) {
        Optional<Competition> foundCompetition = competitionRepository.findById(id);

        return foundCompetition.orElse(null);
    }

        public List<Competition> searchOne(String title) {
        return competitionRepository.findByTitleStartingWith(title);
    }

    @Transactional(readOnly = false)
    public void save(Competition competition) {
        competitionRepository.save(competition);
    }

    @Transactional(readOnly = false)
    public void assignDiscipline(int id, Discipline selectedDiscipline) {
        competitionRepository.findById(id).ifPresent(
                competition -> {
                    competition.setDiscipline(selectedDiscipline);
                }
        );
    }

    @Transactional(readOnly = false)
    public void update(int id, Competition updatedCompetition) {
        Competition competitionToBeUpdated = competitionRepository.findById(id).get();
        updatedCompetition.setTeamList(competitionToBeUpdated.getTeamList());
        updatedCompetition.setMemberList(competitionToBeUpdated.getMemberList());
        updatedCompetition.setId(id);

        competitionRepository.save(updatedCompetition);
    }

    @Transactional(readOnly = false)
    public void delete(int id) {
        competitionRepository.deleteById(id);
    }
}
