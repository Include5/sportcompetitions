package ru.strelkov.sportcompetitions.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.strelkov.sportcompetitions.models.Member;
import ru.strelkov.sportcompetitions.services.MemberService;
import ru.strelkov.sportcompetitions.services.TeamService;
import ru.strelkov.sportcompetitions.models.Team;

import javax.validation.Valid;

@Controller
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final TeamService teamService;

    @Autowired
    public MemberController(MemberService memberService, TeamService teamService) {
        this.memberService = memberService;
        this.teamService = teamService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("members", memberService.findAll());
        return "members/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("team") Team team) {
        model.addAttribute("member", memberService.findOne(id));
        model.addAttribute("teamList", teamService.findAll());

        return "members/show";
    }

    @PatchMapping("{id}/release")
    public String deleteTeam(@PathVariable("id") int id) {
        memberService.release(id);

        return "redirect:/members/" + id;
    }

    @PatchMapping("/{id}/assignTeam")
    public String updateMember(@PathVariable("id") int id, @ModelAttribute("teamList") Team selectedTeam) {
        memberService.assignTeam(id, selectedTeam);
        return "redirect:/members/" + id;
    }

    @GetMapping("/new")
    public String newMember(@ModelAttribute("member") Member member) {
        return "members/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("member") @Valid Member member, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "members/new";

        memberService.save(member);

        return "redirect:/members/" + member.getId();
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("member", memberService.findOne(id));

        return "members/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("member") @Valid Member member, BindingResult bindingResult,
                                                    @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "members/edit";

        memberService.update(id, member);
        return "redirect:/members/" + id;
    }

    @GetMapping("/search")
    public String search() {
        return "members/search";
    }

    @PostMapping("/search")
    public String makeSearch(Model model, @RequestParam("query") String query) {
        model.addAttribute("member", memberService.searchOne(query));
        return "members/search";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        memberService.delete(id);

        return "redirect:/members";
    }
}
