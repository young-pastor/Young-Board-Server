
package com.zhisida.board.result;

import cn.hutool.core.lang.Dict;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 用户员工信息
 *
 * @author young-pastor
 */
@Data
public class SysEmpInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 工号
     */
    private String jobNum;

    /**
     * 所属机构id
     */
    private Long orgId;

    /**
     * 所属机构名称
     */
    private String orgName;

    /**
     * 附属机构与职位信息
     */
    private List<Dict> extOrgPos;

    /**
     * 职位信息
     */
    private List<Dict> positions;

}
