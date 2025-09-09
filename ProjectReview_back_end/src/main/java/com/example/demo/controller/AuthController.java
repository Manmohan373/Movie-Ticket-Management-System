package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ServiceResponse;
import com.example.demo.entity.User;
import com.example.demo.exception.UsernameNotFoundException;
import com.example.demo.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/TicketBooking")
@CrossOrigin(origins = "http://localhost:4200/")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;
    
    @PostMapping("/getAccessToken")
    public ResponseEntity<ServiceResponse> getAccessToken( @RequestBody User user) throws UsernameNotFoundException {
	ServiceResponse result = authService.getAccessToken(user);
	return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
