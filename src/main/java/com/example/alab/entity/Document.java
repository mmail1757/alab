package com.example.alab.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "DOCUMENT")
@Getter
@Setter
public class Document {

    @Id
    private Integer id;

    @Column(name = "doc_type")
    private String docType;

    @Column(name = "doc_num")
    private String docNum;

    @ManyToOne
    @JoinColumn(name="person_id", nullable=false)
    private Person person;
}
