package com.moon.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Author : moon
 * Date  : 2018/12/21 15:30
 * Description : Class for
 */
@Component
public class RedisUtil {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    private static final Logger LOG = LoggerFactory.getLogger(RedisUtil.class);

//    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
//        this.redisTemplate = redisTemplate;
//    }


    /**************************************************************************
     *                         String类型操作                                 *
     *************************************************************************/

    /**
     * 获取value
     *
     * @param key
     * @return
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 设置键值
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            LOG.error(e.getMessage());
            return false;
        }
    }

    /**
     * 设置键值与过期时间
     *
     * @param key   键
     * @param value 值
     * @param time  过期时间,若小于0,则无期限
     * @return
     */
    public boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            LOG.error(e.getMessage());
            return false;
        }
    }

    /**
     * 递增
     *
     * @param key 键
     * @param inc 递增值
     * @return
     */
    public long incr(String key, long inc) {
        if (inc < 0) {
            throw new RuntimeException("Redis递增值需大于0");
        }
        return redisTemplate.opsForValue().increment(key, inc);
    }

    /**
     * 递减
     *
     * @param key 键
     * @param dec 递减值
     * @return
     */
    public long decr(String key, long dec) {
        if (dec < 0) {
            throw new RuntimeException("Redis递减值需大于0");
        }
        return redisTemplate.opsForValue().increment(key, -dec);
    }


    /**************************************************************************
     *                         hash类型操作                                 *
     *************************************************************************/
    /**
     * hashGet
     *
     * @param key  键
     * @param item 域
     * @return
     */
    public Object hget(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * hashSet
     *
     * @param keyAndItem xxx:xxx
     * @param value
     * @return
     */
    public boolean hset(String keyAndItem, Object value) {
        try {
            String[] split = keyAndItem.split(":");
            hset(split[0], split[1], value);
            return true;
        } catch (Exception e) {
            LOG.error(e.getMessage());
            return false;
        }
    }

    /**
     * hashSet
     *
     * @param key   键值
     * @param item  域
     * @param value value
     * @return
     */
    public boolean hset(String key, String item, Object value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            LOG.error(e.getMessage());
            return false;
        }
    }


    /**
     * hashGet 所有值
     *
     * @param key 键
     * @return
     */
    public Map<Object, Object> hgetAll(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * hashSet
     *
     * @param key
     * @param map
     * @return
     */
    public boolean hsetAll(String key, Map<Object, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            LOG.error(e.getMessage());
            return false;
        }
    }

    /**
     * hsetAll 并设置过期时间
     *
     * @param key  键
     * @param map
     * @param time 过期时间,秒
     * @return
     */
    public boolean hsetAll(String key, Map<Object, Object> map, long time) {
        try {
            if (hsetAll(key, map)) {
                if (time > 0) {
                    expire(key, time);
                }
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
            return false;
        }
    }


    /**
     * hashDelete
     *
     * @param keyAndItem xxxx:xxxx
     */
    public void hdel(String keyAndItem) {
        try {
            String[] split = keyAndItem.split(":");
            hdel(split[0], split[1]);
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
    }

    /**
     * hashDelete
     *
     * @param key  键
     * @param item 域
     */
    public void hdel(String key, String item) {
        redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * 判断hash表是否存在该域
     *
     * @param key
     * @param item
     * @return
     */
    public boolean hHasItem(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * hash递增
     *
     * @param keyAndItem xxx:xxx
     * @param inc
     * @return
     */
    public Long hincr(String keyAndItem, long inc) {
        try {
            String[] split = keyAndItem.split(":");
            return hincr(split[0], split[1], inc);
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return null;
    }

    /**
     * hash 递增
     *
     * @param key
     * @param item
     * @param inc
     * @return
     */
    public Long hincr(String key, String item, long inc) {
        return redisTemplate.opsForHash().increment(key, item, inc);
    }

    /**
     * hash递减
     *
     * @param keyAndItem xxx:xxx
     * @param inc
     * @return
     */
    public Long hdecr(String keyAndItem, long inc) {
        try {
            String[] split = keyAndItem.split(":");
            return hdecr(split[0], split[1], inc);
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return null;
    }

    /**
     * hash 递减
     *
     * @param key
     * @param item
     * @param dec
     * @return
     */
    public Long hdecr(String key, String item, long dec) {
        return redisTemplate.opsForHash().increment(key, item, -dec);
    }

    /**************************************************************************
     *                               Set操作                                 *
     *************************************************************************/

    /**
     * Set 添加
     *
     * @param key
     * @param vars
     * @return
     */
    public boolean Sset(String key, Object... vars) {
        try {
            redisTemplate.opsForSet().add(key, vars);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Set 返回Set大小
     *
     * @param key
     * @return
     */
    public Long Ssize(String key) {
        return redisTemplate.opsForSet().size(key);
    }


    /**
     * Set 返回集合成员
     *
     * @param key
     * @return
     */
    public Object Sall(String key) {
        return redisTemplate.opsForSet().members(key);

    }

    /**
     * Set 移除元素
     *
     * @param key
     * @param vars
     * @return
     */
    public Long Sremove(String key, Object... vars) {
        try {
            return redisTemplate.opsForSet().remove(key, vars);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            return null;
        }
    }

    /**
     * Set 差集
     * 以Key1为标准,返回的是Key1与key2的差集
     *
     * @param key1 {1,2,3,4}
     * @param key2 {1,4,5}
     * @return {2,3}
     */
    public Set<Object> Sdiff(String key1, String key2) {
        try {
            return redisTemplate.opsForSet().difference(key1, key2);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            return null;
        }
    }

    /**************************************************************************
     *                               基本操作                                 *
     *************************************************************************/

    /**
     * 键值删除
     *
     * @param key
     */
    public void del(String key) {
        redisTemplate.delete(key);
    }


    /**
     * 判断键值是否存在
     *
     * @param key
     * @return
     */
    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            return false;
        }
    }


    /**
     * 指定过期时间
     *
     * @param key  键值
     * @param time 过期时间,单位秒
     * @return
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            LOG.error(e.getMessage());
            return false;
        }
    }


    /**
     * 获取过期时间 ,单位秒
     *
     * @param key 键值
     * @return
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

}
