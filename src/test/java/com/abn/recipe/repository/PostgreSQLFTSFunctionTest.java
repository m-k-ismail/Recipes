package com.abn.recipe.repository;

import com.abn.recipe.domain.entity.RecipeEntity_;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.type.BooleanType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class PostgreSQLFTSFunctionTest {

    @Mock
    private SessionFactoryImpl sessionFactory;
    private PostgreSQLFTSFunction postgreSQLFTSFunction;

    @BeforeEach
    public void setup() {
        postgreSQLFTSFunction = new PostgreSQLFTSFunction();
    }

    @Test
    public void should_render_query() {
        // given
        String expectedQuery = "instructions @@ to_tsquery(?)";
        List<String> arguments = List.of(RecipeEntity_.INSTRUCTIONS, "oven");

        // when
        String query = postgreSQLFTSFunction.render(new BooleanType(), arguments, sessionFactory);

        // then
        Assertions.assertEquals(expectedQuery, query);
    }

    @Test
    public void should_fail_to_render_query() {
        // given
        List<String> arguments = List.of(RecipeEntity_.INSTRUCTIONS);

        // when
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () ->
                postgreSQLFTSFunction.render(new BooleanType(), arguments, sessionFactory));

        // then
        Assertions.assertEquals("The function must be passed at least 2 arguments", exception.getMessage());
    }
}
