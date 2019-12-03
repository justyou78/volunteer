package volunteer.data.vo;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class MemberVO {
	private String email;
	private String name;
	private int age;
	private int gender;
	private String address;
	private String member_type;
	private String disabled_detail;
	private String picture;
	private int regist_number_vol;
	private int regist_number_disabled;
	private int total_vol_time;
	private int point;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getMember_type() {
		return member_type;
	}
	public void setMember_type(String member_type) {
		this.member_type = member_type;
	}
	public String getDisabled_detail() {
		return disabled_detail;
	}
	public void setDisabled_detail(String disabled_detail) {
		this.disabled_detail = disabled_detail;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public int getRegist_number_vol() {
		return regist_number_vol;
	}
	public void setRegist_number_vol(int regist_number_vol) {
		this.regist_number_vol = regist_number_vol;
	}
	public int getRegist_number_disabled() {
		return regist_number_disabled;
	}
	public void setRegist_number_disabled(int regist_number_disabled) {
		this.regist_number_disabled = regist_number_disabled;
	}
	public int getTotal_vol_time() {
		return total_vol_time;
	}
	public void setTotal_vol_time(int total_vol_time) {
		this.total_vol_time = total_vol_time;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	
	
	
	
	
	
}
