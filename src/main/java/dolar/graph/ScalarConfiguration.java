package dolar.graph;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import graphql.scalars.ExtendedScalars;
import graphql.schema.GraphQLScalarType;

@Configuration
public class ScalarConfiguration {
    @Bean
    public GraphQLScalarType dateType() {
        return ExtendedScalars.Date;
    }
}