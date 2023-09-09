package com.example.main;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class Console extends JPanel{


    //array for storing the data in program memory viewer
    private static String[][] code_memory = new String[7500][8];

    //columns of table
    String col[] = {"Addresses", "opcode", "rs1", "rs2", "rd", "imm12", "funcT7", "funT3"};

    private static JTable tab;

    Console() {

        //fill the table with zeros
        for(String[] row : code_memory){
            Arrays.fill(row, "0X00000000");
        }
        for(int i = 0; i < 7500; i++){
            code_memory[i][0] = String.format("0x%08X", i);
        }


        //create the JTable and put it in scroll pane
        setLayout(new BorderLayout());
        tab = new JTable(code_memory, col);
        JScrollPane sp = new JScrollPane(tab);
        sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.add(sp, BorderLayout.CENTER);



        //stylize the table
        tab.setFont(new Font("Calibri", Font.PLAIN, 14));
        tab.setRowHeight(25);
        tab.getTableHeader().setFont(new Font("Calibri", Font.BOLD, 14));
        tab.getTableHeader().setBackground(new Color(0, 153, 0));
        tab.setGridColor(new Color(102, 255, 102));
        tab.setDragEnabled(false);
        tab.setEnabled(false);

    }


    //method for writing in the Jtable
    public static void writeMemory(int row, int col, String val){

        code_memory[row][col] = val;
        tab.setSelectionForeground(Color.RED);
        tab.setSelectionBackground(new Color(255, 204, 0));
        tab.changeSelection(row, col, false, false);
    }


    //method for refreshing the table
    public static void reset(){
        tab.repaint();
    }

    //method for resetting the table
    public static void clear(){

        for(String[] row : code_memory){
            Arrays.fill(row, "0X00000000");
        }
        for(int i = 0; i < 7500; i++){
            code_memory[i][0] = String.format("0x%08X", i);
        }

    }

}
