# Assembly Language
This Java program simulates an assembly language execution. It allows the user to interactively enter commands and perform operations on registers.

## Technical Stack Used
- JAVA 8
- IntelliJ IDEA

## Getting Started
1. Clone the repository or download the source code files.
2. Open the project in your preferred Java development environment.
3. Compile and run the Main.java file to start the program.

## Usage
1. Upon running the program, a menu of commands will be displayed.
   - Available commands:
      - MV: Make a new variable
        ```
        For e.g:
        MV REG1,#500
        MV REG2,#200
        ```
        The above statements will create registers named REG1 and REG2 and initialize their value by 500 and 200 respectively.
    
      - ADD: Add the values of registers
        ```
        For e.g:
        ADD REG1,REG2
        ADD REG1,100
        ```
           In first instruction of the above statements will add the values of REG1 and REG2 and store the sum in the first variable.
           Similarly, in second instruction the value 100 will get added in the register REG1.
      - SHOW: See the result of a register
        ```
        For e.g:
        SHOW REG1 (It will display Specific Register)
        SHOW REG (It will Display all the Registers)
        ```
           In first instruction of the above statements will display the value of REG1 specifically and in second instruction 
           it will display all the registers.
      - EXIT or QUIT: Terminate the program
           ```
            For e.g:
            EXIT 
            QUIT
          ```
          In both the cases the program will be terminated.
3. Enter the desired command and follow the prompts to perform operations on registers.
4. The current register values are stored in a file named assembly_file.txt.
5. The program will continue running until the user enters the EXIT or QUIT command.

## File Descriptions
- Main.java: Contains the main method and program flow and all the necessary functions.
- assembly_file.txt: Stores the current register values.

##

Happy Coding!