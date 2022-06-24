
package com.zhisida.board.param;

import com.zhisida.board.core.pojo.base.param.BaseParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 系统字典值参数
 *
 * @author Young-Pastor
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysAreaParam extends BaseParam {

    /**
     * 主键
     */
    private Long id;

    /**
     * 层级
     */
    private Integer levelCode;

    /**
     * 父级行政代码
     */
    private String parentCode;

    /**
     * 行政代码
     */
    private String areaCode;

    /**
     * 邮政编码
     */
    private String zipCode;

    /**
     * 区号
     */
    private String cityCode;

    /**
     * 名称
     */
    private String name;

    /**
     * 简称
     */
    private String shortName;

    /**
     * 组合名
     */
    private String mergerName;

    /**
     * 拼音
     */
    private String pinyin;

    /**
     * 经度
     */
    private BigDecimal lng;

    /**
     * 纬度
     */
    private BigDecimal lat;
}
