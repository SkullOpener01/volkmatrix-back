package com.volkmatrix.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class WapIntegerUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(WapIntegerUtils.class);
	
	public static int getRandom(int start,int limit,int size){
        try {

            //Set<Integer> indexes=new HashSet<Integer>();
            List<Integer> indexes=new ArrayList<Integer>();
            ThreadLocalRandom.current().ints(start, size).distinct().limit(limit).forEach(x->{indexes.add(x);});
            return indexes.get(0);
        }catch(Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

	 public static double getDoubleValue(double value){
	        String amt;
	        if(value%1==0){
//				LOGGER.info("###");
	            DecimalFormat df = new DecimalFormat("##.00");
	            amt=df.format(value);
	        }else{
	            DecimalFormat df = new DecimalFormat("0.00");
	            amt=df.format(value);
	        }
//			LOGGER.info("amount"+amt);
	        return Double.parseDouble(amt);
	    }
	 

    public static void main(String args[])  {
    	try {
    	//Set<Integer> indexes=new HashSet<Integer>();
		//ThreadLocalRandom.current().ints(2, 100).distinct().limit(100).forEach(x->{indexes.add(x);});
		int i=getRandom(1000000, 1,9999999);
		System.out.println("Indexes::::"+i);
    }catch(Exception e) {
    	System.out.println("Error::::"+e.getMessage());
    }
    }
}
