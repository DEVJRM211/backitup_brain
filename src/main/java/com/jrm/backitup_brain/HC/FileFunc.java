package com.jrm.backitup_brain.HC;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class FileFunc {
	String v_Path = "/home/codeos/Documents/";
	public JsonArray f_WriteImages(String p_UserName, JsonArray p_List) throws Exception{
		byte[] o_Each;
		JsonObject o_Inner,o_Error;
		JsonArray ro_ErrList = new JsonArray();
		if(Files.notExists(Paths.get((v_Path+p_UserName)))){
			for(int i_image=0;i_image<p_List.size();i_image++){
				o_Inner = p_List.get(i_image).getAsJsonObject();
				o_Each = Base64.getDecoder().decode(o_Inner.get("ImageData").getAsString());
				try(OutputStream o_Stream = new FileOutputStream(v_Path+"/"+o_Inner.get("ImageName").getAsString())){
					o_Stream.write(o_Each);
				}catch(Exception p_Exception){
					o_Error = new JsonObject();
					o_Error.addProperty("ErrAt",i_image);
					o_Error.addProperty("ErrImage",p_List.get(i_image).getAsJsonObject().get("ImageName").getAsString());
					o_Error.addProperty("ErrMsg",p_Exception.getMessage().toString());
					ro_ErrList.add(o_Error);
				}
			}
		}else{
			o_Error = new JsonObject();
			o_Error.addProperty("ErrAt",-1);
			o_Error.addProperty("ErrImage","none");
			o_Error.addProperty("ErrMsg","No folder available for user");
		}
	return ro_ErrList;
	}

	public JsonArray f_ReadImages(String p_UserName) throws Exception{
		FileInputStream o_Reader;
		byte[] o_EachImage;
		JsonObject o_Data;
		JsonArray ro_Data = new JsonArray();
		File[] o_ImageList = new File(v_Path+p_UserName).listFiles();
		for(int i_image=0;i_image<o_ImageList.length;i_image++){
			if(o_ImageList[i_image].isFile()){
				o_Reader = new FileInputStream(o_ImageList[i_image]);
				o_EachImage = new byte[(int) o_ImageList[i_image].length()];
				o_Reader.read(o_EachImage);
				o_Data = new JsonObject();
				o_Data.addProperty("ImageName",o_ImageList[i_image].getName());
				o_Data.addProperty("ImageData",Base64.getEncoder().encodeToString(o_EachImage));
				ro_Data.add(o_Data);
			}
		}
		return ro_Data;
	}
}
