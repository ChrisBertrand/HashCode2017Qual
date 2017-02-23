package com.hashcode;

import java.io.File;
import java.util.ArrayList;

import com.hashcode.Solver.Slice;

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
        ArrayList<Slice> slices = null;
        
        for(int i=0;i<100;i++)
        {
            double coverage = solver.solve();
            
            if(bestCoverage<coverage)
            {
                bestCoverage = coverage;
                slices = solver.slices;
            }
        }
        
        
        System.out.println("BestScore: " + bestCoverage);
        
        Writer writer = new Writer(fl, slices, bestCoverage);

    }
    
}
