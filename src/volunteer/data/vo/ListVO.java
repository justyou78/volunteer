package volunteer.data.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

public class ListVO {
	private String vol_email;
	private String disabled_email;
	private int vol_time;
	private String content;
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
	public int getVol_time() {
		return vol_time;
	}
	public void setVol_time(int vol_time) {
		this.vol_time = vol_time;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
