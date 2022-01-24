package crawler.db.dao

import crawler.db.model.ItemBean
import org.jdbi.v3.core.mapper.RowMapper
import org.jdbi.v3.core.statement.StatementContext
import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.customizer.BindBean
import org.jdbi.v3.sqlobject.kotlin.RegisterKotlinMapper
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import java.sql.ResultSet
import java.sql.SQLException

interface ItemDao {
    @SqlQuery("""SELECT code, trading_name, market_type, sector33_code, sector33_name, sector17_code, sector17_name, 
        scale_code, scale_name, created_at, updated_at FROM items""")
    @RegisterKotlinMapper(ItemBean::class)
    fun getAll(): List<ItemBean>

    @SqlQuery("""SELECT code, trading_name, market_type, sector33_code, sector33_name, sector17_code, sector17_name,
        scale_code, scale_name, created_at, updated_at FROM items WHERE code = :code""")
    @RegisterKotlinMapper(ItemBean::class)
    fun getByCode(@Bind("code") code: String): ItemBean

    @SqlUpdate("""INSERT INTO items (code, trading_name, market_type, sector33_code, sector33_name, sector17_code, sector17_name, scale_code, scale_name) 
        VALUES (:code, :tradingName, :marketType, :sector33Code, :sector33Name, :sector17Code, :sector17Name, :scaleCode, :scaleName)""")
    fun insertItemBean(@BindBean itemBean: ItemBean)

    @SqlUpdate("""UPDATE items set code = :code, trading_name = :tradingName, market_type = :marketType, sector33_code = :sector33Code, 
        sector33_name = :sector33Name, sector17_code = :sector17Code, sector17_name = :sector17Name, scale_code = :scaleCode, scale_name = :scaleName
        WHERE code = :whereCode""")
    fun updateByCode(@BindBean itemBean: ItemBean, @Bind("whereCode") code: String)

    @SqlUpdate("""DELETE FROM items WHERE code = :code""")
    fun deleteByCode(@Bind("code") code: String)
}