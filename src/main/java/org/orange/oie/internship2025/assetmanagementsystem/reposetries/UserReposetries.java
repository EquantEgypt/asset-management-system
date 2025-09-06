package org.orange.oie.internship2025.assetmanagementsystem.reposetries;

import org.orange.oie.internship2025.assetmanagementsystem.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserReposetries extends CrudRepository<User, Long> {
    User findByEmail(String email);
}
