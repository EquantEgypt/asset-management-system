    package org.orange.oie.internship2025.assetmanagementsystem.dto;
    import lombok.Getter;
    import lombok.Setter;
    import java.time.LocalDate;

    @Setter
    @Getter
    public class UserDetailsDTO {
        private Long id;
        private String username;
        private String fullName;
        private String email;
        private String phone;
        private String role;
        private Long departmentId;
        private String departmentName;
        private LocalDate hireDate;
        private Boolean isActive;
    }
