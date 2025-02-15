package com.dnd.demo.common;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "test")
public class Test {

	@Id
	private String _id;
	private String email;
	private String password;
	private String phone;
	private String name;
}
