package com.dl.common.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="dl_user_room")
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DlUserRoom extends BaseEntity{

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "userid")
	private Integer userid;
	
	@Column(name = "roomid")
	private Integer roomid;

	@Column(name = "state")
	private Integer state;

}
