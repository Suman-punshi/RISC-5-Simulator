package com.example.main;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;




//class to create an area to input code
public class Codebox extends JPanel implements DocumentListener{

    private static StyledDocument doc;
    private static JTextPane box;

    private JTextArea linenumber;
    private static SimpleAttributeSet mnemonic;
    private static SimpleAttributeSet reg;
    private static SimpleAttributeSet invalid;
    private static SimpleAttributeSet comment;

    private static SimpleAttributeSet label;

    private String selected_text;


    private ArrayList<String> Keywords = new ArrayList<String>(Arrays.asList("ADD", "SUB", "SLT", "SLTU", "AND", "OR", "XOR", "SLL", "SRL", "SRA", "ADDI", "SLTI", "SLTIU", "ANDI", "ORI", "XORI", "SLLI", "SRLI", "SRAI", "LD", "LW", "LH", "LB", "LWU", "LHU", "LBU", "SW", "SH", "SD", "SB", "BEQ", "BNE", "BLT", "BGE", "BLTU", "BGEU"));
    private ArrayList<String> registers = new ArrayList<String>(Arrays.asList("x0", "x1", "x2", "x3", "x4", "x5", "x6", "x7", "x8", "x9", "x10", "x11", "x12", "x13", "x14", "x15", "x16", "x17", "x18", "x19", "x20", "x21", "x22", "x23", "x24", "x25", "x26", "x27", "x28", "x29", "x30", "x31"));


    Codebox() {
        //layout manager
        setLayout(new BorderLayout());

        //creating object of JTextPane class
        box = new JTextPane();

        //set color of text pane
        box.setBackground(Color.WHITE);

        //set font of text pane
        box.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));

        //get document object from text pane and register it to document listener
        box.getDocument().addDocumentListener(this);

        //anonymous inner class for copying and pasting feature
        box.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_C && e.getModifiers() == KeyEvent.CTRL_DOWN_MASK){
                    selected_text = box.getSelectedText();
                }
                if(e.getKeyCode() == KeyEvent.VK_C && e.getModifiers() == KeyEvent.CTRL_DOWN_MASK) {
                    doc = box.getStyledDocument();
                        try
                        {
                            doc.insertString(box.getCaretPosition(), selected_text, null);
                        }
                        catch (BadLocationException ex)
                        {
                            throw new RuntimeException(ex);
                        }
                }
            }
        });


        // create the text area for displaying line number and stylize the text area
        linenumber = new JTextArea("1");
        linenumber.setEditable(false);
        linenumber.setBackground(Color.GRAY);
        linenumber.setMargin(new Insets(0, 5, 0, 5));
        linenumber.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));



        //put components in scroll pane and stylize the scroll pane
        JScrollPane scroll = new JScrollPane(box);
        scroll.setRowHeaderView(linenumber);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setBackground(Color.WHITE);
        this.add(scroll, BorderLayout.CENTER);


        //attributes for different keywords of instruction
        mnemonic = new SimpleAttributeSet();
        reg = new SimpleAttributeSet();
        invalid = new SimpleAttributeSet();
        comment = new SimpleAttributeSet();
        label = new SimpleAttributeSet();
        StyleConstants.setForeground(label, Color.PINK);
        StyleConstants.setForeground(mnemonic, new Color(102, 0, 153));
        StyleConstants.setForeground(reg, Color.MAGENTA);
        StyleConstants.setForeground(invalid, Color.RED);
        StyleConstants.setForeground(comment, Color.BLUE);


    }


    //method for syntax highlighting feature
    public void Highlight() throws BadLocationException {
        String text;
        String[] tok;
        doc = box.getStyledDocument();
        text = doc.getText(0, doc.getLength());
        int currentPosition = 0;

        tok = text.split("\\s*(\\(|\\)|\\{|\\}|\\[|\\]|;|\\+|\\-|\\*|\\/|%|<|>|\\=|\\!|&|\\||\\^|,|\\s)\\s*");



        for (int i = 0; i < tok.length; i++) {
            int startIndex = text.indexOf(tok[i], currentPosition);
            currentPosition = startIndex + tok[i].length();
            if (Keywords.contains(tok[i]) ) {
                int finalI = i;
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        doc.setCharacterAttributes(startIndex, tok[finalI].length(), mnemonic, false);
                    }

                });
            } else if (registers.contains(tok[i]) | tok[i].matches("\\d+")){
                int finalI1 = i;
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        doc.setCharacterAttributes(startIndex, tok[finalI1].length(), reg, false);
                    }
                });
            }
            else if(tok[i].startsWith("#")){
                int finalI1 = i;
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        doc.setCharacterAttributes(startIndex, tok[finalI1].length(), comment, false);
                    }
                });
            }
            else if(tok[i].endsWith(":")){
                int finalI1 = i;
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        doc.setCharacterAttributes(startIndex, tok[finalI1].length(), label, false);
                    }
                });
            }
            else if(Assembler.getLabelExist() == 0)
                {
                    int finalI2 = i;
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            doc.setCharacterAttributes(startIndex, tok[finalI2].length(), label, false);
                        }
                    });
                }
            else {

                int finalI2 = i;
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        doc.setCharacterAttributes(startIndex, tok[finalI2].length(), invalid, false);
                    }
                });
            }
        }
        

    }



    //Method for Highlighting current instruction being executed
    public static void HighlightText(int pc) {

        int currentInstructionIndex = pc; // the index of the current instruction in your code array
        StyledDocument doc = box.getStyledDocument();

        // Remove previous highlights
        Style defaultStyle = box.getStyle("default");
        doc.setCharacterAttributes(0, doc.getLength(), defaultStyle, true);

        // Highlight selected line
        Element element = doc.getDefaultRootElement().getElement(currentInstructionIndex);
        int start = element.getStartOffset();
        int end = element.getEndOffset() - 1;

        Style highlight = doc.addStyle("highlight", null);
        StyleConstants.setBackground(highlight, Color.YELLOW);
        doc.setCharacterAttributes(start, end - start, highlight, false);

        // Set white background for other lines
        for (int i = 0; i < doc.getDefaultRootElement().getElementCount(); i++) {
            if (i != currentInstructionIndex) {
                Element otherElement = doc.getDefaultRootElement().getElement(i);
                int otherStart = otherElement.getStartOffset();
                int otherEnd = otherElement.getEndOffset() - 1;
                StyleConstants.setBackground(defaultStyle, Color.WHITE);
                doc.setCharacterAttributes(otherStart, otherEnd - otherStart, defaultStyle, false);
            }
        }
    }

    //method for getting total instruction numbers
    public static int getLineNum(){
        String text = box.getText();
        String[] lines = text.split("\\n");
        int num = 0;
        for(String line : lines){
            if(!line.startsWith("#")){
                num++;
            }
        }
        return num;
    }

    //method for incrementing line number on pressing enter
    public void IncrementLineNum() {
        int caretPosition = box.getCaretPosition();
        Element root = box.getDocument().getDefaultRootElement();
        StringBuilder builder = new StringBuilder("1\n");
        for (int i = 2; i <= root.getElementIndex(caretPosition) + 2; i++) {
            builder.append(i).append("\n");
        }
        linenumber.setText(builder.toString());
    }


    //method for decrementing line number on pressing backspace
    public void DecrementLineNum() {
        int caretPosition = box.getCaretPosition();
        Element root = box.getDocument().getDefaultRootElement();
        StringBuilder builder = new StringBuilder("1\n");
        for (int i = 2; i <= root.getElementIndex(caretPosition) + 1; i++) {
            builder.append(i).append("\n");
        }
        linenumber.setText(builder.toString());
    }


    //method for getting the code from text box
    public static String getCode(){
        return box.getText();
    }


    //method of document listener interface
    @Override
    public void insertUpdate(DocumentEvent e) {
        if (e.getDocument() == box)
            try {
                Highlight();
                box.revalidate();
                box.repaint();
            } catch (BadLocationException ex) {
                throw new RuntimeException(ex);
            }
        IncrementLineNum();


    }


    //method of document listener interface
    @Override
    public void removeUpdate(DocumentEvent e) {
        try {
            Highlight();
            box.revalidate();
            box.repaint();
        } catch (BadLocationException ex) {
            throw new RuntimeException(ex);
        }
        DecrementLineNum();
    }


    //method of document listener interface
    @Override
    public void changedUpdate(DocumentEvent e) {

    }

}