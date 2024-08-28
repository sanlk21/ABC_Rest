package com.icbt.ABC_Rest.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.icbt.ABC_Rest.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, String> {  // Use String as the type for primary key

    User findByEmail(String email);  // Find by email for login and other operations
}
