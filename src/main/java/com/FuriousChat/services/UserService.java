package com.FuriousChat.services;

import com.FuriousChat.DTOs.UpdateUserRequest;
import com.FuriousChat.infra.exceptionHandler.exceptions.UserNotFoundException;
import com.FuriousChat.models.User;
import com.FuriousChat.models.enums.UserRole;
import com.FuriousChat.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    public UserRepository userRepository;

    public List<User> listUsers() {
        return userRepository.findAll();
    }

    public void updateUser(Long studentId, UpdateUserRequest request, UserRole role) {

        var userEntity = userRepository.findById(studentId);

        if (userEntity.isPresent()) {
            var user = userEntity.get();

            if (request.firstName() != null)
                user.setFirstName(request.firstName());

            if (request.lastName() != null)
                user.setLastName(request.lastName());

            if (request.username() != null)
                user.setUsername(request.username());

            if (request.email() != null)
                user.setEmail(request.email());

            if(request.password() != null)
                user.setPassword(request.password());

            if(request.role() != null)
                user.setRole(role);

            userRepository.save(user);
        } throw new UserNotFoundException("");
    }

    public void deleteUser(Long userId) {

        if (!userRepository.existsById(userId))
            throw new UserNotFoundException("Usuario com Id " + userId + " nao encontrado");

        userRepository.deleteById(userId);
    }
}
