package com.smily.dbserv.bean;

import com.smily.mybatis.dbserv.AbstractTable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class TestTable extends AbstractTable {
    @Id
    private Integer id;

    private String name;

    private BigDecimal price;

    private Date inDate;

    private Date inTime;

    private Float floatColumn;

    private Double doubleColumn;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getInDate() {
        return inDate;
    }

    public void setInDate(Date inDate) {
        this.inDate = inDate;
    }

    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    public Float getFloatColumn() {
        return floatColumn;
    }

    public void setFloatColumn(Float floatColumn) {
        this.floatColumn = floatColumn;
    }

    public Double getDoubleColumn() {
        return doubleColumn;
    }

    public void setDoubleColumn(Double doubleColumn) {
        this.doubleColumn = doubleColumn;
    }

    @Override
    public boolean contains(String column) {
        return Column.parse(column) != null;
    }

    public String get(Column column) {
        Object value;
        if (column == null) return null;
        switch (column) {
        case id: {
            return (value = getId()) == null ? null : value.toString();
        }
        case name: {
            return getName();
        }
        case price: {
            return (value = getPrice()) == null ? null : value.toString();
        }
        case in_date: {
            return (value = getInDate()) == null ? null : formatDate((Date) value);
        }
        case in_time: {
            return (value = getInTime()) == null ? null : formatDate((Date) value);
        }
        case float_column: {
            return (value = getFloatColumn()) == null ? null : value.toString();
        }
        case double_column: {
            return (value = getDoubleColumn()) == null ? null : value.toString();
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

    public TestTable set(Column column, String value) {
        if (column == null) return this;
        switch (column) {
        case id: {
            value = StringUtils.stripToNull(value);
            setId(value == null ? null : new Integer(value));
            break;
        }
        case name: {
            setName(value);
            break;
        }
        case price: {
            value = StringUtils.stripToNull(value);
            setPrice(value == null ? null : new BigDecimal(value));
            break;
        }
        case in_date: {
            setInDate(parseDate(value));
            break;
        }
        case in_time: {
            setInTime(parseDate(value));
            break;
        }
        case float_column: {
            value = StringUtils.stripToNull(value);
            setFloatColumn(value == null ? null : new Float(value));
            break;
        }
        case double_column: {
            value = StringUtils.stripToNull(value);
            setDoubleColumn(value == null ? null : new Double(value));
            break;
        }
        default: {
            break;
        }}
        return this;
    }

    @Override
    public TestTable set(String column, String value) {
        return set(Column.parse(column), value);
    }

    @Override
    public TestTable set(Map<String, ?> map) {
        for (Column c: Column.values()) {
            if (map.containsKey(c.toString())) {
                Object v = map.get(c.toString());
                if (v instanceof Date) {
                    set(c, formatDate((Date) v));
                } else {
                    set(c, v == null ? null : v.toString());
                }
            }
        }
        return this;
    }

    @Override
    public TestTable set(AbstractTable table) {
        return set(table.get());
    }

    @Override
    public String toKey() {
        StringBuilder sb = new StringBuilder();
        sb.append(getId()).append("|");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (obj == this) {
            return true;
        } else if (!(obj instanceof TestTable)) {
            return false;
        }
        TestTable rhs = (TestTable) obj;
        return new EqualsBuilder()
                .append(getId(), rhs.getId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getId())
                .toHashCode();
    }

    @Override
    public String toString() {
        ToStringBuilder builder =
                new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        if (getId() != null) {
            builder.append("id", getId());
        }
        if (getName() != null) {
            builder.append("name", getName());
        }
        if (getPrice() != null) {
            builder.append("price", getPrice());
        }
        if (getInDate() != null) {
            builder.append("inDate", getInDate());
        }
        if (getInTime() != null) {
            builder.append("inTime", getInTime());
        }
        if (getFloatColumn() != null) {
            builder.append("floatColumn", getFloatColumn());
        }
        if (getDoubleColumn() != null) {
            builder.append("doubleColumn", getDoubleColumn());
        }
        return builder.toString();
    }

    public TestTable() {
        /* pass */
    }

    public TestTable(Map<String, ?> map) {
        set(map);
    }

    public TestTable(AbstractTable table) {
        set(table);
    }

    public enum Column {
        id,
        name,
        price,
        in_date,
        in_time,
        float_column,
        double_column;

        public static Column parse(String column) {
            try {
                return Column.valueOf(column.toUpperCase());
            } catch (Exception e) {
                return null;
            }
        }
    }
}