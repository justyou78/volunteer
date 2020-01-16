package volunteer.data.method;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.java.Log;
import volunteer.data.component.Component;


@Repository
@Log
public class Kakao_Restapi {
	
	//카카오 REST API를 통해서 카카오 사용자 토큰을 가져옵니다.
	public JsonNode getAccessToken(String autorize_code) {
        final String RequestUrl = "https://kauth.kakao.com/oauth/token";

        final List<NameValuePair> postParams = new ArrayList<NameValuePair>();
 
        postParams.add(new BasicNameValuePair("grant_type", "authorization_code"));
 
        postParams.add(new BasicNameValuePair("client_id", "0b04594c8c451f296a17595e84141bd2"));
 
        postParams.add(new BasicNameValuePair("redirect_uri", "http://192.168.0.48:8081/volunteer/main_join/oauth.vol"));
 
        postParams.add(new BasicNameValuePair("code", autorize_code));
 
        final HttpClient client = HttpClientBuilder.create().build();
 
        final HttpPost post = new HttpPost(RequestUrl);
 
        JsonNode returnNode = null;
 
        try {
 
            post.setEntity(new UrlEncodedFormEntity(postParams));
 
            final HttpResponse response = client.execute(post);
 
            ObjectMapper mapper = new ObjectMapper();
 
            returnNode = mapper.readTree(response.getEntity().getContent());
 
        } catch (UnsupportedEncodingException e) {
 
            e.printStackTrace();
 
        } catch (ClientProtocolException e) {
 
            e.printStackTrace();
 
        } catch (IOException e) {
 
            e.printStackTrace();
 
        } finally {
 
        }
 
        return returnNode;
 
    }
	// 카카오 로그아웃을 사용합니다.
	public JsonNode Logout(String autorize_code) {
        final String RequestUrl = "https://kapi.kakao.com/v1/user/logout";
 
        final HttpClient client = HttpClientBuilder.create().build();
 
        final HttpPost post = new HttpPost(RequestUrl);
 
        post.addHeader("Authorization", "Bearer " + autorize_code);
 
        JsonNode returnNode = null;
 
        try {
 
            final HttpResponse response = client.execute(post);
 
            ObjectMapper mapper = new ObjectMapper();
            
            returnNode = mapper.readTree(response.getEntity().getContent());
 
        } catch (UnsupportedEncodingException e) {
 
            e.printStackTrace();
 
        } catch (ClientProtocolException e) {
 
            e.printStackTrace();
 
        } catch (IOException e) {
 
            e.printStackTrace();
 
        } finally {
 
        }
 
        return returnNode;
 
    }
	
	// 토큰을 이용하여 카카오  REST API를 통해서 사용자의 정보를 가져옵니다.
	public JsonNode getKakaoUserInfo(String accessToken) {
		 
        final String RequestUrl = "https://kapi.kakao.com/v2/user/me";
        final HttpClient client = HttpClientBuilder.create().build();
        final HttpPost post = new HttpPost(RequestUrl);
 
        // add header
        post.addHeader("Authorization", "Bearer " + accessToken);
 
        JsonNode returnNode = null;
 
        try {
            final HttpResponse response = client.execute(post);
            final int responseCode = response.getStatusLine().getStatusCode();
 
 
            // JSON 형태 반환값 처리
            ObjectMapper mapper = new ObjectMapper();
            returnNode = mapper.readTree(response.getEntity().getContent());
 
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // clear resources
        }
 
        return returnNode;
    }
	
	//카카오페이를 준비시킵니다.
	public JsonNode kakaoPayReady(HttpSession httpSession,String money) {
		
		 final String RequestUrl = "https://kapi.kakao.com/v1/payment/ready";
		
		 final HttpClient client = HttpClientBuilder.create().build();
		
		 final HttpPost post = new HttpPost(RequestUrl);
		 String token = (String)httpSession.getAttribute("token");
		 post.addHeader("Authorization", "Bearer "+token);
		 post.addHeader("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		 final List<NameValuePair> postParams = new ArrayList<NameValuePair>();
		 postParams.add(new BasicNameValuePair("cid", "TC0ONETIME"));
		 postParams.add(new BasicNameValuePair("partner_order_id", "1001"));
		 postParams.add(new BasicNameValuePair("partner_user_id", "global"));
		 postParams.add(new BasicNameValuePair("item_name", "Donation"));
		 postParams.add(new BasicNameValuePair("quantity", "1"));
		 postParams.add(new BasicNameValuePair("total_amount", money));
		 postParams.add(new BasicNameValuePair("tax_free_amount", "0"));
		 postParams.add(new BasicNameValuePair("approval_url", "http://192.168.0.48:8081/volunteer/commons/kakaoPaySuccess.vol"));
		 postParams.add(new BasicNameValuePair("cancel_url", "http://192.168.0.48:8081/volunteer/commons/kakaoPayCancel.vol"));
		 postParams.add(new BasicNameValuePair("fail_url", "http://192.168.0.48:8081/volunteer/commons/kakaoPayFail.vol"));
		 
		 
		 JsonNode returnNode = null;
		 
		 try {
			 
	            post.setEntity(new UrlEncodedFormEntity(postParams));
	 
	            final HttpResponse response = client.execute(post);
	 
	            ObjectMapper mapper = new ObjectMapper();
	 
	            returnNode = mapper.readTree(response.getEntity().getContent());
	 
	        } catch (UnsupportedEncodingException e) {
	 
	            e.printStackTrace();
	 
	        } catch (ClientProtocolException e) {
	 
	            e.printStackTrace();
	 
	        } catch (IOException e) {
	 
	            e.printStackTrace();
	 
	        } finally {
	 
	        }
	 
	        return returnNode; 

	}
	
	// 각 내용들을 활용하여 카카오페이가 처리된 내용을 가져옵니다.
	public JsonNode kakaoPayInfo(String pg_token,HttpSession session,String tid) {
		 final String RequestUrl = "https://kapi.kakao.com/v1/payment/approve";
		 final HttpClient client = HttpClientBuilder.create().build();
		 final HttpPost post = new HttpPost(RequestUrl);
		 post.addHeader("Authorization", "Bearer "+(String)session.getAttribute("token"));
		 post.addHeader("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		 final List<NameValuePair> postParams = new ArrayList<NameValuePair>();
		 postParams.add(new BasicNameValuePair("cid", "TC0ONETIME"));
		 postParams.add(new BasicNameValuePair("tid", tid));
		 postParams.add(new BasicNameValuePair("partner_order_id", "1001"));
		 postParams.add(new BasicNameValuePair("partner_user_id", "global"));
		 postParams.add(new BasicNameValuePair("pg_token", pg_token));
		 
		 JsonNode returnNode= null;
		 try {
			 	post.setEntity(new UrlEncodedFormEntity(postParams));
			 	final HttpResponse response = client.execute(post);
			 	
			 	ObjectMapper mapper = new ObjectMapper();
			 	
	            returnNode = mapper.readTree(response.getEntity().getContent());
	            
	 
	        } catch (UnsupportedEncodingException e) {
	 
	            e.printStackTrace();
	 
	        } catch (ClientProtocolException e) {
	 
	            e.printStackTrace();
	 
	        } catch (IOException e) {
	 
	            e.printStackTrace();
	 
	        } finally {
	 
	        }
		 
		 return returnNode;
      
    }
}