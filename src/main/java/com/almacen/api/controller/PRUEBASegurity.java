package com.almacen.api.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PRUEBASegurity {

    @GetMapping("/admin/test")
    public String admin() {
        return "Acceso ADMIN OK";
    }

    @GetMapping("/manager/test")
    public String manager() {
        return "Acceso MANAGER OK";
    }

    @GetMapping("/operator/test")
    public String operator() {
        return "Acceso OPERATOR OK";
    }

    @GetMapping("/auditor/test")
    public String auditor() {
        return "Acceso AUDITOR OK";
    }

    @GetMapping("/yo")
    public Object me(Authentication auth) {
        return auth;
    }
}
