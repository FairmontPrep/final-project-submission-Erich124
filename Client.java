import java.util.*;

public class Client {
    static int[][] A;

    public static void main(String[] args) {
        run();
    }

    static void run() {
        ArrayList<ArrayList<Integer>> test_array_2 = new ArrayList<>();
        test_array_2.add(new ArrayList<>(Arrays.asList(1, 0, 0, 1, 0, 1, 0, 1)));
        test_array_2.add(new ArrayList<>(Arrays.asList(0, 0, 1, 1, 0, 1, 0, 1)));
        test_array_2.add(new ArrayList<>(Arrays.asList(0, 1, 1, 1, 0, 1, 0, 1)));
        test_array_2.add(new ArrayList<>(Arrays.asList(9, 0, 1, 0, 0, 0, 0, 1)));
        test_array_2.add(new ArrayList<>(Arrays.asList(1, 1, 1, 1, 0, 0, 0, 1)));
        test_array_2.add(new ArrayList<>(Arrays.asList(0, 0, 0, 1, 1, 0, 0, 1)));
        test_array_2.add(new ArrayList<>(Arrays.asList(0, 0, 0, 1, 0, 0, 0, 1)));
        test_array_2.add(new ArrayList<>(Arrays.asList(1, 1, 1, 1, 0, 1, 1, 0)));

        A = new int[test_array_2.size()][];
        for(int i = 0; i < test_array_2.size(); i++) {
            ArrayList<Integer> row = test_array_2.get(i);
            A[i] = new int[row.size()];
            for(int j = 0; j < row.size(); j++) {
                A[i][j] = row.get(j);
            }
        }

        ArrayList<String> path = findPath();
        System.out.println(path);
        printPathMap(path);

        if(!path.isEmpty()) {
            valid(path);
        }
    }

    public static ArrayList<String> findPath() {
        boolean[][] visited = new boolean[A.length][A[0].length];
        ArrayList<String> bestPath = new ArrayList<>();

        for(int i = 0; i < A.length; i++) {
            for(int j = 0; j < A[0].length; j++) {
                if(A[i][j] == 1 && wall(i, j)) {
                    ArrayList<String> currentPath = new ArrayList<>();
                    if(searching(i, j, visited, currentPath)) {
                        if(currentPath.size() > bestPath.size()) {
                            bestPath = new ArrayList<>(currentPath);
                        }
                    }
                }
            }
        }
        return bestPath;
    }

    public static boolean searching(int i, int j, boolean[][] visited, ArrayList<String> path) {
        if(i < 0 || i >= A.length || j < 0 || j >= A[0].length || A[i][j] != 1 || visited[i][j]) {
            return false;
        }

        visited[i][j] = true;
        path.add("A[" + i + "][" + j + "]");

        boolean isDeadEnd = true;
        int[][] directionVectors = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for(int[] d : directionVectors) {
            int ni = i + d[0], nj = j + d[1];
            if(searching(ni, nj, visited, path)) {
                isDeadEnd = false;
                break;
            }
        }

        if(isDeadEnd && path.size() < 2) {
            path.remove(path.size() - 1);
            visited[i][j] = false;
            return false;
        }

        return true;
    }

    public static void printPathMap(ArrayList<String> path) {
        String[][] output = new String[A.length][A[0].length];
        for(int i = 0; i < A.length; i++) {
            Arrays.fill(output[i], " ");
        }
        for(String coord : path) {
            String[] parts = coord.replace("A[", "").replace("]", "").split("\\[|\\]");
            int row = Integer.parseInt(parts[0]);
            int col = Integer.parseInt(parts[1]);
            output[row][col] = "1";
        }
        for(String[] row : output) {
            System.out.println(Arrays.toString(row)
                .replace(", ", "][")
                .replace("[", "[")
                .replace("]", "]"));
        }
    }

    public static boolean wall(int i, int j) {
        return (i == 0 || j == 0 || i == A.length - 1 || j == A[0].length - 1);
    }

    public static void valid(ArrayList<String> path) {
        String[] start = path.get(0).replace("A[", "").split("]\\[?");
        int a = Integer.parseInt(start[0]);
        int b = Integer.parseInt(start[1].replace("]", ""));

        String[] end = path.get(path.size()-1).replace("A[", "").split("]\\[?");
        int c = Integer.parseInt(end[0]);
        int d = Integer.parseInt(end[1].replace("]", ""));

        if(!(wall(a, b) && wall(c, d))) {
            System.out.println("❌ Path does not begin and end on walls.");
        } else if((a == c || b == d)) {
            System.out.println("❌ Path does not contain a 90-degree turn.");
        } else {
            System.out.println("✅ Path begins and ends on adjacent walls with a 90-degree turn.");
        }
    }
}