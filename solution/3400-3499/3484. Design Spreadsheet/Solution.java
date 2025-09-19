class Spreadsheet {
    // Map to store cell values, where key is cell name (e.g., "A1") and value is the integer value
    private Map<String, Integer> cellValues;

    /**
     * Constructor to initialize the spreadsheet with a given number of rows
     * @param rows The number of rows in the spreadsheet (currently unused in this implementation)
     */
    public Spreadsheet(int rows) {
        this.cellValues = new HashMap<>();
    }

    /**
     * Sets the value of a specific cell
     * @param cell The cell identifier (e.g., "A1", "B2")
     * @param value The integer value to store in the cell
     */
    public void setCell(String cell, int value) {
        cellValues.put(cell, value);
    }

    /**
     * Resets (removes) a cell from the spreadsheet
     * @param cell The cell identifier to reset
     */
    public void resetCell(String cell) {
        cellValues.remove(cell);
    }

    /**
     * Evaluates a formula and returns the computed value
     * Formula format: "=A1+B2+5" (starts with '=' and contains cell references and/or numbers separated by '+')
     * @param formula The formula string to evaluate
     * @return The computed sum of all values in the formula
     */
    public int getValue(String formula) {
        int sum = 0;
      
        // Remove the leading '=' and split by '+' to get individual terms
        String[] terms = formula.substring(1).split("\\+");
      
        // Process each term in the formula
        for (String term : terms) {
            // Check if the term is a number or a cell reference
            if (Character.isDigit(term.charAt(0))) {
                // If it starts with a digit, parse it as a number
                sum += Integer.parseInt(term);
            } else {
                // Otherwise, treat it as a cell reference and get its value (default to 0 if not found)
                sum += cellValues.getOrDefault(term, 0);
            }
        }
      
        return sum;
    }
}
