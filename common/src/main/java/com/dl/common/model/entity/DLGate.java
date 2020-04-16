package com.dl.common.model.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="dl_gate")
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class DLGate extends BaseEntity implements Serializable{


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	
	@Column(name="areaid", unique = true)
	private Integer areaid;
	
	@Column(name="name", unique = true)
	private String name;
	
	//门栋类型(1 公开，2 私有，3半公有)
	@Column(name="gate_type", unique = true)
	private Integer gateType;
	
}
