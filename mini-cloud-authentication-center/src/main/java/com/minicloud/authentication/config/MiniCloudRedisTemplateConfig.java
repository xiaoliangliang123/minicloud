

package com.minicloud.authentication.config;
import io.lettuce.core.ReadFrom;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;
import lombok.AllArgsConstructor;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * RedisTemplate  配置
 *
 * @author alan.wang
 */


@AllArgsConstructor
@AutoConfigureBefore(RedisAutoConfiguration.class)
public class  MiniCloudRedisTemplateConfig {



	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
		redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		return redisTemplate;
	}

	@Bean
	public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {

		StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
		stringRedisTemplate.setKeySerializer(new StringRedisSerializer());
		stringRedisTemplate.setConnectionFactory(redisConnectionFactory);
		return stringRedisTemplate;
	}


	@Autowired
	RedisProperties redisProperties;

	@Bean
	public GenericObjectPoolConfig poolConfig() {
		GenericObjectPoolConfig config = new GenericObjectPoolConfig();
		config.setMinIdle(redisProperties.getLettuce().getPool().getMinIdle());
		config.setMaxIdle(redisProperties.getLettuce().getPool().getMaxIdle());
		config.setMaxTotal(redisProperties.getLettuce().getPool().getMaxActive());
		config.setMaxWaitMillis(redisProperties.getLettuce().getPool().getMaxWait().toMillis());
		return config;
	}


	/**
	 * sentinel 哨兵模式configuration
	 *
	 * */
	@Bean
	@ConditionalOnProperty(value = "spring.redis.mode",havingValue = "sentinel")
	public RedisSentinelConfiguration redisConfigurationModeSentinel() {
		RedisSentinelConfiguration redisConfig = new RedisSentinelConfiguration();
		redisConfig.setMaster(redisProperties.getSentinel().getMaster());
		if(redisProperties.getSentinel().getNodes()!=null) {
			List<RedisNode> sentinelNode=new ArrayList<RedisNode>();
			for(String sen : redisProperties.getSentinel().getNodes()) {
				String[] arr = sen.split(":");
				sentinelNode.add(new RedisNode(arr[0],Integer.parseInt(arr[1])));
			}
			redisConfig.setDatabase(redisProperties.getDatabase());
			redisConfig.setPassword(redisProperties.getPassword());
			redisConfig.setSentinelPassword(redisConfig.getPassword());
			redisConfig.setSentinels(sentinelNode);
		}
		return redisConfig;
	}

	/**
	 * singleten单机 模式configuration
	 *
	 * */
	@Bean
	@ConditionalOnProperty(value = "spring.redis.mode",havingValue = "singleten")
	public RedisStandaloneConfiguration redisConfigurationModeSingleten() {

		RedisStandaloneConfiguration standaloneConfiguration = new RedisStandaloneConfiguration();
		standaloneConfiguration.setDatabase(redisProperties.getDatabase());
		standaloneConfiguration.setHostName(redisProperties.getHost());
		standaloneConfiguration.setPassword(redisProperties.getPassword());
		standaloneConfiguration.setPort(redisProperties.getPort());
		return standaloneConfiguration;

	}

	/**
	 * cluster 模式configuration
	 *
	 * */
	@Bean
	@ConditionalOnProperty(value = "spring.redis.mode",havingValue = "cluster")
	public RedisClusterConfiguration redisClusterConfigurationModeCluster() {

		RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration(redisProperties.getCluster().getNodes());
		redisClusterConfiguration.setPassword(redisProperties.getPassword());
		return redisClusterConfiguration;
	}

	/**
	 * singleten单机 模式redisConnectionFactory
	 *
	 */
	@Bean("redisConnectionFactory")
	@ConditionalOnProperty(value = "spring.redis.mode",havingValue = "singleten")
	public LettuceConnectionFactory redisConnectionFactoryModeSingleten(@Qualifier("poolConfig") GenericObjectPoolConfig config,
																		RedisStandaloneConfiguration redisStandaloneConfiguration) {//注意传入的对象名和类型RedisSentinelConfiguration
		LettuceClientConfiguration clientConfiguration = LettucePoolingClientConfiguration.builder().poolConfig(config).build();
		return new LettuceConnectionFactory(redisStandaloneConfiguration, clientConfiguration);
	}


	/**
	 * sentinel哨兵 模式redisConnectionFactory
	 *
	 */
	@Bean("redisConnectionFactory")
	@ConditionalOnProperty(value = "spring.redis.mode",havingValue = "sentinel")
	public LettuceConnectionFactory redisConnectionFactoryModeSentinel(@Qualifier("poolConfig") GenericObjectPoolConfig config,
														 RedisSentinelConfiguration redisConfig) {//注意传入的对象名和类型RedisSentinelConfiguration
		LettuceClientConfiguration clientConfiguration = LettucePoolingClientConfiguration.builder().poolConfig(config).build();
		return new LettuceConnectionFactory(redisConfig, clientConfiguration);
	}


	/**
	 * cluster 模式redisConnectionFactory
	 *
	 */
	@Bean("redisConnectionFactory")
	@ConditionalOnProperty(value = "spring.redis.mode",havingValue = "cluster")
	public LettuceConnectionFactory redisConnectionFactory(RedisClusterConfiguration redisClusterConfiguration) {

		ClusterTopologyRefreshOptions clusterTopologyRefreshOptions = ClusterTopologyRefreshOptions.builder()
				.enablePeriodicRefresh()
				.enableAllAdaptiveRefreshTriggers()
				.refreshPeriod(Duration.ofSeconds(5))
				.build();
		ClusterClientOptions clusterClientOptions = ClusterClientOptions.builder()
				.topologyRefreshOptions(clusterTopologyRefreshOptions).build();
		LettuceClientConfiguration lettuceClientConfiguration = LettuceClientConfiguration.builder()
				.readFrom(ReadFrom.REPLICA_PREFERRED)
				.clientOptions(clusterClientOptions).build();
		return new LettuceConnectionFactory(redisClusterConfiguration, lettuceClientConfiguration);
	}



}
