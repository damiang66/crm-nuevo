
package com.posta.crm.entity.canvas;

import com.posta.crm.entity.Client;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class CanvasModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private CustomerSegments customerSegments;
    @OneToOne
    private ValuePropositions valuePropositions;
    @OneToOne
    private Channels channels;
    @OneToOne
    private CustomerRelationships customerRelationships;
    @OneToOne
    private KeyRecources keyRecources;
    @OneToOne
    private KeyActivities keyActivities;
    @OneToOne
    private KeyPartners keyPartners;
    @OneToOne
    private RevenueStreams revenueStreams;
    @OneToOne
    private CostStructure costStructure;
    @OneToOne
    private Client client;


}
