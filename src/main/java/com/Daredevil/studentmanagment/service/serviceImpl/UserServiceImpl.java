package com.Daredevil.studentmanagment.service.serviceImpl;

import com.Daredevil.studentmanagment.model.Users;
import com.Daredevil.studentmanagment.repository.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserDetailsService {

    private userRepository userRepo;

    @Autowired
    public UserServiceImpl(userRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid username"));

        return User.withUsername(username)
                .password(user.getPassword())
                .disabled(!user.isActive())
                .build();

    }
}
