package com.recse4cloud.core.config.redis;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

import static com.recse4cloud.core.utils.IConstants.REDIS_PROPERTY;

/**
 * redis连接配置信息
 */
@ConfigurationProperties(prefix = "redission")
@Setter
@Getter
@PropertySource(value = REDIS_PROPERTY)
public class RedissionProperty {
    /**
     * 是否集群配置redis开关
     * true，集群；false，单机
     */
    private boolean useCluster;

    /**
     * -----------------通用配置----------开始
     */

    /**
     * 添加节点地址.格式host:port,多个节点未host:port,host:port
     * 可以通过host:port的格式来添加Redis集群节点的地址。多个节点可以一次性批量添加。
     */
    private String nodeAddresses;
    /**
     * 连接空闲超时，单位：毫秒.默认值：10000.
     * 如果当前连接池里的连接数量超过了最小空闲连接数，而同时有连接空闲时间超过了该数值，那么这些连接将会自动被关闭，并从连接池里去掉。时间单位是毫秒。
     */
    private int idleConnectionTimeout = 10000;
    /**
     * 连接超时，单位：毫秒.默认值：10000.
     * 同任何节点建立连接时的等待超时。时间单位是毫秒。
     */
    private int connectTimeout = 10000;
    /**
     * 命令等待超时，单位：毫秒.默认值：3000.
     * 等待节点回复命令的时间。该时间从命令发送成功时开始计时。
     */
    private int timeout = 10000;
    /**
     * 从节点最小空闲连接数.默认值：32.
     * 服务节点用于普通操作（非 发布和订阅）的最小保持连接数（长连接）。长期保持一定数量的连接有利于提高瞬时读取反映速度。
     */
    private int connectionMinimumIdleSize = 32;
    /**
     * 从节点连接池大小.默认值：64.
     * 服务节点用于普通操作（非发布和订阅）连接的连接池最大容量。连接池的连接数量自动弹性伸缩。
     */
    private int connectionPoolSize = 64;

    /**
     * -----------------通用配置----------结束
     */

    /**
     * 单机配置，数据库名
     */
    private int database = 0;

    /**
     * -----------------集群配置----------开始
     */

    /**
     * 集群扫描间隔时间;默认值： 1000.
     * 对Redis集群节点状态扫描的时间间隔。单位是毫秒。
     */
    private int scanInterval = 1000;
    /**
     * 主节点连接池大小.默认值：64.
     * 多主节点的环境里，【每个主节点】的连接池最大容量。连接池的连接数量自动弹性伸缩。
     */
    private int masterConnectionPoolSize = 64;
    /**
     * 主节点最小空闲连接数.默认值：32.
     * 多节点的环境里，每个 主节点的最小保持连接数（长连接）。长期保持一定数量的连接有利于提高瞬时写入反应速度。
     */
    private int masterConnectionMinimumIdleSize = 32;
    /**
     * 从节点连接池大小.默认值：64.
     * 多从节点的环境里，【每个从服务节点】里用于普通操作（非发布和订阅）连接的连接池最大容量。连接池的连接数量自动弹性伸缩。
     */
    private int slaveConnectionPoolSize = 64;
    /**
     * 从节点最小空闲连接数.默认值：32.
     * 多从节点的环境里，【每个从服务节点】里用于普通操作（非 发布和订阅）的最小保持连接数（长连接）。长期保持一定数量的连接有利于提高瞬时读取反映速度。
     */
    private int slaveConnectionMinimumIdleSize = 32;
    /**
     * 用于节点身份验证的密码。
     */
    private String password;

    /**
     * -----------------集群配置----------结束
     */
}
