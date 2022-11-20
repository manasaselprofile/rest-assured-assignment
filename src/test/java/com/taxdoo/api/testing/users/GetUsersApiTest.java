package com.taxdoo.api.testing.users;

import org.testng.annotations.Test;
import static org.hamcrest.Matchers.is;

public class GetUsersApiTest extends UsersApiTestBase {

    @Test(description = "Tet Get All users with no page criteria")
    public void testGetAllUsers_defaultSize(){
        requestSpecification()
                .when()
                .get(USERS_URI)
                .then()
                .statusCode(200)
                .assertThat()
                .body("size()", is(10));
    }

    @Test(description = "Get All users with count of results per page")
    public void testGetAllUsers_pageSize(){
        requestSpecification()
                .when()
                .queryParam("per_page", 30)
                .queryParam("page", 1)
                .get(USERS_URI)
                .then()
                .statusCode(200)
                .assertThat()
                .body("size()", is(30));
    }

    @Test(description = "Fetch user which is not existing")
    public void testGetUser_resourceNotFound(){
       requestSpecification()
                .when()
                .get(USERS_URI+"/9999")
               .then()
               .statusCode(404);
    }

}
