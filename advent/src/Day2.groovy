import groovy.transform.CompileStatic

@CompileStatic
class Day2 implements AdventDay {
    private final static int MIN_STEP = 1
    private final static int MAX_STEP = 3

    void run() {
        List<int[]> reports = parseInputList('main/resources/day2/puzzle1_input.txt')
        int puzzle1result = getSafeReports(reports, 0)
        int puzzle2result = getSafeReports(reports, 1)
        println("Puzzle 1 result = ${puzzle1result}")
        println("Puzzle 2 result = ${puzzle2result}")
    }

    private int getSafeReports(List<int[]> reports, int maxUnsafeLevels) {
        return (int) reports.sum { isSafe(it, 0, maxUnsafeLevels) ? 1 : 0}
    }

    private boolean isSafe(int[] levels, int currentUnsafeLevels, int maxUnsafeLevels) {
        int diff = levels[0] - levels[1]

        // Base case
        int signum = Integer.signum(diff)
        if (currentUnsafeLevels > maxUnsafeLevels) {
            return false
        }

        for (int i = 0; i < levels.length - 1; ++i) {
            diff = levels[i] - levels[i + 1]
            int absDiff = Math.abs(diff)
            int thisSignum = Integer.signum(diff)

            if (signum != thisSignum || absDiff < MIN_STEP || absDiff > MAX_STEP) { // Unsafe
                ++currentUnsafeLevels
                if (i == 1 && isSafe(removeAtIndex(levels, 0), currentUnsafeLevels, maxUnsafeLevels)) { // First was wrong direction
                     return true
                } else if (isSafe(removeAtIndex(levels, i), currentUnsafeLevels, maxUnsafeLevels)) {
                    return true
                } else {
                    return isSafe(removeAtIndex(levels, i + 1), currentUnsafeLevels, maxUnsafeLevels)
                }
            }
        }
        return true
    }

    private static int[] removeAtIndex(int[] array, int i) {
        int[] result = new int[array.length - 1]
        System.arraycopy(array, 0, result, 0, i)
        System.arraycopy(array, i + 1, result, i, array.length - i - 1)
        return result
    }

    private static List<int[]> parseInputList(String fileName) {
        FileReader input = new FileReader(fileName)
        BufferedReader bufferedReader = new BufferedReader(input)

        List<int[]> lists = []

        String line
        while ((line = bufferedReader.readLine()) != null) {
            String[] strings = line.split('\\s+')
            lists.add(Arrays.stream(strings).mapToInt(Integer::parseInt).toArray())
        }

        return lists
    }
}
