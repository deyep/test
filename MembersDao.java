package jp.ken.jdbc.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import jp.ken.jdbc.entity.Members;

@Component
public class MembersDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private PlatformTransactionManager transactionManager;

	private RowMapper<Members> membersMapper = new BeanPropertyRowMapper<Members>(Members.class);

	public List<Members> getList() {
		String sql = "SELECT * FROM members";
		List<Members> membersList = jdbcTemplate.query(sql, membersMapper);
		return membersList;
	}

	public List<Members> getListByName(String name) {
		String sql = "SELECT * FROM members WHERE name LIKE ?";
		name = name.replace("%", "\\%").replace("_", "\\_");
		name = "%" + name + "%";
		Object[] parameters = { name };
		List<Members> membersList = jdbcTemplate.query(sql, parameters, membersMapper);
		return membersList;
	}

	public Members getMembersById(Integer id) {
		String sql = "SELECT * FROM members WHERE id=?";
		Object[] parameters = { id };
		try {
			Members members = jdbcTemplate.queryForObject(sql, parameters, membersMapper);
			return members;
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
}
