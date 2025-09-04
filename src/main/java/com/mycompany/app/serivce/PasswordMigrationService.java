package com.mycompany.app.serivce;



import com.mycompany.app.entity.User;
import com.mycompany.app.reposetries.UserReposetries;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordMigrationService {

    private final UserReposetries userReposetries;
    private final PasswordEncoder passwordEncoder;

    public PasswordMigrationService(UserReposetries userReposetries, PasswordEncoder passwordEncoder) {
        this.userReposetries = userReposetries;
        this.passwordEncoder = passwordEncoder;
    }

    public void migratePasswords() {
        Iterable<User> users = userReposetries.findAll();

        for (User user : users) {
            String password = user.getPassword();

            if (password == null || password.isEmpty()) continue;

            if (password.startsWith("$2a$") || password.startsWith("$2b$") || password.startsWith("$2y$")) {
                continue;
            }
            String encodedPassword = passwordEncoder.encode(password);
            user.setPassword(encodedPassword);
            userReposetries.save(user);
        }
    }
}
