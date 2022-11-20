package com.taxdoo.api.testing;

import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class TestBase {

    protected static final String API_ACCESS_TOKEN="03ad9453f86265952c15dfee562b06552d909055495fd2f746c6eca9c2db7705";
    protected static final String USERS_URI="https://gorest.co.in/public/v2/users/";
    protected static final String USER_POSTS_API = "https://gorest.co.in/public/v2/users/USER_ID/posts/";

    protected RequestSpecification requestSpecification(){
        return given().auth()
                .preemptive()
                .oauth2(API_ACCESS_TOKEN);
    }

}
