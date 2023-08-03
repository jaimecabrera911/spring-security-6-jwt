package com.springjwtauthorization.services;

import com.springjwtauthorization.dtos.ProductReqDto;
import com.springjwtauthorization.dtos.ProductResDto;

import java.util.List;

public interface ProductService {

    List<ProductResDto> getProducts();

    void saveProduct(ProductReqDto productReqDto);
}
