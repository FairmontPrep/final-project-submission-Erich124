import java.util.*;

public class Client {
    static int[][] A;

    public static void main(String[] args) {
        run();
    }

    static void run() {
        ArrayList<ArrayList<Integer>> test_array_2 = new ArrayList<>();
        test_array_2.add(new ArrayList<>(Arrays.asList(1, 0, 0, 0, 0, 0)));
test_array_2.add(new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 0)));
test_array_2.add(new ArrayList<>(Arrays.asList(1, 1, 1, 1, 0, 0)));
test_array_2.add(new ArrayList<>(Arrays.asList(9, 0, 0, 1, 1, 1)));
test_array_2.add(new ArrayList<>(Arrays.asList(0, 1, 1, 1, 0, 0)));
test_array_2.add(new ArrayList<>(Arrays.asList(0, 1, 0, 1, 0, 0)));
test_array_2.add(new ArrayList<>(Arrays.asList(0, 1, 0, 1, 0, 0)));
test_array_2.add(new ArrayList<>(Arrays.asList(0, 1, 0, 1, 0, 0)));
test_array_2.add(new ArrayList<>(Arrays.asList(0, 1, 0, 0, 0, 0)));
test_array_2.add(new ArrayList<>(Arrays.asList(0, 1, 0, 0, 0, 0)));



        A = new int[test_array_2.size()][];
        for (int i = 0; i < test_array_2.size(); i++) {
            ArrayList<Integer> row = test_array_2.get(i);
            A[i] = new int[row.size()];
            for (int j = 0; j < row.size(); j++) {
                A[i][j] = row.get(j);
            }
        }

        ArrayList<String> path = findPath();
        System.out.println(path);
        printPathMap(path);

        if (!path.isEmpty()) {
            valid(path);
        }
    }

    public static ArrayList<String> findPath() {
        boolean[][] visited = new boolean[A.length][A[0].length];
        ArrayList<String> bestPath = new ArrayList<>();

        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[0].length; j++) {
                if (A[i][j] == 1 && wall(i, j)) {
                    ArrayList<String> currentPath = new ArrayList<>();
                    dfs(i, j, visited, currentPath, bestPath, i, j);
                }
            }
        }

        return bestPath;
    }

    public static void dfs(int i, int j, boolean[][] visited, ArrayList<String> path,
                           ArrayList<String> bestPath, int startRow, int startCol) {
        if (i < 0 || j < 0 || i >= A.length || j >= A[0].length || visited[i][j] || A[i][j] != 1) return;

        visited[i][j] = true;
        path.add("A[" + i + "][" + j + "]");

        // If it's a wall and not the starting point, consider it an endpoint
        if (wall(i, j) && !(i == startRow && j == startCol)) {
            if (path.size() > bestPath.size()) {
                bestPath.clear();
                bestPath.addAll(path);
            }
        }

        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for (int[] d : directions) {
            dfs(i + d[0], j + d[1], visited, path, bestPath, startRow, startCol);
        }

        path.remove(path.size() - 1);
        visited[i][j] = false;
    }

    public static boolean wall(int i, int j) {
        return i == 0 || j == 0 || i == A.length - 1 || j == A[0].length - 1;
    }

    public static void printPathMap(ArrayList<String> path) {
        String[][] output = new String[A.length][A[0].length];
        for (int i = 0; i < A.length; i++) Arrays.fill(output[i], " ");
        for (String coord : path) {
            String[] parts = coord.replace("A[", "").replace("]", "").split("\\[|\\]");
            int row = Integer.parseInt(parts[0]);
            int col = Integer.parseInt(parts[1]);
            output[row][col] = "1";
        }
        for (String[] row : output) {
            System.out.println(Arrays.toString(row).replace(",", "]["));
        }
    }

    public static void valid(ArrayList<String> path) {
        String[] start = path.get(0).replace("A[", "").replace("]", "").split("\\[|\\]");
        String[] end = path.get(path.size() - 1).replace("A[", "").replace("]", "").split("\\[|\\]");
        int a = Integer.parseInt(start[0]);
        int b = Integer.parseInt(start[1]);
        int c = Integer.parseInt(end[0]);
        int d = Integer.parseInt(end[1]);

        boolean hasTurn = false;
        for (int k = 2; k < path.size(); k++) {
            String[] prev = path.get(k - 2).replace("A[", "").replace("]", "").split("\\[|\\]");
            String[] curr = path.get(k - 1).replace("A[", "").replace("]", "").split("\\[|\\]");
            String[] next = path.get(k).replace("A[", "").replace("]", "").split("\\[|\\]");
            int x1 = Integer.parseInt(prev[0]), y1 = Integer.parseInt(prev[1]);
            int x2 = Integer.parseInt(curr[0]), y2 = Integer.parseInt(curr[1]);
            int x3 = Integer.parseInt(next[0]), y3 = Integer.parseInt(next[1]);

            if ((x1 == x2 && y2 == y3) || (y1 == y2 && x2 == x3)) {
                // Straight line
                continue;
            } else {
                hasTurn = true;
                break;
            }
        }

        if (!(wall(a, b) && wall(c, d))) {
            System.out.println("❌ Path does not begin and end on walls.");
        } else if (!hasTurn) {
            System.out.println("❌ Path does not contain a 90-degree turn.");
        } else {
            System.out.println("✅ Path begins and ends on walls and has a 90-degree turn.");
        }
    }
}
