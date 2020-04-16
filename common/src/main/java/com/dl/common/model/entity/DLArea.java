package com.dl.common.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "dl_area")
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class DLArea extends BaseEntity {

    @Id
    @Column(name = "id", unique = true, nullable = false, precision = 22, scale = 0)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "regionid", nullable = false)
    private Integer regionid;

    @Column(name = "projectid", nullable = false)
    private Integer projectid;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "lockid", nullable = false)
    private String lockid;

    @Column(name = "address")
    private String address;

    @Column(name = "linkman")
    private String linkman;

    @Column(name = "linkphone")
    private String linkphone;

    @Column(name = "company")
    private String company;

    @Column(name = "state")
    private Integer state;        //0禁用1正常

    @Column(name = "lng")
    private String lng;

    @Column(name = "lat")
    private String lat;
}
