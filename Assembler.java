package com.example.main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class Assembler {

    //variables to store the fields of instruction
    private String opcode;
    private String rs1;
    private String rs2;
    private String rd;
    private String imm12;
    private String funcT3;
    private String funcT7;
    private int format;

    //variable of to keep track of instruction number
    private int insTCounter = 0;

    //Array list to store the label to instructions
    private ArrayList<String> label = new ArrayList<String>();

    //Array list to store the label to addresses to labeled instructions
    private ArrayList<Integer> Addresses = new ArrayList<Integer>();
    //String array to store the tokenized instruction
    private String[] tokens;

    //files for storing assembly code and machine code
    private File code, Exe;

    //File writer for clearing the file and writing the code onto the file
    private FileWriter myWriter, clear;

    //Variable to determine whether the instruction is labeled or not
    private static int labelExist = 0;
    private static boolean isNegative = false;


    public Assembler(String filename, String exe) throws IOException {
        //create the file object containing assembly code
        code = new File(filename);
        //create the file object containing machine code
        Exe = new File(exe);
        //file writer for writing the machine code on machine code file
        myWriter = new FileWriter(Exe, true);
        //file writer for clearing the machine code file
        clear = new FileWriter(Exe, false);
        //clear any previous data on machine code file
        clear.write("");
        clear.close();
    }

    public void Assemble() throws Exception {

        //scanner to read assembly code from file
        Scanner sc = new Scanner(code);

        //variable to store one line of assembly code
        String codeLine;

        //variable to keep track of memory locations in processor
        int count = 0;

        //string to store machine code for line
        String text;

        //loop over the file containing assembly code
        loop: while(sc.hasNextLine()){

            //read line by line
            codeLine = sc.nextLine();

            //if instruction contains a label then first extract the label then tokenize the instruction
            if(codeLine.contains(codeLine.valueOf(":"))){

                //label and remaining instruction
                String[] temps = codeLine.split(":");

                //determining whether line is comment or an instruction
                if(temps[0].startsWith("#"))
                {
                    continue;
                }
                //add label and address to array list to keep track of labeled instructions
                label.add(temps[0]);
                System.out.println(label);
                Addresses.add(insTCounter);
                System.out.println(Addresses);

                //split the remaining instruction
                temps[1].trim();
                System.out.println(temps[1]);
                System.out.println();
                tokens= temps[1].split("[ :,();]+");
            }

            //if instruction does not contain a label then simply tokenize it
            else{
                //split code line into mnemonic, immediate values and register
                tokens= codeLine.split("[ :,();]+");

                if(tokens[0].startsWith("#")){
                    continue;
                }
            }



            //If instruction length is not correct then throw exception
            if(tokens.length != 5 && tokens.length != 4){
                System.out.println(tokens);
                throw new Exception("Invalid syntax at line: " + insTCounter);
            }



            //switch statement to determine opcode, func3, func7 fields
            switch (tokens[0]){
                case "BEQ":
                    this.opcode = "1100011";
                    this.funcT3 = "000";
                    this.format = 3;
                    break;

                case "BNE":
                    this.opcode = "1100011";
                    this.funcT3 = "001";
                    this.format = 3;
                    break;

                case "BLT":
                    this.opcode = "1100011";
                    this.funcT3 = "100";
                    this.format = 3;
                    break;

                case "BGE":
                    this.opcode = "1100011";
                    this.funcT3 = "101";
                    this.format = 3;
                    break;

                case "BLTU":
                    this.opcode = "1100011";
                    this.funcT3 = "110";
                    this.format = 3;
                    break;

                case "BGEU":
                    this.opcode = "1100011";
                    this.funcT3 = "111";
                    this.format = 3;
                    break;

                case "ADD":
                    this.opcode = "0110011";
                    this.funcT3 = "000";
                    this.funcT7 = "0000000";
                    this.format = 0;
                    break;


                case "SUB":
                    this.opcode = "0110011";
                    this.funcT3 = "000";
                    this.funcT7 = "0100000";
                    this.format = 0;
                    break;


                case "SLT":
                    this.opcode = "0110011";
                    this.funcT3 = "010";
                    this.funcT7 = "0000000";
                    this.format = 0;
                    break;


                case "SLTU":
                    this.opcode = "0110011";
                    this.funcT3 = "011";
                    this.funcT7 = "0000000";
                    this.format = 0;
                    break;

                case "AND":
                    this.opcode = "0110011";
                    this.funcT3 = "111";
                    this.funcT7 = "0000000";
                    this.format = 0;
                    break;

                case "OR":
                    this.opcode = "0110011";
                    this.funcT3 = "110";
                    this.funcT7 = "0000000";
                    this.format = 0;
                    break;

                case "XOR":
                    this.opcode = "0110011";
                    this.funcT3 = "100";
                    this.funcT7 = "0000000";
                    this.format = 0;
                    break;

                case "SLL":
                    this.opcode = "0110011";
                    this.funcT3 = "001";
                    this.funcT7 = "0000000";
                    this.format = 0;
                    break;

                case "SRL":
                    this.opcode = "0110011";
                    this.funcT3 = "101";
                    this.funcT7 = "0000000";
                    this.format = 0;
                    break;

                case "SRA":
                    this.opcode = "0110011";
                    this.funcT3 = "101";
                    this.funcT7 = "0100000";
                    this.format = 0;
                    break;


                case "ADDI":
                    this.opcode = "0010011";
                    this.funcT3 = "000";
                    this.format = 1;
                    break;

                case "SLTI":
                    this.opcode = "0010011";
                    this.funcT3 = "010";
                    this.format = 1;
                    break;

                case "SLTIU":
                    this.opcode = "0010011";
                    this.funcT3 = "011";
                    this.format = 1;
                    break;

                case "ANDI":
                    this.opcode = "0010011";
                    this.funcT3 = "111";
                    this.format = 1;
                    break;


                case "ORI":
                    this.opcode = "0010011";
                    this.funcT3 = "110";
                    this.format = 1;
                    break;

                case "XORI":
                    this.opcode = "0010011";
                    this.funcT3 = "100";
                    this.format = 1;
                    break;

                case "SLLI":
                    this.opcode = "0010011";
                    this.funcT3 = "001";
                    this.format = 1;
                    break;

                case "SRLI":
                    this.opcode = "0010011";
                    this.funcT3 = "101";
                    this.funcT7 = "0000000";
                    this.format = 1;
                    break;

                case "SRAI":
                this.opcode = "0010011";
                this.funcT3 = "001";
                this.funcT7 = "0100000";
                this.format = 1;
                break;


                case "LD":
                    this.opcode = "0000011";
                    this.funcT3 = "011";
                    this.format = 1;
                    break;


                case "LW":
                    this.opcode = "0000011";
                    this.funcT3 = "010";
                    this.format = 1;
                    break;

                case "LH":
                    this.opcode = "0000011";
                    this.funcT3 = "001";
                    this.format = 1;
                    break;

                case "LB":
                    this.opcode = "0000011";
                    this.funcT3 = "000";
                    this.format = 1;
                    break;

                case "LWU":
                    this.opcode = "0000011";
                    this.funcT3 = "110";
                    this.format = 1;
                    break;

                case "LHU":
                    this.opcode = "0000011";
                    this.funcT3 = "101";
                    this.format = 1;
                    break;


                case "LBU":
                    this.opcode = "0000011";
                    this.funcT3 = "100";
                    this.format = 1;
                    break;

                case "SW":
                    this.opcode = "0100011";
                    this.funcT3 = "010";
                    this.format = 2;
                    break;

                case "SH":
                    this.opcode = "0100011";
                    this.funcT3 = "001";
                    this.format = 2;
                    break;


                case "SD":
                    this.opcode = "0100011";
                    this.funcT3 = "011";
                    this.format = 2;
                    break;


                case "SB":
                    this.opcode = "0100011";
                    this.funcT3 = "000";
                    this.format = 2;
                    break;

                default:
                    throw new Exception("Invalid Mnemonic at line: " + insTCounter + 1);
            }//end switch for opcode, func3, func7

            //switch statement to determine destination register, and source register 2 according to instruction format
            switch(tokens[1]){
                case "x0":
                    if(format == 0 | format == 1)
                    {
                        this.rd = "00000";
                    }
                    else if(format == 2)
                    {
                        this.rs2 = "00000";
                    }
                    else
                    {
                        this.rs1 = "00000";
                    }
                    break;

                case "x1":
                    if(format == 0 | format == 1)
                    {
                        this.rd = "00001";
                    }
                    else if (format == 2)
                    {
                        this.rs2 = "00001";
                    }
                    else
                    {
                        this.rs1 = "00001";
                    }
                    break;

                case "x2":
                    if(format == 0 | format == 1)
                    {
                        this.rd = "00010";
                    }
                    else if (format == 2)
                    {
                        this.rs2 = "00010";
                    }
                    else
                    {
                        this.rs1 = "00010";
                    }
                    break;

                case "x3":
                    if(format == 0 | format == 1)
                    {
                        this.rd = "00011";
                    }
                    else if (format == 2)
                    {
                        this.rs2 = "00011";
                    }
                    else
                    {
                        this.rs1 = "00011";
                    }
                    break;

                case "x4":
                    if(format == 0 | format == 1)
                    {
                        this.rd = "00100";
                    }
                    else if(format == 2)
                    {
                        this.rs2 = "00100";
                    }
                    else
                    {
                        this.rs1 = "00100";
                    }
                    break;

                case "x5":
                    if(format == 0 | format == 1)
                    {
                        this.rd = "00101";
                    }
                    else if (format == 2)
                    {
                        this.rs2 = "00101";
                    }
                    else
                    {
                        this.rs1 = "00101";
                    }
                    break;

                case "x6":
                    if(format == 0 | format == 1)
                    {
                        this.rd = "00110";
                    }
                    else if (format == 2)
                    {
                        this.rs2 = "00110";
                    }
                    else
                    {
                        this.rs1 = "00110";
                    }
                    break;

                case "x7":
                    if(format == 0 | format == 1)
                    {
                        this.rd = "00111";
                    }
                    else if(format == 2)
                    {
                        this.rs2 = "00111";
                    }
                    else
                    {
                        this.rs1 = "00111";
                    }
                    break;

                case "x8":
                    if(format == 0 | format == 1)
                    {
                        this.rd = "01000";
                    }
                    else if(format == 2)
                    {
                        this.rs2 = "01000";
                    }
                    else
                    {
                        this.rs1 = "01000";
                    }
                    break;

                case "x9":
                    if(format == 0 | format == 1)
                    {
                        this.rd = "01001";
                    }
                    else if(format == 2)
                    {
                        this.rs2 = "01001";
                    }
                    else
                    {
                        this.rs1 = "01001";
                    }
                    break;

                case "x10":
                    if(format == 0 | format == 1)
                    {
                        this.rd = "01010";
                    }
                    else if (format == 2)
                    {
                        this.rs2 = "01010";
                    }
                    else
                    {
                        this.rs1 = "01010";
                    }
                    break;

                case "x11":
                    if(format == 0 | format == 1)
                    {
                        this.rd = "01011";
                    }
                    else if(format == 2)
                    {
                        this.rs2 = "01011";
                    }
                    else
                    {
                        this.rs1 = "01011";
                    }
                    break;

                case "x12":
                    if(format == 0 | format == 1)
                    {
                        this.rd = "01100";
                    }
                    else if (format == 2)
                    {
                        this.rs2 = "01100";
                    }
                    else
                    {
                        this.rs1 = "01100";
                    }
                    break;

                case "x13":
                    if(format == 0 | format == 1)
                    {
                        this.rd = "01101";
                    }
                    else if(format == 2)
                    {
                        this.rs2 = "01101";
                    }
                    else
                    {
                        this.rs1 = "01101";
                    }
                    break;

                case "x14":
                    if(format == 0 | format == 1)
                    {
                        this.rd = "01110";
                    }
                    else if(format == 2)
                    {
                        this.rs2 = "01110";
                    }
                    else
                    {
                        this.rs1 = "01110";
                    }
                    break;

                case "x15":
                    if(format == 0 | format == 1)
                    {
                        this.rd = "01111";
                    }
                    else if(format == 2)
                    {
                        this.rs2 = "01111";
                    }
                    else
                    {
                        this.rs1 = "01111";
                    }
                    break;

                case "x16":
                    if(format == 0 | format == 1)
                    {
                        this.rd = "10000";
                    }
                    else if(format == 2)
                    {
                        this.rs2 = "10000";
                    }
                    else
                    {
                        this.rs1= "10000";
                    }
                    break;

                case "x17":
                    if(format == 0 | format == 1)
                    {
                        this.rd = "10001";
                    }
                    else if(format == 2)
                    {
                        this.rs2 = "10001";
                    }
                    else
                    {
                        this.rs1 = "10001";
                    }
                    break;

                case "x18":
                    if(format == 0 | format == 1)
                    {
                        this.rd = "10010";
                    }
                    else if(format == 2)
                    {
                        this.rs2 = "10010";
                    }
                    else
                    {
                        this.rs1 = "10010";
                    }
                    break;

                case "x19":
                    if(format == 0 | format == 1)
                    {
                        this.rd = "10011";
                    }
                    else if(format == 2)
                    {
                        this.rs2 = "10011";
                    }
                    else
                    {
                        this.rs1 = "10011";
                    }
                    break;

                case "x20":
                    if(format == 0 | format == 1)
                    {
                        this.rd = "10100";
                    }
                    else if(format == 2)
                    {
                        this.rs2 = "10100";
                    }
                    else
                    {
                        this.rs1 = "10100";
                    }
                    break;

                case "x21":
                    if(format == 0 | format == 1)
                    {
                        this.rd = "10101";
                    }
                    else if(format == 2)
                    {
                        this.rs2 = "10101";
                    }
                    else
                    {
                        this.rs1 = "10101";
                    }
                    break;

                case "x22":
                    if(format == 0 | format == 1)
                    {
                        this.rd = "10110";
                    }
                    else if(format == 2)
                    {
                        this.rs2 = "10110";
                    }
                    else
                    {
                        this.rs1 = "10110";
                    }
                    break;

                case "x23":
                    if(format == 0 | format == 1)
                    {
                        this.rd = "10111";
                    }
                    else if(format == 2)
                    {
                        this.rs2 = "10111";
                    }
                    else
                    {
                        this.rs1 = "10111";
                    }
                    break;

                case "x24":
                    if(format == 0 | format == 1)
                    {
                        this.rd = "11000";
                    }
                    else if(format == 2)
                    {
                        this.rs2 = "11000";
                    }
                    else
                    {
                        this.rs1 = "11000";
                    }
                    break;

                case "x25":
                    if(format == 0 | format == 1)
                    {
                        this.rd = "11001";
                    }
                    else if(format == 2)
                    {
                        this.rs2 = "11001";
                    }
                    else
                    {
                        this.rs1 = "11001";
                    }
                    break;

                case "x26":
                    if(format == 0 | format == 1)
                    {
                        this.rd = "11010";
                    }
                    else if(format == 2)
                    {
                        this.rs2 = "11010";
                    }
                    else
                    {
                        this.rs1 = "11010";
                    }
                    break;

                case "x27":
                    if(format == 0 | format == 1)
                    {
                        this.rd = "11011";
                    }
                    else if(format == 2)
                    {
                        this.rs2 = "11011";
                    }
                    else
                    {
                        this.rs2 = "11011";
                    }
                    break;

                case "x28":
                    if(format == 0 | format == 1)
                    {
                        this.rd = "11100";
                    }
                    else if(format == 2)
                    {
                        this.rs2 = "11100";
                    }
                    else
                    {
                        this.rs1 = "11100";
                    }
                    break;

                case "x29":
                    if(format == 0 | format == 1)
                    {
                        this.rd = "11101";
                    }
                    else if(format == 2)
                    {
                        this.rs2 = "11101";
                    }
                    else
                    {
                        this.rs1 = "11101";
                    }
                    break;

                case "x30":
                    if(format == 0 | format == 1)
                    {
                        this.rd = "11110";
                    }
                    else if(format == 2)
                    {
                        this.rs2 = "11110";
                    }
                    else
                    {
                        this.rs1 = "11110";
                    }
                    break;

                case "x31":
                    if(format == 0 | format == 1)
                    {
                        this.rd = "11111";
                    }
                    else if(format == 2)
                    {
                        this.rs2 = "11111";
                    }
                    else
                    {
                        this.rs1 = "11111";
                    }

                    break;

                default:
                    if(tokens[1].matches("\\d+")){
                        throw new Exception("Immediate value is not allowed in Place of destination register: line " + insTCounter + 1);
                    }
            }//end switch statement to determine destination register, and source register 2 according to instruction format


            //switch statement to determine first source register or immediate value
            switch(tokens[2]) {
                case "x0":
                    if (format == 0 | format == 1 | format == 2) {
                        this.rs1 = "00000";
                    } else {
                        this.rs2 = "00000";
                    }
                    break;

                case "x1":
                    if (format == 0 | format == 1 | format == 2) {
                        this.rs1 = "00001";
                    } else {
                        this.rs2 = "00001";
                    }
                    break;

                case "x2":
                    if (format == 0 | format == 1 | format == 2) {
                        this.rs1 = "00010";
                    } else {
                        this.rs2 = "00010";
                    }

                    break;

                case "x3":
                    if (format == 0 | format == 1 | format == 2) {
                        this.rs1 = "00011";
                    } else {
                        this.rs2 = "00011";
                    }
                    break;

                case "x4":
                    if (format == 0 | format == 1 | format == 2) {
                        this.rs1 = "00100";
                    } else {
                        this.rs2 = "00100";
                    }
                    break;

                case "x5":
                    if (format == 0 | format == 1 | format == 2) {
                        this.rs1 = "00101";
                    } else {
                        this.rs2 = "00101";
                    }

                    break;

                case "x6":
                    if (format == 0 | format == 1 | format == 2) {
                        this.rs1 = "00110";
                    } else {
                        this.rs2 = "00110";
                    }
                    break;

                case "x7":
                    if (format == 0 | format == 1 | format == 2)
                    {
                        this.rs1 = "00111";
                    }
                    else
                    {
                        this.rs2 = "00111";
                    }
                    break;

                case "x8":
                    if(format == 0 | format == 1 | format == 2)
                    {
                        this.rs1 = "01000";
                    }
                    else
                    {
                        this.rs2 = "01000";
                    }
                    break;

                case "x9":
                    if(format == 0 | format == 1 | format == 2)
                    {
                        this.rs1 = "01001";
                    }
                    else
                    {
                        this.rs2 = "01001";
                    }
                    break;

                case "x10":
                    if(format == 0 | format == 1 | format == 2)
                    {
                        this.rs1 = "01010";
                    }
                    else
                    {
                        this.rs2 = "01010";
                    }
                    break;

                case "x11":
                    if(format == 0 | format == 1 | format == 2)
                    {
                        this.rs1 = "01011";
                    }
                    else
                    {
                        this.rs2 = "01011";
                    }
                    break;

                case "x12":
                    if(format == 0 | format == 1 | format == 2)
                    {
                        this.rs1 = "01100";
                    }
                    else
                    {
                        this.rs2 = "01100";
                    }
                    break;

                case "x13":
                    if(format == 0 | format == 1 | format == 2)
                    {
                        this.rs1 = "01101";
                    }
                    else
                    {
                        this.rs2 = "01101";
                    }
                    break;

                case "x14":
                    if(format == 0 | format == 1 | format == 2)
                    {
                        this.rs1 = "01110";
                    }
                    else
                    {
                        this.rs2= "01110";
                    }
                    break;

                case "x15":
                    if(format == 0 | format == 1 | format == 2)
                    {
                        this.rs1 = "01111";
                    }
                    else
                    {
                        this.rs2 = "01111";
                    }
                    break;

                case "x16":
                    if(format == 0 | format == 1 | format == 2)
                    {
                        this.rs1 = "10000";
                    }
                    else
                    {
                        this.rs2 = "10000";
                    }
                    break;

                case "x17":
                    if(format == 0 | format == 1 | format == 2)
                    {
                        this.rs1 = "10001";
                    }
                    else
                    {
                        this.rs2 = "10001";
                    }
                    break;

                case "x18":
                    if(format == 0 | format == 1 | format == 2)
                    {
                        this.rs1 = "10010";
                    }
                    else
                    {
                        this.rs2 = "10010";
                    }
                    break;

                case "x19":
                    if(format == 0 | format == 1 | format == 2)
                        this.rs1 = "10011";
                    break;

                case "x20":
                    if(format == 0 | format == 1 | format == 2)
                    {
                        this.rs1 = "10100";
                    }
                    else
                    {
                        this.rs2 = "10100";
                    }
                    break;

                case "x21":
                    if(format == 0 | format == 1 | format == 2)
                    {
                        this.rs1 = "10101";
                    }
                    else
                    {
                        this.rs2 = "10101";
                    }
                    break;

                case "x22":
                    if(format == 0 | format == 1 | format == 2)
                    {
                        this.rs1 = "10110";
                    }
                    else
                    {
                        this.rs2 = "10110";
                    }
                    break;

                case "x23":
                    if(format == 0 | format == 1 | format == 2)
                    {
                        this.rs1 = "10111";
                    }
                    else
                    {
                        this.rs2 = "10111";
                    }
                    break;

                case "x24":
                    if(format == 0 | format == 1 | format == 2)
                    {
                        this.rs1 = "11000";
                    }
                    else
                    {
                        this.rs2 = "11000";
                    }
                    break;

                case "x25":
                    if(format == 0 | format == 1 | format == 2)
                    {
                        this.rs1 = "11001";
                    }
                    else
                    {
                        this.rs2 = "11001";
                    }
                    break;

                case "x26":
                    if(format == 0 | format == 1 | format == 2)
                    {
                        this.rs1 = "11010";
                    }
                    else
                    {
                        this.rs2 = "11010";
                    }
                    break;

                case "x27":
                    if(format == 0 | format == 1 | format == 2)
                    {
                        this.rs1 = "11011";
                    }
                    else
                    {
                        this.rs2 = "11011";
                    }
                    break;

                case "x28":
                    if(format == 0 | format == 1 | format == 2)
                    {
                        this.rs1 = "11100";
                    }
                    else
                    {
                        this.rs2 = "11100";
                    }
                    break;

                case "x29":
                    if(format == 0 | format == 1 | format == 2)
                    {
                        this.rs1 = "11101";
                    }
                    else
                    {
                        this.rs1 = "11101";
                    }
                    break;

                case "x30":
                    if(format == 0 | format == 1 | format == 2)
                    {
                        this.rs1 = "11110";
                    }
                    else
                    {
                        this.rs2 = "11110";
                    }
                    break;

                case "x31":
                    if(format == 0 | format == 1 | format == 2)
                    {
                        this.rs1 = "11111";
                    }
                    else
                    {
                        this.rs2 = "11111";
                    }
                    break;

                default:
                    if(tokens[2].matches("\\d+")){
                        if(format == 0){
                            throw new Exception("Immediate value is not allowed in R-type instruction format: line" + insTCounter + 1);
                        }
                        this.imm12 = Integer.toBinaryString(Integer.parseInt(tokens[2]));
                    }
                    else{
                        throw new Exception("Invalid register or non-integer value at line: " + insTCounter + 1);
                    }
                    break;
            }//end switch statement for source register 1 and immediate value


            //switch statement to determine source register 2 and immediate value
            switch(tokens[3]){
                case "x0":
                    if(format == 0){
                        this.rs2 = "00000";
                    }
                    else
                    {
                        this.rs1 = "00000";
                    }
                    break;

                case "x1":
                    if(format == 0) {
                        this.rs2 = "00001";
                    }
                    else
                    {
                        this.rs1 = "00001";
                    }
                    break;

                case "x2":
                    if(format == 0)
                    {
                        this.rs2 = "00010";
                    }
                    else
                    {
                        this.rs1 = "00010";
                    }
                    break;

                case "x3":
                    if(format == 0)
                    {
                        this.rs2 = "00011";
                    }
                    else
                    {
                        this.rs1 = "00011";
                    }
                    break;

                case "x4":
                    if(format == 0)
                    {
                        this.rs2 = "00100";
                    }
                    else{
                        this.rs1 = "00100";
                    }
                    break;

                case "x5":
                    if(format == 0)
                    {
                        this.rs2 = "00101";
                    }
                    break;

                case "x6":
                    if(format == 0)
                    {
                        this.rs2 = "00110";
                    }
                    else
                    {
                        this.rs1 = "00110";
                    }
                    break;

                case "x7":
                    if(format == 0)
                    {
                        this.rs2 = "00111";
                    }
                    else
                    {
                        this.rs1 = "00111";
                    }
                    break;

                case "x8":
                    if(format == 0)
                    {
                        this.rs2 = "01000";
                    }
                    else
                    {
                        this.rs1 = "01000";
                    }
                    break;

                case "x9":
                    if(format == 0)
                    {
                        this.rs2 = "01001";
                    }
                    else
                    {
                        this.rs1 = "01001";
                    }
                    break;

                case "x10":
                    if(format == 0)
                    {
                        this.rs2 = "01010";
                    }
                    else
                    {
                        this.rs1 = "01010";
                    }
                    break;

                case "x11":
                    if(format == 0)
                    {
                        this.rs2 = "01011";
                    }
                    else
                    {
                        this.rs1 = "01011";
                    }
                    break;

                case "x12":
                    if(format == 0){
                        this.rs2 = "01100";
                    }
                    else{
                        this.rs1 = "01100";
                    }
                    break;

                case "x13":
                    if(format == 0){
                        this.rs2 = "01101";
                    }
                    else
                    {
                        this.rs1 = "01101";
                    }
                    break;

                case "x14":
                    if(format == 0)
                    {
                        this.rs2 = "01110";
                    }
                    else
                    {
                        this.rs1 = "01110";
                    }
                    break;

                case "x15":
                    if(format == 0)
                    {
                        this.rs2 = "01111";
                    }
                    else
                    {
                        this.rs1 = "01111";
                    }
                    break;

                case "x16":
                    if(format == 0){
                        this.rs2 = "10000";
                    }
                    else
                    {
                        this.rs1 = "10000";
                    }
                    break;

                case "x17":
                    if(format == 0)
                    {
                        this.rs2 = "10001";
                    }
                    else
                    {
                        this.rs1 = "10001";
                    }
                    break;

                case "x18":
                    if(format == 0)
                    {
                        this.rs2 = "10010";
                    }
                    else
                    {
                        this.rs1 = "10010";
                    }
                    break;

                case "x19":
                    if(format == 0)
                    {
                        this.rs2 = "10011";
                    }
                    else
                    {
                        this.rs1 = "10011";
                    }
                    break;

                case "x20":
                    if(format == 0){
                        this.rs2 = "10100";
                    }
                    else
                    {
                        this.rs1 = "10100";
                    }
                    break;

                case "x21":
                    if(format == 0){
                        this.rs2 = "10101";
                    }
                    else
                    {
                        this.rs1 = "10101";
                    }
                    break;

                case "x22":
                    if(format == 0)
                    {
                        this.rs2 = "10110";
                    }
                    else
                    {
                        this.rs1 = "10110";
                    }
                    break;

                case "x23":
                    if(format == 0){
                        this.rs2 = "10111";
                    }
                    else
                    {
                        this.rs1 = "10111";
                    }
                    break;

                case "x24":
                    if(format == 0)
                    {
                        this.rs2 = "11000";
                    }
                    else
                    {
                        this.rs1 = "11000";
                    }
                    break;

                case "x25":
                    if(format == 0){
                        this.rs2 = "11001";
                    }
                    else{
                        this.rs1 = "11001";
                    }
                    break;

                case "x26":
                    if(format == 0){
                        this.rs2 = "11010";
                    }
                    else
                    {
                        this.rs1 = "11010";
                    }
                    break;

                case "x27":
                    if(format == 0)
                    {
                        this.rs2 = "11011";
                    }
                    else
                    {
                        this.rs1 = "11011";
                    }
                    break;

                case "x28":
                    if(format == 0){
                        this.rs2 = "11100";
                    }
                    else
                    {
                        this.rs1 = "11100";
                    }
                    break;

                case "x29":
                    if(format == 0)
                    {
                        this.rs2 = "11101";
                    }
                    else
                    {
                        this.rs1 = "11101";
                    }
                    break;

                case "x30":
                    if(format == 0)
                    {
                        this.rs2 = "11110";
                    }
                    else
                    {
                        this.rs1 = "11110";
                    }
                    break;

                case "x31":
                    if(format == 0)
                    {
                        this.rs2 = "11111";
                    }
                    else
                    {
                        this.rs1 = "11111";
                    }
                    break;

                default:
                    if(format == 1 | format == 0)
                    {
                        if (tokens[3].matches("\\d+"))
                        {
                            this.imm12 = Integer.toBinaryString(Integer.parseInt(tokens[3]));
                        }
                        else if(tokens[3].startsWith("-"))
                        {
                            String int_part = tokens[3].substring(1);
                            if(int_part.matches("\\d+")){
                                this.imm12 = Integer.toBinaryString(Integer.parseInt(tokens[3]));
                                isNegative = true;
                                System.out.println(imm12);
                            }
                            else {
                                System.out.println("Exception reached");
                                throw new Exception("Invalid register or non-integer value or undefined label at line" + insTCounter + 1);
                            }
                        }
                    }
                    else if(format == 3)
                    {
                        if(label.contains(tokens[3]))
                        {
                            System.out.println(tokens[3]);
                            labelExist = 1;
                            int address = Addresses.get(label.indexOf(tokens[3]));
                            this.imm12 = Integer.toBinaryString(address);
                        }
                        else{
                            int l = insTCounter + 1;
                            throw new Exception("Invalid register or non-integer value or undefined label at line" + l);
                        }
                    }

            }//end switch for determining source register 2 or immediate value

            //converting binary string to hexadecimal and write opcode in memory
            Console.writeMemory(count, 1, String.format("0x%08X",Integer.parseInt(opcode, 2)));

            //convert binary to hexadecimal and write func3 to memory
            Console.writeMemory(0, 7, String.format("0x%08X",Integer.parseInt(funcT3, 2)));

            //convert the binary to hexadecimal and write immediate value to memory
            if(imm12 == null)
            {
                Console.writeMemory(count, 5, "NULL");
            }
            else
            {
                if(tokens[3].startsWith("-")){
                    long temp_immediate = Long.parseLong(imm12, 2);
                    Integer imm12_int = (int) temp_immediate;
                    System.out.println(imm12_int);
                    Console.writeMemory(count, 5, String.format("0x%08X", imm12_int));
                }
                else {
                    Console.writeMemory(count, 5, String.format("0x%08X", Integer.parseInt(imm12, 2)));
                }
            }

            //convert binary to hexadecimal and write first source register to memory
            if(rs1 == null)
            {
                Console.writeMemory(count, 2, "NULL");
            }
            else
            {
                Console.writeMemory(count, 2, String.format("0x%08X", Integer.parseInt(rs1, 2)));
            }

            //convert binary to hexadecimal and write second source register to memory
            if(rs2 == null)
            {
                Console.writeMemory(count, 3, "NULL");
            }
            else
            {
                Console.writeMemory(count, 3, String.format("0x%08X", Integer.parseInt(rs2, 2)));
            }

            //convert binary to hexadecimal and write func7 to memory
            if (funcT7 == null)
            {
                Console.writeMemory(count, 6, "NULL");
            }
            else
            {
                Console.writeMemory(count, 6, String.format("0x%08X",Integer.parseInt(funcT7, 2)));
            }

            //convert binary to hexadecimal and write destination register to memory
            if (rd == null)
            {
                Console.writeMemory(count, 4, "NULL");
            }
            else
            {
                Console.writeMemory(count, 4, String.format("0x%08X",Integer.parseInt(rd, 2)));
            }

            //store machine code in text variable
            text = format + " " + opcode + " " + funcT3 + " " + funcT7 + " " + rd + " " + rs1 + " " + rs2 + " " + imm12 + "\n";

            //write machine code in exe file using write method of file writer class
            try {
                myWriter.write(text);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            //increment the address position
            count++;

            //refresh the table in GUI to show Immediate changes
            Console.reset();


            //reset the value of registers and immediate value
            rd = null;
            rs1 = null;
            rs2 = null;
            imm12 = null;
            insTCounter++;
        }
        //close the file
        myWriter.close();
        // end while
    }//end method


    public static int getLabelExist(){
        return labelExist;
    }

    public static boolean getIsNegative(){
        return isNegative;
    }

}// end class