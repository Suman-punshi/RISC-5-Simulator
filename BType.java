package com.example.main;

import java.security.spec.RSAOtherPrimeInfo;

public class BType extends Instruction {

    private int temp1;
    private final int temp2;
    private int imm;
    private int index1;
    private int index2;

    BType(int rs1, int rs2, String funcT3, int imm12) {
        super(rs1, rs2, funcT3, imm12);

        //convert the value of source register 1 to decimal
        this.index1 = Integer.parseInt(Integer.toString(getRs1()), 2);

        //convert the value of source register 2 to decimal
        this.index2 = Integer.parseInt(Integer.toString(getRs2()), 2);

        //get the content present in source register 1
        this.temp1 = RegisterGUI.getVal(index1, 1);

        //get the content present in source register 2
        this.temp2 = RegisterGUI.getVal(index2, 1);

        //get the immediate value
        this.imm = Integer.parseInt(Integer.toString(getImm12()), 2);
    }


    @Override
    public int Execute() {


        switch (getFuncT3())
        {
            //BEQ
            case "000":
                if(temp1 == temp2)
                {
                    return imm;
                }
                break;

            //BNE
            case "001":
                if(temp1 != temp2)
                {
                    return imm;
                }
                break;

            //BLT
            case "100":
                if(temp1 < temp2)
                {
                    return imm;
                }
                break;

            //BGE
            case "101":
                if(temp1 >= temp2)
                {
                    return imm;
                }
                break;

            //BLTU
            case "110":
                if(Integer.compareUnsigned(temp1, temp2) == -1)
                {
                    return imm;
                }
                break;

            //BGEU
            case "111":
                if(Integer.compareUnsigned(temp1, temp2) == 0 | Integer.compareUnsigned(temp1, temp2) == 1 )
                {
                    return imm;
                }
                break;
        }

        //return -1 if the condition is not met
        return -1;

    }
}