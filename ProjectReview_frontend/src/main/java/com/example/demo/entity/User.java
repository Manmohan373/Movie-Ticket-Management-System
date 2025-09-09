package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Entity
@NoArgsConstructor@AllArgsConstructor@Getter@ToString
@Table(name = "user")
public class User {
	@Id
	String username;
	String password;
	

}
