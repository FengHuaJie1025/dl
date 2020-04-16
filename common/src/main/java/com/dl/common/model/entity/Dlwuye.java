package com.dl.common.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="dl_wuye")
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Dlwuye  extends BaseEntity{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@Column(name = "name")
	private String name;

	@Column(name = "phone")
	private String phone;

	@Column(name = "password")
	private String password;

	@Column(name = "imgurl")
	private String imgurl;

	@Column(name = "usertype")
	private Integer usertype;

}
