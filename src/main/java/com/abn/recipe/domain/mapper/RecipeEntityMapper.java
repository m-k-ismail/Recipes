package com.abn.recipe.domain.mapper;

import com.abn.recipe.domain.entity.RecipeEntity;
import com.abn.recipe.domain.model.RecipeBo;
import org.mapstruct.Mapper;

@Mapper(implementationName ="RecipeEntityMapperImpl" ,componentModel = "spring")
public interface RecipeEntityMapper {

    RecipeBo mapToBo(RecipeEntity recipeEntity);

    RecipeEntity mapToEntity (RecipeBo recipeBo);
}
