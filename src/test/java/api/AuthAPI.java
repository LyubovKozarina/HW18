package api;

import models.LoginRequestModel;
import models.LoginResponseModel;
import specs.LoginSpec;
import utils.TestData;

import static io.restassured.RestAssured.given;

public class AuthAPI{

    public static LoginResponseModel login() {
        LoginRequestModel request = new LoginRequestModel(TestData.login,TestData.password);
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