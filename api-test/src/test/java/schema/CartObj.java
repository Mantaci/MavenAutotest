package schema;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CartObj {

    private List<ProductObj> cart;

    @JsonProperty("total_price")
    private double totalPrice;

    @JsonProperty("total_discount")
    private double totalDiscount;

    public CartObj(List<ProductObj> cart, double totalPrice, double totalDiscount) {
        this.cart = cart;
        this.totalPrice = totalPrice;
        this.totalDiscount = totalDiscount;
    }

    public CartObj() {
    }

    public List<ProductObj> getCart() {
        return cart;
    }

    public void setCart(List<ProductObj> cart) {
        this.cart = cart;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(double totalDiscount) {
        this.totalDiscount = totalDiscount;
    }
}
