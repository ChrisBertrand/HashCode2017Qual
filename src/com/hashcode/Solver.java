package com.hashcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
        
        double [][] costMap= new double [reader.requests.size()][reader.noOfCaches];
        
        for(int i=0;i<costMap.length;i++)
        {
            for(int j=0;j<reader.noOfCaches;j++)
            {
                Request request = reader.requests.get(i);
                costMap[i][j] = computeCost(request, j);
            }
        }
        
        for(int i=0;i<costMap.length;i++)
        {
            double leastScore = Double.POSITIVE_INFINITY;
            int leastIndex = -1;
            for(int j=0;j<reader.noOfCaches;j++)
            {
                if(costMap[i][j] < leastScore)
                {
                    leastIndex = j;
                    leastScore = costMap[i][j];
                }
            }
            
            for(int j=0;j<reader.noOfCaches;j++)
            {
                if(j!=leastIndex)
                    costMap[i][j] = Double.POSITIVE_INFINITY;
            }
        }
        
        for(int j=0;j<reader.noOfCaches;j++)
        {
            ArrayList<RequestScore> dpList = new ArrayList<RequestScore>();
            for(int i=0;i<costMap.length;i++)
            {
                Request request = reader.requests.get(i);
                dpList.add(new RequestScore(request, costMap[i][j]));
            
            }
            
            dpList.sort(new Comparator <RequestScore>()
            {
                
                @Override
                public int compare(RequestScore arg0, RequestScore arg1)
                {
                    if (arg0.score < arg1.score)
                        return -1;
                    else if (arg0.score > arg1.score)
                        return 1;
                    else
                        return 0;
                    
                }
            });
            
            ArrayList <Integer> videoIds = new ArrayList<Integer>();
            int sum =0;
            for(RequestScore rq:dpList)
            {
                sum = sum +reader.videoList.get(rq.request.vID).size;
                if(sum<reader.cacheSize)
                    videoIds.add(rq.request.vID);
                
            }
            
            Command command = new Command(j, videoIds);
            commands.add(command);
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
        ArrayList <Request> result = new ArrayList <Request>();
        
        
        for(Request req:requests)
        {
            
        }
        
        
        return requests;
    }



    public ArrayList <Command> getCommands()
    {
        return commands;
    }
    
    
    public static class RequestScore
    {
        Request request;
        double score;
        public RequestScore(Request request, double score)
        {
            super();
            this.request = request;
            this.score = score;
        }
        
    }
  
}
