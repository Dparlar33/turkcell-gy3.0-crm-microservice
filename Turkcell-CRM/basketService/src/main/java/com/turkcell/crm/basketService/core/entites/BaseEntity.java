package com.turkcell.crm.basketService.core.entites;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Data
public class BaseEntity {

    @Id
    private String id;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime updatedDate;


    private LocalDateTime deletedDate;

    public BaseEntity(){
        this.createdDate = LocalDateTime.now();
        this.updatedDate = LocalDateTime.now();
        this.id= UUID.randomUUID().toString();
    }

}
