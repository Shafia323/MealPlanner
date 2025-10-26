package meta;
import java.util.ArrayList;
import java.util.List;

public class KnapsackSolver {

    public static List<Meal> solveKnapsack(Meal[] meals, int budget) {
        int n = meals.length;
        int[][] dp = new int[n + 1][budget + 1];

        // Build DP table using 0/1 Knapsack algorithm
        for (int i = 1; i <= n; i++) {
            for (int w = 0; w <= budget; w++) {
                int currentCost = meals[i - 1].getCost();
                int currentNutrition = meals[i - 1].getNutritionScore();

                if (currentCost <= w) {
                    dp[i][w] = Math.max(
                            dp[i - 1][w],
                            dp[i - 1][w - currentCost] + currentNutrition
                    );
                } else {
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }

        // Backtrack to find selected meals
        return backtrackSelectedMeals(meals, dp, budget);
    }

    private static List<Meal> backtrackSelectedMeals(Meal[] meals, int[][] dp, int budget) {
        List<Meal> selectedMeals = new ArrayList<>();
        int n = meals.length;
        int w = budget;

        for (int i = n; i > 0 && w > 0; i--) {
            if (dp[i][w] != dp[i - 1][w]) {
                selectedMeals.add(meals[i - 1]);
                w -= meals[i - 1].getCost();
            }
        }

        return selectedMeals;
    }
}