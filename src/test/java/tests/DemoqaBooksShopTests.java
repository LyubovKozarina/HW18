package tests;

import api.AuthAPI;
import api.BookAPI;
import com.codeborne.selenide.Selenide;
import helpers.WithLogin;
import models.BookModel;
import models.LoginResponseModel;
import models.UserBooksResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;
import static tests.TestData.*;

public class DemoqaBooksShopTests extends TestBase {

    @Test
    @WithLogin
    @DisplayName("Удаление книги из профиля: проверка через API и UI")
    void deleteBookTest() {
        LoginResponseModel auth = step("Авторизация через API", AuthAPI::login);

        step("Очистка коллекции пользователя", () -> {
            BookAPI.deleteAllBooks(auth.getUserId(), auth.getToken());
        });

        step("Добавление книги через API", () -> {
            BookAPI.addBook(auth.getUserId(), auth.getToken(), isbn);
        });

        step("Проверка, что книга добавлена через API", () -> {
            UserBooksResponseModel books = BookAPI.getUserBooks(auth.getUserId(), auth.getToken());
            List<BookModel> userBooks = books.getBooks();
            assertThat(userBooks).extracting(BookModel::getIsbn).contains(isbn);
        });

        step("Переход на страницу профиля", () -> {
            open("/profile");
            $("#userName-value").shouldHave(text(login));
        });

        step("Удаление книги через API", () -> {
            BookAPI.deleteBook(auth.getUserId(),auth.getToken(), isbn);
        });

        step("Проверка через API, что книга удалена", () -> {
            UserBooksResponseModel books = BookAPI.getUserBooks(auth.getUserId(), auth.getToken());
            assertThat(books.getBooks()).noneMatch(book -> book.getIsbn().equals(isbn));
        });

        step("Обновление страницы и проверка UI", () -> {
            Selenide.refresh();
            $(".rt-noData").shouldBe(visible).shouldHave(text("No rows found"));
        });
    }
}
