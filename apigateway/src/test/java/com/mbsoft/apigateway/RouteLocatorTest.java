package com.mbsoft.apigateway;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = {
        "spring.main.allow-bean-definition-overriding=true"
})
public class RouteLocatorTest {

    @Autowired
    private RouteLocator routeLocator;

    @Test
    public void testProductCodeServiceRoute() {
        List<Route> routes = routeLocator.getRoutes().collectList().block();
        
        assertThat(routes).isNotNull();
        assertThat(routes).anyMatch(route -> 
            route.getId().equals("product-code-service") &&
            route.getUri().toString().equals("http://localhost:8081") &&
            route.getPredicate().toString().contains("/api/product-codes/**")
        );
    }

    @Test
    public void testMedicationServiceRoute() {
        List<Route> routes = routeLocator.getRoutes().collectList().block();
        
        assertThat(routes).isNotNull();
        assertThat(routes).anyMatch(route -> 
            route.getId().equals("medication-service") &&
            route.getUri().toString().equals("http://localhost:8082") &&
            route.getPredicate().toString().contains("/api/medications/**") &&
            route.getPredicate().toString().contains("/api/medication-types/**")
        );
    }
}