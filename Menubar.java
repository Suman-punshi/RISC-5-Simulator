package com.example.main;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Arrays;

public class Menubar extends JPanel implements ActionListener {


    //create the menu bar
    private JMenuBar mb;


    //Buttons for assemble and step in
    private JButton run, step;

    //file for writing the assembly code
    private File c;

    //file writer for writing the assembly code
    private FileWriter writer;

    //variable to determine whether the program counter should be reset to zero
    private static boolean start = false;

    //Icon for stylizing the buttons
    private ImageIcon image1;

    //static variables for event handling
    static int res, index, format;


    //Constructor
    Menubar(){

        //setting layout and creating the menu bar
        setLayout(new BorderLayout());
        //constructor for menu bar
        mb = new JMenuBar();
        mb.setBackground(new Color(255, 102, 102));


        //creating and stylizing the run button
        run = new JButton();
        run.setToolTipText("Assemble");
        run.setBorder(BorderFactory.createLineBorder(Color.MAGENTA));
        run.setBackground(Color.PINK);
        image1 = new ImageIcon(getClass().getResource("compile.png"));
        Image img = image1.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(img);
        run.setIcon(icon);
        run.addActionListener(this);


        //creating and stylizing the step button
        step = new JButton("");
        step.setToolTipText("Step in");
        step.setBorder(BorderFactory.createLineBorder(Color.MAGENTA));
        image1 = new ImageIcon(getClass().getResource("stepin.png"));
        Image img1 = image1.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon icon1 = new ImageIcon(img1);
        step.setIcon(icon1);
        step.addActionListener(this);



        //add buttons to menu bar
        mb.add(run);
        mb.add(step);

        this.add(mb, BorderLayout.CENTER);
    }


    //method from action listener interface
    @Override
    public void actionPerformed(ActionEvent e)
    {
        //if button clicked is run button then assemble the code into machine code
        if(e.getSource() == run)
        try {
            c = new File("C:\\Users\\suman.bscs22seecs\\IdeaProjects\\lab3\\src\\com\\example\\main\\code");
            writer = new FileWriter(c, false);
            writer.write("");
            writer.close();
            writer = new FileWriter(c, true);
            writer.write(Codebox.getCode());
            writer.close();
            RegisterGUI.clear();
            Console.clear();
            MemoryViewerGUI.clear();
            Assembler as = new Assembler("C:\\Users\\suman.bscs22seecs\\IdeaProjects\\lab3\\src\\com\\example\\main\\code", "C:\\Users\\suman.bscs22seecs\\IdeaProjects\\lab3\\src\\com\\example\\main\\mCode");
            as.Assemble();
            start = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Syntax Error", JOptionPane.ERROR_MESSAGE);
        }
        else if(e.getSource() == step)
        {
            startThread();
        }

    }



    //start thread method
    public static void startThread(){
        SwingWorker sw = new SwingWorker() {

            //do in background method for background tasks of executing instructions
            @Override
            protected Object doInBackground() throws Exception {
                try {
                    Simulate S = new Simulate("C:\\Users\\suman.bscs22seecs\\IdeaProjects\\lab3\\src\\com\\example\\main\\mCode", start);
                    start = false;
                    res = S.getRes();
                    index = S.getIndex();
                    format = S.getFormat();

                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                return null;
            }

            //done method for updating the GUI
            @Override
            protected void done() {
                super.done();

                if(format == 0 | format == 1){
                    RegisterGUI.writeVal(index, 1, Integer.toString(res));
                    RegisterGUI.writeVal(index, 2, String.format("0x%08X", res));
                    //refresh the table in GUI to show Immediate changes
                    RegisterGUI.reset();
                }
                else if(format == 2)
                {

                    MemoryViewerGUI.writeMemory(index, 1, Integer.toBinaryString(res));
                    MemoryViewerGUI.writeMemory(index, 2, String.format("0x%08X", res));
                    MemoryViewerGUI.writeMemory(index, 3, Integer.toString(res));
                    MemoryViewerGUI.reset();
                }

            }
        };
        sw.execute();
    }


}
