package com.smily.service;

import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "my")
@Component
public class AutoConfigProperties {
	private String value;
	private Integer number;
	private Long bigNumber;
	private Integer test1;
	private Integer test2;
	private List<String> config;
	private Map<String, String> map;

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

	public List<String> getConfig() {
		return config;
	}

	public void setConfig(List<String> config) {
		this.config = config;
	}

	@Override
	public String toString() {
		return "AutoConfigProperties [value=" + value + ", number=" + number + ", bigNumber=" + bigNumber + ", test1="
				+ test1 + ", test2=" + test2 + ", config=" + config + ", map=" + map + "]";
	}

}
