import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class TestScenarios {

    String authToken = null;

    @BeforeClass

    @Test(priority = 0)

    public void token()
    {
        Response response = given()
                .formParam("grant_type", "password")
                .formParam("username", "beenabioinfo@gmail.com")
                .formParam("password", "abc123")
                .post("https://spree-vapasi-prod.herokuapp.com/spree_oauth/token");
        authToken = "Bearer " + response.path("access_token");
        System.out.println(response.prettyPeek());
    }

    @Test(priority = 1)
    public void productList()
    {
        Response respo = given()
                .when()
                .log().all()
                .get("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/products");
        System.out.println(respo.prettyPeek());

    }

    @Test(priority = 2)

    public void showProductDetails()
    {
        Map<String, String> authHead = new HashMap<String, String>();
        authHead.put("Authorization", authToken);

        Response resp = given()
                .headers(authHead)
                .get("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/products/3");
        Assert.assertEquals(resp.statusCode(), 200);
        System.out.println(resp.prettyPeek());

    }

    @Test(priority = 3)

    public void createCart()
    {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", authToken);
        headers.put("Content-Type", "application/json");

        Response res = given()
                .headers(headers)
                .post("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/cart");
        Assert.assertEquals(res.statusCode(), 201);
        System.out.println(res.prettyPeek());

    }
    @Test(priority = 4)

    public void addOneItem()
    {
        Map<String, String> head = new HashMap<String, String>();
        head.put("Authorization", authToken);
        head.put("Content-Type", "application/json");
        String reqBody = "{\n" +
                "  \"variant_id\": \"17\",\n" +
                "  \"quantity\": 5\n" +
                "}";

        Response re = given()
                .headers(head)
                .body(reqBody)
                .when()
                .post("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/cart/add_item");
        Assert.assertEquals(re.statusCode(), 200);
        System.out.println(re.prettyPeek());

    }
    @Test(priority = 5)

    public void addSecItem()
    {
        Map<String, String> head = new HashMap<String, String>();
        head.put("Authorization", authToken);
        head.put("Content-Type", "application/json");
        String reqBody = "{\n" +
                "  \"variant_id\": \"2\",\n" +
                "  \"quantity\": 3\n" +
                "}";

        Response re = given()
                .headers(head)
                .body(reqBody)
                .when()
                .post("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/cart/add_item");
        Assert.assertEquals(re.statusCode(), 200);
        System.out.println(re.prettyPeek());

    }

    @Test(priority = 6)

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
    @Test(priority = 7)
    public void deleteItem()
    {

        Map<String, String> header = new HashMap<String, String>();
        header.put("Content-Type", "application/json");
        header.put("Authorization", authToken);

        Response re = given()
                .headers(header)
                .when()
 // To delete an item from the cart, give the product id in the URL
                .delete("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/cart/remove_line_item/461");
        Assert.assertEquals(re.statusCode(), 200);
        System.out.println(re.prettyPeek());

    }



}
