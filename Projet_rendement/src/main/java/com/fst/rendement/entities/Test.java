package com.fst.rendement.entities;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Test {
	public static void main(String[] args) {
	    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	    String hash = "$2a$10$2iUCsvBk4c0JMzFSGLVP1.cuUXQ4gZEoNMVJJbpvqX5MRaekxz9uy";
	    System.out.println(encoder.matches("admin123", hash)); // doit afficher true
	}

}
