package com.abn.recipe.repository;

import com.abn.recipe.domain.entity.IngredientEntity;
import com.abn.recipe.domain.entity.IngredientEntity_;
import com.abn.recipe.domain.entity.RecipeEntity;
import com.abn.recipe.domain.entity.RecipeEntity_;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

import java.util.Collections;
import java.util.Set;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RecipeSpecificationTest {

    @Mock
    private CriteriaBuilder criteriaBuilderMock;
    @Mock
    private CriteriaQuery criteriaQueryMock;
    @Mock
    private Root<RecipeEntity> recipeEntityRootMock;
    @Mock
    private Root<IngredientEntity> ingredientEntityRootMock;

    @Test
    public void should_return_equal_query_for_recipe_type() {
        // given
        String RecipeType = "VEGETARIAN";

        // when
        Specification<RecipeEntity> recipeEntitySpecification = RecipeSpecification.equalType(RecipeType);
        recipeEntitySpecification
                .toPredicate(recipeEntityRootMock, criteriaQueryMock, criteriaBuilderMock);

        // then
        verify(criteriaBuilderMock, times(1))
                .equal(recipeEntityRootMock.get(RecipeEntity_.TYPE), RecipeType);
        verifyNoMoreInteractions(criteriaBuilderMock);
    }

    @Test
    public void should_return_conjunction_for_recipe_type() {
        // given
        String RecipeType = null;

        // when
        Specification<RecipeEntity> recipeEntitySpecification = RecipeSpecification.equalType(RecipeType);
        recipeEntitySpecification
                .toPredicate(recipeEntityRootMock, criteriaQueryMock, criteriaBuilderMock);

        // then
        verify(criteriaBuilderMock, times(0))
                .equal(recipeEntityRootMock.get(RecipeEntity_.TYPE), RecipeType);
    }

    @Test
    public void should_return_equal_query_for_recipe_number_of_servings() {
        // given
        Integer numberOfServings = 1;

        // when
        Specification<RecipeEntity> recipeEntitySpecification = RecipeSpecification.equalNumberOfServings(numberOfServings);
        recipeEntitySpecification
                .toPredicate(recipeEntityRootMock, criteriaQueryMock, criteriaBuilderMock);

        // then
        verify(criteriaBuilderMock, times(1))
                .equal(recipeEntityRootMock.get(RecipeEntity_.NUMBER_OF_SERVINGS), numberOfServings);
        verifyNoMoreInteractions(criteriaBuilderMock);
    }

    @Test
    public void should_return_conjunction_for_recipe_number_of_servings() {
        // given
        Integer numberOfServings = null;

        // when
        Specification<RecipeEntity> recipeEntitySpecification = RecipeSpecification.equalNumberOfServings(numberOfServings);
        recipeEntitySpecification
                .toPredicate(recipeEntityRootMock, criteriaQueryMock, criteriaBuilderMock);

        // then
        verify(criteriaBuilderMock, times(0))
                .equal(recipeEntityRootMock.get(RecipeEntity_.NUMBER_OF_SERVINGS), numberOfServings);
    }

    @Test
    public void should_return_in_query_for_recipe_included_ingredients() {
        // given
        Set<String> includedIngredients = Set.of("potato", "tomato");
        Join recipeIngredientsJoinMock = mock(Join.class);
        when(recipeEntityRootMock.join(RecipeEntity_.INGREDIENTS)).thenReturn(recipeIngredientsJoinMock);
        Path recipeIngredientNamesPathMock = mock(Path.class);
        when(recipeIngredientsJoinMock.get(IngredientEntity_.NAME)).thenReturn(recipeIngredientNamesPathMock);
        CriteriaBuilder.In inRecipeIngredientNamesMock = mock(CriteriaBuilder.In.class);
        when(criteriaBuilderMock.in(recipeIngredientNamesPathMock)).thenReturn(inRecipeIngredientNamesMock);

        // when
        Specification<RecipeEntity> recipeEntitySpecification = RecipeSpecification.includeIngredients(includedIngredients);
        recipeEntitySpecification
                .toPredicate(recipeEntityRootMock, criteriaQueryMock, criteriaBuilderMock);

        // then
        verify(criteriaBuilderMock, times(1))
                .in(recipeEntityRootMock.join(RecipeEntity_.INGREDIENTS).get(IngredientEntity_.NAME));
        verifyNoMoreInteractions(criteriaBuilderMock);
    }

    @Test
    public void should_return_conjunction_for_recipe_included_ingredients() {
        // given
        Set<String> includedIngredients = Collections.emptySet();

        // when
        Specification<RecipeEntity> recipeEntitySpecification = RecipeSpecification.includeIngredients(includedIngredients);
        recipeEntitySpecification
                .toPredicate(recipeEntityRootMock, criteriaQueryMock, criteriaBuilderMock);

        // then
        verify(criteriaBuilderMock, times(0))
                .in(recipeEntityRootMock.join(RecipeEntity_.INGREDIENTS));
    }

    @Test
    public void should_return_query_for_recipe_excluded_ingredients() {
        // given
        Set<String> excludedIngredients = Set.of("potato", "tomato");
        Subquery subqueryMock = mock(Subquery.class);
        when(criteriaQueryMock.subquery(IngredientEntity.class)).thenReturn(subqueryMock);
        when(subqueryMock.from(IngredientEntity.class)).thenReturn(ingredientEntityRootMock);
        when(subqueryMock.select(ingredientEntityRootMock.get(IngredientEntity_.RECIPE_ID)))
                .thenReturn(subqueryMock);
        CriteriaBuilder.In inRecipeIngredientNamesMock = mock(CriteriaBuilder.In.class);
        Path ingredientNamePathMock = ingredientEntityRootMock.get(IngredientEntity_.NAME);
        when(ingredientEntityRootMock.get(IngredientEntity_.NAME)).thenReturn(ingredientNamePathMock);
        when(criteriaBuilderMock.in(ingredientEntityRootMock.get(IngredientEntity_.NAME)))
                .thenReturn(inRecipeIngredientNamesMock);
        Path recipeIdPathMock = mock(Path.class);
        when(recipeEntityRootMock.get(RecipeEntity_.ID)).thenReturn(recipeIdPathMock);
        Predicate notPredicateMock = mock(Predicate.class);
        when(criteriaBuilderMock.not(recipeIdPathMock.in(subqueryMock)))
                .thenReturn(notPredicateMock);
        // when
        Specification<RecipeEntity> recipeEntitySpecification = RecipeSpecification.excludeIngredients(excludedIngredients);
        recipeEntitySpecification
                .toPredicate(recipeEntityRootMock, criteriaQueryMock, criteriaBuilderMock);

        // then
        verify(criteriaBuilderMock, times(1))
                .in(ingredientNamePathMock);
        verify(criteriaBuilderMock, times(1))
                .not(recipeIdPathMock.in(subqueryMock));
        verifyNoMoreInteractions(criteriaBuilderMock);
    }

    @Test
    public void should_return_conjunction_for_recipe_excluded_ingredients() {
        // given
        Set<String> excludedIngredients = Collections.emptySet();

        // when
        Specification<RecipeEntity> recipeEntitySpecification = RecipeSpecification.excludeIngredients(excludedIngredients);
        recipeEntitySpecification
                .toPredicate(recipeEntityRootMock, criteriaQueryMock, criteriaBuilderMock);

        // then
        verify(criteriaBuilderMock, times(0))
                .in(any(Path.class));
        verify(criteriaBuilderMock, times(0))
                .not(any(Path.class));
    }

    @Test
    public void should_return_fts_query_for_recipe_free_text() {
        // given
        String freeText = "oven";
        Expression ftsExpressionMock = mock(Expression.class);
        Path instructionsPath = mock(Path.class);
        when(recipeEntityRootMock.get(RecipeEntity_.INSTRUCTIONS)).thenReturn(instructionsPath);
        Expression literalFreeTextMock = mock(Expression.class);
        when(criteriaBuilderMock.literal(freeText)).thenReturn(literalFreeTextMock);
        when(criteriaBuilderMock
                .function(eq("fts"), eq(Boolean.class), any(Expression.class), any(Expression.class)))
                .thenReturn(ftsExpressionMock);
        // when
        Specification<RecipeEntity> recipeEntitySpecification = RecipeSpecification.ftsFreeText(freeText);
        recipeEntitySpecification
                .toPredicate(recipeEntityRootMock, criteriaQueryMock, criteriaBuilderMock);

        // then
        verify(criteriaBuilderMock, times(1))
                .function("fts", Boolean.class, instructionsPath, literalFreeTextMock);
        verify(criteriaBuilderMock, times(1))
                .isTrue(ftsExpressionMock);
        verifyNoMoreInteractions(criteriaBuilderMock);
    }
}
