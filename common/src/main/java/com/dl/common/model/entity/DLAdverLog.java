package com.dl.common.model.entity;

import lombok.*;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
/**
 *广告日志 
 *
 */
@Entity
@Table(name="dl_adverlog")
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class DLAdverLog extends BaseEntity{

	@Id
    @Column(name = "id", unique = true, nullable = false, precision = 22, scale = 0)
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name="adid", nullable = false)
	private Integer adid;
	
	@Column(name="advertiserid")
	private Integer advertiserid;

	@Column(name="userid")
	private Integer userid;
	
	//展示时间
	@Column(name="showTime")
	private Integer showTime;
	
	//浏览时间
	@Column(name="browsingTime")
	private Integer browsingTime;
	
	//是否点击（0否 1是）
	@Column(name="click")
	private Boolean click;
	
	//是否登记（0否 1是）
	@Column(name="mark")
	private Boolean mark;

}
