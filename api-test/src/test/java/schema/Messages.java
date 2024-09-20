package schema;

public class Messages {
    static String ProductNotFoundMessage = "Product not found";
    static String ProductAddedMessage = "Product added to cart successfully";
    static String ProductRemovedMessage = "Product removed from cart";
    static String ProductNotFoundInCartMessage = "Product not found in cart";

    public static String getProductNotFoundMessage() {
        return ProductNotFoundMessage;
    }

    public static String getProductAddedMessage() {
        return ProductAddedMessage;
    }
    public static String getProductRemovedMessage() {
        return ProductRemovedMessage;
    }
    public static String getProductNotFoundInCartMessage() {
        return ProductNotFoundInCartMessage;
    }
}
