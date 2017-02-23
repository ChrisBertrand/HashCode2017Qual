package com.hashcode;

import java.io.File;
import java.util.ArrayList;

import com.hashcode.Reader.Command;

public class Main
{
    
    public static void main(String[] args) throws Exception
    {
        
        File file = new File(args[0]);
        if(file.isDirectory())
        {
            File[] files = file.listFiles();
            for(File fl:files)
            {
                process(fl);
            }
        }
        else
        {
            process(file);
        }
    }

    private static void process(File fl) throws Exception
    {
        System.out.println("processing " + fl);
        
        Reader reader = new Reader(fl);
        reader.print();
        
        Solver solver = new Solver(reader);
        
        double bestCoverage = 0;
        ArrayList<Command> command = solver.getCommands();
        
        ArrayList<Integer>vid = new ArrayList<Integer>();
        vid.add(new Integer(2));
        vid.add(new Integer(4));
        
        ArrayList<Command> test = new ArrayList<Command>();
        test.add(new Command(0,vid ));
        
        System.out.println("BestScore: " + bestCoverage);
        
        Writer writer = new Writer(fl, test, bestCoverage);

    }
    
}
