package com.roadpatrol.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.roadpatrol.service.EscalationService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EscalationScheduler {

    private final EscalationService escalationService;

    @Scheduled(fixedRate = 3600000) // every 1 hour
    public void runEscalation() {
        escalationService.checkAndEscalateIssues();
    }
}