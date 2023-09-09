package com.example.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Stream;

public class ProgramCounter {

    //static for pc counter
    private int pcCount;

    //variable for selecting line number from instruction file
    int i = 0;

    //object of file
    File mCode;

    //text variable for storing individual instruction
    String text;

    ProgramCounter(String fName, int pcCount) {
        mCode = new File(fName);
        this.pcCount = pcCount;
    }

    //getting instruction at specific line number
    public String getInstruction() throws FileNotFoundException {

        Scanner sc = new Scanner(mCode);
        while (sc.hasNextLine()) {

            if (i == pcCount) {
                text = sc.nextLine();
                break;
            }
            sc.nextLine();
            i++;


        }
        System.out.println(text);
        return text;

    }


}
