package com.austin.walletapp.utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class AppUtil {
    public List<String> split(String delimitedString){
        if (delimitedString!=null)
            return  Arrays.stream(delimitedString.split(",")).collect(Collectors.toList());
        return null;
    }
    private final Logger logger = LoggerFactory.getLogger(AppUtil.class);

    public void log(String message) {
        logger.info(message);
    }
    public void print(Object obj){
        try {
            logger.info(new ObjectMapper().writeValueAsString(obj));
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public  String generateSerialNumber(String prefix) {
        Random rand = new Random();
        long x = (long)(rand.nextDouble()*100000000000000L);
        return  prefix + String.format("%014d", x);
    }

    public boolean validImage(String fileName)
    {
        String regex = "(.*/)*.+\\.(png|jpg|gif|bmp|jpeg|PNG|JPG|GIF|BMP|JPEG)$";
        Pattern p = Pattern.compile(regex);
        if (fileName == null) {
            return false;
        }
        Matcher m = p.matcher(fileName);
        return m.matches();
    }

    public boolean validEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    public String getFormattedNumber(String number){
        number=number.trim();
        if(number.startsWith("0"))
            number="+234"+number.substring(1);
        else if(number.startsWith("234"))
            number="+"+number;
        else {
            if (!number.startsWith("+")) {
                if (Integer.parseInt(String.valueOf(number.charAt(0))) > 0) {
                    number = "+234" + number;
                }
            }
        }
        return  number;
    }

    public Long generateOTP(){
        Random rnd = new Random();
        Long number = (long) rnd.nextInt(999999);
        return  number;
    }
    public String  getString(Object o){
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(o);
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
    public  Object getObject(String content, Class cls){
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(content,cls);
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
    public ObjectMapper getMapper(){
        ObjectMapper mapper= new ObjectMapper();
        return mapper;
    }

}
