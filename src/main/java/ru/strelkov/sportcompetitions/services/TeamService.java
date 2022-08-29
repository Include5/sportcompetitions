package ru.strelkov.sportcompetitions.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.strelkov.sportcompetitions.models.Member;
import ru.strelkov.sportcompetitions.models.Team;
import ru.strelkov.sportcompetitions.repositories.MemberRepository;
import ru.strelkov.sportcompetitions.repositories.TeamRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class TeamService {

    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository, MemberRepository memberRepository) {
        this.teamRepository = teamRepository;
        this.memberRepository = memberRepository;
    }

    public List<Team> findAll() {
        return teamRepository.findAll();
    }

        public List<Member> getMembersByTeamId(int id) {
        Optional<Team> team = teamRepository.findById(id);

        if (team.isPresent()) {
            Hibernate.initialize(team.get().getMembers());

            return team.get().getMembers();
        } else {
            return Collections.emptyList();
        }
    }

    public Team findOne(int id) {
        Optional<Team> foundTeam = teamRepository.findById(id);

        return foundTeam.orElse(null);
    }

    @Transactional(readOnly = false)
    public void assignMember(int id, Member selectedMember) {

        Member memberToBeAssigned = memberRepository.findById(selectedMember.getId()).get();
        memberToBeAssigned.setId(selectedMember.getId());
        memberToBeAssigned.setTeam(teamRepository.findById(id).orElse(null));
        memberRepository.save(memberToBeAssigned);


    }

    @Transactional(readOnly = false)
    public void save(Team team) {
        teamRepository.save(team);
    }

    public List<Team> searchOne(String name) {
        return teamRepository.findByNameStartingWith(name);
    }

    @Transactional(readOnly = false)
    public void update(int id, Team updatedTeam) {
        Team teamToBeUpdated = teamRepository.findById(id).get();
        updatedTeam.setId(id);

        teamRepository.save(updatedTeam);
    }

    @Transactional(readOnly = false)
    public void delete(int id) {
        teamRepository.deleteById(id);
    }
}
