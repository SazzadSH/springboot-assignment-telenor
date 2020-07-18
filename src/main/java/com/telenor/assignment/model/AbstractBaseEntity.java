package com.telenor.assignment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.Date;


@MappedSuperclass
public @Data
abstract class AbstractBaseEntity {

    @JsonIgnore
    @Column(name = "created_dt", columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT now()", nullable = false, updatable = false)
    private OffsetDateTime createdDateTime;

    @JsonIgnore
    @Column(name = "modified_dt", columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT now()", nullable = false)
    private OffsetDateTime modifiedDateTime;

    @PrePersist
    void prePersist() {

        setCreatedDateTime(OffsetDateTime.now());
        setModifiedDateTime(OffsetDateTime.now());
    }

    @PreUpdate
    void preUpdate() {
        setModifiedDateTime(OffsetDateTime.now());
    }
}
