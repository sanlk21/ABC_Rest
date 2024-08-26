package com.icbt.ABC_Rest.repo;

import com.icbt.ABC_Rest.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {

    // Custom query method to find a category by name (optional)
    Optional<Category> findByName(String name);
}
