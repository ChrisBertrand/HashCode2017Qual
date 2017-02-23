package com.hashcode;

import java.io.File;

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
        
        
        System.out.println("BestScore: " + bestCoverage);
        
        Writer writer = new Writer(fl, null, bestCoverage);

    }
    
}
