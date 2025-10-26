public class QuickSort {

    public static void sortByCost(Meal[] meals) {
        if (meals == null || meals.length == 0) return;
        quickSort(meals, 0, meals.length - 1);
    }

    private static void quickSort(Meal[] meals, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(meals, low, high);
            quickSort(meals, low, pivotIndex - 1);
            quickSort(meals, pivotIndex + 1, high);
        }
    }

    private static int partition(Meal[] meals, int low, int high) {
        int pivot = meals[high].getCost();
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (meals[j].getCost() <= pivot) {
                i++;
                swap(meals, i, j);
            }
        }

        swap(meals, i + 1, high);
        return i + 1;
    }

    private static void swap(Meal[] meals, int i, int j) {
        Meal temp = meals[i];
        meals[i] = meals[j];
        meals[j] = temp;
    }
}