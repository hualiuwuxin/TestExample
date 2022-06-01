package com.lomi.serialize;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * 
 * @author ZHANGYUKUN
 * @Date2022年5月31日
 *
 */
public class DateDeserializer extends JsonDeserializer<Date> {
	
	Logger log = LoggerFactory.getLogger(getClass());
	
    @Override
    public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
    	  String text = p.getText();
          
          if ( text != null && !"".equals( text.trim() )  ) {
        	  DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	try {
				return df.parse(text);
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}
          }else {
          	 return null;
          }
    }
    
    
    
    public static void main(String[] args) throws ParseException {
    	  //DateFormat dfy = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	
    	
    	 // String a = "YYYY-MM-dd";
    	  String a = "YYYY-MM-dd HH:mm:ss";
    	  DateFormat df = new SimpleDateFormat(a);
    	  DateTimeFormatter formatter = DateTimeFormatter.ofPattern(a);
    	  
    	  System.out.println(  df.format( new Date() )  );
    	  System.out.println(  df.format( df.parse( df.format( new Date() ))));
    	  
    	  
			/*
			 * LocalDateTime localDateTime = LocalDateTime.now(); System.out.println(
			 * localDateTime.format( formatter ) );; System.out.println(
			 * LocalDateTime.parse( localDateTime.format( formatter ) , formatter).format(
			 * formatter ) );;
			 */
    	  
    	  
	}
    
}
