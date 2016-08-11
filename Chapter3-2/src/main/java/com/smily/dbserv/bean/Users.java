package com.smily.dbserv.bean;

import com.smily.mybatis.dbserv.AbstractTable;
import java.util.Map;
import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Users extends AbstractTable {
    @Id
    private String username;

    private String password;

    private String role;

    private String salt;

    private String passwordStr;

    private static final long serialVersionUID = 1L;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role == null ? null : role.trim();
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    public String getPasswordStr() {
        return passwordStr;
    }

    public void setPasswordStr(String passwordStr) {
        this.passwordStr = passwordStr == null ? null : passwordStr.trim();
    }

    @Override
    public boolean contains(String column) {
        return Column.parse(column) != null;
    }

    public String get(Column column) {
        if (column == null) return null;
        switch (column) {
        case username: {
            return getUsername();
        }
        case password: {
            return getPassword();
        }
        case role: {
            return getRole();
        }
        case salt: {
            return getSalt();
        }
        case password_str: {
            return getPasswordStr();
        }
        default: {
            return null;
        }}
    }

    @Override
    public String get(String column) {
        return get(Column.parse(column));
    }

    @Override
    public Map<String, String> get(Map<String, String> map) {
        for (Column c: Column.values()) {
            map.put(c.toString(), get(c));
        }
        return map;
    }

    public Users set(Column column, String value) {
        if (column == null) return this;
        switch (column) {
        case username: {
            setUsername(value);
            break;
        }
        case password: {
            setPassword(value);
            break;
        }
        case role: {
            setRole(value);
            break;
        }
        case salt: {
            setSalt(value);
            break;
        }
        case password_str: {
            setPasswordStr(value);
            break;
        }
        default: {
            break;
        }}
        return this;
    }

    @Override
    public Users set(String column, String value) {
        return set(Column.parse(column), value);
    }

    @Override
    public Users set(Map<String, ?> map) {
        for (Column c: Column.values()) {
            if (map.containsKey(c.toString())) {
                Object v = map.get(c.toString());
                set(c, v == null ? null : v.toString());
            }
        }
        return this;
    }

    @Override
    public Users set(AbstractTable table) {
        return set(table.get());
    }

    @Override
    public String toKey() {
        StringBuilder sb = new StringBuilder();
        sb.append(getUsername()).append("|");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (obj == this) {
            return true;
        } else if (!(obj instanceof Users)) {
            return false;
        }
        Users rhs = (Users) obj;
        return new EqualsBuilder()
                .append(getUsername(), rhs.getUsername())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getUsername())
                .toHashCode();
    }

    @Override
    public String toString() {
        ToStringBuilder builder =
                new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        if (getUsername() != null) {
            builder.append("username", getUsername());
        }
        if (getPassword() != null) {
            builder.append("password", getPassword());
        }
        if (getRole() != null) {
            builder.append("role", getRole());
        }
        if (getSalt() != null) {
            builder.append("salt", getSalt());
        }
        if (getPasswordStr() != null) {
            builder.append("passwordStr", getPasswordStr());
        }
        return builder.toString();
    }

    public Users() {
        /* pass */
    }

    public Users(Map<String, ?> map) {
        set(map);
    }

    public Users(AbstractTable table) {
        set(table);
    }

    public enum Column {
        username,
        password,
        role,
        salt,
        password_str;

        public static Column parse(String column) {
            try {
                return Column.valueOf(column.toUpperCase());
            } catch (Exception e) {
                return null;
            }
        }
    }
}