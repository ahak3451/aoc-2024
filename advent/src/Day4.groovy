import groovy.transform.CompileStatic

@CompileStatic
class Day4 implements AdventDay {
    private char[][] matrix
    private static final Direction[] CROSS_DIRECTIONS = [Direction.UP_LEFT, Direction.UP_RIGHT, Direction.DOWN_LEFT, Direction.DOWN_RIGHT] as Direction[]

    Day4() {
        matrix = parseInput('main/resources/day4/puzzle_input.txt')
    }

    void run() {
        int puzzle1_result = countTotalOccurances('XMAS', 'individual')
        int puzzle2_result = countTotalOccurances('MAS', 'crosses', 1)
        println("Puzzle 1 result = ${puzzle1_result} \nPuzzle 2 result = ${puzzle2_result}")
    }

    private int countTotalOccurances(String word, String typeOfCount, int indexOfPivot = 0) {
        int totalOccurances = 0
        for (int r = 0; r < matrix.length; ++r) {
            for (int c = 0; c < matrix[0].length; ++c) {
                if (matrix[r][c] == word.charAt(indexOfPivot)) {
                    if (typeOfCount == 'individual') {
                        totalOccurances += countOccurancesForCoord(r, c, word)
                    } else if (typeOfCount == 'crosses') {
                        totalOccurances += (countOccurancesForCoord(r, c, word, indexOfPivot, CROSS_DIRECTIONS) == 2) ? 1 : 0
                    } else {
                        throw new IllegalStateException("Unknown typeOfCount: ${typeOfCount}")
                    }
                }
            }
        }
        return totalOccurances
    }

    private int countOccurancesForCoord(int row, int col, String word, int indexOfPivot = 0, Direction[] allowedDirections = Direction.values() ) {
        int occurances = 0
        for (Direction direction in allowedDirections) {
            for (int i = 0; i < word.length(); ++i) {
                int[] coord = coordAfterSteps(i - indexOfPivot, row, col, direction)

                if (!isWithinMatrix(coord) || matrix[coord[0]][coord[1]] != word.charAt(i)) {
                    break
                } else if (i == word.length() - 1) {
                    ++occurances
                }
            }
        }
        return occurances
    }

    private static int[] coordAfterSteps(int steps, int startRow, int startCol, Direction direction) {
        return [startRow + direction.stepRow * steps, startCol + direction.stepCol * steps] as int[]
    }

    private boolean isWithinMatrix(int[] coord) {
        return !(coord[0] < 0 || coord[0] >= matrix.length || coord[1] < 0 || coord[1] >= matrix[0].length)
    }

    private static char[][] parseInput(String fileName) {
        FileReader input = new FileReader(fileName)
        BufferedReader bufferedReader = new BufferedReader(input)

        List<String> rows = []
        String line
        while ((line = bufferedReader.readLine()) != null) {
            rows.add(line)
        }

        char[][] matrix = new char[rows.size()][rows.first().length()]
        for (int r = 0; r < rows.size(); ++r) {
            matrix[r] = rows[r].toCharArray()
        }
        return matrix
    }

    private enum Direction {
        UP(-1, 0),
        UP_RIGHT(-1, 1),
        RIGHT(0, 1),
        DOWN_RIGHT(1, 1),
        DOWN(1, 0),
        DOWN_LEFT(1, -1),
        LEFT(0, -1),
        UP_LEFT(-1, -1)

        final int stepRow
        final int stepCol

        Direction(int stepRow, int stepCol) {
            this.stepRow = stepRow
            this.stepCol = stepCol
        }
    }

}
