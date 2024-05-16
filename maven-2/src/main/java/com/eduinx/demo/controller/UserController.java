package com.eduinx.demo.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eduinx.demo.pojo.*;

@Controller
public class UserController {
    
	@RequestMapping("/")
    public String home() {
        return "index"; // Returns the index.html
    }
	
	@PostMapping("/register")
    public String addUser(@ModelAttribute User user) {
        System.out.println(user.getName() + " >> " + user.getPassword());
        try {
            // db connection info
            String url = "jdbc:mysql://localhost:3306/demo?useSSL=false&serverTimezone=UTC";
            String username = "root";
            String password = "root";
            
            // sql statement with parameters
            String sql = "INSERT INTO User (name, password) VALUES (?, ?)";
            
            // Establishing db Connection
            try (Connection conn = DriverManager.getConnection(url, username, password);
                 PreparedStatement statement = conn.prepareStatement(sql)) {
                
                // Setting parameters
                statement.setString(1, user.getName());
                statement.setString(2, user.getPassword());
                
                // Executing the statement
                statement.executeUpdate();
                
                System.out.println("User added Successfully");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
        }
        return "thankyou"; // Assuming you have an index.html in your templates
    }
}
