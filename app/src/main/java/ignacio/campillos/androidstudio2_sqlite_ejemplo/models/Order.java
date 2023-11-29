package ignacio.campillos.androidstudio2_sqlite_ejemplo.models;

public class Order {

    private String menu;

    private boolean large;

    private int quantity;

    private float price, total;

    public Order() {
    }

    public Order(String menu, boolean large, int quantity, float price) {
        this.menu = menu;
        this.large = large;
        this.quantity = quantity;
        this.price = price;
        calcularTotal();
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public boolean isLarge() {
        return large;
    }

    public void setLarge(boolean large) {
        this.large = large;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        calcularTotal();
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
       calcularTotal();
    }

    public float getTotal() {
        return total;
    }

    public void calcularTotal(){
        total = quantity * price;
    }
}
