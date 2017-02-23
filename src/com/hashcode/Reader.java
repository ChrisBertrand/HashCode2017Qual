package com.hashcode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Reader
{
    public int[] info = null;
    public byte [][] array = null;

    private ArrayList<String[]> lines;
    public Reader(File file) throws Exception
    {
        
        lines = new ArrayList<String[]>();
        
        
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        
        int row = 0;
        while ( (line = bufferedReader.readLine()) != null)
        {
            String[] words = parse(line);
            
            if(info ==null)
            {
                info = parseWordsIntoInts(words);
                continue;
            }
            if(array==null && info !=null)
            {
                array = new byte[info[0]][info[1]];
            }
            
            array[row++] = words[0].getBytes();
            
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
        System.out.println("Row " +  info[0]);
        System.out.println("Col " +  info[1]);
        System.out.println("Min " +  info[2]);
        System.out.println("max " +  info[3]);
        
        for(int i=0;i<array.length;i++)
        {
            for(int j=0;j<array[i].length;j++)
            {
                System.out.print(array[i][j] + ",");
            }
            
            System.out.print("\n");
        }
    }
    
}
