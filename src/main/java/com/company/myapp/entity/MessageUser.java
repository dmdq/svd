package com.company.myapp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_msg_user")
public class MessageUser implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "type", nullable = false)
	private Integer type;

	@Column(name = "value", nullable = false)
	private String value;

	@Column(name = "from_id", nullable = false)
	private Long fromId;

	@Column(name = "extension", nullable = true)
	private String extension;
	
	@Column(name = "user_id", nullable = false)
	private Long userId;
	

	@Column(name = "created_time", nullable = false)
	private Long createdTime;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Integer getType() {
		return type;
	}


	public void setType(Integer type) {
		this.type = type;
	}


	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
	}


	public Long getFromId() {
		return fromId;
	}


	public void setFromId(Long fromId) {
		this.fromId = fromId;
	}


	public String getExtension() {
		return extension;
	}


	public void setExtension(String extension) {
		this.extension = extension;
	}


	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public Long getCreatedTime() {
		return createdTime;
	}


	public void setCreatedTime(Long createdTime) {
		this.createdTime = createdTime;
	}


}
