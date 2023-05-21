package crawler.db.dao

import crawler.db.model.ItemBean
import org.jdbi.v3.core.mapper.RowMapper
import org.jdbi.v3.core.statement.StatementContext
import java.sql.ResultSet
import java.sql.SQLException

internal class ItemBeanMapper : RowMapper<ItemBean?> {
    @Throws(SQLException::class)
    override fun map(rs: ResultSet, ctx: StatementContext?): ItemBean {
        return ItemBean(rs.getString("code"),
            rs.getString("trading_name"),
            rs.getString("market_type"),
            rs.getInt("sector33_code"),
            rs.getString("sector33_name"),
            rs.getInt("sector17_code"),
            rs.getString("sector17_name"),
            rs.getInt("scale_code"),
            rs.getString("scale_name"),
            rs.getString("created_at"),
            rs.getString("updated_at")
        )
    }
}