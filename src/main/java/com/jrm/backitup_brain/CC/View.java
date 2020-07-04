package com.jrm.backitup_brain.CC;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jrm.backitup_brain.HC.DbFunc;
import com.jrm.backitup_brain.HC.FileFunc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path="/view")
public class View {

	@Autowired
	Gson o_Gson;

	@PostMapping("/BI")
	public String cf_BackUpImages(@RequestHeader Map<String,String> p_Headers, @RequestBody String p_Body){
		String v_Query = "",v_ResMsg = "Empty",
				v_uh_a = p_Headers.get("uh_a"),
				v_uh_b = p_Headers.get("uh_b"),
				v_uh_l = p_Headers.get("uh_l");
		JsonArray ro_ResArray = null, o_ReqArray = o_Gson.fromJson(p_Body, JsonObject.class).getAsJsonArray("values");
		JsonObject ro_ResData = new JsonObject();
		DbFunc o_DbFunc = new DbFunc();
		FileFunc o_FileFunc = new FileFunc();

		try{
			if(o_ReqArray.size() > 0){
				if(v_uh_l.equals("0")){
					ro_ResArray = o_FileFunc.f_ReadImages(v_uh_b);
				}else if(v_uh_l.equals("1")){
					ro_ResArray = o_FileFunc.f_WriteImages(v_uh_b,o_ReqArray);
				}
			}else{
				v_ResMsg = "No Data";
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
