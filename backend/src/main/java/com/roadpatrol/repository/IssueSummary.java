package com.roadpatrol.repository;

import java.util.UUID;

public interface IssueSummary {

    UUID getId();
    String getTitle();
    Double getLatitude();
    Double getLongitude();
    Float getPriorityScore();
}
