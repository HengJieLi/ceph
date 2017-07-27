package com.manager.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;


/**
 * json数据解析
 * @author 许友昌、张汉林
 * 2016-7-15
 */
public class DataAnalysis {

	/**
	   * 要解析的json字符串
	   * @param str
	   */
	  
	  //解析集群health的状态信息
	  public List<String> parsehealth(String str){
		System.out.println("parseHealth...");
		List<String> health=new ArrayList<String>();
		JSONObject result = (JSONObject) JSONValue.parse(str);
		System.out.println(result);
		JSONObject output=(JSONObject) result.get("output");
		System.out.println(output);
		String object0=((String) output.get("overall_status"));
		health.add(object0);
		JSONArray summary=(JSONArray) output.get("summary");
		System.out.println(summary);
		
		for(int i=0;i<summary.size();i++){
			JSONObject object=(JSONObject) summary.get(i);
			health.add((String) object.get("summary"));
		}

		return health;
		  
	  }
	  
	  //解析osd节点信息
	  public List<Long> parseosd(String str){
		  List<Long> osd=new ArrayList<Long>();
		  JSONObject result = (JSONObject) JSONValue.parse(str);
		  JSONObject output=(JSONObject) result.get("output");
		  
		  Long total= (Long) output.get("num_osds");
		  Long up= (Long) output.get("num_up_osds");
		  Long in= (Long) output.get("num_in_osds");
		  
		  osd.add(total);
		  osd.add(up);
		  osd.add(in);
		  System.out.println("osd状态"+total+"=="+up+"=="+in);
		  return osd;
		  
	  }
	  
	  //解析集群空间信息
	  public List<Long> parseSpace(String str){
		  List<Long> space=new ArrayList<Long>();
		  JSONObject result = (JSONObject) JSONValue.parse(str);
		  JSOBObject output=(JSONObject) result.get("output");
		  
		  Long total = (Long) output.get("total_bytes")/1024;
		  Long avail = (Long) output.get("total_avail_bytes")/1024;
		  Long used = (Long) output.get("total_used_bytes")/1024;
		  
		  space.add(total);
		  space.add(avail);
		  space.add(used);
		  System.out.println("集群空间状态"+total+"MB=="+avail+"MB=="+used+"MB");
		  return space;
		  
	  }
	  
	  //解析osd树状态信息
	  public List<String> parseTree(String str){
		  List<String> tree=new ArrayList<String>();
		  JSONObject result = (JSONObject) JSONValue.parse(str);
		  JSONBObject output=(JSONObject) result.get("output");
		  
		  JSONObject nodes=(JSONObject) output.get("nodes");
		  JSONArray id=(JSONArray) nodes.get("id");
		  JSONArray type=(JSONArray) nodes.get("type");
		  JSONArray name=(JSONArray) nodes.get("name");
		  JSONArray weight=(JSONArray) nodes.get("crush_weight");
		  JSONArray status=(JSONArray) nodes.get("status");
		  JSONArray reweight=(JSONArray) nodes.get("reweight");
		  JSONArray primary-affinity=(JSONArray) nodes.get("primary_affinity");
		  
		  //tree.add(id);
		  //tree.add(type);
		  //tree.add(name);
		  //tree.add(weight);
		  //tree.add(status);
		  //tree.add(reweight);
		  //tree.add(primary);
		  for(int i=0;i<id.size();i++){
				JSONObject object=(JSONObject) id.get(i);
				tree.add((String) object.get("id"));
				JSONObject object1=(JSONObject) type.get(i);
				tree.add((String) object1.get("type"));
				JSONObject object2=(JSONObject) name.get(i);
				tree.add((String) object2.get("name"));
				JSONObject object3=(JSONObject) weight.get(i);
				tree.add((String) object3.get("weight"));
				JSONObject object4=(JSONObject) status.get(i);
				tree.add((String) object.get("status"));
				JSONObject object5=(JSONObject) reweight.get(i);
				tree.add((String) object5.get("reweight"));
				JSONObject object6=(JSONObject) primary-affinity.get(i);
				tree.add((String) object6.get("primary-affinity"));
			}
		  
	     return tree;
		  
	  }
	  
	  //解析mon的映射信息
	  public List<String> parsedump(String str){
		  List<String> dump=new ArrayList<String>();
		  JSONObject result = (JSONObject) JSONValue.parse(str);
		  String status=(String) result.get("status");
		  JSONObject output=(JSONObject) result.get("output");
		  String created=(String) output.get("created");
		  String modified=(String) output.get("modified");
		  String fsid=(String) output.get("fsid");
		  JSONObject mons=(JSONObject) output.get("mons");
		  String name=(String) mons.get("name");
		  String addr=(String) mons.get("addr");
		  
		  dump.add(status);
		  dump.add(fsid);
		  dump.add(addr);
		  dump.add(created);
		  dump.add(modified);
		  System.out.println("mon的映射信息"+status+"=="+fsid+"=="+addr+"=="+created+"=="+modified);
		return dump;
		 
	  }
	  
	  //解析mds状态信息
	  public List<String> parsemon(String str){
		  List<String> mds=new ArrayList<String>();
		  JSONObject result = (JSONObject) JSONValue.parse(str);
		  String status=(String) result.get("status");
		  JSONObject output=(JSONObject) result.get("output");
		  String standbys=(String) output.get("standbys");
		  mds.add(status);
		  mds.add(standbys);
		  
		  System.out.println("mds状态信息"+status+"=="+standbys);
		  
		  return mds;
	  }
	  
 	  //解析监控节点信息
	  public List<String> parsemon(String str){
		  JSONObject result = (JSONObject) JSONValue.parse(str);
		  String status=(String) result.get("status");
		  JSONObject output=(JSONObject) result.get("output");
		  String name=(String) output.get("name");
		  List<String> mon=new ArrayList<String>();
		  mon.add(name);
		  mon.add(status);
		  
		  System.out.println("mon状态"+name+"=="+status);
		return mon;
		  
		  
	  }
	  
	  //解析osd容量信息
	  public List<Long> parsePG(String str){
		  JSONObject result =(JSONObject) JSONValue.parse(str);
		  JSONObject output =(JSONObject) result.get("output");
		  JSONObject health=(JSONObject) output.get("health");
		  JSONArray health_services=(JSONArray) health.get("health_services");
		  JSONObject temp1=(JSONObject) health_services.get(0);
		  JSONArray mons=(JSONArray) temp1.get("mons");
		  JSONObject temp2=(JSONObject) mons.get(0);
		  
		  Long used= (Long) temp2.get("kb_used");
		  Long avail= (Long) temp2.get("kb_avail");
//		  JSONObject osd_stats_sum=(JSONObject) output.get("osd_stats_sum");
//		  Long used= (Long) osd_stats_sum.get("kb_used");
//		  Long avail= (Long) osd_stats_sum.get("kb_avail");
//		  
		  List<Long> PG=new ArrayList<Long>();
		  PG.add(used);
		  PG.add(avail);
		  System.out.println("PG OK");
		  for(Long s:PG){
				System.out.println("PG:"+s);
			}
		  return PG;
	  }
	  
	  
	  //解析PG状态信息
	  public List<Long> parsePgStat(String str){
		  JSONObject result =(JSONObject) JSONValue.parse(str);
		  JSONObject output =(JSONObject) result.get("output");
		  JSONObject pgmap=(JSONObject) output.get("pgmap");
		  Long num_pgs=(Long) pgmap.get("num_pgs");
		 
		 // JSONObject pgs_by_state=(JSONObject) pgmap.get("pgs_by_state");
		  JSONArray pgs_by_state = (JSONArray) pgmap.get("pgs_by_state");
		  List<Long> PG_Stat=new ArrayList<Long>();
		  PG_Stat.add(num_pgs);
		  for(int i=0;i<pgs_by_state.size();i++)
		  {
			  JSONObject object=new JSONObject();
			  object=(JSONObject) pgs_by_state.get(i);
			  PG_Stat.add((Long) object.get("count"));
		  }
		  
		  return PG_Stat;
		  
	  }

	  //数据切割以“；”切割
	 public static void spilt(String str){
		 String a[]=str.split(";");
		 List<String> userList = new ArrayList<String>();
		 Collections.addAll(userList, a);
		 for(int i=0;i<userList.size();i++){
			 System.out.println("集合数据为"+userList.get(i));
		 }
	 }
	 
}
