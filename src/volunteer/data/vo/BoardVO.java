package volunteer.data.vo;

import java.sql.Timestamp;

public class BoardVO {
	private int bbsno;
	private String id;
	private String categor;
	private String subject;
	private String content;
	private Timestamp regdate;
	private String file01;
	private String file02;
	private String link01;
	private int views;
	private String name;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getBbsno() {
		return bbsno;
	}
	public void setBbsno(int bbsno) {
		this.bbsno = bbsno;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCategor() {
		return categor;
	}
	public void setCategor(String categor) {
		this.categor = categor;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Timestamp getRegdate() {
		return regdate;
	}
	public void setRegdate(Timestamp regdate) {
		this.regdate = regdate;
	}
	public String getFile01() {
		return file01;
	}
	public void setFile01(String file01) {
		this.file01 = file01;
	}
	public String getFile02() {
		return file02;
	}
	public void setFile02(String file02) {
		this.file02 = file02;
	}
	public String getLink01() {
		return link01;
	}
	public void setLink01(String link01) {
		this.link01 = link01;
	}
	public int getViews() {
		return views;
	}
	public void setViews(int views) {
		this.views = views;
	}
	
}
