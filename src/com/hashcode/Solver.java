package com.hashcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;

import com.hashcode.Reader.Command;
import com.hashcode.Reader.Request;
import com.hashcode.Reader.Video;

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
                //costMap[i][j] = computeCost(request, j);
                
                costMap[i][j] = computeSavings(request, j);
            }
        }
        
        double max = Double.NEGATIVE_INFINITY;
        
        for(int i=0;i<costMap.length;i++)
        {
            for(int j=0;j<reader.noOfCaches;j++)
            {
                if(max<costMap[i][j])
                    max = costMap[i][j];
            }
        }
        
        for(int i=0;i<costMap.length;i++)
        {
            for(int j=0;j<reader.noOfCaches;j++)
            {
                costMap[i][j] = max - costMap[i][j];
            }
        }
        
        
        reader.print();
        
        
        
        
        
//        
//        for(int i=0;i<costMap.length;i++)
//        {
//            double leastScore = Double.POSITIVE_INFINITY;
//            int leastIndex = -1;
//            for(int j=0;j<reader.noOfCaches;j++)
//            {
//                if(costMap[i][j] < leastScore)
//                {
//                    leastIndex = j;
//                    leastScore = costMap[i][j];
//                }
//            }
//            
//            for(int j=0;j<reader.noOfCaches;j++)
//            {
//                if(j!=leastIndex)
//                    costMap[i][j] = Double.POSITIVE_INFINITY;
//            }
//        }
        
        
        ArrayList<RequestScore> dpList[] = new ArrayList[reader.noOfCaches];
        int[]dpListIndex = new int[reader.noOfCaches];
        
        ArrayList<Integer> videoIds[] = new ArrayList[reader.noOfCaches];
        int []usedCache = new int[reader.noOfCaches];
        
        for(int j=0;j<reader.noOfCaches;j++)
        {
            dpList[j] = new ArrayList<RequestScore>();
            videoIds[j] = new ArrayList<Integer>();
            
            
            for(int i=0;i<costMap.length;i++)
            {
                Request request = reader.requests.get(i);
                dpList[j].add(new RequestScore(request, costMap[i][j]));
            }
            
            dpList[j].sort(new Comparator <RequestScore>()
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
            
        }

        
        System.out.println("sorted");
        
       
        while(listsNotEmpty(dpList, dpListIndex) && cacheHasCapacity(usedCache))
        {
            RequestScore[] nextBest = getNextBestValidRequests(dpList, dpListIndex);
            
            RequestScore bestReq = null;
            int bestIndex = -1;
            for(int j=0;j<nextBest.length;j++)
            {
                if(bestReq==null && nextBest[j]!=null)
                {
                    bestReq = nextBest[j];
                    bestIndex = j;
                    continue;
                }
                
                if(nextBest[j]!=null )
                {
                    if( nextBest[j].score < bestReq.score)
                    {
                        bestReq = nextBest[j];
                        bestIndex = j;
                    }
                    else if( nextBest[j].score == bestReq.score)
                    {
                        int selectedCacheCapacity = reader.cacheSize - usedCache[bestIndex];
                        int candidateCacheCapacity = reader.cacheSize - usedCache[j];
                        
                        if(candidateCacheCapacity > selectedCacheCapacity)
                        {
                            bestReq = nextBest[j];
                            bestIndex = j;
                        }
                    }

                }
            }
            
            if(bestReq!=null && bestIndex!=-1)
            {
                boolean found = false;
                for(Integer vid: videoIds[bestIndex])
                {
                    if(vid.intValue() == bestReq.request.vID)
                    {
                        found=true;
                        
                        bestReq.request.dealtWith = true;
                        //System.out.println("ARequest : " + bestReq);

                    }
                }
                
                if(!found)
                {
                    int vSize = reader.videoList.get(bestReq.request.vID).size;
                    
                    if(usedCache[bestIndex] + vSize<reader.cacheSize)
                    {
                        usedCache[bestIndex] = usedCache[bestIndex] + vSize;
                        videoIds[bestIndex].add(bestReq.request.vID);
                        
                        bestReq.request.dealtWith = true;
                        //System.out.println("Request : " + bestReq);
                    }
                    else
                    {
                        dpListIndex[bestIndex]++;
                        //dpList[bestIndex].remove(0);
                    }
                }
            }
        }
            
            

        
        for(int j=0;j<videoIds.length;j++)
        {
            if(videoIds[j].size()>0)
            {   
                Command command = new Command(j, videoIds[j]);
                commands.add(command);
            }
        }
        
        
        int sum = 0;
        
        for(int i=0;i<usedCache.length;i++)
            sum+=usedCache[i];
        
        System.out.println("Used Cache " + sum + ", total " + (reader.cacheSize * reader.noOfCaches));
        
        double score = computeScore(commands);
        System.out.println("Score: " + score);
    }

    private double computeScore(ArrayList <Command> commands)
    {
        double total =0 ;
        
        for(Request request: reader.requests)
        {
            int cacheId = -1;
            int bestLatency = Integer.MAX_VALUE;
            
            for(Command command: commands)
            {
                if(command.videoIds.contains(request.vID))
                {
                    int cacheLatency = reader.endPointList.get(request.sourceEndPointId).getCacheLatency()[command.cacheID];
                    if(cacheLatency<bestLatency)
                    {
                        cacheId = command.cacheID;
                    }
                }        
            }
            
            if(cacheId!=-1)
            {
                double latencySaving = reader.endPointList.get(request.sourceEndPointId).latencyDataCenter 
                        - reader.endPointList.get(request.sourceEndPointId).getCacheLatency()[cacheId];
                total += latencySaving * request.nOfrequest;
            }
            
        }
        
        
        
        
        return total * 1000.0 / reader.totalNoOfRequests;
    }


    private RequestScore[] getNextBestValidRequests(ArrayList <RequestScore>[] dpList, int[] dpListIndex)
    {
        RequestScore[] result = new RequestScore[dpList.length];
        
        int ts = 0;
        for(int i=0;i<result.length;i++)
        {
            while(dpListIndex[i] < dpList[i].size())
            {
                RequestScore rs = dpList[i].get(dpListIndex[i]);
                if(rs.request.dealtWith)
                {
                    dpListIndex[i]++;
                }
                else
                {
                    result[i] = rs;
                    break;
                }
            }
            
            int s = dpList[i].size() - dpListIndex[i];
            ts+=s;
            
        }
        
        if(ts%10000==0)
            System.out.println("req: " + ts);
        return result;
    }



    private boolean cacheHasCapacity(int[] usedCache)
    {
        boolean result = false;
        
        for(int i=0;i<usedCache.length;i++)
        {
            if(usedCache[i] < reader.cacheSize)
                return true;
        }
        
        return result;
    }



    private boolean listsNotEmpty(ArrayList <RequestScore>[] dpList, int[] dpListIndex)
    {
        boolean result = false;
        
        for(int i=0;i<dpList.length;i++)
        {
            if(dpList[i].size() - dpListIndex[i] > 0)
                return true;
        }
        
        return result;
    }



    private double computeCost(Request request, int cacheId)
    {
        double result = Double.POSITIVE_INFINITY;
        
        if(reader.endPointList.get(request.sourceEndPointId).getConnected()[cacheId])
        {
            int videoSize = reader.videoList.get(request.vID).size;
            result = request.nOfrequest * reader.endPointList.get(request.sourceEndPointId).getCacheLatency()[cacheId] ;
            
        }
        return result;
    }

    private double computeSavings(Request request, int cacheId)
    {
        double result = 0;
        
        if(reader.endPointList.get(request.sourceEndPointId).getConnected()[cacheId])
        {
            int videoSize = reader.videoList.get(request.vID).size;
            
            double latencySaving = reader.endPointList.get(request.sourceEndPointId).latencyDataCenter 
                    - reader.endPointList.get(request.sourceEndPointId).getCacheLatency()[cacheId] ; 
            
            double requestW = request.nOfrequest / reader.totalNoOfRequests;
            double sizeW = videoSize / reader.totalVideoSize;
            
            result = requestW * latencySaving / sizeW ;
            
        }
        return result;
    }


    private ArrayList <Request> reduce(ArrayList <Request> requests)
    {
        ArrayList <Request> result = new ArrayList <Request>();
        
        Hashtable<Request, Request> table = new Hashtable<Request, Request>();
        for(Request req:requests)
        {
            Request existingReq = table.get(req);
            if(existingReq == null)
            {
                table.put(req, req);
            }
            else
            {
               existingReq.nOfrequest += req.nOfrequest;
            }
        }
        
        Enumeration <Request> keys = table.keys();
        
        while(keys.hasMoreElements())
        {
            result.add(keys.nextElement());
        }
        
        return result;
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
        @Override
        public String toString()
        {
            return "RequestScore [request=" + request + ", score=" + score + "]";
        }
        
    }
  
}
