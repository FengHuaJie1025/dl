package com.dl.common.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

/**
 * Base entity.
 *
 * @author FHJ
 * @date 2020-2-27
 */
@Entity
@Table(name = "account")
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class Account extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "loginAccount",nullable = false,length = 20)
    private String loginAccount;

    @Column(name = "name",nullable = false,length = 20)
    private String name;

    @Column(name = "phone",nullable = false,length = 20)
    private String phone;

    @Column(name = "password",nullable = false,length = 20)
    private String password;

    @Column(name = "areaid",nullable = false,length = 20)
    private Integer areaid;

    @Override
    protected void prePersist() {
        super.prePersist();
    }
}
