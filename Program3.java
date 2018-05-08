
//Name: Aria Pahlavan
//EID: AP44342


public class Program3 {
    EconomyCalculator calculator;
    VibraniumOreScenario vibraniumScenario;

    public Program3() {
        this.calculator = null;
        this.vibraniumScenario = null;
    }

    /**
     * This method is used in lieu of a required constructor signature to initialize
     * your Program3. After calling a default (no-parameter) constructor, we
     * will use this method to initialize your Program3 for Part 1.
     */
    public void initialize(EconomyCalculator ec) {
        this.calculator = ec;
    }

    /**
     * This method is used in lieu of a required constructor signature to initialize
     * your Program3. After calling a default (no-parameter) constructor, we
     * will use this method to initialize your Program3 for Part 2.
     */
    public void initialize(VibraniumOreScenario vs) {
        this.vibraniumScenario = vs;
    }

    private int f(int n, int v) {
        return calculator.calculateGain(n, v);
    }

    /**
     * This method returns an integer that is maximum possible gain in the Wakandan economy
     * given a certain amount of Vibranium
     */
    public int computeGain() {
        int N = calculator.getNumProjects();
        int V = calculator.getNumVibranium();
        int[][] memo = new int[N][V + 1];


        for (int i = 0; i < N; i++)
            memo[i][0] = calculator.calculateGain(i, 0);

        for (int j = 0; j <= V; j++)
            memo[0][j] = calculator.calculateGain(0, j);

        return opt1Iter(N - 1, V, memo);
    }

    /**
     * Iterative solution to Compute Gain problem
     *
     * @param N    number of projects
     * @param V    amount Vibranium available
     * @param memo memoization table to be used
     * @return max economical gain
     */
    private int opt1Iter(int N, int V, int[][] memo) {
        for (int n = 1; n <= N; n++)
            for (int v = 1; v <= V; v++) {
                int max = Integer.MIN_VALUE;

                for (int j = 0; j <= v; j++)
                    max = Math.max(max, memo[n-1][v-j] + f(n, j));

                memo[n][v] = max;
            }

        return memo[N][V];
    }

    /**
     * Recursive solution to Compute Gain problem
     *
     * @param N    number of projects
     * @param V    amount Vibranium available
     * @param memo memoization table to be used
     * @return max economical gain
     */
    private int opt1Rec(int N, int V, int[][] memo) {
        if (N < 0 || V < 1) return 0;
        if (memo[N][V] != Integer.MIN_VALUE) return memo[N][V];

        int max = Integer.MIN_VALUE;

        for (int j = 0; j <= V; j++) {
            if (memo[N - 1][j] == Integer.MIN_VALUE)
                memo[N - 1][j] = opt1Rec(N - 1, j, memo);

            max = Math.max(max, memo[N - 1][j] + f(N, V - j));
        }

        memo[N][V] = max;

        return max;
    }

    /**
     * This method returns an integer that is the maximum possible dollar value that a thief
     * could steal given the weight and volume capacity of his/her bag by using the
     * VibraniumOreScenario instance.
     */
    public int computeLoss() {
        int N = vibraniumScenario.getNumOres();
        int W = vibraniumScenario.getWeightCapacity();
        int V = vibraniumScenario.getVolumeCapacity();

        return opt2Iter(N, W, V);
    }

    //
    //             0                                                       if i == 0
    // OPT(i,w) =  OPT(i−1, W, V)                                          if wi > W or vi > V
    //             max(OPT(i−1, W, V), pi + OPT(i−1, W−wi, V-vi))          otherwise
    //

    /**
     * Iterative solution to Compute Loss problem
     *
     * @param n number of items to steal
     * @param W Weight capacity of knapsack
     * @param V Volume capacity of knapsack
     * @return max loss
     */
    private int opt2Iter(int n, int W, int V) {
        int[][][] memo2 = new int[n + 1][W + 1][V + 1];

        for (int w = 1; w <= W; w++)
            for (int v = 1; v <= V; v++)
                memo2[0][w][v] = 0;

        for (int i = 1; i <= n; i++) {
            VibraniumOre item = vibraniumScenario.getVibraniumOre(i - 1);
            int pi = item.getPrice();
            int wi = item.getWeight();
            int vi = item.getVolume();

            for (int w = 1; w <= W; w++)
                for (int v = 1; v <= V; v++)
                    if (wi > w || vi > v) memo2[i][w][v] = memo2[i - 1][w][v];
                    else memo2[i][w][v] = Math.max(memo2[i - 1][w][v],
                                                   memo2[i - 1][w - wi][v - vi] + pi);
        }

        return memo2[n][W][V];
    }

    /**
     * Recursive solution to Compute Loss problem
     *
     * @param i     number of items to steal
     * @param W     Weight capacity of knapsack
     * @param V     Volume capacity of knapsack
     * @param memo2 memoization table to be used
     * @return max loss
     */
    private int opt2Rec(int i, int W, int V, int[][][] memo2) {
        if (i == 0) return 0;

        if (memo2[i][W][V] != 0) return memo2[i][W][V];

        VibraniumOre item = vibraniumScenario.getVibraniumOre(i - 1);
        int pi = item.getPrice();
        int wi = item.getWeight();
        int vi = item.getVolume();

        if (wi > W || vi > V) {
            memo2[i][W][V] = opt2Rec(i - 1, W, V, memo2);
            return memo2[i][W][V];
        } else {
            memo2[i][W][V] = Math.max(opt2Rec(i - 1, W, V, memo2),
                                      opt2Rec(i - 1, W - wi, V - vi, memo2) + pi);
            return memo2[i][W][V];
        }
    }

}


