package com.example.questionnaire.config;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Caffeine;


@EnableAutoConfiguration //Spring framework ���Ψ��X�ʽw�s������
						//�e���ܤ֭n���@��CacheManager��Bean
@Configuration //�t�m���]�w��,���Spring Boot�U��
public class CaffeineCaheConfig {
	
	@Bean
	public CacheManager cacheManager() {
		CaffeineCacheManager  cacheManager  = new CaffeineCacheManager();
		cacheManager.setCaffeine(Caffeine.newBuilder()
				//�]�w�L���ɶ�   1.�̫�@���g�J��   2.�X�ݫ�T�w�ɶ�
				.expireAfterAccess(600,TimeUnit.SECONDS)
				//��l�w�s�Ŷ��j�p
				.initialCapacity(100)
				//�w�s���̤j����
				.maximumSize(500)
				
				
				);
		
		
		return cacheManager;
	}
	
	
}
