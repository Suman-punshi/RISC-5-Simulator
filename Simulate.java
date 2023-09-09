package com.example.main;

import java.io.FileNotFoundException;
import javax.swing.JOptionPane;


public class Simulate {


    //variables for keeping track of instructions;
    private static int count = 0;
    private static int tempC = 0;

    //boolean variables for Jumping the program counter specific to specific instruction
    private static boolean bout = false;
    //variable to get the result from instruction classes
    private int res;

    //variable which store the index where to write the value;
    private int index;

    //variable to indicate instruction format
    private int format;

    //tokenize the machine code
    private String[] tokens;

    Simulate(String fName, boolean start) throws Exception {


        //reset the counter if start variable is true
        if(start){
            count = 0;
        }

        //Jump the program counter to specific instruction number
        if(bout){

            if(count == Codebox.getLineNum()){
                JOptionPane.showMessageDialog(null, "All instructions have been executed");
            }

            //create object of program counter class
            ProgramCounter pc = new ProgramCounter(fName, count);

            //highlight the current instruction in text pane
            Codebox.HighlightText(count);
            //get the instruction according to the line number passed using get instruction method of program counter class
            String inst_line = pc.getInstruction();
            //split the instruction
            tokens = inst_line.split(" ");


            //retain the previously executed B-type instruction
            count = tempC;

            //set bout too false to indicate that the target instruction have been executed
            bout = false;
        }

        //otherwise go to next instruction
        else {

            if(count == Codebox.getLineNum()){
                JOptionPane.showMessageDialog(null, "All instructions have been executed");
            }
            //store the counter in temporary variable
            tempC = count;
            //create object of program counter class
            ProgramCounter pc = new ProgramCounter(fName, count);
            //highlight the current instruction in text pane
            Codebox.HighlightText(count);
            //get the instruction according to the line number passed using get instruction method of program counter class
            String inst_line = pc.getInstruction();
            System.out.println(inst_line);
            //split the instruction
            tokens = inst_line.split(" ");
            System.out.println(tokens[7]);
            count++;

        }



        //get the format of instruction
        format = Integer.parseInt(tokens[0]);

        //if format is equal to 0 then the instruction has R-type format so, create object of alu class and execute it
        if(Integer.parseInt(tokens[0]) == 0){
            ALU rInst =  new ALU(tokens[1], Integer.parseInt(tokens[5]), Integer.parseInt(tokens[6]), Integer.parseInt(tokens[4]), tokens[3], tokens[2]);
            //get the result from the execute method
            res = rInst.Execute();
            //get the where to write the value
            index = rInst.getIndex3();
        }
        //if format is equal to 1 then the instruction has I-type format, so create object of IType class and execute it
        else if (Integer.parseInt(tokens[0]) == 1)
        {
            System.out.println(tokens[1]);
            System.out.println(tokens[5]);
            System.out.println(tokens[4]);
            System.out.println(tokens[7]);
            long imm = Long.parseLong(tokens[7], 2);
            int immediate_val = (int) imm;
            Itype iInst = new Itype(tokens[1], Integer.parseInt(tokens[5]), Integer.parseInt(tokens[4]), immediate_val, tokens[2]);
            if(Assembler.getIsNegative()){
                //get the result from the execute method
                try {
                    res = iInst.Execute();
                }
                catch(Exception e){
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Invalid access error", JOptionPane.ERROR_MESSAGE);
                }
                //get the where to write the value
            }
            else {
                //get the result from the execute method
                try {
                    res = iInst.Execute();
                }
                catch(Exception e){
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Invalid access error", JOptionPane.ERROR_MESSAGE);
                }
                //get the where to write the value
            }
            System.out.println(res);
            index = iInst.getIndex3();

        }
        //if format is equal to 2 then the instruction has S-type format, so create object of SType class and execute it
        else if(format == 2)
        {

            System.out.println("S-type in simulate");
            long imm = Long.parseLong(tokens[7], 2);
            System.out.println(imm);
            int immediate_val = (int) imm;
            System.out.println(immediate_val);
                Stype sInst = new Stype(Integer.parseInt(tokens[6]), Integer.parseInt(tokens[5]), immediate_val, tokens[2]);
                System.out.println("S-type constructor");
                //get the result from the execute method
                res = sInst.Execute();
                try {
                    System.out.println("Try block");
                    index = sInst.getIndex();
                    System.out.println("after get Index method");
                }
                catch(Exception e){
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Invalid access error", JOptionPane.ERROR_MESSAGE);
                }

                //JOptionPane.showMessageDialog(null, e.getMessage(), "Invalid access error", JOptionPane.ERROR_MESSAGE);

            System.out.println("S-type executed");
            //get the where to write the value
            System.out.println("S-type index");
        }
        else   //if format is equal to 3 then the instruction has B-type format, so create object of BType class and execute it
        {
            BType bInst = new BType(Integer.parseInt(tokens[5]), Integer.parseInt(tokens[6]), tokens[2], Integer.parseInt(tokens[7]));
            res = bInst.Execute();

            //if condition is true then indicate the system to jump to the target instruction
            if(res != -1)
            {
                bout = true;
                count = res;
            }
            //other go to next instruction
            else
            {
                bout = false;
                count = tempC + 1;
            }

        }

    }


    //setters and getters for instance variables
    public int getRes(){
        return res;
    }

    public int getIndex(){
        return index;
    }

    public int getFormat() {
        return format;
    }
}
