package com.example.mythymleaf.service;

import com.example.mythymleaf.model.Board;
import com.example.mythymleaf.model.User;
import com.example.mythymleaf.repository.BoardRepository;
import com.example.mythymleaf.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    public Board save(String username, Board board) {
        User user = userRepository.findByUsername(username);
        board.setUser(user);
        return boardRepository.save(board);
    }
}
