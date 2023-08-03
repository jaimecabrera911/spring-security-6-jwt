package com.springjwtauthorization.mappers;

import com.springjwtauthorization.dtos.UserReqDto;
import com.springjwtauthorization.dtos.UserResDto;
import com.springjwtauthorization.entities.UserEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    UserEntity toEntity(UserReqDto userReqDto);

    UserReqDto toDto(UserEntity userEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserEntity partialUpdate(UserReqDto userReqDto, @MappingTarget UserEntity userEntity);

    UserResDto toResDto(UserEntity userEntity);
}