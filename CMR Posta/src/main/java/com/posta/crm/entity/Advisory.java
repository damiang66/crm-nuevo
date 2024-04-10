
package com.posta.crm.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;

import jakarta.validation.constraints.NotBlank;
import java.sql.Timestamp;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Data
public class Advisory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Client client;
    @NotBlank
    @Lob
    @Column(length = 2500)
    private String advisory;
    @ManyToOne
    private User user;
    @CreationTimestamp
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm", timezone = "America/Bogota")
    private Timestamp regdate;
    @UpdateTimestamp
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm", timezone = "America/Bogota")
    private Timestamp updatedate;
}
