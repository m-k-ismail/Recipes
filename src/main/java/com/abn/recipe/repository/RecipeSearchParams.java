package com.abn.recipe.repository;

import lombok.*;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RecipeSearchParams {
    private Integer numberOfServings;
    private String type;
    private Set<String> includedIngredient;
    private Set<String> excludedIngredient;
    private String freeText;
    private Integer pageLimit;
    private Integer pageOffset;

}
