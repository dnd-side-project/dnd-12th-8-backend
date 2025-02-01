package com.dnd.demo.common;

import lombok.Data;
import org.springframework.data.annotation.Id;

import org.springframework.data.mongodb.core.mapping.Document;

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
