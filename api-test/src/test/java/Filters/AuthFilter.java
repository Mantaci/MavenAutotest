package Filters;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public class AuthFilter implements Filter {

    private String token;

    public AuthFilter(String token) {
        this.token = token;
    }

    @Override
    public Response filter (FilterableRequestSpecification filterableRequestSpecification, FilterableResponseSpecification filterableResponseSpecification, FilterContext filterContext) {

        filterableRequestSpecification.header(new Header("Authorization", "Bearer " + token));

        return filterContext.next(filterableRequestSpecification, filterableResponseSpecification);

    }
}
