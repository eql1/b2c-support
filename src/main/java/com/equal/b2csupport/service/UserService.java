package com.equal.b2csupport.service;

import com.equal.b2csupport.dto.UserResponse;
import com.equal.b2csupport.model.User;
import com.equal.b2csupport.repo.UserRepository;
import com.equal.b2csupport.util.mappers.MapperToResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final MapperToResponse mapToResponse;

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with the given ID not found"));
    }

    public UserResponse getUserInfo() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .map(mapToResponse::mapToResponse)
                .orElseThrow(() -> new UsernameNotFoundException("User with the given ID not found"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(mapToResponse::mapToResponse)
                .collect(Collectors.toList());
    }

}
