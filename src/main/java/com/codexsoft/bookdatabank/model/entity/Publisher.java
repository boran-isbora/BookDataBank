package com.codexsoft.bookdatabank.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "PUBLISHERS")
@Getter
@Setter
public class Publisher extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long publisherId;
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    private PublisherAddress publisherAddress;
}
