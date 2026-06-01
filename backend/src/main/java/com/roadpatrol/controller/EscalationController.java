package com.roadpatrol.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.roadpatrol.service.EscalationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/escalation")
public class EscalationController {

    private final EscalationService escalationService;

    @PostMapping("/run")
    public String runEscalation() {

        escalationService.checkAndEscalateIssues();

        return "Escalation executed";
    }
}
