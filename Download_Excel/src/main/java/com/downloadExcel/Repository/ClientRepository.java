package com.downloadExcel.Repository;



import com.downloadExcel.Model.ClientDTO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
 
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
@Repository
public class ClientRepository {
	private final NamedParameterJdbcTemplate jdbcTemplate;
 
    public ClientRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
 
    public List<ClientDTO> getClientData() {
        String sql = "SELECT client_id, client_brand_name FROM client";
 
        return jdbcTemplate.query(sql, Map.of(), new RowMapper<ClientDTO>() {
            @Override
            public ClientDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            	ClientDTO clientDto = new ClientDTO();
            	clientDto.setClientId(rs.getString("client_id"));
            	clientDto.setClientName( rs.getString("client_brand_name"));
                return clientDto;
            }
        });
    }
}
