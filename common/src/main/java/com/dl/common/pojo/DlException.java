package com.dl.common.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
@EqualsAndHashCode
public class DlException {

    private String exceptionMsg;

    private Date time;
}
