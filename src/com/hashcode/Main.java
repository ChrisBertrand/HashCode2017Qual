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
                if(fl.getName().endsWith(".in"))
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
        
        
        Solver solver = new Solver(reader);
        
        double bestCoverage = 0;
        ArrayList<Command> commands = solver.getCommands();
        
        ArrayList<Integer>vid = new ArrayList<Integer>();
        vid.add(new Integer(2));
        vid.add(new Integer(4));
        
        Writer writer = new Writer(fl, commands, bestCoverage);

    }
    
}
