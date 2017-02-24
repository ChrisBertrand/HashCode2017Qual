package com.hashcode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.StringTokenizer;

public class Reader
{
    int noOfVideos;
    int noOfEp;
    int noOfRequests;
    int noOfCaches;
    int cacheSize;
    
    ArrayList<Video> videoList = new ArrayList<Video>();
    ArrayList<EndPoint> endPointList = new ArrayList<EndPoint>();
    ArrayList<Request> requests = new ArrayList<Request>();
    
    
    
    private ArrayList<String[]> lines;
    
    
    public Reader(File file) throws Exception
    {
        
        lines = new ArrayList<String[]>();
        
        
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        
        int countOfConnectedCache = 0;
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
            
            if(lineCount>1 && countOfConnectedCache==0 && endPointList.size()< noOfEp)
            {
                int latencyToDC = Integer.valueOf(words[0]);
                countOfConnectedCache = Integer.valueOf(words[1]);
                
                EndPoint ep = new EndPoint(epId, latencyToDC, noOfCaches);
                endPointList.add(ep);
                
                continue;
            }
            
            if(lineCount>1 && countOfConnectedCache!=0 )
            {
                int cacheId = Integer.valueOf(words[0]);
                int latency = Integer.valueOf(words[1]);
                
                EndPoint ep = endPointList.get(endPointList.size()-1);
                ep.getCacheLatency()[cacheId] = latency;
                ep.getConnected()[cacheId] = true;
                
                countOfConnectedCache--;
                
                continue;
            }
            
            Request request = new Request(Integer.valueOf(words[2]), 
                                          Integer.valueOf(words[0]), 
                                          Integer.valueOf(words[1]));
            
            requests.add(request);
            
        }
        fileReader.close();
        
       // print();
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
        log("noOfVideos" + noOfVideos);
        log("noOfEp" + noOfEp);
        log("noOfRequests" + noOfRequests);
        log("noOfCaches" + noOfCaches);
        log("cacheSize" + cacheSize);
        
        log("videoList" + videoList.size());
        log("endPointList" + endPointList.size());
        log("requests" + requests.size());
    }
 
    private void log(String string)
    {
        System.out.println(string);
        
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
        @Override
        public String toString()
        {
            return "Video [id=" + id + ", size=" + size + "]";
        }
        
        
    }
    
    public static class EndPoint
    {
        int endPointId;
        int latencyDataCenter;
        boolean []connected;
        int []cacheLatency;
        public EndPoint(int endPointId, int latencyDataCenter, int cacheCount)
        {
            super();
            this.endPointId = endPointId;
            this.latencyDataCenter = latencyDataCenter;
            
            connected = new boolean[cacheCount];
            cacheLatency = new int[cacheCount];
            
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
        @Override
        public String toString()
        {
            return "EndPoint [endPointId=" + endPointId + ", latencyDataCenter=" + latencyDataCenter
                   + ", connected=" + Arrays.toString(connected) + ", cacheLatency="
                   + Arrays.toString(cacheLatency) + "]";
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
        @Override
        public String toString()
        {
            return "Cahce [id=" + id + ", capacity=" + capacity + "]";
        }
        
    }
    
    public static class Request
    {
        int nOfrequest;
        int vID;
        int sourceEndPointId;
        boolean dealtWith;
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
        @Override
        public String toString()
        {
            return "Request [nOfrequest=" + nOfrequest + ", vID=" + vID + ", sourceEndPointId="
                   + sourceEndPointId + "]";
        }
        @Override
        public int hashCode()
        {
            final int prime = 31;
            int result = 1;
            result = prime * result + sourceEndPointId;
            result = prime * result + vID;
            return result;
        }
        @Override
        public boolean equals(Object obj)
        {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Request other = (Request) obj;
            if (sourceEndPointId != other.sourceEndPointId)
                return false;
            if (vID != other.vID)
                return false;
            return true;
        }
        public boolean isDealtWith()
        {
            return dealtWith;
        }
        public void setDealtWith(boolean dealtWith)
        {
            this.dealtWith = dealtWith;
        }
        
        
        
        
    }
    
    public static class Command
    {
        int cacheID;
        ArrayList <Integer> videoIds = new ArrayList<Integer>();
        public Command(int cacheID, ArrayList <Integer> videoIds)
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

        public ArrayList <Integer> getVideoIds()
        {
            return videoIds;
        }
        public void setVideoIds(ArrayList <Integer> videoIds)
        {
            this.videoIds = videoIds;
        }
        @Override
        public String toString()
        {
            StringBuilder sb = new StringBuilder();
            
            for(Integer i:videoIds)
            {
                sb.append(i.toString());
                sb.append(" ");
            }
            return "" + cacheID + " " + sb.toString();
        }
        
        
    }
    
}
