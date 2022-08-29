package ru.strelkov.sportcompetitions.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.strelkov.sportcompetitions.models.Member;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {

    List<Member> findAllByTeamIsNull();

    List<Member> findByNameStartingWith(String name);
}
