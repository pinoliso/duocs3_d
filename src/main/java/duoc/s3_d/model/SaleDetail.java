package duoc.s3_d.model;

public class SaleDetail {
    
    private Product product;
    private int quantity;

    public SaleDetail(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getProfit() {
        return product.getSellingPrice() - product.getCostPrice();
    }

    public double getTotalProfit() {
        return getProfit() * quantity;
    }
}
