package de.andre.repository.catalog;

import de.andre.entity.catalog.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryHRepository extends JpaRepository<Category, String> {
}