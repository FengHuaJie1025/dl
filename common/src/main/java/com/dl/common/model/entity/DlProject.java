package com.dl.common.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="dl_project")
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class DlProject extends BaseEntity {

	@Id
    @Column(name = "id", unique = true, nullable = false, precision = 22, scale = 0)
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name="projectname",nullable = false)
	private String projectName;
	
	@Column(name="company",nullable = false)
	private String company;
	
	@Column(name="linkman",nullable = false)
	private String linkMan;
	
	@Column(name="linkphone",nullable = false)
	private String linkPhone;

	@Column(name="areanum")
	private Integer areaNum;

}
