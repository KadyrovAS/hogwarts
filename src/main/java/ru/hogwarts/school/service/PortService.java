package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PortService {
    @Value("${server.port}")
    private String port;
    public String getPort(){
        return port;
    }
}
