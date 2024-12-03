import groovy.transform.CompileStatic

@CompileStatic
class Day1 implements AdventDay {

    void run() {
        List<List<Integer>> lists = parseInputList('main/resources/day1/puzzle1_input.txt', 2)
        int puzzle1Result = getDistance(lists)
        int puzzle2Result = getSimilarity(lists)


        println("Puzzle 1 result = ${puzzle1Result}")
        println("Puzzle 2 result = ${puzzle2Result}")
    }

    private static int getDistance(List<List<Integer>> lists) {
        lists.forEach { Collections.sort(it)}

        int distance = 0
        for (int i = 0; i < lists.first().size(); ++i) {
            distance += Math.abs(lists[0][i] - lists[1][i])
        }
        return distance
    }

    private static int getSimilarity(List<List<Integer>> lists) {
        Map<Integer, Integer> frequencyMap = getFrequencyMap(lists[1])

        int similarity = 0
        for (int i = 0; i < lists[0].size(); ++i) {
            Integer value = lists[0][i]
            similarity += value * (frequencyMap[value] ?: 0)
        }
        return similarity
    }

    private static Map<Integer, Integer> getFrequencyMap(List<Integer> sortedList) {
        Map<Integer, Integer> map = [:]

        int prevValue = sortedList[0]
        int count = 0
        for (int i = 1; i < sortedList.size(); ++i) {
            ++count
            if (sortedList[i] != prevValue) {
                map[prevValue] = count
                prevValue = sortedList[i]
                count = 0
            }
        }
        map[prevValue] = count

        return map
    }

    private static List<List<Integer>> parseInputList(String fileName, int num_cols) {
        FileReader input = new FileReader(fileName)
        BufferedReader bufferedReader = new BufferedReader(input)

        List<List<Integer>> lists = []
        for (int i = 0; i < num_cols; ++i) {
            lists.add([])
        }

        String line
        while ((line = bufferedReader.readLine()) != null) {
            String[] strings = line.split('\\s+')

            for (int i = 0; i < num_cols; ++i) {
                lists[i].add(strings[i].toInteger())
            }
        }

        return lists
    }
}
