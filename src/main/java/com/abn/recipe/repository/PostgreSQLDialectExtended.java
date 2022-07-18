package com.abn.recipe.repository;

import org.hibernate.dialect.PostgreSQL10Dialect;

public class PostgreSQLDialectExtended extends PostgreSQL10Dialect {

    public PostgreSQLDialectExtended() {
        super();
        this. registerFunction("fts", new PostgreSQLFTSFunction());
    }
}