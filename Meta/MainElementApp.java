import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainElementApp {
    private List<Meal> meals;

    public MainElementApp() {
        meals = new ArrayList<>();
        loadMeals();
    }

    private void loadMeals() {
        // Try to load from file, fall back to sample data
        try {
            loadMealsFromFile();
            System.out.println("✅ Loaded " + meals.size() + " meals from database");
        } catch (Exception e) {
            System.out.println("❌ Error loading file: " + e.getMessage());
            System.out.println("📝 Using sample meal data...");
            createSampleMeals();
        }
    }

    private void loadMealsFromFile() throws IOException {
        // Try multiple possible file locations
        String[] possiblePaths = {
                "Meta/recipes.json",
                "recipes.json",
                "../Meta/recipes.json"
        };

        BufferedReader br = null;
        String foundPath = null;

        // Find a working file path
        for (String path : possiblePaths) {
            try {
                br = new BufferedReader(new FileReader(path));
                foundPath = path;
                System.out.println("📁 Found recipes.json at: " + path);
                break; // Found a working path
            } catch (IOException e) {
                // Try next path
                if (br != null) {
                    try { br.close(); } catch (IOException ex) {}
                }
                br = null;
            }
        }

        if (br == null) {
            throw new IOException("Could not find recipes.json in any known location");
        }

        // Use try-with-resources with the found BufferedReader
        try (BufferedReader reader = br) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("[")) continue;

                // Remove JSON brackets and quotes for simplicity
                line = line.replace("[", "").replace("]", "").replace("\"", "").replace("{", "").replace("}", "");

                // Parse CSV-like format: id,name,cost,nutrition_score
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    try {
                        int id = Integer.parseInt(parts[0].trim());
                        String name = parts[1].trim();
                        int cost = Integer.parseInt(parts[2].trim());
                        int nutritionScore = Integer.parseInt(parts[3].trim());

                        meals.add(new Meal(id, name, cost, nutritionScore));
                    } catch (NumberFormatException e) {
                        // Skip invalid lines
                    }
                }
            }
        }
    }

    private void createSampleMeals() {
        meals.add(new Meal(1, "Plain Rice & Lentil Soup (Dal)", 35, 8));
        meals.add(new Meal(2, "Egg Curry & Rice", 45, 8));
        meals.add(new Meal(3, "Vegetable Khichuri", 50, 9));
        meals.add(new Meal(4, "Masoor Dal & Roti", 30, 7));
        meals.add(new Meal(5, "Potato Curry & Paratha", 50, 7));
        meals.add(new Meal(6, "Chola (Chickpea) & Roti", 40, 9));
        meals.add(new Meal(7, "Mixed Vegetable Curry & Rice", 45, 8));
        meals.add(new Meal(8, "Boiled Egg & Bread", 30, 7));
        meals.add(new Meal(9, "Egg Fried Rice", 60, 8));
        meals.add(new Meal(10, "Vegetable Bhaji & Rice", 40, 8));
        meals.add(new Meal(11, "Lentil & Spinach Soup", 50, 9));
        meals.add(new Meal(12, "Chicken Curry & Rice", 90, 9));
        meals.add(new Meal(13, "Fried Tofu & Rice", 70, 8));
        meals.add(new Meal(14, "Mixed Veg Pulao", 80, 8));
        meals.add(new Meal(15, "Bread, Banana & Milk", 40, 8));
        meals.add(new Meal(16, "Boiled Vegetables + Egg", 50, 9));
        meals.add(new Meal(17, "Plain Rice, Dal & Egg Fry", 55, 9));
        meals.add(new Meal(18, "Chickpea Salad", 40, 9));
        meals.add(new Meal(19, "Seasonal Fruit Bowl", 40, 10));
        meals.add(new Meal(20, "Vegetable Chowmein", 60, 8));
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n🍽️  WELCOME TO SMART MEAL PLANNER! 🍽️");
        System.out.println("=====================================");
        System.out.println("DSA Project: 0/1 Knapsack + QuickSort");

        while (true) {
            System.out.println("\n📊 MAIN MENU:");
            System.out.println("1. View All Meals");
            System.out.println("2. Generate Optimal Meal Plan");
            System.out.println("3. Sort Meals by Cost (QuickSort)");
            System.out.println("4. Exit");
            System.out.print("Choose an option (1-4): ");

            try {
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        displayAllMeals();
                        break;
                    case 2:
                        generateMealPlan(scanner);
                        break;
                    case 3:
                        sortMealsByCost();
                        break;
                    case 4:
                        System.out.println("👋 Thank you for using Smart Meal Planner!");
                        System.out.println("📚 DSA Algorithms Used:");
                        System.out.println("   - 0/1 Knapsack (Dynamic Programming)");
                        System.out.println("   - QuickSort (Divide & Conquer)");
                        scanner.close();
                        return;
                    default:
                        System.out.println("❌ Invalid option. Please choose 1-4.");
                }
            } catch (Exception e) {
                System.out.println("❌ Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear invalid input
            }
        }
    }

    private void displayAllMeals() {
        System.out.println("\n📋 ALL AVAILABLE MEALS (" + meals.size() + " meals)");
        System.out.println("=".repeat(75));
        for (Meal meal : meals) {
            System.out.println(meal);
        }
        System.out.println("=".repeat(75));
    }

    private void generateMealPlan(Scanner scanner) {
        System.out.print("\n💰 Enter your weekly budget (₹): ");
        int budget = scanner.nextInt();

        if (budget <= 0) {
            System.out.println("❌ Please enter a positive budget amount.");
            return;
        }

        Meal[] mealArray = meals.toArray(new Meal[0]);
        List<Meal> selectedMeals = KnapsackSolver.solveKnapsack(mealArray, budget);

        System.out.println("\n🎯 OPTIMAL MEAL PLAN (0/1 Knapsack Algorithm)");
        System.out.println("💰 Budget: ₹" + budget);
        System.out.println("=".repeat(75));

        if (selectedMeals.isEmpty()) {
            System.out.println("❌ No meals can be selected within your budget.");
            return;
        }

        int totalCost = 0;
        int totalNutrition = 0;

        for (Meal meal : selectedMeals) {
            System.out.println(meal);
            totalCost += meal.getCost();
            totalNutrition += meal.getNutritionScore();
        }

        System.out.println("=".repeat(75));
        System.out.printf("📊 SUMMARY: %d meals | Total Cost: ₹%d | Nutrition: %d | Remaining: ₹%d%n",
                selectedMeals.size(), totalCost, totalNutrition, budget - totalCost);
        System.out.printf("💰 Budget Utilization: %.1f%%%n", (totalCost * 100.0 / budget));
    }

    private void sortMealsByCost() {
        Meal[] mealArray = meals.toArray(new Meal[0]);

        System.out.println("\n🔄 Sorting meals by cost using QuickSort algorithm...");
        QuickSort.sortByCost(mealArray);

        System.out.println("\n📊 MEALS SORTED BY COST (Ascending Order)");
        System.out.println("=".repeat(75));
        for (Meal meal : mealArray) {
            System.out.println(meal);
        }
        System.out.println("=".repeat(75));
    }

    public static void main(String[] args) {
        MainElementApp app = new MainElementApp();
        app.run();
    }
}