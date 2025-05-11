package api;

import io.restassured.response.Response;
import models.UserBooksResponseModel;

import static io.restassured.RestAssured.given;
import static specs.BookSpec.*;

public class BookAPI {

    public static void deleteAllBooks(String token, String userId) {
        given(request)
                .header("Authorization", "Bearer " + token)
                .queryParam("UserId", userId)
                .when()
                .delete("/BookStore/v1/Books")
                .then()
                .spec(response204);
    }

    public static void addBook(String token, String userId, String isbn) {
        String jsonBody = String.format("{\"userId\":\"%s\",\"collectionOfIsbns\":[{\"isbn\":\"%s\"}]}",
                userId, isbn);

        given(request)
                .header("Authorization", "Bearer " + token)
                .body(jsonBody)
                .when()
                .post("/BookStore/v1/Books")
                .then()
                .spec(response201);
    }

    public static void deleteBook(String token, String userId, String isbn) {
        String jsonBody = String.format("{\"isbn\":\"%s\",\"userId\":\"%s\"}",
                isbn,userId);

        given(request)
                .header("Authorization", "Bearer " + token)
                .body(jsonBody)
                .when()
                .delete("/BookStore/v1/Book")
                .then()
                .spec(response204);
    }

    public static UserBooksResponseModel getUserBooks(String token, String userId) {
        Response response = given(request)
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/Account/v1/User/" + userId)
                .then()
                .spec(response200)
                .extract().response();

        return response.as(UserBooksResponseModel.class);
    }
}
