package com.jrm.backitup_brain.MC;

public class TAImage {
    private String ImageName,ImageData;

    public String f_ImageName(String p_Value){
        return (p_Value == null)?ImageName:(ImageName=p_Value);
    }
    public String f_ImageData(String p_Value){
        return (p_Value == null)?ImageData:(ImageData=p_Value);
    }
}
