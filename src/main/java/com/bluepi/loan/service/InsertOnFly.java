package com.bluepi.loan.service;

import com.bluepi.loan.base.IntegratorConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Slf4j
public class InsertOnFly {

	JdbcTemplate jdbcTemplate=IntegratorConstants.APPLICATION_CONTEXT_INSTANCE.getBean(JdbcTemplate.class);

	public Long selectEntitiesFromOracle(String entityName, List<String> columnNames, Object[] entityColumnValues, String whereCondition) throws SQLException {
		Long last_inserted_id = null;
		StringBuilder sql = new StringBuilder();
		ResultSet rs = null;
		String sqlColumns = "";

		if (columnNames != null && columnNames.size() > 0) {
			sqlColumns = columnNames.get(0);
			for (int i = 1; i < columnNames.size(); i++) {
				sqlColumns += ", " + columnNames.get(i);
			}
			sql.append("SELECT ").append(sqlColumns).append(" FROM ").append(entityName).append(" ").append(whereCondition);
		}
		log.info("Oracle query - " + sql);
		last_inserted_id = jdbcTemplate.execute(sql.toString(), new PreparedStatementCallback<Long>() {
			@Override
			public Long doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				Long id = null;
				for (int i = 0; i < columnNames.size(); i++) {
					ps.setObject(i + 1, (entityColumnValues[i]));
				}

				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					id = rs.getLong(1);
				}
				return id;
			}
		});
		log.info("last_inserted_id " + last_inserted_id);
		return last_inserted_id;
	}

	public Long saveEntitiesToOracle(String entityName, List<String> columnNames, Object[] entityColumnValues) throws SQLException {
		Long last_inserted_id = null;
		// TODO - getCount()
		log.info("enter into InsertOnFly- ");
		StringBuilder sql = new StringBuilder();
		ResultSet rs = null;
		String sqlColumns = "";
		String sqlColumnValues = "";
		int returnUpdate = 0;
		if (columnNames != null && columnNames.size() > 0) {
			sqlColumns += columnNames.get(0);
			sqlColumnValues += "?";
			for (int i = 1; i < columnNames.size(); i++) {
				sqlColumns += ", " + columnNames.get(i);
				sqlColumnValues += ", ?";
			}

			sql.append(" INSERT INTO ").append(entityName).append(" ( ").append(sqlColumns).append(") VALUES ( ").append(sqlColumnValues).append(")");
		}
		log.info("Oracle query - " + sql);
		returnUpdate = jdbcTemplate.execute(sql.toString(), new PreparedStatementCallback<Integer>() {
			@Override
			public Integer doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {

				for (int i = 0; i < columnNames.size(); i++) {
					ps.setObject(i + 1, (entityColumnValues[i]));
				}
				return ps.executeUpdate();
			}
		});
		log.info("result after insert " + returnUpdate);
		return 1l;
	}

	public int updateEntitiesToOracle(String entityName, List<String> columnNames, Object[] entityColumnValues, String saveType, String whereCondition) throws SQLException {
		StringBuilder sql = new StringBuilder();
		ResultSet rs = null;
		String sqlColumns = "";
		String sqlColumnValues = "";
		int returnUpdate = 0;
		if (saveType.equalsIgnoreCase("UPDATE")) {
			if (columnNames != null && columnNames.size() > 0) {
				sqlColumns = columnNames.get(0);
				sqlColumnValues = "=?";
				sql.append(" UPDATE ").append(entityName).append(" SET ").append(sqlColumns).append(sqlColumnValues);
				for (int i = 1; i < columnNames.size(); i++) {
					sqlColumns = ", " + columnNames.get(i);
					sql.append(sqlColumns);
					sqlColumnValues = "=?";
					sql.append(" ").append(sqlColumnValues);
				}
				sql.append(whereCondition);
			}
		}
		log.info("oracle query - " + sql);
		returnUpdate = jdbcTemplate.execute(sql.toString(), new PreparedStatementCallback<Integer>() {
			@Override
			public Integer doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {

				for (int i = 0; i < columnNames.size() + 1; i++) {
					ps.setObject(i + 1, entityColumnValues[i]);
				}
				return ps.executeUpdate();
			}
		});
		log.info("result after update " + returnUpdate);
		return returnUpdate;
	}
}
