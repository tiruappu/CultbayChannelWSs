package com.cultuzz.channel.jdbcTemplate.impl;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.cultuzz.channel.jdbcTemplate.JDBCTemplate;



@Component
@Qualifier("cusebedaTemplate")
public class CusebedaJdbcTemplate implements JDBCTemplate {

	private DataSource dataSource;

	private JdbcTemplate jdbcTemplate;
	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
//	public DataSource getDataSource() {
//		return dataSource;
//	}

	@Autowired
	@Qualifier("cusebeda")
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate= new JdbcTemplate(this.dataSource);
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(this.dataSource);
	}

//	public void setJdbcTemplate() {
//		this.jdbcTemplate = new JdbcTemplate(getDataSource());
//	}

	public JdbcTemplate getJdbcTemplate() {
		return this.jdbcTemplate;
	}

	public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
		// TODO Auto-generated method stub
		return this.namedParameterJdbcTemplate;
	}
}
