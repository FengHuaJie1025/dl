package com.dl.common.model.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="dl_openlog")
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class DlOpenlog  extends BaseEntity implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@Column(name="userid", unique = true)
	private Integer userid;
	
	@Column(name = "opentime", unique = true)
	private String opentime;
	
	@Column(name = "pid")
	private String pid;

	@Column(name = "msg")
	private String msg;
	
	@Column(name = "os")
	private String os;
	
	@Column(name = "rssi")
	private Integer rssi;
	
	@Column(name = "model")
	private String model;
	
	@Column(name = "state")
	private Integer state;
	
	@Column(name = "version")
	private String version;
	
	//来源（source取值 1住户版 2物业版 ）
	@Column(name = "source")
	private Integer source;
	
}
