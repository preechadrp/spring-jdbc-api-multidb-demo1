package com.mycom.springjdbcapimultidbdemo1.repository;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.mycom.springjdbcapimultidbdemo1.model.CustOrder;


@Repository
public class CustOrderRepositoryDb1 {

	private final JdbcTemplate jdbcTemplate;

	//ไม่ต้องระบุ @Qualifier("jdbcTemplateDb1") เพราะเป็น @Primary
	public CustOrderRepositoryDb1(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	private final RowMapper<CustOrder> rowMapper = (rs, rowNum) -> {
		return new CustOrder()
				.setOrderId(rs.getInt("order_id"))
				.setCustomerName(rs.getString("customer_name"))
				.setTotalAmount(rs.getBigDecimal("total_amount"))
				.setOrderDate(rs.getObject("order_date", LocalDate.class))
				.setInsertDatetime(rs.getObject("insert_datetime", LocalDateTime.class));
	};

	public void insert(CustOrder custorder) {

		String sql = """
				INSERT INTO cust_order
				(order_id, customer_name, total_amount, order_date, insert_datetime)
				VALUES (?,?,?,?,?)
				""";

		jdbcTemplate.update(sql,
				custorder.getOrderId(),
				custorder.getCustomerName(),
				custorder.getTotalAmount(),
				custorder.getOrderDate(),
				custorder.getInsertDatetime());

	}

	public List<CustOrder> findAll() {
		String sql = "SELECT * FROM cust_order order by order_id";
		return jdbcTemplate.query(sql, rowMapper);
	}

	public CustOrder findById(int orderId) {
		String sql = "SELECT * FROM cust_order where order_id=?";
		return jdbcTemplate.queryForObject(sql, rowMapper, new Object[] { orderId });
	}

	public List<CustOrder> findByCustomerName(String customerName) {
		String sql = "SELECT * FROM cust_order where customer_name=? order by order_id";
		return jdbcTemplate.query(sql, rowMapper, new Object[] { customerName });
	}

	public int update(CustOrder custorder) {

		String sql = """
				update cust_order set
				customer_name=?, total_amount=?, order_date=?, insert_datetime=?
				where order_id=?
				""";

		return jdbcTemplate.update(sql,
				custorder.getCustomerName(),
				custorder.getTotalAmount(),
				custorder.getOrderDate(),
				custorder.getInsertDatetime(),
				custorder.getOrderId());
	}

	public int deleteById(int orderId) {
		String sql = "delete from cust_order where order_id=?";
		return jdbcTemplate.update(sql, orderId);
	}

	public void insertUsersByBatch() {
		//=== ตัวอย่างการ insert แบบ batch
		String sql = """
				INSERT INTO cust_order
				(order_id, customer_name, total_amount, order_date, insert_datetime)
				VALUES (?,?,?,?,?)
				""";

		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

			int start = 2011;

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {

				int idx = start + i;

				ps.setInt(1, idx); //order_id
				ps.setString(2, "customer_name" + idx); //customer_name
				ps.setBigDecimal(3, new BigDecimal(idx + "00")); //total_amount
				ps.setDate(3, java.sql.Date.valueOf(LocalDate.now())); //order_date
				ps.setTimestamp(4, java.sql.Timestamp.valueOf(LocalDateTime.now())); //insert_datetime
			}

			@Override
			public int getBatchSize() {
				return 10;
			}

		});
	}

}
