package com.springjwtauthorization.mappers;

import com.springjwtauthorization.dtos.ProductReqDto;
import com.springjwtauthorization.dtos.ProductResDto;
import com.springjwtauthorization.entities.ProductEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {
    ProductEntity toEntity(ProductReqDto productReqDto);

    ProductReqDto toDto(ProductEntity productEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ProductEntity partialUpdate(ProductReqDto productReqDto, @MappingTarget ProductEntity productEntity);

    ProductResDto toResDto(ProductEntity productEntity);
}