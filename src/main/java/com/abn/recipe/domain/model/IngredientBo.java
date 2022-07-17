package com.abn.recipe.domain.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@ToString
public class IngredientBo {

    private String name;
    private Integer quantity;
}
