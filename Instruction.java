package com.example.main;

public abstract class Instruction{

    private String opcode;
    private int rs1;
    private int rs2;
    private int rd;
    private int imm12;
    private String funcT7;
    private String funcT3;


    //constructor for R-type instruction
    Instruction(String opcode, int rs1, int rs2, int rd, String funcT7, String funcT3){
        this.opcode = opcode;
        this.rs1 = rs1;
        this.rs2 = rs2;
        this.rd = rd;
        this.funcT7 = funcT7;
        this.funcT3 = funcT3;
        //super(opcode,rs1, rs2, rd, funcT7, funcT3);
    }

    //constructor for I-type instruction
    Instruction(String opcode, int rs1, int rd, int imm12, String funcT3){
        this.opcode = opcode;
        this.rs1 = rs1;
        this.rd = rd;
        this.imm12 = imm12;
        this.funcT3 = funcT3;

        System.out.println(opcode);
        System.out.println(rs1);
        System.out.println(rd);
        System.out.println(imm12);
        System.out.println(funcT3);
    }

    //constructor for S-type Instruction
    Instruction(int rs2, int rs1, int imm12, String funcT3)
    {
        this.rs2 = rs2;
        this.rs1 = rs1;
        this.imm12 = imm12;
        this.funcT3 = funcT3;
    }

    //constructor for B-type instruction
    Instruction(int rs1, int rs2, String funcT3, int imm12){
        this.rs1 = rs1;
        this.rs2 = rs2;
        this.funcT3 = funcT3;
        this.imm12 = imm12;
    }

    //setters and getters for instruction fields
    public void setOpcode(String opcode) {
        this.opcode = opcode;
    }

    public void setRs1(int rs1) {
        this.rs1 = rs1;
    }

    public void setRs2(int rs2) {
        this.rs2 = rs2;
    }

    public void setRd(int rd) {
        this.rd = rd;
    }

    public void setImm12(int imm12) {
        this.imm12 = imm12;
    }

    public void setFuncT7(String funcT7) {
        this.funcT7 = funcT7;
    }

    public void setFuncT3(String funcT3) {
        this.funcT3 = funcT3;
    }

    public String getOpcode() {
        return opcode;
    }

    public int getRs1() {
        return rs1;
    }

    public int getRs2() {
        return rs2;
    }

    public int getRd() {
        return rd;
    }

    public int getImm12() {
        return imm12;
    }

    public String getFuncT7() {
        return funcT7;
    }

    public String getFuncT3() {
        return funcT3;
    }


    //abstract method which execute different instruction
    public abstract int Execute() throws Exception;

}
