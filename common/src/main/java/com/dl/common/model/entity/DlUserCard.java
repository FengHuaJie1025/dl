package com.dl.common.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="dl_user_card")
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DlUserCard extends BaseEntity{

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "userid")
	private Integer userid;
	
	@Column(name = "cardid")
	private String cardid;

	@Column(name = "state")
	private Integer state;

}
