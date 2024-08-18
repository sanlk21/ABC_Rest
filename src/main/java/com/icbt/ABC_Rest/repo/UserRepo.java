package com.icbt.ABC_Rest.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.icbt.ABC_Rest.entity.User;

public interface UserRepo extends JpaRepository<User, Long> {
}
