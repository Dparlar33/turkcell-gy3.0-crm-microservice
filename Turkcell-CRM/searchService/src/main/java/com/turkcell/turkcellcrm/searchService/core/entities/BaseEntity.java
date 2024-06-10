package com.turkcell.turkcellcrm.searchService.core.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@Document
public class BaseEntity {

    @Id
    private ObjectId id;

    @CreatedDate
    @Field("createdDate")
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Field("updatedDate")
    private LocalDateTime updatedDate;

    @Field("deletedDate")
    private LocalDateTime deletedDate;

    public BaseEntity(){
        this.createdDate = LocalDateTime.now();
        this.updatedDate = LocalDateTime.now();

    }

}