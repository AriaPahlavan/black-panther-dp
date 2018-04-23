
//Name: Aria Pahlavan
//EID: AP44342


import java.util.function.BiFunction;

public class Program3 {
    EconomyCalculator calculator;
    VibraniumOreScenario vibraniumScenario;

    BiFunction<Integer, Integer, Integer> f = (n, v) -> calculator.calculateGain(n, v);

    private int[][] memo;

    public Program3() {
        this.calculator = null;
        this.vibraniumScenario = null;
        this.memo = null;
    }

    /**
     * This method is used in lieu of a required constructor signature to initialize
     * your Program3. After calling a default (no-parameter) constructor, we
     * will use this method to initialize your Program3 for Part 1.
     */
    public void initialize(EconomyCalculator ec) {
        this.calculator = ec;
        this.memo = new int[ec.getNumProjects()][ec.getNumVibranium()+1];
    }

    /**
     * This method is used in lieu of a required constructor signature to initialize
     * your Program3. After calling a default (no-parameter) constructor, we
     * will use this method to initialize your Program3 for Part 2.
     */
    public void initialize(VibraniumOreScenario vs) {
        this.vibraniumScenario = vs;
    }

    /**
     * This method returns an integer that is maximum possible gain in the Wakandan economy
     * given a certain amount of Vibranium
     */
    public int computeGain() {
        int N = calculator.getNumProjects();
        int V = calculator.getNumVibranium();

        for (int i = 0; i < N; i++)
            for (int j = 0; j <= V; j++)
                memo[i][j] = Integer.MIN_VALUE;

        for (int i = 0; i <= V; i++)
            memo[0][i] = calculator.calculateGain(0, i);

/*
        for (int i = 1; i < N; i++) {
            int max = 0;

            for (int j = 1; j <= V; j++) {
                memo[i][j] = Math.max(memo[i-1][j], f.apply(i, j));

                if (memo[i][j] > max)
                    max = memo[i][j];
            }

            res[i] = max;
        }


        return res[N-1];
*/
        int maxGrowth = of(N-1, V, memo, N, V);

//        Arrays.stream(memo).forEach(vs -> {
//            Arrays.stream(vs).forEach(v -> System.out.print(v + "\t"));
//            System.out.println();
//        });

        return maxGrowth;
    }

    private int of(int n, int v, int[][] memo, int N, int V) {
        if (n < 0 || n >= N || v < 1 || v > V) return 0;
        if (memo[n][v] != Integer.MIN_VALUE) return memo[n][v];
        if (v == 1) return Math.max(f.apply(n, 1), memo[n-1][1]);

        int max = Integer.MIN_VALUE;

        for (int j = 0; j <= v; j++) {
            if (memo[n-1][j] == Integer.MIN_VALUE)
                memo[n-1][j] = of(n-1, j, memo, N, V);

            max = Math.max(
                    max,
                    memo[n-1][j] + f.apply(n, v-j)
            );
        }

        memo[n][v] = max;

        return max;
    }

    /**
     * This method returns an integer that is the maximum possible dollar value that a thief 
     * could steal given the weight and volume capacity of his/her bag by using the 
     * VibraniumOreScenario instance.
     */
     public int computeLoss() {
         //TODO: Complete this method

        return 0;
     }
}


