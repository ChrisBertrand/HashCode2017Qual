package com.hashcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.hashcode.Reader.Command;
import com.hashcode.Reader.Request;

public class Solver
{
    private Reader reader;
    
    ArrayList <Command> commands= new ArrayList <Command>();
    
    public Solver(Reader reader)
    {
        super();
        this.reader = reader;
        
        reader.requests = reduce(reader.requests);
        
        double [][] costMap= new double [reader.requests.size()][reader.noOfCaches +1];
        
        for(int i=0;i<costMap.length;i++)
        {
            for(int j=0;j<reader.noOfCaches;j++)
            {
                Request request = reader.requests.get(i);
                costMap[i][j] = computeCost(request, j);
            }
        }
        
        
        
        
        
        
    }



    private double computeCost(Request request, int cacheId)
    {
        double result = Double.POSITIVE_INFINITY;
        
        if(reader.endPointList.get(request.sourceEndPointId).getConnected()[cacheId])
        {
            result = request.nOfrequest * reader.endPointList.get(request.sourceEndPointId).getCacheLatency()[cacheId];
            
        }
        return result;
    }



    private ArrayList <Request> reduce(ArrayList <Request> requests)
    {
        
        return requests;
    }



    public ArrayList <Command> getCommands()
    {
        return commands;
    }
    
  
}
