import Service.BaseApiTest;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import schema.*;

import java.util.List;

public class ApiTests extends BaseApiTest {

    @Test
    //Проверка авторизации незарегистрированного пользователя
    public void unregisteredUserTest(){
        preparePost(new RegLogObj("UnregisteredUser", "UnregisteredUserPass"), TestPages.LOGIN)
                .then().assertThat().statusCode(401);
    }

    @Test
    //Проверка авторизации зарегистрированного пользователя с неверным паролем
    public void wrongPassTest(){
        preparePost(new RegLogObj("UniqueUser", "WrongPass"), TestPages.LOGIN)
                .then().assertThat().statusCode(401);
    }

    @Test
    //Проверка получения списка продуктов
    public void getProductListTest(){
        prepareGet(TestPages.PRODUCTS)
                .then().assertThat().statusCode(200)
                .extract().as(new TypeRef<List<ProductObj>>() {});

    }

    @Test
    //Проверка поиска продукта по id
    public void searchProductByIdTest(){
            prepareGet(TestPages.PRODUCTS + "/1")
                    .then().assertThat().statusCode(200)
                    .extract().as(new TypeRef<List<ProductObj>>() {});
    }

    @Test
    //Проверка поиска несуществующего продукта по id
    public void searchProductByWrongIdTest() {
        String message = prepareGet(TestPages.PRODUCTS + "/0")
                .then().assertThat().statusCode(404)
                .extract().body().jsonPath().getString("message");

        Assertions.assertEquals(Messages.getProductNotFoundMessage(), message);
    }

    @Test
    //Проверка получения карты клиента
    public void getProductCartTest() {

        //preparePost(new AddProductToCard(1,5), TestPages.CART);
        prepareGet(TestPages.CART)
                .then().assertThat().statusCode(200)
                .extract().as(CartObj.class);
    }

    @Test
    //Проверка добавления существующего продукта в карточку клиента
    public void postProductInCartTest() {
        String message = preparePost(new AddProductObj(1,5), TestPages.CART)
                .then().assertThat().statusCode(201)
                .extract().body().jsonPath().getString("message");

        Assertions.assertEquals(Messages.getProductAddedMessage(), message);

    }

    @Test
    //Проверка добавления в карточку клиента несуществующего продукта
    public void postNonExistingProductInCartTest() {
        String message = preparePost(new AddProductObj(0,5), TestPages.CART)
                .then().assertThat().statusCode(404)
                .extract().body().jsonPath().getString("message");
        Assertions.assertEquals(Messages.getProductNotFoundMessage(), message);
    }

    @Test
    //Проверка добавления продукта в карточку клиента, если клиент не авторизован
    public void postProductInCartWithWrongTokenTest(){
        RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .header(new Header( "Authorization", "Bearer " + "invalidToken"))
                .body(new AddProductObj(1,5))
                .post(TestPages.CART)
                .then().assertThat().statusCode(401);
    }

    @Test
    //Проверка удаления продукта, который был добавлен ранее
    public void deleteProductInCartTest() {
        int productId = 2;
        preparePost(new AddProductObj(productId,1), TestPages.CART);
        String message = prepareDelete(TestPages.CART + "/" + productId)
        .then().assertThat().statusCode(200)
        .extract().body().jsonPath().getString("message");

        Assertions.assertEquals(Messages.getProductRemovedMessage(),message);
    }

    @Test
    //Проверка удаления продукта, который не был добавлен в карточку клиента
    public void deleteNonExistingProductInCartTest() {
        int productId = 0;
        String message = prepareDelete(TestPages.CART + "/" + productId)
                .then().assertThat().statusCode(404)
                .extract().body().jsonPath().getString("message");

        Assertions.assertEquals(Messages.getProductNotFoundInCartMessage(),message);

    }

}
