package com.dl.common.model.entity;

import lombok.*;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

@Entity
@Table(name="dl_notice")
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class DlNotice extends BaseEntity{

    @Id
    @Column(name = "id", unique = true, nullable = false, precision = 22, scale = 0)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    //类型（1小区通知 2系统通知 3站内通知）
    @Column(name="type",nullable = false)
    private Integer type;

    @Column(name="title",nullable = false)
    private String title;

    @Column(name="msg",nullable = false)
    private String msg;

    //推送方式（1消息栏 2消息栏+APP弹窗）
    @Column(name="method")
    private Integer method;

    @Transient
    private Boolean read;

}
