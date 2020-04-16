package com.dl.common.pojo;

import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 本地缓存工具类
 * LFU (Least frequently used) 最不经常使用
 */
public class LFUCache<K, V> {

    private ConcurrentHashMap<Object, DlCache> concurrentHashMap;

    final int size;


    public LFUCache(int capacity) {
        this.size = capacity;
        this.concurrentHashMap = new ConcurrentHashMap<>(capacity);
        new Thread(new TimeoutTimerThread()).start();
    }

    /**
     * 获取缓存
     *
     * @param key
     * @return
     */
    public Object get(K key) {
        checkNotNull(key);
        if (concurrentHashMap.isEmpty()) {
            return null;
        }
        if (!concurrentHashMap.containsKey(key)) {
            return null;
        }
        DlCache DlCache = concurrentHashMap.get(key);
        if (DlCache == null) {
            return null;
        }
        DlCache.setHitCount(DlCache.getHitCount()+1);
        DlCache.setAccessTime(System.currentTimeMillis());
        return DlCache.getValue();
    }

    /**
     * 添加缓存
     *
     * @param key
     * @param value
     */
    public void put(K key, V value,long expire) {
        checkNotNull(key);
        checkNotNull(value);
        // 当缓存存在时，更新缓存
        if (concurrentHashMap.containsKey(key)){
            DlCache DlCache = concurrentHashMap.get(key);
            DlCache.setHitCount(DlCache.getHitCount()+1);
            DlCache.setWriteTime(System.currentTimeMillis());
            DlCache.setAccessTime(System.currentTimeMillis());
            DlCache.setExpireTime(expire);
            DlCache.setValue(value);
            return;
        }
        // 已经达到最大缓存
        if (isFull()) {
            Object kickedKey = getKickedKey();
            if (kickedKey !=null){
                // 移除最少使用的缓存
                concurrentHashMap.remove(kickedKey);
            }else {
                return;
            }
        }
        DlCache DlCache = new DlCache();
        DlCache.setKey(key);
        DlCache.setValue(value);
        DlCache.setWriteTime(System.currentTimeMillis());
        DlCache.setAccessTime(System.currentTimeMillis());
        DlCache.setHitCount(1);
        DlCache.setExpireTime(expire);
        concurrentHashMap.put(key, DlCache);
    }

    /**
     * 检测字段是否合法
     *
     * @param reference
     * @param <T>
     * @return
     */
    public static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }

    /**
     * 判断是否达到最大缓存
     *
     * @return
     */
    private boolean isFull() {
        return concurrentHashMap.size() == size;
    }

    /**
     * 获取最少使用的缓存
     * @return
     */
    private Object getKickedKey() {
        DlCache min = Collections.min(concurrentHashMap.values());
        return min.getKey();
    }




    /**
     * 处理过期缓存
     */
    class TimeoutTimerThread implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(60);
                    expireDlCache();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        /**
         * 创建多久后，缓存失效
         *
         * @throws Exception
         */
        private void expireDlCache() throws Exception {
            System.out.println("检测缓存是否过期缓存");
            for (Object key : concurrentHashMap.keySet()) {
                DlCache DlCache = concurrentHashMap.get(key);
                long timoutTime = TimeUnit.NANOSECONDS.toSeconds(System.nanoTime()
                        - DlCache.getWriteTime());
                if (DlCache.getExpireTime() > timoutTime) {
                    continue;
                }
                System.out.println(" 清除过期缓存 ： " + key);
                //清除过期缓存
                concurrentHashMap.remove(key);
            }
        }
    }

}


