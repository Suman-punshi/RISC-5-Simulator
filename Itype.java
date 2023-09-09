package com.example.main;

import javax.swing.*;

public class Itype extends Instruction{

    //variables getting values from registers
    private int temp1, imm, index1, index3;


    Itype(String opcode, int rs1, int rd, int imm12, String funcT3){
        super( opcode, rs1, rd, imm12, funcT3);

        //convert the value of source register 1 to decimal
        this.index1 = Integer.parseInt(Integer.toString(getRs1()), 2);
        System.out.println(index1);

        //convert the value of destination register to decimal
        this.index3 = Integer.parseInt(Integer.toString(getRd()), 2);
        System.out.println(index3);

        //get the content present in source register 1
        this.temp1 = RegisterGUI.getVal(index1, 1);

        //get the immediate value
        if(Assembler.getIsNegative()){
            this.imm = imm12;
        }
        else {
            this.imm = imm12;
            //this.imm = Integer.parseInt(Integer.toString(getImm12()), 2);
        }

    }




    @Override
    public int Execute() throws Exception {

        if(imm > 2047 | imm< -2047){
            throw new Exception("Immediate value is out of range");
        }

        int extracted_val, sign_bit, extended_val, unsigned_val;

        //variable to store result of instructions
        int result = 0;

        //variable used for shift right arithmetic immediate instruction
        int shift_right;

        switch (getFuncT3()){

            //add immediate and load byte instruction
            case "000":
                //add immediate
                if(getOpcode().equals("0010011"))
                {
                    System.out.println("registers val: " + RegisterGUI.getVal(index1, 1));
                    result = temp1 + imm;
                }
                //load byte
                else
                {
                    //extract lower eight bits from specified memory address
                    if(imm + temp1 >= 7500 | imm + temp1 <= 0){
                        throw new Exception("Invalid access of memory");
                    }
                    extracted_val = ((1 << 8) - 1) & Integer.parseInt(MemoryViewerGUI.getVal(imm + temp1, 3));
                    //extract the sign bit of extracted value
                    sign_bit = extracted_val & 0x80000000;
                    //extend the extracted value to 32 bits with the sign bit
                    extended_val = sign_bit | (extracted_val & 0x7fffffff);
                    result = extended_val;
                }
                break;



            //set less than immediate and load word instruction
            case "010":
                if(getOpcode().equals("0010011"))
                {
                    result = temp1 < imm ? 1 : 0;
                    RegisterGUI.writeVal(index3, 1, Integer.toString(temp1 < imm ? 1 : 0));
                    RegisterGUI.writeVal(index3, 2, String.format("0x%08X",temp1 < imm ? 1 : 0));
                }
                //load word
                else
                {
                    if(imm + temp1 >= 7500 | imm + temp1 <= 0){
                        throw new Exception("Invalid access of memory");
                    }
                    //extract lower 32 bits from specified memory address
                    extracted_val = Integer.parseInt(MemoryViewerGUI.getVal(imm + temp1, 3));
                    //extract the sign bit of extracted value
                    sign_bit = extracted_val & 0x80000000;
                    //extend the extracted value to 32 bits with the sign bit
                    extended_val = sign_bit | (extracted_val & 0x7fffffff);

                    result = extended_val;
                }
                break;


            //Set less than immediate unsigned and load double word
            case "011":
                if(getOpcode().equals("0010011"))
                {
                    if (Integer.compareUnsigned(temp1, imm) < 0)
                    {
                        result = 1;

                    } else
                    {
                        result = 0;

                    }
                }


                //and instruction
            case "111":
                result = temp1 & imm;
                break;

            //xor immediate and load byte unsigned instruction
            case "100":
                if(getOpcode().equals("0010011"))
                {
                    result = temp1 ^ imm;
                }
                else
                {
                    if(imm + temp1 >= 7500 | imm + temp1 <= 0){
                        throw new Exception("Invalid access of memory");
                    }
                    //extract lower eight bits from specified memory address
                    extracted_val = ((1 << 8) - 1) & Integer.parseInt(MemoryViewerGUI.getVal(imm + temp1, 3));
                    //zero extend the value
                    unsigned_val = extracted_val & 0xff;
                    result = unsigned_val;
                }
                break;



            //or instruction and load word unsigned instruction
            case "110":
                if(getOpcode().equals("0010011"))
                {
                    result = temp1 | imm;
                }
                else
                {
                    if(imm + temp1 >= 7500 | imm + temp1 <= 0){
                        throw new Exception("Invalid access of memory");
                    }
                    //extract lower 32 bits from specified memory address
                    extracted_val = Integer.parseInt(MemoryViewerGUI.getVal(imm + temp1, 3));
                    //zero extend the value
                    unsigned_val = extracted_val & 0xff;
                    result = unsigned_val;
                }
                break;



            //shift left logical immediate and load half word
            case "001":
                if(getOpcode().equals("0010011"))
                {
                    result = temp1 << imm;
                }
                // load half word
                else
                {
                    if(imm + temp1 >= 7500 | imm + temp1 <= 0){
                        throw new Exception("Invalid access of memory");
                    }
                    //extract lower sixteen bits from specified memory address
                    extracted_val = ((1 << 16) - 1) & Integer.parseInt(MemoryViewerGUI.getVal(imm + temp1, 3));
                    //extract the sign bit of extracted value
                    sign_bit = extracted_val & 0x80000000;
                    //extend the extracted value to 32 bits with the sign bit
                    extended_val = sign_bit | (extracted_val & 0x7fffffff);
                    result = extended_val;
                }
                break;



            //shift right logical immediate and load half word unsigned immediate
            case "101":
                if(getOpcode().equals("0010011") & getFuncT7().equals("0000000"))
                {
                    result = temp1 >> imm;
                }
                //shift right arithmetic immediate
                else if(getOpcode().equals("0010011") & getFuncT7().equals("0100000")){
                    shift_right = temp1 >> imm;
                    if(temp1 < 0){
                        shift_right |= (-1 << (32 - imm));
                    }
                    result = shift_right;
                }

                //load half word unsigned
                else
                {
                    if(imm + temp1 >= 7500 | imm + temp1 <= 0){
                        throw new Exception("Invalid access of memory");
                    }
                    //extract lower 32 bits from specified memory address
                    extracted_val = ((1 << 16) - 1) & Integer.parseInt(MemoryViewerGUI.getVal(imm + temp1, 3));
                    //zero extend the value
                    unsigned_val = extracted_val & 0xff;
                    result = unsigned_val;
                }
                break;





        }
        return result;
    }



    //setters and getters for instance variables
    public void setImm(int imm) {
        this.imm = imm;
    }

    public void setTemp1(int temp1) {
        this.temp1 = temp1;
    }

    public void setIndex1(int index1) {
        this.index1 = index1;
    }

    public void setIndex3(int index3) {
        this.index3 = index3;
    }

    public int getImm() {
        return imm;
    }

    public int getTemp1() {
        return temp1;
    }

    public int getIndex1() {
        return index1;
    }
    public int getIndex3() {
        return index3;
    }
}
