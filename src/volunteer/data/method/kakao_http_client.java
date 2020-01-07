package volunteer.data.method;

import static org.junit.Assume.assumeNoException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import volunteer.data.vo.MemberVO;

public class kakao_http_client {
	
	// 주소값을 좌표로 변환후 필요한 데이터(좌표, 주소, 아이디, 이름, 성별, 나이) 해쉬맵으로 돌려줌
	public HashMap<String, String> get(MemberVO vo) throws ClientProtocolException, IOException {
		HashMap<String, String> hs = new HashMap<String, String>();
		String app_key = "0b04594c8c451f296a17595e84141bd2";
		String address = vo.getAddress().replaceAll(" ", ""); // 주소 공백 제거
		String requestURL = "https://dapi.kakao.com/v2/local/search/address.json?query="+address;
		
		HttpClient client = HttpClientBuilder.create().build();
		
		HttpGet getRequest = new HttpGet(requestURL);
		
		getRequest.addHeader("Authorization", "KakaoAK " + app_key);

		HttpResponse response = client.execute(getRequest);
		
		JsonNode returnNode;
		ObjectMapper mapper = new ObjectMapper();
        returnNode = mapper.readTree(response.getEntity().getContent());
		
		String str = returnNode.toString();
		JsonParser parser = new JsonParser();
		JsonObject jsonObject = (JsonObject) parser.parse(str);
		JsonArray array = (JsonArray) jsonObject.get("documents");
		
		JsonObject object = (JsonObject) array.get(0);
		String x = object.get("x").toString();
//		str1 = str1.replaceAll("\"", ""); 
		String y = object.get("y").toString();
//		str2 = str2.replaceAll("\"", ""); 
		
//		double x = Double.parseDouble(str1);
//		double y = Double.parseDouble(str2);
		hs.put("x", x);	// x 좌표
		hs.put("y", y); // y 좌표
		hs.put("name", vo.getName()); // 이름
		hs.put("type",vo.getMember_type()); //멤버 타입
		hs.put("age", vo.getAge()+""); // 나이
		hs.put("id", vo.getId()); // 아이디	
		if(vo.getGender() == 1) { // 성별
			hs.put("gender", "남자"); 
		}else if(vo.getGender() == 2) {
			hs.put("gender", "여자");
		}
		return hs;
	}
	
	// 해쉬맵으로 가져온 데이터를 제이슨 파일로 생성 (사용안함)
	public void toJson(List<HashMap<String, String>> position) {
		
		JSONObject obj = new JSONObject();
		JSONArray list = new JSONArray();
		JSONObject obj2 = null;
		
		
		for(HashMap<String, String> hs : position) {
			obj2 = new JSONObject();
			String x = hs.get("x").replaceAll("\"", ""); 
			String y = hs.get("y").replaceAll("\"", ""); 
			obj2.put("lng", Double.parseDouble(x));
			obj2.put("lat", Double.parseDouble(y));
			obj2.put("name", hs.get("name"));
			obj2.put("type", hs.get("type"));
			obj2.put("age", hs.get("age"));
			obj2.put("gender", hs.get("gender"));
			obj2.put("id", hs.get("id"));
			list.add(obj2);	
		}
		
		obj.put("positions", list);
		System.out.println(obj);
		FileWriter file;
	       try {
	    	   file = new FileWriter("D:\\kbj\\workspace\\volunteer\\WebContent\\web\\data\\test.json");
	       	   file.write(obj.toJSONString());
	       	   file.flush();
	       	   file.close();
	       } catch (IOException e) {
	       	   e.printStackTrace();
	       }
	
	}
	
	public void readToJson(List<HashMap<String, String>> position) throws FileNotFoundException, IOException, ParseException{
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(new FileReader("C:\\Spring_An\\work\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp7\\wtpwebapps\\volunteer\\web\\data\\member.json")); // json 파싱
		 
		JSONArray list = (JSONArray) object.get("positions"); // 리스트에 담기

		JSONObject obj = new JSONObject(); // 최종적으로 들어갈 객체
		JSONObject obj2 = null;
		
		// 리스트에 새로운 데이터 추가
		for(HashMap<String, String> hs : position) {
			obj2 = new JSONObject();
			String x = hs.get("x").replaceAll("\"", ""); 
			String y = hs.get("y").replaceAll("\"", ""); 
			obj2.put("lng", Double.parseDouble(x));
			obj2.put("lat", Double.parseDouble(y));
			obj2.put("name", hs.get("name"));
			obj2.put("type", hs.get("type"));
			obj2.put("age", hs.get("age"));
			obj2.put("gender", hs.get("gender"));
			obj2.put("id", hs.get("id"));
			list.add(obj2);	
		}
		
		obj.put("positions", list);	// 리스트 넣기
		
		// 파일 생성(덮어쓰기)
		FileWriter file;
	       try {
	    	   file = new FileWriter("C:\\Spring_An\\work\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp7\\wtpwebapps\\volunteer\\web\\data\\member.json");
	       	   file.write(obj.toJSONString());
	       	   file.flush();
	       	   file.close();
	       } catch (IOException e) {
	       	   e.printStackTrace();
	       }
	
	}
	
	public void readToJsonModify(List<HashMap<String, String>> position, String id) throws FileNotFoundException, IOException, ParseException{
	      
	      JSONParser parser = new JSONParser();
	      JSONObject object = (JSONObject) parser.parse(new FileReader("C:\\Spring_An\\work\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp7\\wtpwebapps\\volunteer\\web\\data\\member.json")); // json 파싱
	       
	      JSONArray list = (JSONArray) object.get("positions"); // 리스트에 담기
	      System.out.println(list);
	      JSONObject obj = new JSONObject(); // 최종적으로 들어갈 객체
	      JSONObject obj2 = null;
	      JSONObject obj3 = null;
	      
	      for(int i = 0; i < list.size(); i++) {
	         obj2 = (JSONObject) list.get(i);
	         String listid = null;
	         if(obj2.get("id") != null) {
	        	 listid= (String) obj2.get("id");	 
	         }
	         else {
	        	 continue;
	         }
	         System.out.println(listid+"아이디확인");
	         if(listid != null && listid.equals(id)) {
	            list.remove(i);
	            for(HashMap<String, String> hs : position) {
	            	obj3 = new JSONObject();
	               String x = hs.get("x").replaceAll("\"", ""); 
	               String y = hs.get("y").replaceAll("\"", ""); 
	               obj3.put("lng", Double.parseDouble(x));
	               obj3.put("lat", Double.parseDouble(y));
	               obj3.put("name", hs.get("name"));
	               obj3.put("type", hs.get("type"));
	               obj3.put("age", hs.get("age"));
	               obj3.put("gender", hs.get("gender"));
	               obj3.put("id", hs.get("id"));
	               list.add(obj3);   
	            }
	            break;
	         }
	      }
	      
	      obj.put("positions", list);   // 리스트 넣기
	      
	      // 파일 생성(덮어쓰기)
	      FileWriter file;
	          try {
	             file = new FileWriter("C:\\Spring_An\\work\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp7\\wtpwebapps\\volunteer\\web\\data\\member.json");
	                file.write(obj.toJSONString());
	                file.flush();
	                file.close();
	          } catch (IOException e) {
	                e.printStackTrace();
	          }

	   }
	
	
	
	
	
}
