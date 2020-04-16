package com.dl.common.pojo;

import lombok.Data;

@Data
public class DlCache implements Comparable<DlCache> {

    // 键
    private Object key;
    // 缓存值
    private Object value;
    // 最后一次访问时间
    private long accessTime;
    // 创建时间
    private long writeTime;
    // 存活时间
    private long expireTime;
    // 命中次数
    private Integer hitCount;

    @Override
    public int compareTo(DlCache cache) {
        return hitCount.compareTo(cache.hitCount);
    }
}
