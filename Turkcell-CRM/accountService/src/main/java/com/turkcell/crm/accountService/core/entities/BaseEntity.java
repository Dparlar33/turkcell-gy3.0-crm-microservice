package com.turkcell.crm.accountService.core.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@MappedSuperclass
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "createdDate")
    private LocalDateTime createdDate;

    @Column(name = "updatedDate")
    private LocalDateTime updatedDate;

    @Column(name = "deletedDate")
    private LocalDateTime deletedDate;
    public BaseEntity(){
        this.createdDate = LocalDateTime.now();
        this.updatedDate = LocalDateTime.now();
    }
}
