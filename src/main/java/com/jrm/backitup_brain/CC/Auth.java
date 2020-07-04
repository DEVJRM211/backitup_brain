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

	@PostMapping("/user")
	public String cf_User(@RequestHeader Map<String,String> p_Headers,@RequestBody String p_Body){
		String v_Query = "",v_ResMsg = "Empty",
				v_uh_l = p_Headers.get("uh_l");
		JsonArray ro_ResArray = null, o_ReqArray = o_Gson.fromJson(p_Body,JsonObject.class).getAsJsonArray("values");
		JsonObject ro_ResData = new JsonObject();
		DbFunc o_DbFunc = new  DbFunc();
		FileFunc o_FileFunc = new FileFunc();
		try{
			if(o_ReqArray.size()>0){
				JsonObject o_User = o_ReqArray.get(0).getAsJsonObject();
				if(v_uh_l.equals("SI")){
					v_Query = "select * from TAU where TAU_Mobile='"+o_User.get("TAU_Mobile").getAsString()+"' and TAU_Password='"+o_User.get("TAU_Password").getAsString()+"' ;";
					ro_ResArray = o_DbFunc.f_CallDb('0',v_Query);
					v_ResMsg = (ro_ResArray.size()>0)?"Success":"Invalid Mobile Number Or Password";
				}else if(v_uh_l.equals("SU")){
				 	v_Query = "select * from TAU where TAU_Mobile='"+o_User.get("TAU_Mobile").getAsString()+"';";
				 	v_ResMsg = (o_DbFunc.f_CallDb('0',v_Query).size() > 0)?"User Exists":"New";
				 	if(v_ResMsg.equals("New")){
				 		v_Query = "insert into TAU values(0,"+
								"'"+o_User.get("TAU_Name").getAsString()+"',"+
								"'"+o_User.get("TAU_Mobile").getAsString()+"',"+
								"'"+o_User.get("TAU_Email").getAsString()+"',"+
								"'"+o_User.get("TAU_Password").getAsString()+"',"+
								"'"+o_User.get("TAU_CreatedOn").getAsString()+"',"+
								"'"+o_User.get("TAU_Status").getAsString()+"');";
				 		ro_ResArray = o_DbFunc.f_CallDb('1',v_Query);
				 		o_FileFunc.f_CreateFolder(o_User.get("TAU_Mobile").getAsString());
					}
				}
			}else{
				v_ResMsg = "Wrong Request";
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
