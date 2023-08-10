package com.codingdojo.productosycategorias.services;

import com.codingdojo.productosycategorias.models.Product;
import com.codingdojo.productosycategorias.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServices {
    public final ProductRepository productRepository;
    public ProductServices(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    //Crear producto
    public Product crearProducto(Product product){
        return productRepository.save(product);
    }

    //Traer todos los productos
    public List<Product> allProducts(){
        return productRepository.findAll();
    }

    //Buscar producto por id
    public Product buscarProducto(Long id){
        Optional<Product> optionalProduct = productRepository.findById(id);

        return optionalProduct.orElse(null);
    }
}
