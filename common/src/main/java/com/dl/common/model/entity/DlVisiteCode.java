package com.dl.common.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="dl_visite_code")
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class DlVisiteCode extends BaseEntity{

	@Id
    @Column(name = "id", unique = true, nullable = false, precision = 22, scale = 0)
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name="v_code",nullable = false)
	private String vCode;
	
	//0一次性 1当天可用
	@Column(name="v_code_type",nullable = false)
	private Integer vCodeType;
	
	@Column(name="v_name")
	private String vName;
	
	@Column(name="v_phone")
	private String vPhone;

	//访客说明
	@Column(name="v_mark")
	private String vMark;

	/**
	 * 被访用户
	 */
	@Column(name="userid")
	private Integer userid;

	@Column(name="u_room_id",nullable = false)
	private Integer uRoomId;
	
	@Transient
	private Integer pushed;

}
