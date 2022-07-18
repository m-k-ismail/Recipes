package com.abn.recipe.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "recipe")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "number_of_servings")
    private Integer numberOfServings;
    @Column(name = "instructions")
    private String instructions;
    @Type(type = "com.vladmihalcea.hibernate.type.search.PostgreSQLTSVectorType")
    @Column(name = "instructions_tsv")
    private String instructionsTSV;
    @Column(name = "type")
    private String type;
    @OneToMany(targetEntity = IngredientEntity.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "recipe_id", referencedColumnName = "id")
    private List<IngredientEntity> ingredients;
}