package com.mycom.springjdbcapimultidbdemo1;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mycom.springjdbcapimultidbdemo1.model.CustOrder;
import com.mycom.springjdbcapimultidbdemo1.repository.CustOrderRepositoryDb2;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Db2Test {
	@Autowired
	public CustOrderRepositoryDb2 custOrderRepositoryDb2;

	private final int start = 2011;
	private final int to = start + 5;

	@Test
	@Order(1)
	void delete() {
		log.info("====== start delete");
		for (int idx = start; idx < to; idx++) {
			int eff_row = custOrderRepositoryDb2.deleteById(idx);
			log.info("delete idx={}, effect row={}", idx, eff_row);
		}
	}

	@Test
	@Order(2)
	void insert() {
		log.info("====== start insert");
		for (int idx = start; idx < to; idx++) {
			var custorder = new CustOrder()
					.setOrderId(idx)
					.setCustomerName("customer_name" + idx)
					.setTotalAmount(new BigDecimal(idx + "00"))
					.setOrderDate(LocalDate.now())
					.setInsertDatetime(LocalDateTime.now());

			custOrderRepositoryDb2.insert(custorder);
		}
	}

	@Test
	@Order(3)
	void findAll() {
		log.info("====== start findAll");
		var datas = custOrderRepositoryDb2.findAll();
		for (CustOrder custOrder : datas) {
			log.info("custOrder={}", custOrder.toString());
		}
	}

	@Test
	@Order(4)
	void findById() {
		log.info("====== start findById");
		var data = custOrderRepositoryDb2.findById(this.start);
		if (data != null) {
			log.info("custOrder={}", data.toString());
		} else {
			log.info("Not found data.");
		}
	}

	@Test
	@Order(5)
	void findByCustomerName() {
		log.info("====== start findByCustomerName");
		var datas = custOrderRepositoryDb2.findByCustomerName("customer_name" + start);
		if (datas.size() == 0) {
			log.info("Not found data.");
			return;
		}
		for (CustOrder data : datas) {
			log.info("custOrder={}", data.toString());
		}
	}

	@Test
	@Order(6)
	void update() {
		log.info("====== start update");
		var data = custOrderRepositoryDb2.findById(this.start);
		if (data != null) {
			data.setTotalAmount(new BigDecimal("200"));
			custOrderRepositoryDb2.update(data);
			log.info("update={}", data.toString());
		} else {
			log.info("Not found data.");
		}
	}
}
