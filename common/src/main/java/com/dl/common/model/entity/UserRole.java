package com.dl.common.model.entity;

import lombok.*;
import javax.persistence.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;

@Entity
@Table(name="user_role")
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRole  extends BaseEntity implements Serializable {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "userid")
	private Integer userid;
	
	@Column(name = "roleid")
	private Integer roleid;

}
