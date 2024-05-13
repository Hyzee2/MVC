package net.admin.db;

import java.sql.Date;

public class AdminBean {
	private String id;
	private String mail;
	private String name;
	private String IDnum;
	private String birth;
	private String hobby;
	private String introduction;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIDnum() {
		return IDnum;
	}
	public void setIDnum(String iDnum) {
		IDnum = iDnum;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getHobby() {
		return hobby;
	}
	public void setHobby(String hobby) {
		this.hobby = hobby;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	

}
