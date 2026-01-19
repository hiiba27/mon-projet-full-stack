package com.fst.rendement.entities;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
public class PasswordGenerator {
	public static void main(String[] args) { 
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); // Exemple : encoder deux mots de passe 
  String adminPassword = encoder.encode("admin123");
  String chefPassword = encoder.encode("chef123");
  System.out.println("Admin hash: " + adminPassword); 
  System.out.println("Chef hash: " + chefPassword); } }
 