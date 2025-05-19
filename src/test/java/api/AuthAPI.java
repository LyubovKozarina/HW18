package api;

import models.LoginRequestModel;
import models.LoginResponseModel;
import specs.LoginSpec;
import tests.TestData;


import static io.restassured.RestAssured.given;

public class AuthAPI{

    public static LoginResponseModel login() {
        LoginRequestModel request = new LoginRequestModel(TestData.USERNAME,TestData.PASSWORD);
        return
                given(LoginSpec.request)
                        .body(request)
                        .when()
                        .post("/Account/v1/Login")
                        .then()
                        .spec(LoginSpec.response)
                        .extract().as(LoginResponseModel.class);
    }
}