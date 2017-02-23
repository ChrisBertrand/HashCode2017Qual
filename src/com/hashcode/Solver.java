package com.hashcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.hashcode.Reader.Command;

public class Solver
{
    private Reader reader;
    
    ArrayList <Command> commands= new ArrayList <Command>();
    
    public Solver(Reader reader)
    {
        super();
        this.reader = reader;
        
        
        
        
        
        
    }



    public ArrayList <Command> getCommands()
    {
        return commands;
    }
    
  
}
