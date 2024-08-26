package com.icbt.ABC_Rest.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import com.icbt.ABC_Rest.entity.User;
import org.springframework.stereotype.Repository;
@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User findByEmail(String email);  // Find by email for login and other operations
}
