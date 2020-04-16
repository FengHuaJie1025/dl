package com.dl.common.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="dl_equipment")
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class DlEquipment extends BaseEntity{

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "pid", nullable = false)
    private String pid;

    @Column(name = "lockid")
    private String lockId;

    @Column(name = "devicename", nullable = false)
    private String devicename;

    @Column(name = "gateid", nullable = false)
    private Integer gateid;

    @Transient
    private String hexkey;
}
