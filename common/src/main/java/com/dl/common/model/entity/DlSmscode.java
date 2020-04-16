package com.dl.common.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 短信验证码表
 */

@Entity
@Table(name = "dl_smscode")
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class DlSmscode  extends BaseEntity{

    @Id
    @Column(name = "id", unique = true, nullable = false, precision = 22, scale = 0)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="smscode",nullable = false)
    private String smsCode;
    @Column(name="value_time",nullable = false)
    private Date valueTime;
    @Column(name="phone",nullable = false)
    private String phone;

}
