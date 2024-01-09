import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

  // Map to store registers
  private static final Map<String, Integer> registers;
    // Read data from a file and returns a HashMap containing the data.
    static {
        try {
            registers = readFromFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
   * Initializes registers and reads an assembly file to process each statement.
   *
   * @param args the command line arguments
   */
  public static void main(String[] args) {

    // take input from user
    Scanner scn = new Scanner(System.in);

    // display menu
    System.out.println("SELECT ONE OF THE FOLLOWING COMMANDS:");
    System.out.println("'MV' (REG{number}, value) to make a new variable");
    System.out.println("'ADD' (REG{number}, value) or (REG{number},REG{number}) to add the values of register");
    System.out.println("'SHOW' (REG for all) or (REG{number} for specific) to see the result of a register");
    System.out.println("'EXIT' or 'QUIT' to terminate the program");
    System.out.println("(For example - 'MV REG1 1000' or 'ADD REG1 REG2' or 'SHOW REG1' or 'EXIT')");


    boolean flag = true;
    // loop until user enters 'EXIT' or 'QUIT'
    while (flag) {
      String input = scn.nextLine();
      input = input.toUpperCase();
      if(input.equals("EXIT") || input.equals("QUIT")){
        System.out.println("The program has been terminated");
        flag = false;
      }else{
        processAssemblyFile(input);
      }
    }
  }

  /**
   * Processes an assembly file statement.
   *
   * @param statement the assembly file statement to process
   */
  private static void processAssemblyFile(String statement) {

    // Split statement into tokens
    String[] tokens = statement.split("[\\s,#]+");
//    System.out.println(Arrays.toString(tokens));

    // Check if statement is valid
    if (tokens.length < 2 || tokens.length > 3) {
      System.out.println("Invalid assembly statement: use valid format 'operation REG{number} value' ");
     return;
    }

    // Extract operation
    String operation = tokens[0];

    // Process statement based on operation
    switch (operation) {

      // Register operations
      case "MV":

        // Check if MV statement is valid and token1 is a valid register and token2 is a
        // valid constant
       if (tokens.length == 3 && tokens[1].matches("REG[1-9]\\d*") && tokens[2].matches("\\d+")) {

          // Extract value from token
          int value = Integer.parseInt(tokens[2]);

            // Move value to register
            moveValueToRegister(tokens[1], value);

        }

        // Invalid MV statement
        else {
          if(Integer.parseInt(tokens[2])<0 ){
            System.out.println(" Invalid MV statement: Value cannot be less than 0");
          }
          else{
          System.out.println("Invalid MV statement: REG must followed by non-zero or positive integer: " + statement);
          }
        }
        break;

      // Register operations
      case "ADD":

        // Check if ADD statement is valid and token1 is a valid register and token2 is
        // a valid constant
         if (tokens.length == 3 && tokens[1].matches("REG[1-9]\\d*") && tokens[2].matches("\\d+")) {

          // Check if register exists
          if(registers.containsKey(tokens[1])){
            // Extract value from token
            int value = Integer.parseInt(tokens[2]);
              // Add value to register
              addValueToRegister(tokens[1], value);
          }
          else{
            System.out.println("The register does not exist");
          }
        }

        // Check if ADD statement is valid and token1 is a valid register and token2 is a valid register
        else if (tokens.length == 3 && tokens[1].matches("REG[1-9]\\d*") && tokens[2].matches("REG[1-9]\\d*")) {
         // Check if register1 exists
          if (registers.containsKey(tokens[1])){
            // Check if register2 exists
            if(registers.containsKey(tokens[2])){
              // Extract value from token
                int value = registers.get(tokens[2]);
                // Add value to register
                addValueToRegister(tokens[1], value);
            }
            else{
              System.out.println("The register 2 does not exist");
          }
          }
          else{
              System.out.println("The register 1 does not exist");
            }
        }

        // Invalid ADD statement
        else {
          if(Integer.parseInt(tokens[2])<0 ){
            System.out.println(" Invalid Add statement: Value cannot be less than 0");
          }
          else{
            System.out.println("Invalid Add statement: REG must followed by non-zero or positive integer: " + statement);
          }

        }
        break;

      // Register operations
      case "SHOW":

        // Check if SHOW statement is valid and token1 is a valid register
        if (tokens.length == 2 && tokens[1].matches("REG[1-9]\\d*")) {
          // call showRegister function
          showRegister(tokens[1]);
        }

        // Check if SHOW statement is valid and token1 is a valid register
        else if (tokens.length == 2 && tokens[1].matches("REG")) {
          // call showAllRegister function
          showAllRegister();
        }

        // Invalid SHOW statement
        else {
          System.out.println("Invalid SHOW statement: " + statement);
        }
        break;

      // Invalid operation
      default:
        System.out.println("Unsupported operation: " + operation);
    }
  }

  /**
   * Moves a value to a specified register.
   *
   * @param register the register to move the value to
   * @param value    the value to be moved
   */
  private static void moveValueToRegister(String register, int value) {

    // Put value in registers map
    if(!registers.containsKey(register)){
       registers.put(register, value);
       storeRegisterValues();
       System.out.println("Value moved to register: " + register +" : "+value);
     }
     else{
       System.out.println("This register already exists");
     }
  }

  /**
   * Adds a value to the specified register and updates the value in the registers
   * map.
   *
   * @param register the register to add the value to
   * @param value    the value to be added to the register
   */

  private static void addValueToRegister(String register, int value) {

    // Add value to register and put in registers map
    registers.put(register, registers.get(register) + value);
    // Store register values
    storeRegisterValues();
    System.out.println("Value Added to register: " + register +" : "+ registers.get(register));
  }

  /**
   * Displays the value of the specified register.
   *
   * @param register the register whose value is to be displayed
   */
  private static void showRegister(String register) {

     // Check if register exists
    if (!registers.containsKey(register)) {
      System.out.println("The register does not exist");
    }
    // Extract value of register
    int value = registers.get(register);

    // Print value
    System.out.println(register + " = " + value);
  }

  /**
   * Show all registers.
   */
  private static void showAllRegister() {

    if(registers.isEmpty()){
      System.out.println("No registers exist");
    }

    // Extract all registers
    for (String s : registers.keySet()) {

      // Extract value of register
      int value = registers.get(s);

      // Print value
      System.out.println(s + " : " + value);
    }

  }

  /**
   * Stores the register values in a file named "assembly_file.txt".
   *
   */
  private static void storeRegisterValues() {
    // Store register values in a file
    try {
      FileWriter writer = new FileWriter("assembly_file.txt");
      for (Map.Entry<String, Integer> entry : Main.registers.entrySet()){
        writer.append(entry.getKey()).append(" ").append(String.valueOf(entry.getValue())).append("\n");
      }
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Reads data from a file and returns a HashMap containing the data.
   *
   * @return         	a HashMap<String,Integer> containing the data read from the file
   * @throws IOException	if an I/O error occurs while reading the file
   */
  private static HashMap<String,Integer> readFromFile() throws IOException {

    HashMap<String, Integer> map = new HashMap<>();
    BufferedReader reader = new BufferedReader(new FileReader("assembly_file.txt"));
    String line;
    while ((line = reader.readLine()) != null) {
      String[] parts = line.split(" ", 2);
      if (parts.length >= 2) {
        String key = parts[0];
        int value = Integer.parseInt(parts[1]);
        if(!map.containsKey(key))
          map.put(key, value);
      }
    }
    return map;
  }
}
