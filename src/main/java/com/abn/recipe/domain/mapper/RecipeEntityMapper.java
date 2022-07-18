package com.abn.recipe.domain.mapper;

import com.abn.recipe.domain.entity.RecipeEntity;
import com.abn.recipe.domain.model.RecipeBo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(implementationName ="RecipeEntityMapperImpl" ,componentModel = "spring")
public interface RecipeEntityMapper {

    RecipeBo mapToBo(RecipeEntity recipeEntity);

    @Mapping(source = "instructions", target = "instructionsTSV")
    RecipeEntity mapToEntity (RecipeBo recipeBo);

    List<RecipeBo> mapToBoList(List<RecipeEntity> recipeEntities);

}
