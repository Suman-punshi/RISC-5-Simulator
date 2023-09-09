package com.example.main;

import javax.swing.*;

public class Stype extends Instruction{

    private int index1, temp1,temp2, imm, index2;

    Stype(int rs2, int rs1, int imm12, String funcT3){
        super(rs2, rs1, imm12, funcT3);
        System.out.println("S-type constructor reached");
            this.index1 = Integer.parseInt(Integer.toString(getRs1()), 2);
            this.index2 = Integer.parseInt(Integer.toString(getRs2()), 2);
            this.temp1 = RegisterGUI.getVal(index1, 1);
            this.temp2 = RegisterGUI.getVal(index2, 1);
            //this.imm = Integer.parseInt(Integer.toString(getImm12()), 2);


    }

    @Override
    public int Execute() {

        //variable used in store instruction
       int extracted_val, sign_bit, extended_val;

        //variable to store result of instructions
       int result = 0;


        switch(getFuncT3()){

            //store word
            case "010":

                //extract lower 32 bits from specified memory address
                extracted_val =  temp2;
                //extract the sign bit of extracted value
                sign_bit = extracted_val & 0x80000000;
                //extend the extracted value to 32 bits with the sign bit
                extended_val = sign_bit | (extracted_val & 0x7fffffff);
                result = extended_val;
                break;


            //store half word
            case "001":
                //extract lower sixteen bits from specified memory address
                extracted_val =  ((1 << 16) - 1) & temp2;
                //extract the sign bit of extracted value
                sign_bit = extracted_val & 0x80000000;
                //extend the extracted value to 32 bits with the sign bit
                extended_val = sign_bit | (extracted_val & 0x7fffffff);
                result = extended_val;
                break;


            //store byte
            case "000":
                //extract lower 8 bits from specified memory address
                System.out.println("Store byte reached");
                extracted_val =  ((1 << 8) - 1) & temp2;
                System.out.println("extracted value is : " + extracted_val);
                //extract the sign bit of extracted value
                sign_bit = extracted_val & 0x80000000;
                System.out.println("Sign bit is" + sign_bit);
                //extend the extracted value to 32 bits with the sign bit
                extended_val = sign_bit | (extracted_val & 0x7fffffff);
                System.out.println("Extended value is: " + extended_val);
                result = extended_val;
                break;

        }
        System.out.println("result of stype instruction is: " + result);
        return result;
    }


    //setters and getters for instance variables
    public void setIndex1(int index1) {
        this.index1 = index1;
    }

    public void setIndex2(int index2) {
        this.index2 = index2;
    }

    public void setTemp2(int temp2) {
        this.temp2 = temp2;
    }

    public void setTemp1(int temp1) {
        this.temp1 = temp1;
    }

    public void setImm(int imm) {
        this.imm = imm;
    }

    public int getIndex1() {
        return index1;
    }

    public int getIndex2() {
        return index2;
    }

    public int getTemp1() {
        return temp1;
    }

    public int getTemp2() {
        return temp2;
    }

    public int getImm() {
        return imm;
    }


    //getting index where we write the values from registers
    public int getIndex() throws Exception {
        if(temp1 + getImm12() >= 7500){
            throw new Exception("Accessing Invalid memory address");
        }
        else {
            return temp1 + getImm12();
        }

    }
}
