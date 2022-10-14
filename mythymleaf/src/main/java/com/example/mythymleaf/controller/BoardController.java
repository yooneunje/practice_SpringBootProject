package com.example.mythymleaf.controller;

import com.example.mythymleaf.model.Board;
import com.example.mythymleaf.repository.BoardRepository;
import com.example.mythymleaf.service.BoardService;
import com.example.mythymleaf.validator.BoardValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
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
    private BoardService boardService;
    @Autowired
    private BoardValidator boardValidator;

    @GetMapping("/list")
    public String list(Model model, @PageableDefault(size = 2) Pageable pageable, @RequestParam(required = false, defaultValue = "") String searchText) {
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
    public String postForm(@Valid Board board, BindingResult bindingResult, Authentication authentication) {

        boardValidator.validate(board, bindingResult);

        if (bindingResult.hasErrors()) {
            return "board/form";
        }
        String userName = authentication.getName(); //스프링에서 제공하는 security 를 이용하여 인증정보를 가지고 올 수 있다.
        boardService.save(userName, board);
        //boardRepository.save(board);
        return "redirect:/board/list";
    }
}
