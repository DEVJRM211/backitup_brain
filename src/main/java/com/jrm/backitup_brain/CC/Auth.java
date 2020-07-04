package com.jrm.backitup_brain.CC;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jrm.backitup_brain.HC.DbFunc;
import com.jrm.backitup_brain.HC.FileFunc;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path="/auth")
public class Auth {

	@Autowired
	Gson o_Gson;

	@GetMapping("/test")
	public String cf_test(){
		JsonObject ro_Test = new  JsonObject();
		FileFunc o_FileFunc = new FileFunc();
		try{
		ro_Test.add("ResData",o_FileFunc.f_ReadImages("testuser1"));
		}catch(Exception p_Exception){
		}
		return ro_Test.toString();
	}

	@PostMapping("/BI")
	public String cf_BackUpImages(@RequestHeader Map<String,String> p_Headers,@RequestBody String p_Body){
		String v_Query = "",v_ResMsg = "Empty",
				v_uh_d = p_Headers.get("uh_d"),
				v_uh_l = p_Headers.get("uh_l");
		JsonArray ro_ResArray = null, o_ReqArray = o_Gson.fromJson(p_Body,JsonObject.class).getAsJsonArray("values");
		JsonObject ro_ResData = new JsonObject();
		DbFunc o_DbFunc = new DbFunc();
		int v_ReqLen = o_ReqArray.size();
		try{
			if(o_ReqArray.size() > 0){
				if(v_uh_l.equals("0")){
					//code for get
				}else if(v_uh_l.equals("1")){
					//code for set
				}else{
					//return empty request data
				}
			}else{
				//retrun empty array data
			}
			ro_ResData.addProperty("ResMsg",v_ResMsg);
			ro_ResData.add("ResData",ro_ResArray);
		}catch(Exception p_Exception){
			ro_ResData = new JsonObject();
			ro_ResData.addProperty("ResMsg","ServerError");
			ro_ResData.addProperty("ErrMsg",p_Exception.getMessage());
		}
		return ro_ResData.toString();
	}
}
