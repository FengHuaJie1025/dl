package com.dl.common.model.entity;

import lombok.*;

import javax.persistence.*;

/**
 *广告管理 
 *
 */
@Entity
@Table(name="dl_adver")
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class DLAdver extends BaseEntity{

	@Id
    @Column(name = "id", unique = true, nullable = false, precision = 22, scale = 0)
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	/**
	 * 投放小区
	 */
	@Column(name="areaid", nullable = false)
	private Integer areaid;

	@Column(name="advertiserid", nullable = false)
	private Integer advertiserid;

	//广告类型（1弹窗 目前只有弹窗）
	@Column(name="type")
	private Integer type;
	
	//1启动页 2开门成功弹窗 3启动页+开门成功弹窗
	@Column(name="location")
	private Integer location;
	
	//展示次数，-1无限制
	@Column(name="number")
	private Integer number;
	
	//0待投放 1投放中 2已下线
	@Column(name="state")
	private Integer state;
	
	@Column(name="title")
	private String title;
	
	@Column(name="url")
	private String url;
	
	@Column(name="img1")
	private String img1;
	
	@Column(name="img2")
	private String img2;

}
