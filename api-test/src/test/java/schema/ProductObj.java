package schema;

import com.fasterxml.jackson.annotation.JsonInclude;

public class ProductObj {

    String name;
    String category;
    double price;
    double discount;
    int quantity;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    int id;

    public ProductObj(String name, String category, double price, double discount, int id, int quantity) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.discount = discount;
        this.id = id;
        this.quantity = quantity;
    }
    public ProductObj(String name, String category, double price, double discount, int id) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.discount = discount;
        this.id = id;
    }

    public ProductObj(String name, String category, double price, double discount) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.discount = discount;
    }

    public ProductObj() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
