package com.dl.common.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

/**
 * Base entity.
 *
 * @author FHJ
 * @date 2020-2-27
 */
@Data
@ToString
@MappedSuperclass
@EqualsAndHashCode
public class BaseEntity {

    /**
     * create time
     */
    @Column(name = "create_time", columnDefinition = "timestamp default CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    /**
     * create man
     */
    @Column(name = "create_man")
    private String createMan;

    /**
     * update time
     */
    @Column(name = "update_time", columnDefinition = "timestamp default CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    /**
     * update man
     */
    @Column(name = "update_man")
    private String updateMan;

    /**
     * Delete flag.
     */
    @Column(name = "deleted", columnDefinition = "TINYINT default 0")
    private Boolean deleted = false;

    @PrePersist
    protected void prePersist() {
        deleted = false;
        Date now = new Date();
        if (createTime == null) {
            createTime = now;
        }
        if (updateTime == null) {
            updateTime = now;
        }
    }

    @PreUpdate
    protected void preUpdate() {
        updateTime = new Date();
    }

    @PreRemove
    protected void preRemove() {
        updateTime = new Date();
    }
}