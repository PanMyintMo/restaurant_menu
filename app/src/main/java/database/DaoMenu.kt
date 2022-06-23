package database


import androidx.room.*
import com.example.foodsample.entity.MenuEntity
import com.example.foodsample.models.Menu


@Dao
interface DaoMenu {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMenu(menuEntity: MenuEntity)

    @Query("SELECT * FROM menu_table  ORDER BY id ASC")
    fun getAllData():List<MenuEntity>


    @Delete
     fun delete(menu:MenuEntity)
}
