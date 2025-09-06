package org.orange.oie.internship2025.assetmanagementsystem.runner;

import org.orange.oie.internship2025.assetmanagementsystem.serivce.PasswordMigrationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MigrationRunner implements CommandLineRunner {

    private final PasswordMigrationService passwordMigrationService;

    public MigrationRunner(PasswordMigrationService passwordMigrationService) {
        this.passwordMigrationService = passwordMigrationService;
    }

    @Override
    public void run(String... args) throws Exception {
        passwordMigrationService.migratePasswords();
    }
}
