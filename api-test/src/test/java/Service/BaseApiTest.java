package Service;

import Filters.AuthFilter;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import schema.RegLogObj;
import schema.TestPages;

public class BaseApiTest {

    @BeforeAll

    // Авторизация клиента в приложении + Получение токена

    public static void setUp() {
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.baseURI = "http://9b142cdd34e.vps.myjino.ru:49268";
        String TOKEN = RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(new RegLogObj("UniqueUser", "UniquePass"))
                .post(TestPages.LOGIN)
                .then().assertThat().statusCode(200).extract().body().jsonPath().getString("access_token");
        RestAssured.filters(new RequestLoggingFilter(System.out), new ResponseLoggingFilter(System.out), new AuthFilter(TOKEN));

    }


    public Response prepareGet(String getPage){
        return RestAssured.given()
                .accept(ContentType.JSON)
                .get(getPage);

    }

    public Response preparePost(Object postPageBody, String postPage ){
        return RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(postPageBody)
                .post(postPage);
    }
    public Response prepareDelete(String deletePage){
        return RestAssured.given()
                .accept(ContentType.JSON)
                .delete(deletePage);
    }
}
