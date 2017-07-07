package com.mmall.common;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import jdk.internal.org.objectweb.asm.tree.TryCatchBlockNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by Keane on 7/7/17.
 */
public class TokenCache {
    private static Logger logger = LoggerFactory.getLogger(TokenCache.class);
    private static LoadingCache<String, String> localCache =
        CacheBuilder.newBuilder().initialCapacity(1000).maximumSize(10000)
            .expireAfterAccess(12, TimeUnit.HOURS).build(new CacheLoader<String, String>() {
            //默认加载的数据加载实现，当调用get取值，如果key没有对应的至就调用这个方法进行加载

            @Override public String load(String s) throws Exception {
                return "null";
            }
        });
    //1000是Google gua wa里面自带的缓存的初始化容量超过后会使用lru算法

    public static void setKey(String key, String value) {
        localCache.put(key, value);

    }

    public static String getKey(String key) {
        String value = null;
        try {
            value = localCache.get(key);
            if ("null".equals(value)) {
                return null;
            }
            return value;
        } catch (Exception e) {
            logger.error("localCache get error", e);

        }
        return null;

    }

}



