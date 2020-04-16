package com.dl.common.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "dl_applys")
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class DlApplys extends BaseEntity {

    @Id
    @Column(name = "id", unique = true, nullable = false, precision = 22, scale = 0)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    //0主动授权 1用户申请
    @Column(name = "type")
    private Integer type;

    @Column(name = "roomid", nullable = false)
    private Integer roomid;

    @Column(name = "userid")
    private Integer userId;

    @Column(name = "state")
    private ApplyState state;

    @Column(name = "famtype")
    private Integer famtype;

    @Column(name = "agreetime")
    private String agreetime;

    @Column(name = "agreeman")
    private String agreeman;

    @Column(name = "valuetime")
    private String valuetime;

    //0管理员审核 1物业经理审核
    @Column(name = "authtype")
    private Integer authtype;

    //来源（0业主添加 ，1 用户申请，2 人工录入，3登录匹配，4批量授权  5管理员添加 6物业经理添加）
    @Column(name = "source")
    private Integer source;

    public enum ApplyState {
		/**
		 * 申请中
		 */
		APPLYING(0),
		/**
		 * 同意
		 */
		AGREE(1),
		/**
		 * 不同意
		 */
		DISAGREE(2),
		/**
		 * 已过期
		 */
		OVERDATE(3);

        private Integer value;

        ApplyState(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    public enum Famtype {
		/**
		 * 业主
		 */
		OWNER(1),
		/**
		 * 家属
		 */
        DEPENDENTS(2),
		/**
		 * 租客
		 */
        TENANT(3);

        private Integer value;

        Famtype(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }
}
