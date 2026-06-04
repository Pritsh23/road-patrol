package com.roadpatrol.entity;

import java.util.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "issue_images")
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class IssueImage {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "issue_id")
    private Issue issue;
   private String publicId;
    private String imageUrl;
}
