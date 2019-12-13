import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class BasicTest {

    //public static authToken;
    //String getProductsURL = "https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/products";
    //String authorizationURL = "https://spree-vapasi-prod.herokuapp.com/spree_oauth/token";
    String authToken = null;
    //String addItemURL = "https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/cart/add_item";


    @Test
    public void testStatusCode()
    {

        given()
                .get("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/products")
                .then()
                .statusCode(200);
    }

    @Test
    public void testLogging()
    {
        given()
                .log().all()
                .get("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/products");

    }
    @Test
    public void printResponse()
    {
        Response res = given().when()
                .log().all()
                .get("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/products");
        /*System.out.println(res.asString());
        System.out.println("************");*/
        System.out.println(res.prettyPrint());

    }

    @Test
    public void testCurrency()
    {
        /*given()
                .get("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/products")
                .then()
                .body("data[0].attributes.currency",equalTo("USD"));*/
        Response res = given()
                .get("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/products");
        JsonPath jsonPathEvaluator = res.jsonPath();
        List<Map> products = jsonPathEvaluator.getList("data");

        for(Map product : products)
        {

            Map attributes = (Map) product.get("attributes");
            System.out.println(attributes.get("currency"));
            Assert.assertTrue(attributes.get(("currency")).equals("USD"));
            //Assert.assertEquals(attributes.get("currency").toString(), "USD");
        }

    }
    @Test
    public void testFilter()
    {
        Response resp = given()

                .log().all()
                .queryParam("filter[name]","bag")
                .get("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/products");
        System.out.println(resp.prettyPeek());

    }
    @Test
    public void filterPrice()
    {
        Response respp = given()
                .log().all()
                .queryParam("filter[price]", "$22.99" )
                .get("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/products");
        System.out.println(respp.prettyPeek());

    }
    @Test
    public void filterIds()
    {
        Response ress = given()
                .log().all()
                .queryParam("filter[ids]", "12")
                .get("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/products");
        System.out.println(ress.prettyPeek());
    }

    @Test
    public void tshirtColor()
    {
        Response reso = given()
                .log().all()
                .queryParam("filter[options][tshirt-color]", "M","Red")
                .get("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/products");
        System.out.println(reso.prettyPeek());

    }
    @Test
    public void filterSku()
    {
        Response ree = given()
                .log().all()
                .queryParam("filter[skus]","true")
                .get("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/products");
        System.out.println(ree.prettyPeek());

    }
    @Test
    public void multipleFilter()
    {
        List<String> par = new ArrayList<String>();
        par.add("bag");
        par.add("mug");
        Response ree = given()
                .log().all()
                .queryParams("filter[name]",par)
                .get("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/products");
        System.out.println(ree.prettyPeek());

    }
    @BeforeClass
    @Test
    public void authToken()
    {
        Response ree = given()
                .formParam("grant_type", "password")
                .formParam("username", "beenabioinfo@gmail.com")
                .formParam("password","abc123")
                .post("https://spree-vapasi-prod.herokuapp.com/spree_oauth/token");
        authToken = "Bearer " + ree.path("access_token");
        System.out.println(ree.prettyPeek());

    }
    //FQQbcs3J5H31NCUiO-TQGr_McRE1gUbW4c-_6SUUbkQ
    //  "access_token": "_18gwLP__vEf9d81bs1Xq4ucLkyQiw8MstdyKtDSQnA"
    @Test
    //Add item to the cart
    public void testPostCal()
    {
        Map <String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", authToken);
        String createBody =
                "{\n" +
        "  \"variant_id\": \"17\",\n" +
                "  \"quantity\": 5\n" +
                "}";

        Response rse = given()
                .headers(headers)
                .body(createBody)
                .when()
                .post("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/cart/add_item");
                Assert.assertEquals(rse.statusCode(), 200);
                System.out.println(rse.prettyPeek());

    }
    @Test
    public void addSecItem()
    {
        Map<String, String> head = new HashMap<String, String>();
        head.put("Content-Type", "application/json");
        head.put("Authorization", authToken);
        String reqBody = "{\n" +
                "  \"variant_id\": \"2\",\n" +
                "  \"quantity\": 5\n" +
                "}";
        Response r = given()
                .headers(head)
                .body(reqBody)
                .when()
                .post("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/cart/add_item");
                Assert.assertEquals(r.statusCode(), 200);
                System.out.println(r.prettyPeek());

    }
    @Test
    public void viewCart()
    {
        Map<String,String> h = new HashMap<String, String>();
        h.put("Authorization", authToken);

        Response rr = given()
                .headers(h)
                .get("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/cart");
                Assert.assertEquals(rr.statusCode(), 200);
        System.out.println(rr.prettyPeek());

    }
    @Test
    public void deleteItem()
    {

        Map<String, String> header = new HashMap<String, String>();
        header.put("Content-Type", "application/json");
        header.put("Authorization", authToken);

        Response re = given()
                .headers(header)
                .when()
                .delete("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/cart/remove_line_item/358");
                Assert.assertEquals(re.statusCode(), 200);
        System.out.println(re.prettyPeek());


    }




}
