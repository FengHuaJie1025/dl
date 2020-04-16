package com.dl.common.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="dl_room")
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DLRoom  extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	
	@Column(name="gateid", unique = true)
	private Integer gateid;

	@Column(name="room_name")
	private String roomName;

	@Transient
	private String hexkey;
}
                                                            