package com.example.mythymleaf.controller;

import com.example.mythymleaf.model.Board;
import com.example.mythymleaf.repository.BoardRepository;
import com.example.mythymleaf.validator.BoardValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardValidator boardValidator;

    @GetMapping("/list")
    public String list(Model model, @PageableDefault(size = 1) Pageable pageable, @RequestParam(required = false, defaultValue = "") String searchText) {
        //Page<Board> boards = boardRepository.findAll(pageable);
        Page<Board> boards = boardRepository.findByTitleContainingOrContentContaining(searchText, searchText, pageable);
        int startPage = Math.max(1, boards.getPageable().getPageNumber() - 4); //Math.max()함수는 두 인자 값 중 큰 값을 리턴하는 함수.
        int endPage = Math.min(boards.getTotalPages(), boards.getPageable().getPageNumber() + 4); //Math.min()함수는 두 인자 값 중 작은 값을 리턴하는 함수.
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("boards", boards);
        return "board/list";
    }

    @GetMapping("/form")
    public String form(Model model, @RequestParam(required = false) Long id) {  //(required = false) -> 파라메터가 필수인지 아닌지의 여부.
        if (id == null) {
            model.addAttribute("board", new Board());
        } else {
            Board board = boardRepository.findById(id).orElse(null);
            model.addAttribute("board", board);
        }
        return "board/form";
    }

    @PostMapping("/form")
    public String greetingSubmit(@Valid Board board, BindingResult bindingResult) {

        boardValidator.validate(board, bindingResult);

        if (bindingResult.hasErrors()) {
            return "board/form";
        }
        boardRepository.save(board);
        return "redirect:/board/list";
    }
}
