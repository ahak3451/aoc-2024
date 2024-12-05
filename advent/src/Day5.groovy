import groovy.transform.CompileStatic

@CompileStatic
class Day5 implements AdventDay {
    private List<List<Integer>> rules
    private List<List<Integer>> updates
    private Map<Integer, Set<Integer>> cantBeAfterMap

    Day5() {
        rules = []
        updates = []

        FileReader input = new FileReader('main/resources/day5/puzzle_input.txt')
        BufferedReader bufferedReader = new BufferedReader(input)

        String line
        while ((line = bufferedReader.readLine()) != "") {
            rules.add(line.split("\\|").collect { String it -> Integer.valueOf(it) })
        }
        while ((line = bufferedReader.readLine()) != null) {
            updates.add(line.split(",").collect { String it -> Integer.valueOf(it) })
        }

        cantBeAfterMap = createCantBeAfterMap(rules)
    }

    void run() {
        int puzzle1Result = validateUpdates(false)
        int puzzle2Result = validateUpdates(true) - puzzle1Result
        println("Puzzle 1 result = ${puzzle1Result} \nPuzzle 2 result = ${puzzle2Result}")
    }

    private int validateUpdates(boolean reorder) {
        int result = 0

        for (update in updates) {
            Set<Integer> previousPages = []

            for (int i = 0; i < update.size(); ++i) {
                Integer wronglyPlaced = previousPages.find { cantBeAfterMap[update[i]]?.contains(it)}
                if (wronglyPlaced) {
                    if (reorder) {
                        int indexAfFejl = update.indexOf(wronglyPlaced)
                        update[indexAfFejl] = update[i]
                        update[i] = wronglyPlaced
                        previousPages = []
                        i = -1
                    } else {
                        break
                    }
                } else if (i == update.size() - 1) {
                    result += update[update.size() / 2 as int]
                } else {
                    previousPages.add(update[i])
                }
            }
        }
        return result
    }

    private static Map<Integer, Set<Integer>> createCantBeAfterMap(List<List<Integer>> rules) {
        Map<Integer, Set<Integer>> map = [:]
        for (rule in rules) {
            if (map[rule[0]] == null) {
                map.put(rule[0], new HashSet<>())
            }
            map[rule[0]].add(rule[1])
        }
        return map
    }
}
