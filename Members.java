package jp.ken.jdbc.entity;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Members implements Serializable{
	private Integer id;
	private String name;
	private String email;
	private String phoneNumber;
	private java.sql.Date birthday;
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

	public static java.sql.Date parseDate(String stringDate) {
		try {
			java.util.Date date = dateFormat.parse(stringDate);
			long time = date.getTime();
			return new java.sql.Date(time);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public java.sql.Date getBirthday() {
		return birthday;
	}

	public void setBirthday(java.sql.Date birthday) {
		this.birthday = birthday;
	}

}
