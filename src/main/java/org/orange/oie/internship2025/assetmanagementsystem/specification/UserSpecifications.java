package org.orange.oie.internship2025.assetmanagementsystem.specification;

import org.orange.oie.internship2025.assetmanagementsystem.entity.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecifications {

    public static Specification<User> hasusername
            (String searchWord) {
        return (root, query, cb) ->{
return  cb.or(
        cb.like(cb.lower(root.get("username")), "%" + searchWord.toLowerCase() + "%")
);
        };

    }

    public static Specification<User> hasRole(String role) {
        return (root, query, cb) ->
                cb.equal(root.get("role").get("name"), role);
    }
    public static Specification<User> inDepartment(Long departmentId) {
        return (root, query, cb) ->
                cb.equal(root.get("department").get("id"), departmentId);
    }
}
