package com.example.main;
import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class MemoryViewerGUI extends JPanel {

    //array for storing the values in table
    private static String[][] memory_value = new String[7500][4];

    //columns of table
    private String[] col = {"Addresses", "Binary Value", "Hexadecimal value", "Decimal"};

    private static JTable Mem;

    MemoryViewerGUI() {


        //filling table with all zeros
        //hexadecimal values
        for (String[] row : memory_value) {
            Arrays.fill(row, "0X00000000");
        }

        //binary values
        for (int i = 0; i < 7500; i++) {
            memory_value[i][1] = "000000000000000000000000000000000000";
        }

        //decimal values
        for (int i = 0; i < 7500; i++) {
            memory_value[i][3] = "0";
        }

        //addresses
        for (int i = 0; i < 7500; i++) {
            memory_value[i][0] = String.format("0x%08X",i);
        }


        //table and scroll pane
        setLayout(new BorderLayout());
        Mem = new JTable(memory_value, col);
        JScrollPane sp = new JScrollPane(Mem);
        this.add(sp, BorderLayout.CENTER);



        //stylize the table
        Mem.setFont(new Font("Calibri", Font.PLAIN, 14));
        Mem.setRowHeight(25);
        Mem.getTableHeader().setFont(new Font("Calibri", Font.BOLD, 14));
        Mem.getTableHeader().setBackground(new Color(0, 153, 0));
        Mem.setGridColor(new Color(102, 255, 102));
    }

    public static void clear(){
        for(int i = 0; i < 32; i++){
            memory_value[i][1] = "00000000000000000000000000000000";
            memory_value[i][2] = "0X00000000";
            memory_value[i][3] = "0";

        }
    }


    //method for getting values from table
    public static String getVal(int row, int col)
    {
        return memory_value[row][col];
    }



    //method for writing values on table
    public static void writeMemory(int row, int col, String val)
    {

        memory_value[row][col] = val;
        Mem.setSelectionForeground(Color.RED);
        Mem.setSelectionBackground(new Color(255, 204, 0));
        Mem.changeSelection(row, col, false, false);
    }


    //method for refreshing the table
    public static void reset(){
        Mem.repaint();
    }

}
