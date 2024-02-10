package com.bank.payment.service;

import com.bank.payment.domain.AuthUser;
import com.bank.payment.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUser user = userRepository.findByUserName(username)
                .map(AuthUser::new)
                .orElseThrow(()-> new UsernameNotFoundException("user not found" + username));
        return user;
    }
}
