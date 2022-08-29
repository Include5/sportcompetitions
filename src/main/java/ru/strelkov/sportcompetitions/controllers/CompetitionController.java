package ru.strelkov.sportcompetitions.controllers;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.strelkov.sportcompetitions.models.Member;
import ru.strelkov.sportcompetitions.services.CompetitionService;
import ru.strelkov.sportcompetitions.models.Competition;
import ru.strelkov.sportcompetitions.models.Discipline;
import ru.strelkov.sportcompetitions.models.Team;
import ru.strelkov.sportcompetitions.services.DisciplineService;
import ru.strelkov.sportcompetitions.services.MemberService;
import ru.strelkov.sportcompetitions.services.TeamService;
import ru.strelkov.sportcompetitions.util.CompetitionValidator;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/competitions")
public class CompetitionController {

    private final CompetitionService competitionService;
    private final TeamService teamService;
    private final DisciplineService disciplineService;
    private final MemberService memberService;
    private final CompetitionValidator competitionValidator;

    @Autowired
    public CompetitionController(CompetitionService competitionService, TeamService teamService, DisciplineService disciplineService, MemberService memberService, CompetitionValidator competitionValidator) {
        this.competitionService = competitionService;
        this.teamService = teamService;
        this.disciplineService = disciplineService;
        this.memberService = memberService;
        this.competitionValidator = competitionValidator;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("AllCompetitions", competitionService.findAll());

        return "competitions/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("team") Team team) {
        Member member = new Member();
        model.addAttribute("member", member);
        model.addAttribute("competition", competitionService.findOne(id));
        model.addAttribute("teamList", teamService.findAll());
        model.addAttribute("memberList", memberService.findAll());
        List<Team> teamListNoComp = teamService.findAll();
        List<Team> tempTeam = competitionService.findOne(id).getTeamList();
        // somehow doesn't work in 1 line, returns false , not
        List<Member> memberListNoComp = memberService.findAll();
        List<Member> tempMember = competitionService.findOne(id).getMemberList();
        teamListNoComp.removeAll(tempTeam);
        memberListNoComp.removeAll(tempMember);
        model.addAttribute("teamListNoComp", teamListNoComp);
        model.addAttribute("memberListNoComp", memberListNoComp);

        return "competitions/show";
    }

    @PatchMapping("/{id}/assignTeam")
    public String assignTeam(@PathVariable("id") int id, @ModelAttribute("teamList") Team selectedTeam) {
        Competition competition = competitionService.findOne(id);
        Team team = teamService.findOne(selectedTeam.getId());
        Hibernate.initialize(competition.getTeamList().add(team));
        competitionService.save(competition);
        return "redirect:/competitions/" + id;
    }

    @PatchMapping("/{id}/releaseTeam")
    public String releaseTeam(@PathVariable("id") int id, @ModelAttribute("compTeamToDelete") Team selectedTeam) {
        Competition competition = competitionService.findOne(id);
        Team team = teamService.findOne(selectedTeam.getId());
        Hibernate.initialize(competition.getTeamList().remove(team));
        competitionService.save(competition);
        return "redirect:/competitions/" + id;
    }

    @PatchMapping("/{id}/assignMember")
    public String assignTeam(@PathVariable("id") int id, @ModelAttribute("memberList") Member selectedMember) {
        Competition competition = competitionService.findOne(id);
        Member member = memberService.findOne(selectedMember.getId());
        Hibernate.initialize(competition.getMemberList().add(member));
        competitionService.save(competition);
        return "redirect:/competitions/" + id;
    }

    @PatchMapping("/{id}/releaseMember")
    public String releaseTeam(@PathVariable("id") int id, @ModelAttribute("compMemberToDelete") Member selectedMember) {
        Competition competition = competitionService.findOne(id);
        Member member = memberService.findOne(selectedMember.getId());
        Hibernate.initialize(competition.getMemberList().remove(member));
        competitionService.save(competition);
        return "redirect:/competitions/" + id;
    }

    @GetMapping("/new")
    public String newCompetition(Model model, @ModelAttribute("competition")Competition competition) {
        model.addAttribute("discplineList", disciplineService.findAll());
        return "competitions/new";
    }

    @PostMapping
     public String create(Model model, @ModelAttribute("competition") @Valid Competition competition,
        BindingResult bindingResult) {

        Discipline selectedDiscipline = new Discipline();

        if (bindingResult.hasErrors()) {
            model.addAttribute("discplineList", disciplineService.findAll());
            model.addAttribute("selectedDiscipline", selectedDiscipline);
            return "competitions/new";
        }



    Discipline discipline = disciplineService.findOne(Integer.parseInt(competition.getDiscipline().getName()));
    competition.setDiscipline(discipline);;
    competitionService.save(competition);
    return "redirect:/competitions/" + competition.getId();
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("competition", competitionService.findOne(id));
        model.addAttribute("discplineList", disciplineService.findAll());

        return "competitions/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("competition") @Valid Competition competition, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        Discipline selectedDiscipline = new Discipline();

        if (bindingResult.hasErrors())
            return "competitions/edit";

        Discipline discipline = disciplineService.findOne(Integer.parseInt(competition.getDiscipline().getName()));
        competition.setDiscipline(discipline);;
        competitionService.update(id, competition);
        return "redirect:/competitions/" + id;
    }

    @GetMapping("/search")
    public String search() {
        return "competitions/search";
    }

    @PostMapping("/search")
    public String makeSearch(Model model, @RequestParam("query") String query) {
        model.addAttribute("competition", competitionService.searchOne(query));
        return "competitions/search";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        competitionService.delete(id);

        return "redirect:/competitions";
    }
}
