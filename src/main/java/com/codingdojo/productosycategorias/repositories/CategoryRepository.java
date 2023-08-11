package com.codingdojo.productosycategorias.repositories;

import com.codingdojo.productosycategorias.models.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {
    List<Category> findAll();

    @Query("SELECT c FROM Category c LEFT JOIN c.products pc ON pc.id = :productId WHERE pc.id IS NULL")
    List<Category> findCategoriesWithoutProduct(Long productId);
}
