package com.example.library_storage.repositories;

import com.example.library_storage.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByDni(String dni);

    List<User> findByName(String name);

    List<User> findBySurname(String surname);
}
