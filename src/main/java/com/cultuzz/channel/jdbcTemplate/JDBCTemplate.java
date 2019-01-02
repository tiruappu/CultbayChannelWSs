package com.cultuzz.channel.jdbcTemplate;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public interface JDBCTemplate {
	
public JdbcTemplate getJdbcTemplate();

public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate();

}
