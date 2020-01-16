package volunteer.data.vo;


//봉사자 연결 VO
public class ConnectVO {
	private String vol_id;
	private String disabled_id;
	private int vol_check;
	private int disabled_check;
	
	public String getVol_id() {
		return vol_id;
	}
	public void setVol_id(String vol_id) {
		this.vol_id = vol_id;
	}
	public String getDisabled_id() {
		return disabled_id;
	}
	public void setDisabled_id(String disabled_id) {
		this.disabled_id = disabled_id;
	}
	public int getVol_check() {
		return vol_check;
	}
	public void setVol_check(int vol_check) {
		this.vol_check = vol_check;
	}
	public int getDisabled_check() {
		return disabled_check;
	}
	public void setDisabled_check(int disabled_check) {
		this.disabled_check = disabled_check;
	}
	
	
}
