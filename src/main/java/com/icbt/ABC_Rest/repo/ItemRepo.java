package com.icbt.ABC_Rest.repo;

import com.icbt.ABC_Rest.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepo extends JpaRepository<Item, Long> {

    // Custom query method to find items by name, ignoring case
    List<Item> findByNameContainingIgnoreCase(String name);

    // Custom query method to find items by category id
    List<Item> findByCategoryId(Long categoryId);
}
