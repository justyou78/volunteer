package volunteer.data.vo;

//사용자 VO
public class MemberVO {

	private String id;
	private String disabled_number;
	private String email;
	private String name;
	private int age;
	private int gender;
	private String address;
	private String member_type;
	private String vol_name;
	private String disabled_name;
	private String picture;
	private String regist_number;
	private int total_vol_time;
	private double point;
	private String callnumber;
	private String pw;

	public double getPoint() {
		return point;
	}

	public void setPoint(double point) {
		this.point = point;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getCallnumber() {
		return callnumber;
	}

	public void setCallnumber(String callnumber) {
		this.callnumber = callnumber;
	}

	public void setDisabled_number(String disabled_number) {
		this.disabled_number = disabled_number;
	}

	public String getDisabled_number() {
		return disabled_number;
	}

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

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getVol_name() {
		return vol_name;
	}

	public void setVol_name(String vol_name) {
		this.vol_name = vol_name;
	}

	public String getDisabled_name() {
		return disabled_name;
	}

	public void setDisabled_name(String disabled_name) {
		this.disabled_name = disabled_name;
	}

	public String getRegist_number() {
		return regist_number;
	}

	public void setRegist_number(String regist_number) {
		this.regist_number = regist_number;
	}

	public int getTotal_vol_time() {
		return total_vol_time;
	}

	public void setTotal_vol_time(int total_vol_time) {
		this.total_vol_time = total_vol_time;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}