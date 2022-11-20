package com.taxdoo.api.testing.users;

import com.taxdoo.api.testing.TestBase;
import org.json.JSONObject;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.hamcrest.Matchers.is;

public class UsersApiTestBase extends TestBase {

    protected String getGender(){
        List<String> genders = Arrays.asList("male", "female");
        Random random = new Random();

        int randomIndex = random.nextInt(genders.size()-1);
        return genders.get(randomIndex);
    }

    protected String getName(){
        return RandomStringUtils.randomAlphabetic(10);
    }

    protected String buildUserDetails(String name, String email, String gender, String status){
        JSONObject userJson = new JSONObject();
        userJson.put("name", name );
        userJson.put("email", email);
        userJson.put("gender", gender);
        userJson.put("status", status);
        return userJson.toString();
    }

    protected void verifyUser(int userId){

        requestSpecification()
                .get(USERS_URI+userId)
                .then()
                .statusCode(200)
                .assertThat()
                .body("id", is(userId));
    }

    protected void deleteUser(int userId){
        requestSpecification()
                .delete(USERS_URI+userId)
                .then()
                .statusCode(204);
    }
}
