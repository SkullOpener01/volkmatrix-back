package com.volkmatrix.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WapLogUtils {
private final static Logger LOGGER= LoggerFactory.getLogger(WapLogUtils.class);
    
	
	public static void objectLogging(String msg,Object obj){
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			// Convert object to JSON string
			LOGGER.info(msg+"::::"+mapper.writeValueAsString(obj));
			//return mapper.writeValueAsString(obj);
					
		} catch (Exception e) {
			
			//throw new ApiServiceException(e.getMessage());
		} 
	}
}
