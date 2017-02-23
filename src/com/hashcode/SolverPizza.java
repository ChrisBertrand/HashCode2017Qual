package com.hashcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class SolverPizza
{
    Reader reader;
    
    ArrayList<Slice> slices;
    
    public SolverPizza(Reader reader)
    {
        super();
        this.reader = reader;
    }
    
    public double solve()
    {
        slices = new ArrayList<Slice>();

        int min = reader.info[2]*2;
        int max = reader.info[3];
        
        int count = 0;
        
        ArrayList<int[]>templates = new ArrayList<int[]>();
        
        for(int i=1;i<=max;i++)
        {
            for(int j=1;j<=max;j++)
            {
                if((i*j) <= max && (i*j) >=min)
                {
                    count++;
                    System.out.println("["  + i + "," + j + "]");
                    
                    templates.add(new int[]{i, j});
                }
            }
        }
        

        Collections.shuffle(templates, new Random(System.currentTimeMillis()));
//                       
//        Collections.sort(templates, new Comparator<int[]>()
//        {
//
//            @Override
//            public int compare(int[] o1, int[] o2)
//            {
//                double prod1 = o1[0] * o1[1];
//                double sum1 = Math.max(o1[0] , o1[1]);
//                double score1 = prod1 / sum1;
//                
//                
//                double prod2 = o2[0] * o2[1];
//                double sum2 = Math.max(o2[0] , o2[1]);
//                double score2 = prod2 / sum2;
//                
//                if(score1 > score2)
//                    return -1;
//                else if(score2 > score1)
//                    return 1;
//                else return 0;
//            } 
//        });
        
        System.out.println(count);
        
        
        int w = reader.array[0].length;
        int h = reader.array.length;
        boolean [][] used = new boolean[h][w];
        
        for(int[] pair: templates)
        {
            
            double prod1 = pair[0] * pair[1];
            double sum1 = Math.max(pair[0] , pair[1]);
            double score1 = prod1 / sum1;
            
            System.out.println("["  + pair[0] + "," + pair[1] + "], " + prod1 + ", " + sum1 + ", " + score1);
            
            int nOfR = pair[0];
            int nOfC = pair[1];

            int selP =0,selQ=0;
            int bestTotal =0;
//            for(int p=0;p<nOfR;p++)
//            {
//                for(int q=0;q<nOfC;q++)
//                {
//                    boolean [][] usedLocal = new boolean[h][w];
//                    int total =0;
//                    for(int i=p;i<h-nOfR ;i++)
//                    {
//                        for(int j=q;j<w-nOfC ;j++)
//                        {
//                            if(testAt(i, j, nOfR, nOfC, reader.array, usedLocal))
//                            {
//                                Slice slice  = new Slice(j, i,  j+nOfC, i + nOfR);
//                                total+=slice.area();
//                            }
//                        }
//                    }
//                    
//                    
//                    if(total>bestTotal)
//                    {
//                        selP = p;
//                        selQ = q;
//                    }
//                }
//            }
            
//            for(int i=selP;i<h-nOfR;i++)
//            {
//                for(int j=selQ;j<w-nOfC;j++)
//                {
//                    if(testAt(i, j, nOfR, nOfC, reader.array, used))
//                    {
//                        slices.add(new Slice(j, i,  j+nOfC-1, i + nOfR-1));
//                        setUsedAt(i, j, nOfR, nOfC, reader.array, used);
//                    }
//                }
//            }
//            
            
            for(int i=0;i<h-nOfR;i++)
            {
                for(int j=0;j<w-nOfC;j++)
                {
                    if(testAt(i, j, nOfR, nOfC, reader.array, used))
                    {
                        slices.add(new Slice(j, i,  j+nOfC, i + nOfR));
                        setUsedAt(i, j, nOfR, nOfC, reader.array, used);
                    }
                }
            }
            
        }
        
       
        System.out.println(slices.size());
        
        int totalArea = 0;
        for(Slice slice:slices)
        {
            totalArea+=slice.area();
            System.out.println(slice);
        }
        
        double p = (100 *totalArea/(w * h));
        System.out.println("score " + totalArea + ", max: " + (w * h) + ", " + p + "%");
        
        return p;
        
    }
    
    

    private boolean testAt(int p, int q, int nOfR, int nOfC, byte[][] array, boolean[][] used)
    {
        int countA = 0;
        int countB = 0;
        for(int i=0;i<nOfR;i++)
        {
            for(int j=0;j<nOfC;j++)
            {
                if(!used[i+p][j+q])
                {
                   if(array[i+p][j+q]==77)
                       countA++;
                   
                   if(array[i+p][j+q]==84)
                       countB++;
                }
                else
                    return false;
            }
        }
        
        if(countA<reader.info[2] || countB<reader.info[2])
            return false;
        
        
        if((countA+countB) > reader.info[3])
            return false;
        
        
        return true;
    }
    
    private void setUsedAt(int p, int q, int nOfR, int nOfC, byte[][] array, boolean[][] used)
    {
        for(int i=0;i<nOfR;i++)
        {
            for(int j=0;j<nOfC;j++)
            {
                used[i+p][j+q] = true;
            }
        }
    }
    
    
    public static class Slice
    {
        public int sx, sy;
        public int ex, ey;
        
        
        public Slice(int sx, int sy, int ex, int ey)
        {
            super();
            this.sx = sx;
            this.sy = sy;
            this.ex = ex;
            this.ey = ey;
        }


        @Override
        public String toString()
        {
            return "" + sy + " " + sx + " " + (ey-1) + " " + (ex-1);
        }
        
        public int area()
        {
            return (ex-sx) * (ey-sy);
        }
        
        
        
    }
    
    
}
