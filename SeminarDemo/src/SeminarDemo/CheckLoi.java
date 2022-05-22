/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SeminarDemo;

/**
 *
 * @author Nam
 */
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class CheckLoi{
    Pattern pattern;
    Matcher matcher;
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";// cấu trúc 1 email thông thường
 
    public CheckLoi() {
        
    }
 
    // Class kiểm định dạng email
    public boolean kiemtraEmail(String email) {
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        if(matcher.matches())
        {
            return true;
        } else {
            return false;
        }
    }
    public boolean kiemtraSo(String chuoi)
    {
        try
        {          
            Integer.parseInt(chuoi);
            return true;
        } catch(Exception e)
        {
            return false;
        }
    }
    public boolean kiemtraSophay(String chuoi)
    {
        try
        {          
            Float.parseFloat(chuoi);
            return true;
        } catch(Exception e)
        {
            return false;
        }
    }
    
    public boolean kiemtraSDT(String chuoi)
    {
        boolean check = true;
        try
        {       
            if(chuoi.length() != 10)
            {
                check = false;
            } else if (chuoi.charAt(0) != '0')
            {
                check = false;
            } 
            Integer.parseInt(chuoi);
        } catch(Exception e)
        {
            check = false;
        }
        return check;
    }
    public boolean kiemtraSokitu(String chuoi, int length)
    {
        if(chuoi.length() <= length)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public boolean kiemtraCmnd(String chuoi)
    {
        boolean check = true;
        try
        {
            if(chuoi.length() != 9)
            {
                check = false;
            }
            Integer.parseInt(chuoi);
        } catch(Exception e)
        {
            check = false;
        }
        return check;
    }

}  
