    package org.orange.oie.internship2025.assetmanagementsystem.controller;

    import org.orange.oie.internship2025.assetmanagementsystem.dto.UserDTO;
    import org.orange.oie.internship2025.assetmanagementsystem.dto.UserDetailsDTO;
    import org.orange.oie.internship2025.assetmanagementsystem.service.serviceInterface.UserService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.Pageable;
    import org.springframework.http.ResponseEntity;
    import org.springframework.security.access.prepost.PreAuthorize;
    import org.springframework.web.bind.annotation.*;


    @RestController
    @RequestMapping("/api/users")

    public class UserController {

        @Autowired
        private UserService userService;

        @GetMapping
        @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('IT') or hasAuthority('DEPARTMENT_MANAGER')")
        public ResponseEntity<Page<UserDTO>> getAllUsers(
                Pageable pageable,
                @RequestParam(required = false) String search,
                @RequestParam(required = false) String role,
                @RequestParam(required = false) Long departmentId
        )
        {


            Page<UserDTO> userDTOPage = userService.searchUsers(search, role, departmentId, pageable);
            return ResponseEntity.ok(userDTOPage);
        }
        @GetMapping("/details/{id}")
        @PreAuthorize("hasAuthority('ADMIN')")
        public ResponseEntity<UserDetailsDTO> getUserDetailsById(@PathVariable Long id) {
            UserDetailsDTO dto = userService.getUserDetailsById(id);
            return ResponseEntity.ok(dto);
        }

    }


