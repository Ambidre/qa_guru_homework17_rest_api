package gmail.anastasiacoder;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class RegresTest {

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "https://reqres.in/";
    }

    @Test
    void getListUsersPage2() {
        given()
                .when()
                .get("api/users?page=2")
                .then()
                .statusCode(200);
    }

    @Test
    void getSingleUser() {
        String response =
                get("api/users/2")
                        .then().log().all()
                        .extract().path("data.email");
        assertThat(response).isEqualTo("janet.weaver@reqres.in");
    }

    @Test
    void postCreateUser() {

        String data = "{ \"name\": \"Michael Lawson\", \"job\": \"QA Engineer\" }";

        given()
                .contentType(JSON)
                .body(data)
                .when()
                .post("api/users")
                .then().log().all()
                .statusCode(201)
                .body("name", is("Michael Lawson"),
                        "job", is("QA Engineer"),
                        "id", notNullValue(),
                        "createdAt", notNullValue());
    }

    @Test
    void deleteUser() {
        given()
                .when()
                .delete("api/users/2")
                .then().log().all()
                .statusCode(204);
    }

    @Test
    void putUpdateUser() {

        String data = "{ \"name\": \"Michael Lawson\", \"job\": \"QA Engineer\" }";

        given()
                .contentType(JSON)
                .body(data)
                .when()
                .put("api/users/2")
                .then().log().all()
                .statusCode(200)
                .body("name", is("Michael Lawson"),
                        "job", is("QA Engineer"),
                        "updatedAt", notNullValue());
    }

    @Test
    void patchUpdateUser() {

        String data = "{ \"name\": \"Michael Lawson\", \"job\": \"QA Engineer\" }";

        given()
                .contentType(JSON)
                .body(data)
                .when()
                .patch("api/users/2")
                .then().log().all()
                .statusCode(200)
                .body("name", is("Michael Lawson"),
                        "job", is("QA Engineer"),
                        "updatedAt", notNullValue());
    }
}