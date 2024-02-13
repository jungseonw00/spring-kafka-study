package com.study.springkafkastudy.config;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DemoViewDTO {

	private String name;
	private String age;
	private String id;
	private String department;

}
