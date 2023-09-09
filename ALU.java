package com.example.main;

public class ALU extends Instruction{


    private int temp1, temp2, index1, index2, index3;
    public ALU(String opcode, int rs1, int rs2, int rd, String funcT7, String funcT3){
        super(opcode,rs1, rs2, rd, funcT7, funcT3);

        //convert the value of source register 1 to decimal
        this.index1 = Integer.parseInt(Integer.toString(getRs1()), 2);

        //convert the value of source register 2 to decimal
        this.index2 = Integer.parseInt(Integer.toString(getRs2()), 2);

        //convert the value of destination register to decimal
        this.index3 = Integer.parseInt(Integer.toString(getRd()), 2);

        //get the content present in source register 1
        this.temp1 = RegisterGUI.getVal(index1, 1);

        //get the content present in source register 2
        this.temp2 = RegisterGUI.getVal(index2, 1);
    }

    @Override
    public int Execute() {

        //variable used in shift right arithmetic instruction
        int shift_right;

        //variable to store the result of instructions
        int result = 0;
        switch(getFuncT3()){

            //add and subtract instruction
            case "000":
                //add
                if(getFuncT7().equals("0000000")) {
                    result = temp1 + temp2;
                }
                //subtract
                else{
                    result = temp1 - temp2;
                }
                break;

            //set less than instruction
            case "010":

                result = temp1<temp2?1:0;
                break;

            //set less than unsigned instruction
            case "011":
                
                if (Integer.compareUnsigned(temp1, temp2) < 0)
                    {
                        result = 1;

                    } else
                    {
                        result = 0;
                    }
                break;


            //and instruction
            case "111":
                
                result = temp1 & temp2;
                break;

            //or instruction
            case "110":
                
                result = temp1 | temp2;
                break;

            //xor instruction
            case "100":
                result = temp1 ^ temp2;
                break;

            //shift left logical operation
            case "001":
                result = temp1 << temp2;
                break;

            //shift right logical operation
            case "101":

                if(getFuncT7().equals("0000000"))
                {
                    result = temp1 >> temp2;
                }
                else
                {
                    
                    shift_right = temp1 >> temp2;
                    if(temp1 < 0){
                        shift_right |= (-1 << (32 - temp2));
                    }
                    
                    result = shift_right;
                }
                break;
        }
        return result;
    }


    //setters and getters for instance variables
    public void setTemp1(int temp1) {
        this.temp1 = temp1;
    }

    public void setTemp2(int temp2) {
        this.temp2 = temp2;
    }

    public void setIndex1(int index1) {
        this.index1 = index1;
    }

    public void setIndex2(int index2) {
        this.index2 = index2;
    }

    public void setIndex3(int index3) {
        this.index3 = index3;
    }

    public int getTemp1() {
        return temp1;
    }

    public int getTemp2() {
        return temp2;
    }

    public int getIndex1() {
        return index1;
    }

    public int getIndex2() {
        return index2;
    }
    public int getIndex3() {
        return index3;
    }
}
