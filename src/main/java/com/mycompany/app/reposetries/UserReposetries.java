package com.mycompany.app.reposetries;

import com.mycompany.app.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserReposetries extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
