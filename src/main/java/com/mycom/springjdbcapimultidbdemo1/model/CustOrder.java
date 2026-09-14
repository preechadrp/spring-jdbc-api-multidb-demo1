package com.mycom.springjdbcapimultidbdemo1.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@ToString
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CustOrder {

	private Integer orderId;
	private String customerName;
	private BigDecimal totalAmount;

	@JsonFormat(pattern = "yyyy-MM-dd") // กำหนดรูปแบบวันที่ในการแสดงผล
	private LocalDate orderDate;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS") // กำหนดรูปแบบวันที่และเวลาในการแสดงผล
	private LocalDateTime insertDatetime;

}
