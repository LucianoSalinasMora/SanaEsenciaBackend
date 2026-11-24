package com.sanaesencia.backend.service;

import com.sanaesencia.backend.dto.ProductDto;
import com.sanaesencia.backend.entity.Product;
import com.sanaesencia.backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductDto> getAvailableProducts() {
        return productRepository.findByIsAvailableTrue().stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional
    public ProductDto createOrUpdateProduct(ProductDto dto) {
        Product product;
        if (dto.getId() != null && productRepository.existsById(dto.getId())) {
            product = productRepository.findById(dto.getId()).get();
            product.setName(dto.getName());
            product.setDescription(dto.getDescription());
            product.setPrice(dto.getPrice());
            if (dto.getIsAvailable() != null) product.setIsAvailable(dto.getIsAvailable());
            if (dto.getImageUrl() != null) product.setImageUrl(dto.getImageUrl());
            if (dto.getStock() != null) product.setStock(dto.getStock());
        } else {
            product = Product.builder()
                    .name(dto.getName())
                    .description(dto.getDescription())
                    .price(dto.getPrice())
                    .isAvailable(dto.getIsAvailable() != null ? dto.getIsAvailable() : true)
                    .imageUrl(dto.getImageUrl() != null ? dto.getImageUrl() : "")
                    .stock(dto.getStock() != null ? dto.getStock() : 10)
                    .build();
        }

        return toDto(productRepository.save(product));
    }

    @Transactional
    public void deleteProduct(Integer id) {
        if (id != null && productRepository.existsById(id)) {
            productRepository.deleteById(id);
        }
    }

    public ProductDto toDto(Product p) {
        return ProductDto.builder()
                .id(p.getId())
                .name(p.getName())
                .description(p.getDescription())
                .price(p.getPrice())
                .isAvailable(p.getIsAvailable())
                .imageUrl(p.getImageUrl())
                .stock(p.getStock())
                .build();
    }
}
