package volunteer.data.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

public class ConnectVO {
	private String vol_email;
	private String disabled_email;
	private int vol_check;
	private int disabled_check;
	public String getVol_email() {
		return vol_email;
	}
	public void setVol_email(String vol_email) {
		this.vol_email = vol_email;
	}
	public String getDisabled_email() {
		return disabled_email;
	}
	public void setDisabled_email(String disabled_email) {
		this.disabled_email = disabled_email;
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
