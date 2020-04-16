package com.dl.common.model.entity;

import lombok.*;
import javax.persistence.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;

@Entity
@Table(name="role")
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role  extends BaseEntity implements Serializable{

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

}
