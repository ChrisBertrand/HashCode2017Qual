package com.hashcode;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import com.hashcode.Solver.Slice;

public class Writer
{

    public Writer(File fl, ArrayList<Slice> slices, double bestCoverage) throws Exception
    {
        String flPath = fl.getAbsolutePath();
        File file = new File(flPath.substring(0, flPath.length()-3) + "_" + bestCoverage + "_"+ ".out");
        FileOutputStream fos =new FileOutputStream(file);
        
        
        String str = "" + slices.size();
        
        fos.write(str.getBytes());
        fos.write("\n".getBytes());
        
        for(Slice slice:slices)
        {
            fos.write(slice.toString().getBytes());
            fos.write("\n".getBytes());
        }

        fos.close();
    }
    
}
