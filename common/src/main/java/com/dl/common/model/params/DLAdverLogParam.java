package com.dl.common.model.params;

import lombok.*;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 *广告日志查询参数
 *
 * @NotNull：用在基本类型上，不能为null，但可以为空字符串
 *
 * @NotEmpty：用在集合类上，不能为null，并且长度必须大于0
 *
 * @NotBlank：只能作用在String上，不能为null，而且调用trim()后，长度必须大于0
 *
 * @NonNull：在方法或构造函数的参数上使用，生成一个空值检查语句
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class DLAdverLogParam {

	private Integer adid;
	
	private Integer advertiserid;

	private Integer userid;
	
	private Integer showTime;
	
	private Integer browsingTime;
	
	private Boolean click;
	
	private Boolean mark;

}
