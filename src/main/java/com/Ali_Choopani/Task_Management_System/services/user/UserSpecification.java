package com.Ali_Choopani.Task_Management_System.services.user;

import com.Ali_Choopani.Task_Management_System.entities.Profile;
import com.Ali_Choopani.Task_Management_System.entities.User;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {

    public static Specification<User> searchByNameOrEmail(String query) {
        return ((root, query1, criteriaBuilder) -> {

            Join<User, Profile> profile = root.join("profile");
            String pattern = "%" + query.toLowerCase() + "%";
            final Expression<String> fullName = criteriaBuilder.concat(profile.get("firstName") + " ", profile.get("surname"));

            final Predicate fullNamePredicate = criteriaBuilder.like(criteriaBuilder.lower(fullName), pattern);
            final Predicate emailPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), pattern);

            return criteriaBuilder.or(fullNamePredicate, emailPredicate);
        });
    }
}
