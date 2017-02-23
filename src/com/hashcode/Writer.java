package com.hashcode;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import com.hashcode.Reader.Command;

public class Writer
{

    public Writer(File fl, ArrayList<Command> command, double bestCoverage) throws Exception
    {
        String flPath = fl.getAbsolutePath();
        File file = new File(flPath.substring(0, flPath.length()-3) + "_" + bestCoverage + "_"+ ".out");
        FileOutputStream fos =new FileOutputStream(file);
        
        
        
        fos.close();
    }
    
}
