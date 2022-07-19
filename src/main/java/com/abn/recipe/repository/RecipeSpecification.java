package com.abn.recipe.repository;

import com.abn.recipe.domain.entity.IngredientEntity;
import com.abn.recipe.domain.entity.IngredientEntity_;
import com.abn.recipe.domain.entity.RecipeEntity;
import com.abn.recipe.domain.entity.RecipeEntity_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.Collection;
import java.util.Set;

/**
 * A JPA Specification class to handle Recipe filtering queries
 *
 * @author Mohammad Ismail
 */
public class RecipeSpecification {

    /**
     * Handles the query of filtering recipes according to the recipe type
     *
     * @param type recipe type
     * @return the {@link Specification} of {@link RecipeEntity}
     */
    public static Specification<RecipeEntity> equalType(String type) {
        return (root, query, criteriaBuilder) -> {
            if (isEmptyString(type)) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.equal(root.get(RecipeEntity_.TYPE), type);
        };
    }

    /**
     * Handles the query of filtering recipes according to the recipe numberOfServings
     *
     * @param numberOfServings recipe numberOfServings
     * @return the {@link Specification} of {@link RecipeEntity}
     */
    public static Specification<RecipeEntity> equalNumberOfServings(Integer numberOfServings) {
        return (root, query, criteriaBuilder) -> {
            if (numberOfServings == null) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.equal(root.get(RecipeEntity_.NUMBER_OF_SERVINGS), numberOfServings);
        };
    }

    /**
     * Handles the query of filtering recipes according to the recipe included ingredient names
     *
     * @param ingredientNames recipe included ingredient names
     * @return the {@link Specification} of {@link RecipeEntity}
     */
    public static Specification<RecipeEntity> includeIngredients(Set<String> ingredientNames) {
        return (root, query, criteriaBuilder) -> {
            if (isEmptyCollection(ingredientNames)) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.in(root.join(RecipeEntity_.INGREDIENTS).get(IngredientEntity_.NAME)).value(ingredientNames);
        };
    }

    /**
     * Handles the query of filtering recipes according to the recipe excluded ingredient names
     *
     * @param ingredientNames recipe excluded ingredient names
     * @return the {@link Specification} of {@link RecipeEntity}
     */
    public static Specification<RecipeEntity> excludeIngredients(Set<String> ingredientNames) {
        return (root, query, criteriaBuilder) -> {
            if (isEmptyCollection(ingredientNames)) {
                return criteriaBuilder.conjunction();
            }

            Subquery<IngredientEntity> ingredientQuery = query.subquery(IngredientEntity.class);
            Root<IngredientEntity> subQueryRoot = ingredientQuery.from(IngredientEntity.class);

            Predicate ingredientNamePredicate = criteriaBuilder
                    .in(subQueryRoot.get(IngredientEntity_.NAME)).value(ingredientNames);

            ingredientQuery.select(subQueryRoot.get(IngredientEntity_.RECIPE_ID)).where(ingredientNamePredicate);

            return criteriaBuilder.not(root.get(RecipeEntity_.ID).in(ingredientQuery));
        };
    }

    /**
     * Handles the query of filtering recipes according to the recipe instructions. This uses the PostgreSQL
     * full text search
     *
     * @param freeText recipe instructions free text
     * @return the {@link Specification} of {@link RecipeEntity}
     */
    public static Specification<RecipeEntity> ftsFreeText(String freeText) {
        return (root, query, criteriaBuilder) -> {
            if (isEmptyString(freeText)) {
                return criteriaBuilder.conjunction();
            }


            Expression<Boolean> fts = criteriaBuilder
                    .function("fts", Boolean.class,
                            root.get(RecipeEntity_.INSTRUCTIONS),
                            criteriaBuilder.literal(freeText));

            return criteriaBuilder.isTrue(fts);
        };
    }

    /**
     * Checks if a string is empty
     *
     * @param searchParam any search parameter
     * @return true if the input string is empty otherwise it returns false
     */
    private static boolean isEmptyString(String searchParam) {
        return searchParam == null || " ".equals(searchParam);
    }

    /**
     * Checks if a collection is empty
     *
     * @param collection any collection
     * @return true if the input collection is empty otherwise it returns false
     */
    private static boolean isEmptyCollection(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }
}
