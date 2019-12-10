package volunteer.data.vo;

import java.sql.Timestamp;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

public class ListVO {
	private String vol_id;
	private String disabled_id;
	private int vol_time;
	private String content;
	private Timestamp time;
	
	
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
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
