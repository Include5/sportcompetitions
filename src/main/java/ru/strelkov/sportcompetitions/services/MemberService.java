package ru.strelkov.sportcompetitions.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.strelkov.sportcompetitions.models.Member;
import ru.strelkov.sportcompetitions.models.Team;
import ru.strelkov.sportcompetitions.repositories.MemberRepository;
import ru.strelkov.sportcompetitions.repositories.TeamRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository, TeamRepository teamRepository) {
        this.memberRepository = memberRepository;
        this.teamRepository = teamRepository;
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Member> findAllNullTeamMembers() {
        return memberRepository.findAllByTeamIsNull();
    }

    public Member findOne(int id) {
        Optional<Member> foundMember = memberRepository.findById(id);

        return foundMember.orElse(null);
    }

    public List<Member> searchOne(String name) {
        return memberRepository.findByNameStartingWith(name);
    }

    @Transactional(readOnly = false)
    public void save(Member member) {
        memberRepository.save(member);
    }

    @Transactional(readOnly = false)
    public void update(int id, Member updateMember) {
        Member updatedMember = memberRepository.getOne(id);
        updateMember.setId(id);
        updateMember.setTeam(updatedMember.getTeam());
        memberRepository.save(updateMember);
    }

    @Transactional(readOnly = false)
    public void assignTeam(int id, Team selectedTeam) {
        memberRepository.findById(id).ifPresent(
                member -> {
                    member.setTeam(selectedTeam);
                }
        );
    }

    @Transactional(readOnly = false)
    public void release(int id) {
        memberRepository.findById(id).ifPresent(
                member -> {
                    member.setTeam(null);
                }
        );
    }

    @Transactional(readOnly = false)
    public void delete(int id) {
        memberRepository.deleteById(id);
    }

}
