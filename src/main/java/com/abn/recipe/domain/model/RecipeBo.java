package com.abn.recipe.domain.model;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@ToString
public class RecipeBo {

    private Long id;
    private String title;
    private LocalDate createdAt;
    private Integer numberOfServings;
    private String instructions;
    private TypeEnumBo type;
    private List<IngredientBo> ingredients;
}