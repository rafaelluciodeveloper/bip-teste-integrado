package com.beneficio.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@RestController
@RequestMapping("/api/v1/test")
public class TestController {

    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping("/entities")
    public String testEntities() {
        try {
            Query query = entityManager.createQuery("SELECT COUNT(e) FROM Beneficio e");
            Long count = (Long) query.getSingleResult();
            return "Beneficio entities found: " + count;
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
