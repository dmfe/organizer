package net.dmfe.organizer.service;

import net.dmfe.organizer.entity.User;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;
import java.util.UUID;

@Service
public class AppUserDetailsService extends MappingSqlQuery<User> implements UserDetailsService {

    public AppUserDetailsService(DataSource ds) {
        super(ds, "select * from org.app_user where username = :username");
        declareParameter(new SqlParameter("username", Types.VARCHAR));
        compile();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return executeByNamedParam(Map.of("username", username)).stream().findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    @Override
    protected User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new User(rs.getObject("id", UUID.class), rs.getString("username"), rs.getString("password"));
    }

}
