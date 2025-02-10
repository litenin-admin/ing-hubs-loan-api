package com.inghubs.loan.repository;

import com.inghubs.loan.model.User;
import com.inghubs.loan.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    List<UserRole> findByUser(@Param("user") User user);

}
