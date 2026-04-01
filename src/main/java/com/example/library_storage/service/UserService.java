package com.example.library_storage.service;

import com.example.library_storage.entities.User;
import com.example.library_storage.exceptions.AlreadyExistsException;
import com.example.library_storage.exceptions.NotFoundException;
import com.example.library_storage.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user) throws AlreadyExistsException {
        if (userRepository.findByDni(user.getDni()) != null) {
            throw new AlreadyExistsException("User already exists");
        }
        userRepository.save(user);
        return user;
    }

    public User deleteUser(Long userId) throws NotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        userRepository.delete(user);

        return user;
    }

    public User editUser(Long userId, User updatedUser) throws NotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        user.setDni(updatedUser.getDni());
        user.setName(updatedUser.getName());
        user.setSurname(updatedUser.getSurname());
        user.setAddress(updatedUser.getAddress());
        user.setPhoneNumber(updatedUser.getPhoneNumber());

        userRepository.save(user);
        return user;
    }
}
