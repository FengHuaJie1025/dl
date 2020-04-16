package com.dl.common.model.entity.test;
 
import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "company")
public class CompanyEntity {
    private String companyId;
    private String companyName;

    @Id
    @Column(name = "company_id")
    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    @Basic
    @Column(name = "company_name")
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompanyEntity that = (CompanyEntity) o;
        return Objects.equals(companyId, that.companyId) &&
                Objects.equals(companyName, that.companyName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyId, companyName);
    }

    @Override
    public String toString() {
        return "公司：{" +
                "公司编号='" + companyId + '\'' +
                ", 公司名称='" + companyName + '\'' +
                '}';
    }
}