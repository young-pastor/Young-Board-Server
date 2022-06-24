
package com.zhisida.board.controller;

import cn.hutool.json.JSONUtil;
import com.zhisida.board.core.annotion.BusinessLog;
import com.zhisida.board.core.cache.CacheBaseTreeNode;
import com.zhisida.board.core.enums.LogAnnotionOpTypeEnum;
import com.zhisida.board.core.pojo.response.ResponseData;
import com.zhisida.board.core.pojo.response.SuccessResponseData;
import com.zhisida.board.core.util.RedisUtil;
import com.zhisida.board.param.SysCacheParam;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;

/**
 * 系统缓存监控控制器
 *
 * @author Young-Pastor
 */
@RestController
public class SysCacheController {
    @Resource
    private RedisUtil redisUtil;

    /**
     * 系统缓存监控
     *
     * @author Young-Pastor
     */
    @GetMapping("/sysCache/keyTree")
    @BusinessLog(title = "系统缓存监控_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData keyTree(SysCacheParam sysCacheParam) {
        if (Objects.isNull(sysCacheParam.getKey())) {
            sysCacheParam.setKey("*");
        }
        if (Objects.isNull(sysCacheParam.getParentId())) {
            sysCacheParam.setParentId(0l);
        }
        Set<String> keySet = redisUtil.getInstance().keys(sysCacheParam.getKey());
        HashMap<String, CacheBaseTreeNode> keyNodeMap = new HashMap<>();
        List<CacheBaseTreeNode> rootChildrenList = new ArrayList<>();
        Random random = new Random();
        long rootParentId = sysCacheParam.getParentId();
        for (String cacheKey : keySet) {
            CacheBaseTreeNode parentNode = null;
            String parentKey = "";
            int pos = sysCacheParam.getKey().length() - 1;
            do {
                pos = cacheKey.indexOf(":", pos);
                String keyStr = cacheKey;
                if (pos >= 0) {
                    keyStr = cacheKey.substring(0, pos);
                    pos++;
                }
                CacheBaseTreeNode cacheNode = keyNodeMap.get(keyStr);
                if (cacheNode == null) {
                    Long parentKeyId = rootParentId;
                    List<CacheBaseTreeNode> parentSubNodes = null;
                    if (parentNode != null) {
                        parentKeyId = parentNode.getId();
                        parentSubNodes = parentNode.getChildren();
                    }
                    cacheNode = new CacheBaseTreeNode();
                    cacheNode.setId(random.nextLong());
                    cacheNode.setParentId(parentKeyId);
                    String title = keyStr.replaceAll(parentKey, "");
                    title = title.startsWith(":") ? title.substring(1) : title;
                    cacheNode.setTitle(title);
                    if (pos > 0) {
                        cacheNode.setKey(keyStr + "*");
                    } else {
                        cacheNode.setKey(keyStr);
                    }
                    if (parentNode != null) {
                        if (parentSubNodes == null) {
                            parentSubNodes = new ArrayList<>();
                        }
                        parentSubNodes.add(cacheNode);
                        parentNode.setChildren(parentSubNodes);
                    } else {
                        rootChildrenList.add(cacheNode);
                    }
                    keyNodeMap.put(keyStr, cacheNode);
                }
                parentNode = cacheNode;
                parentKey = keyStr;
            } while (pos >= 0);
        }
        return new SuccessResponseData(rootChildrenList);
    }

    @PostMapping("/sysCache/queryValue")
    @BusinessLog(title = "系统缓存监控_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryValue(@RequestBody SysCacheParam sysCacheParam) {
        SysCacheParam keyValue = new SysCacheParam();
        keyValue.setTime(redisUtil.getExpire(sysCacheParam.getKey()));
        keyValue.setTimeUnit("MILLISECONDS");
        keyValue.setValue(JSONUtil.toJsonStr(redisUtil.get(sysCacheParam.getKey())));
        keyValue.setKey(sysCacheParam.getKey());
        return new SuccessResponseData(keyValue);
    }

    @PostMapping("/sysCache/add")
    @BusinessLog(title = "系统缓存监控_添加", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody SysCacheParam sysCacheParam) {
        if (!Objects.isNull(sysCacheParam.getTime()) && sysCacheParam.getTime() >= 0) {
            redisUtil.setForTimeCustom(sysCacheParam.getKey(), sysCacheParam.getValue(), sysCacheParam.getTime(), sysCacheParam.getCacheTimeUnit());
        } else {
            redisUtil.set(sysCacheParam.getKey(), sysCacheParam.getValue());
        }
        return new SuccessResponseData();
    }

    @PostMapping("/sysCache/delete")
    @BusinessLog(title = "系统缓存监控_添加", opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody SysCacheParam sysCacheParam) {
        redisUtil.delete(sysCacheParam.getKey());
        return new SuccessResponseData();
    }

    @PostMapping("/sysCache/modify")
    @BusinessLog(title = "系统缓存监控_添加", opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData modify(@RequestBody SysCacheParam sysCacheParam) {
        String cacheKey = sysCacheParam.getKey();
        if (!StringUtils.isEmpty(sysCacheParam.getOldKey())) {
            cacheKey = sysCacheParam.getOldKey();
        }
        if (Objects.isNull(sysCacheParam.getTime())) {
            redisUtil.set(cacheKey, sysCacheParam.getValue());
        } else {
            redisUtil.setForTimeCustom(cacheKey, sysCacheParam.getValue(), sysCacheParam.getTime(), sysCacheParam.getCacheTimeUnit());
        }
        if (!StringUtils.isEmpty(sysCacheParam.getOldKey())) {
            redisUtil.rename(cacheKey, sysCacheParam.getKey());
        }
        return new SuccessResponseData();
    }

    @GetMapping("/sysCache/clientsInfo")
    @BusinessLog(title = "系统缓存监控_添加", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData clientsInfo() {
        return new SuccessResponseData(redisUtil.getInstance().getClientList());
    }
}