package ru.strelkov.sportcompetitions.controllers;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.strelkov.sportcompetitions.models.Member;
import ru.strelkov.sportcompetitions.services.TeamService;
import ru.strelkov.sportcompetitions.models.Team;
import ru.strelkov.sportcompetitions.services.MemberService;

import javax.validation.Valid;

@Controller
@RequestMapping("/teams")
public class TeamController {

    private final TeamService teamService;
    private final MemberService memberService;

    @Autowired
    public TeamController(TeamService teamService, MemberService memberService) {
        this.teamService = teamService;
        this.memberService = memberService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("AllTeams", teamService.findAll());

        return "teams/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("member") Member member) {
        Member memberToDelete = new Member();
        model.addAttribute("memberToDelete", memberToDelete);
        model.addAttribute("team", teamService.findOne(id));
        model.addAttribute("memberList", memberService.findAllNullTeamMembers());
        model.addAttribute("teamMembers", teamService.getMembersByTeamId(id));



        return "teams/show";
    }

    @PatchMapping("/{id}/releaseMember")
    public String releaseTeam(@PathVariable("id") int id, @ModelAttribute("teamMemberToDelete") Member selectedMember) {
        Team team = teamService.findOne(id);
        Member member = memberService.findOne(selectedMember.getId());
        Hibernate.initialize(team.getMembers().remove(member));
        teamService.save(team);
        member.setTeam(null);
        memberService.save(member);
        return "redirect:/teams/" + id;
    }


    @PatchMapping("/{id}/assignMember")
    public String updateMember(@PathVariable("id") int id, @ModelAttribute("member") Member member) {
        teamService.assignMember(id, member);
        return "redirect:/teams/" + id;
    }

    @GetMapping("/new")
    public String newTeam(@ModelAttribute("team") Team team) {
        return "teams/new";
    }

    @PostMapping
    public String create(@ModelAttribute("team") @Valid Team team,
        BindingResult bindingResult) {
    if (bindingResult.hasErrors())
        return "teams/new";

    teamService.save(team);
    return "redirect:/teams/" + team.getId();
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("team", teamService.findOne(id));

        return "teams/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("team") @Valid Team team, BindingResult bindingResult,
                         @PathVariable("id") int id) {

        if (bindingResult.hasErrors())
            return "teams/edit";

        teamService.update(id, team);
        return "redirect:/teams/" + id;
    }

    @GetMapping("/search")
    public String search() {
        return "teams/search";
    }

    @PostMapping("/search")
    public String makeSearch(Model model, @RequestParam("query") String query) {
        model.addAttribute("team", teamService.searchOne(query));
        return "teams/search";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        teamService.delete(id);

        return "redirect:/teams";
    }
}
