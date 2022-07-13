package com.abn.recipe.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RecipeBo {

    private Long id;
    private String title;
    private LocalDate createdAt;
    private Integer numberOfServings;
    private String instructions;
    private TypeEnumBo type;
    private List<IngredientBo> ingredients;
}