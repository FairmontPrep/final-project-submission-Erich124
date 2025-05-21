import java.util.ArrayList;
import java.util.Arrays;

public class Client{
    static int[][] A;
    public static void main(String[] args){
        run();
        }

        public static void run() {
        ArrayList<ArrayList<Integer>> test_array_2 = new ArrayList<>();
        test_array_2.add(new ArrayList<>(Arrays.asList(1, 0, 0, 1, 0, 0, 0, 0 )));
        test_array_2.add(new ArrayList<>(Arrays.asList(0, 0, 0, 1, 0, 0, 0, 0 )));
        test_array_2.add(new ArrayList<>(Arrays.asList(0, 0, 0, 1, 0, 0, 1, 0 )));
        test_array_2.add(new ArrayList<>(Arrays.asList(9, 0, 0, 1, 0, 0, 0, 0 )));
        test_array_2.add(new ArrayList<>(Arrays.asList(0, 0, 0, 1, 0, 0, 0, 0 )));
        test_array_2.add(new ArrayList<>(Arrays.asList(0, 0, 0, 1, 0, 0, 0, 0 )));
        test_array_2.add(new ArrayList<>(Arrays.asList(0, 0, 0, 1, 2, 0, 0, 0 )));
        test_array_2.add(new ArrayList<>(Arrays.asList(1, 0, 0, 1, 1, 1, 1, 1 )));
        A = new int[test_array_2.size()][test_array_2.get(0).size()];
        for (int i = 0; i < test_array_2.size(); i++) {
            for (int j = 0; j < test_array_2.get(i).size(); j++) {
                A[i][j] = test_array_2.get(i).get(j);
            }
        }

        ArrayList<String> path = findPath();
        System.out.println(path);
        printPathMap(path);
        }

        public static ArrayList<String> findPath() {
        ArrayList<String> result = new ArrayList<>();
        boolean[][] visited = new boolean[A.length][A[0].length];

        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[0].length; j++) {
                if (A[i][j] == 1) {
                    ArrayList<String> path = new ArrayList<>();
                    if (dfs(i, j, visited, path)) {
                        return path;
                    }
                }
            }
        }
        return result;
    }
}
