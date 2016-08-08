package com.smily.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ValueConfigProperties {
	@Value("${my.value}")
	private String value;
	@Value("${my.number}")
	private Integer number;
	@Value("${my.bigNumber}")
	private Long bigNumber;
	@Value("${my.test1}")
	private Integer test1;
	@Value("${my.test2}")
	private Integer test2;
	@Value("${my.config}")
	private List<String> config;
	//TODO @Value 如何配置Map
//	@Value("${my.map.*}") 
	private Map<String, String> map;

	public List<String> getConfig() {
		return config;
	}

	public void setConfig(List<String> config) {
		this.config = config;
	}

	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Long getBigNumber() {
		return bigNumber;
	}

	public void setBigNumber(Long bigNumber) {
		this.bigNumber = bigNumber;
	}

	public Integer getTest1() {
		return test1;
	}

	public void setTest1(Integer test1) {
		this.test1 = test1;
	}

	public Integer getTest2() {
		return test2;
	}

	public void setTest2(Integer test2) {
		this.test2 = test2;
	}

	@Override
	public String toString() {
		return "ValueConfigProperties [value=" + value + ", number=" + number + ", bigNumber=" + bigNumber + ", test1="
				+ test1 + ", test2=" + test2 + ", config=" + config + ", map=" + map + "]";
	}

}
