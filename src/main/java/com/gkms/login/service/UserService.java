package com.gkms.login.service;

import com.gkms.login.domain.User;
import com.gkms.login.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    public final UserRepository memberRepository;

    public Optional<User> findByIdPw(String id) {
        return memberRepository.findById(id);
    }
}
