package com.gkms.Login.service;

import com.choongang.erpproject.yslogin.domain.emp_table;
import com.choongang.erpproject.yslogin.repo.EmpTableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final EmpTableRepository empTableRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String empId) throws UsernameNotFoundException {
        return empTableRepository.findByEmpId(empId)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다."));
    }

    //해당하는 User의 데이터가 존재하면 UserDetails 객체로 만들어 리턴
    private UserDetails createUserDetails(emp_table emptable) {
        return User.builder()
                .username(emptable.getEmpId())
                .password(passwordEncoder.encode(emptable.getPassword()))
                .roles(emptable.getRoles().toArray(new String[0]))
                .build();
    }
}
