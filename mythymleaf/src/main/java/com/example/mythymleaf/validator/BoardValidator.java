package com.example.mythymleaf.validator;

import com.example.mythymleaf.model.Board;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BoardValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Board.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Board board = (Board) obj;
        if(StringUtils.isEmpty((board.getContent()))){
            errors.rejectValue("content", "key","내용을 입력하세요.");
        }
    }
}
