package api;

import models.LoginRequestModel;
import models.LoginResponseModel;
import specs.LoginSpec;


import static io.restassured.RestAssured.given;
import static tests.TestData.*;

public class AuthAPI{

    public static LoginResponseModel login() {
        LoginRequestModel request = new LoginRequestModel(login_demoqa,password_demoqa);
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