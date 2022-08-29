package ru.strelkov.sportcompetitions.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.strelkov.sportcompetitions.models.Competition;
import ru.strelkov.sportcompetitions.models.Member;
import ru.strelkov.sportcompetitions.services.CompetitionService;

@Component
public class CompetitionValidator implements Validator {

    private final CompetitionService competitionService;

    @Autowired
    public CompetitionValidator(CompetitionService competitionService) {
        this.competitionService = competitionService;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return Member.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Competition competition = (Competition) target;


    }
}
