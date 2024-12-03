import groovy.transform.CompileStatic

import java.util.regex.Matcher
import java.util.regex.Pattern

@CompileStatic
class Day3 implements AdventDay {
    private static final String CLEAN_REGEX = "mul\\(\\d+,\\d+\\)|don't\\(\\)|do\\(\\)"
    private static final String SPLIT_REGEX = "[\\(,\\)]"

    void run() {
        String inputString = parseInput('main/resources/day3/puzzle1_input.txt')
        List<String> cleanedInput = cleanInput(inputString)
        int result1 = calculateResult(cleanedInput, false)
        int result2 = calculateResult(cleanedInput, true)
        println("Puzzle 1 result = ${result1} \nPuzzle 2 result = ${result2}")
    }

    private static int calculateResult(List<String> cleanedInput, boolean considerConditions) {
        boolean enabled = true
        int sum = 0
        for (operation in cleanedInput) {
            String[] parts = operation.split(SPLIT_REGEX)
            switch (parts[0]) {
                case 'mul':
                    if (!considerConditions || enabled) {
                        sum += parts[1].toInteger() * parts[2].toInteger()
                    }
                    break
                case 'do':
                    enabled = true
                    break
                case "don't":
                    enabled = false
                    break
                default:
                    throw new IllegalStateException("Unknown operand ${parts[0]}")
            }
        }
        return sum
    }

    private static String parseInput(String fileName) {
        FileReader input = new FileReader(fileName)
        BufferedReader bufferedReader = new BufferedReader(input)

        StringBuilder stringBuilder = new StringBuilder()

        String line
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line)
        }
        return stringBuilder.toString()
    }

    private static List<String> cleanInput(String input) {
        Pattern pattern = Pattern.compile(CLEAN_REGEX)
        Matcher matcher = pattern.matcher(input)

        List<String> groups = []
        while (matcher.find()) {
            groups.add(matcher.group())
        }
        return groups
    }

}
