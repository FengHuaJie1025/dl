package com.dl.common.model.entity.test;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * 业务需求，根据公司编号查询学生信息
 *
 * 其实没什么技术难点，唯一需要注意的就是JPA方法的命名：
 *
 * 在JPA的级联查询中，find/get/rebdBy后跟的是本身的属性名，之后是关联实体类的属性名，
 *
 * 比如，根据公司编号查询学生信息
 *
 * getByCompanyCompanyID
 *
 * getBy:jpa方法命名规范
 *
 * company:学生实体类的company属性
 *
 * companyId :公司实体类的companyId属性
 */
@Entity
@Table(name = "student")
public class StudentEntity implements Serializable {
    private String id;
    private String name;
    private String sex;
    private String remark;

    private CompanyEntity company;

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "sex")
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Basic
    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @ManyToOne
    @JoinColumn(name = "company_id")
    public CompanyEntity getCompany() {
        return company;
    }

    public void setCompany(CompanyEntity company) {
        this.company = company;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentEntity that = (StudentEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(sex, that.sex) &&
                Objects.equals(remark, that.remark);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, sex, remark);
    }

    @Override
    public String toString() {
        return "学生{" +
                "学号='" + id + '\'' +
                ", 姓名='" + name + '\'' +
                ", 性别='" + sex + '\'' +
                ", 备注='" + remark + '\'' +
                ", 单位=" + company +
                '}';
    }
}