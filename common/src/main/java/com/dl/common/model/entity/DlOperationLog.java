package com.dl.common.model.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="dl_operationlog")
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DlOperationLog  extends BaseEntity implements Serializable{

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@Column(name="account", unique = true)
	private String account;
	
	@Column(name = "operate_time", unique = true)
	private Date operateTime;
	
	@Column(name = "param")
	private String param;

	@Column(name = "ip_address")
	private String ipAddress;
	
	@Column(name = "msg")
	private String msg;
	
}
