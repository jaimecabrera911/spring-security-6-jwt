package com.springjwtauthorization.services;

import com.springjwtauthorization.dtos.ProductReqDto;
import com.springjwtauthorization.dtos.ProductResDto;
import com.springjwtauthorization.mappers.ProductMapper;
import com.springjwtauthorization.repositories.ProductEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductEntityRepository productEntityRepository;
    private final ProductMapper productMapper;


    @Override
    public List<ProductResDto> getProducts() {
        return productEntityRepository.findAll().stream().map(productMapper::toResDto).toList();
    }

    @Override
    public void saveProduct(ProductReqDto productReqDto) {
        productEntityRepository.save(productMapper.toEntity(productReqDto));
    }
}
