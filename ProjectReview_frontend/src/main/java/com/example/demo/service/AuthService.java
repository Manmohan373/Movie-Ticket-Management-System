package com.example.demo.service;

import com.example.demo.dto.ServiceResponse;
import com.example.demo.entity.User;
import com.example.demo.exception.UsernameNotFoundException;

public interface AuthService {

    ServiceResponse getAccessToken(User user) throws UsernameNotFoundException;

}
