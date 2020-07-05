package com.jrm.backitup_brain.HC;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.sql.*;

public class DbFunc {

	public JsonArray f_CallDb(char p_Type,String p_Query) throws Exception{
		JsonArray ro_ResData = new JsonArray();
		Class.forName("com.mysql.jdbc.Driver");
		Connection o_Connection = DriverManager.getConnection("jdbc:mysql://localhost/biu","root","harharmahadev");
//		Connection o_Connection = DriverManager.getConnection("jdbc:mysql://localhost/biu","root","omkara@123");
		Statement o_Statement = (Statement) o_Connection.createStatement();
		if(p_Type=='1'){
			ro_ResData.add(o_Statement.executeUpdate(p_Query));
		}else{
			ResultSet o_Result = o_Statement.executeQuery(p_Query);
			ResultSetMetaData o_Meta = o_Result.getMetaData();
			JsonObject o_Each = null;
			while(o_Result.next()){
				o_Each = new JsonObject();
				for(int i_each=1;i_each<=o_Meta.getColumnCount();i_each++){
					o_Each.addProperty(o_Meta.getColumnName(i_each),o_Result.getString(i_each));
				}
				ro_ResData.add(o_Each);
			}
		}
		o_Connection.close();
		return ro_ResData;
	}
}
