package com.abn.recipe.domain.mapper;


import com.abn.model.Recipe;
import com.abn.recipe.domain.model.RecipeBo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(implementationName ="RecipeBoMapperImpl" ,componentModel = "spring")
public interface RecipeBoMapper {

    Recipe mapToModel(RecipeBo recipeBo);

    RecipeBo mapToBo (Recipe recipe);

    List<Recipe> mapToModelList(List<RecipeBo> recipeBos);
}
