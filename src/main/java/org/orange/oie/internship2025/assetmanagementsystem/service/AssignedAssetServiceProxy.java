package org.orange.oie.internship2025.assetmanagementsystem.service;

import java.util.List;

import org.orange.oie.internship2025.assetmanagementsystem.dto.AssignedAssetDTO;
import org.orange.oie.internship2025.assetmanagementsystem.dto.AssignedAssetFilterDTO;
import org.orange.oie.internship2025.assetmanagementsystem.entity.AssignedAsset;
import org.orange.oie.internship2025.assetmanagementsystem.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AssignedAssetServiceProxy {
    @Autowired
    private AssignedAssetServiceImpl assignedAssetService;


    public Page<AssignedAssetDTO> checkForAuthorization(AssignedAssetFilterDTO filter, Pageable pageable) {
        // ---------------Manager-----------------

        if (SecurityUtils.getCurrentUserRole().equals("Department Manager")) {
            if (!filter.getDepartment().equals(SecurityUtils.getCurrentUserDepartment())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to access this resource");

            }

        }

        // ---------------Employee-----------------

        else if (SecurityUtils.getCurrentUserRole().equals("Employee")) {
            String assignedUser = filter.getAssignedUser();
            String currentUser = SecurityUtils.getCurrentUserName();

            if (assignedUser == null || !assignedUser.equals(currentUser) || filter.getDepartment() != null) {
                throw new ResponseStatusException(
                        HttpStatus.FORBIDDEN,
                        "Unauthorized access: Employees can only access their own assets.");
            }
        }

        // ---------------Admin-----------------

        else if (SecurityUtils.getCurrentUserRole().equals("Admin")) {

        }

        // ---------------IT Support-----------------

        else if (SecurityUtils.getCurrentUserRole().equals("IT")) {
            if (!filter.getStatus().equals("UNDER_MAINTENANCE") || filter.getDepartment() != null
                    || filter.getAssignedUser() != null) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to access this resource");

            }
        }

         return assignedAssetService.getFilteredAsset(filter,pageable); // Admin can access all assets
    }
}
