package com.taxdoo.api.testing.users;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.testng.Assert.assertEquals;

public class CreateUserApiTest extends UsersApiTestBase {

    @Test(description = "Create an user with all valid details")
    public void testCreateUser_ok(){
        String name = getName();
        String userJson = buildUserDetails(name, name+"@taxdoo.com", getGender(), "active");

        Response response = requestSpecification()
                                .body(userJson)
                                .contentType(ContentType.JSON)
                                .post(USERS_URI);

        assertEquals(response.getStatusCode(), 201);
        assertEquals(response.getBody().jsonPath().getString("name"), name);

        int userId = response.getBody().jsonPath().getInt("id");
        verifyUser(userId);
        deleteUser(userId);
    }

    @Test(description = "Create account with no request body")
    public void testCreateUser_noRequestBody(){
        requestSpecification()
                .post(USERS_URI)
                .then()
                .statusCode(422)
                .and().assertThat().body("size()", is(4))
                .and().assertThat().body("field", hasItems("email", "name", "gender", "status"))
                .and().assertThat().body("message", hasItems("can't be blank", "can't be blank, can be male of female"));
    }

    @Test(description = "Create user with request body containing empty gender value")
    public void testCreateUser_genderEmpty(){
        String name = getName();
        String userJson = buildUserDetails(name, name+"@taxdoo.com", null, "active");
        requestSpecification()
                .body(userJson)
                .contentType(ContentType.JSON)
                .post(USERS_URI)
                .then()
                .statusCode(422)
                .and().assertThat().body("size()", is(1))
                .and().assertThat().body("field", hasItem("gender"))
                .and().assertThat().body("message", hasItem("can't be blank, can be male of female"));
    }

    @Test(description = "Create user api with no authentication")
    public void testCreateUser_withoutAuthentication(){
        given()
                .post(USERS_URI)
                .then()
                .statusCode(401);
    }
}
