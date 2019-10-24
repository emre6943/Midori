package db;

public class Meal {
    private String meal;
    private double price;

    /**
     * creates a meal.
     * @param meal Meal
     * @param price price of the meal
     */
    public Meal(String meal, double price) {
        this.meal = meal;
        this.price = price;
    }

    public String getMeal() {
        return meal;
    }

    public double getPrice() {
        return price;
    }

}
