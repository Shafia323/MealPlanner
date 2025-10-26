package meta;
public class Meal {
    private int id;
    private String name;
    private int cost;
    private int nutritionScore;

    public Meal(int id, String name, int cost, int nutritionScore) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.nutritionScore = nutritionScore;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getCost() { return cost; }
    public int getNutritionScore() { return nutritionScore; }

    @Override
    public String toString() {
        return String.format("ID: %2d | %-35s | Cost: ₹%3d | Nutrition: %2d/10",
                id, name, cost, nutritionScore);
    }
}