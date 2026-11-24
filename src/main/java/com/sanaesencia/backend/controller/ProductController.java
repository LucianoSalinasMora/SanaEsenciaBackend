package com.sanaesencia.backend.controller;

import com.sanaesencia.backend.dto.ProductDto;
import com.sanaesencia.backend.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Catálogo de Productos", description = "Productos terapéuticos y naturales")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Listar productos disponibles", description = "Devuelve el catálogo de productos activos")
    @GetMapping
    public ResponseEntity<List<ProductDto>> getProducts() {
        return ResponseEntity.ok(productService.getAvailableProducts());
    }

    @Operation(summary = "Crear o actualizar producto", description = "Agrega un nuevo producto o actualiza uno existente")
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {
        ProductDto saved = productService.createOrUpdateProduct(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @Operation(summary = "Eliminar producto", description = "Elimina un producto por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
