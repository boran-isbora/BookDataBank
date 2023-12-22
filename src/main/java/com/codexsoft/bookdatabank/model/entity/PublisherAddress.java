package com.codexsoft.bookdatabank.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "PUBLISHER_ADDRESSES")
@Getter
@Setter
public class PublisherAddress extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long publisherAddressId;
    private String address;
    private String city;
    private String country;
}
