package com.codingdojo.productosycategorias.controllers;

import com.codingdojo.productosycategorias.models.Category;
import com.codingdojo.productosycategorias.models.Product;
import com.codingdojo.productosycategorias.services.CategoryServices;
import com.codingdojo.productosycategorias.services.ProductServices;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
public class AplicationController {
    private final CategoryServices categoryServices;
    private final ProductServices productServices;

    public AplicationController(CategoryServices categoryServices, ProductServices productServices){
        this.categoryServices = categoryServices;
        this.productServices = productServices;
    }

    @GetMapping("/")
    public String renderIndex(Model model){
        List<Product> productos = productServices.allProducts();
        List<Category> categorias = categoryServices.allCategories();
        model.addAttribute("productos", productos);
        model.addAttribute("categorias", categorias);
        return "index";
    }

    //Renderizar vista de formulario newproduct
    @GetMapping("/products/new")
    public String renderNewProduct(@ModelAttribute("producto") Product producto){
        return "newproduct";
    }

    //Post para crear productos
    @PostMapping("/products/new")
    public String postNewProduct(@Valid @ModelAttribute("producto") Product producto, BindingResult result){
        if(result.hasErrors()){
            return "newproduct";
        }else{
            productServices.crearProducto(producto);
            return "redirect:/";
        }
    }

    //Renderizar vista de formulario de newcategory
    @GetMapping("/category/new")
    public String renderNewCategory(@ModelAttribute("categoria") Category categoria){
        return "newcategory";
    }

    //Post para crear categoría
    @PostMapping("/category/new")
    public String postNewCategory(@Valid @ModelAttribute("categoria") Category categoria, BindingResult result){
        if(result.hasErrors()){
            return "newcategory";
        }else{
            categoryServices.crearCategoria(categoria);
            return "redirect:/";
        }
    }

    //Esto lo saqué de chat gpt, no sabia como hacer para mostrar las categorias que aun no habian sido asginadas en el producto
    @GetMapping("/products/{id}")
    public String renderDetailProduct(@PathVariable("id") Long id, Model model) {
        Product producto = productServices.buscarProducto(id);
        List<Category> categoriasDisponibles = categoryServices.buscarCategoriasDisponibles(id);


        model.addAttribute("producto", producto);
        model.addAttribute("categorias", categoriasDisponibles);

        return "detailproduct";
    }


    //Recibir la asignación de categoría
    @PostMapping("/products/{id}")
    public String recibirPostCategoria(@PathVariable("id") Long id, @RequestParam("categoriaId") Long categoriaId){
        Product product = productServices.buscarProducto(id);
        Category category = categoryServices.buscarCategoria(categoriaId);

        List<Category> categoriasDelProducto = product.getCategories();

        if(!categoriasDelProducto.contains(category)){
            categoriasDelProducto.add(category);
            productServices.crearProducto(product);
        }

        return "redirect:/products/"+id;

    }

}
