package com.gkms.login.controller;

import com.gkms.login.config.JwtTokenProvider;
import com.gkms.login.domain.User;
import com.gkms.login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserCont {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    final Long sequenceId = Long.valueOf(1);
    final String userId = "redkal";
    final String userName = "오윤섭";
    final String userBirth= "880130";
    final String gender = "남";
    final String admin = "Y";

    User user = User.builder()
            .userId(userId)
            .userName(userName)
            .userBirth(userBirth)
            .gender(gender)
            .admin(admin)
            .userSequenceId(sequenceId)
            .roles(Collections.singletonList("ROLE_USER")) //최초 가입 시, USER로 설정
            .build();

    @PostMapping("/join")
    public String join() {
        log.info("로그인 시도");

        userRepository.save(user);
        return user.toString();
    }

    //로그인
    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> user) {
        log.info("user id = {}", user.get("userid"));
        User member = userRepository.findByUserId(user.get("userid"))
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 ID"));

        return jwtTokenProvider.createToken(member.getUsername(), member.getRoles());
    }
}

