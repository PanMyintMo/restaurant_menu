package database


import androidx.room.*
import com.example.foodsample.entity.MenuEntity

@Dao
interface DaoMenu {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMenu(menuEntity: MenuEntity)

    @Query("SELECT * FROM menu_table  ORDER BY id ASC")
    fun getAllData(): List<MenuEntity>

    //@Delete
//    fun deleteMenuItem(menu: ArrayList<Menu?>)
}
