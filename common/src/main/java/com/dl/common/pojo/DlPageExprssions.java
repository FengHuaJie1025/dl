package com.dl.common.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

import java.util.ArrayList;

@Data
@ToString
@EqualsAndHashCode
public class DlPageExprssions {

    private int pageNum;
    private int pageSize = 20;
    private ArrayList<Criterion> exprssions;
    private ArrayList<Order> orders;

}
