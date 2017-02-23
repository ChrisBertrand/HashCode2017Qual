package com.hashcode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Reader
{
    int noOfVideos;
    int noOfEp;
    int noOfRequests;
    int noOfCaches;
    int cacheSize;
    
    ArrayList<Video> videoList = new ArrayList<Video>();
    
    
    private ArrayList<String[]> lines;
    
    public Reader(File file) throws Exception
    {
        
        lines = new ArrayList<String[]>();
        
        
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        
        
        int lineCount =0;
        int epId = 0;
        while ( (line = bufferedReader.readLine()) != null)
        {
            String[] words = parse(line);
            if(lineCount==0)
            {
                noOfVideos = Integer.valueOf(words[0]);
                noOfEp = Integer.valueOf(words[1]);
                noOfRequests = Integer.valueOf(words[2]);
                noOfCaches = Integer.valueOf(words[3]);
                cacheSize = Integer.valueOf(words[4]);
                
                lineCount++;
                continue;
            }
            
            if(lineCount==1)
            {
                for(int i=0;i<noOfVideos;i++)
                {
                    Video video = new Video(i, Integer.valueOf(words[i]));
                    videoList.add(video);
                }
                
                lineCount++;
                continue;
            }
            
            if(lineCount>1 )
            {
                int latencyToDC = Integer.valueOf(words[0]);
                int countOfConnectedCache = Integer.valueOf(words[1]);
                
            }
            
        }
        fileReader.close();
    }


    private int[] parseWordsIntoInts(String[] words)
    {
        int []result = new int[words.length];
        for(int i=0;i<result.length;i++)
        {
            result[i] = Integer.parseInt(words[i]);
        }
        return result;
    }

    private String[] parse(String line)
    {
        ArrayList<String> list = new ArrayList<String>();
        StringTokenizer st = new StringTokenizer(line , " ");
        while(st.hasMoreTokens())
        {
            list.add(st.nextToken());
        }
        
        String[] result = list.toArray(new String[0]);
        
        
        return result;
    }

    public void print()
    {
        
        
    }
 
    public static class Video
    {
        int id;
        int size;
        public Video(int id, int size)
        {
            super();
            this.id = id;
            this.size = size;
        }
        public int getId()
        {
            return id;
        }
        public void setId(int id)
        {
            this.id = id;
        }
        public int getSize()
        {
            return size;
        }
        public void setSize(int size)
        {
            this.size = size;
        }
        
        
    }
    
    public static class EndPoint
    {
        int endPointId;
        int latencyDataCenter;
        boolean []connected;
        int []cacheLatency;
        public EndPoint(int endPointId, int latencyDataCenter, boolean[] connected,
                        int[] cacheLatency)
        {
            super();
            this.endPointId = endPointId;
            this.latencyDataCenter = latencyDataCenter;
            this.connected = connected;
            this.cacheLatency = cacheLatency;
        }
        public int getEndPointId()
        {
            return endPointId;
        }
        public void setEndPointId(int endPointId)
        {
            this.endPointId = endPointId;
        }
        public int getLatencyDataCenter()
        {
            return latencyDataCenter;
        }
        public void setLatencyDataCenter(int latencyDataCenter)
        {
            this.latencyDataCenter = latencyDataCenter;
        }
        public boolean[] getConnected()
        {
            return connected;
        }
        public void setConnected(boolean[] connected)
        {
            this.connected = connected;
        }
        public int[] getCacheLatency()
        {
            return cacheLatency;
        }
        public void setCacheLatency(int[] cacheLatency)
        {
            this.cacheLatency = cacheLatency;
        }
        
    }
    
    public static class Cahce
    {
        int id;
        int capacity;
        public Cahce(int id, int capacity)
        {
            super();
            this.id = id;
            this.capacity = capacity;
        }
        public int getId()
        {
            return id;
        }
        public void setId(int id)
        {
            this.id = id;
        }
        public int getCapacity()
        {
            return capacity;
        }
        public void setCapacity(int capacity)
        {
            this.capacity = capacity;
        }
        
    }
    
    public static class Request
    {
        int nOfrequest;
        int vID;
        int sourceEndPointId;
        public Request(int nOfrequest, int vID, int sourceEndPointId)
        {
            super();
            this.nOfrequest = nOfrequest;
            this.vID = vID;
            this.sourceEndPointId = sourceEndPointId;
        }
        public int getnOfrequest()
        {
            return nOfrequest;
        }
        public void setnOfrequest(int nOfrequest)
        {
            this.nOfrequest = nOfrequest;
        }
        public int getvID()
        {
            return vID;
        }
        public void setvID(int vID)
        {
            this.vID = vID;
        }
        public int getSourceEndPointId()
        {
            return sourceEndPointId;
        }
        public void setSourceEndPointId(int sourceEndPointId)
        {
            this.sourceEndPointId = sourceEndPointId;
        }
        
        
    }
    
    public static class Command
    {
        int cacheID;
        int []videoIds;
        public Command(int cacheID, int[] videoIds)
        {
            super();
            this.cacheID = cacheID;
            this.videoIds = videoIds;
        }
        public int getCacheID()
        {
            return cacheID;
        }
        public void setCacheID(int cacheID)
        {
            this.cacheID = cacheID;
        }
        public int[] getVideoIds()
        {
            return videoIds;
        }
        public void setVideoIds(int[] videoIds)
        {
            this.videoIds = videoIds;
        }
        
        
    }
    
}
