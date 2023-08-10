package com.codingdojo.productosycategorias.services;

import com.codingdojo.productosycategorias.models.Category;
import com.codingdojo.productosycategorias.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServices {
    private final CategoryRepository categoryRepository;
    public CategoryServices(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    //Crear categoria
    public Category crearCategoria(Category category){
        return categoryRepository.save(category);
    }

    //Traer todas las categorias
    public List<Category> allCategories(){
        return categoryRepository.findAll();
    }

    //Buscar categoria por id
    public Category buscarCategoria(Long id){
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        return optionalCategory.orElse(null);
    }
}
