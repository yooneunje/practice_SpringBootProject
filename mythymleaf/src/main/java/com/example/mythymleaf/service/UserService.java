package com.example.mythymleaf.service;

import com.example.mythymleaf.model.Role;
import com.example.mythymleaf.model.User;
import com.example.mythymleaf.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User save(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword()); //사용자가 전달한 비밀번호를 인코더를 이용해서 인코딩 후 암호화 시킨다.
        user.setPassword(encodedPassword);
        user.setEnabled(true);

        Role role = new Role();
        role.setId(1l);
        user.getRoles().add(role);
        return userRepository.save(user);
    }
}
