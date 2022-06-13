package database


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.foodsample.entity.MenuEntity
import com.example.foodsample.models.Menu


@Dao
interface DaoMenu {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMenu(menuEntity: MenuEntity)

    @Query("SELECT * FROM menu_table ORDER BY id ASC")
    fun getAllData() :kotlinx.coroutines.flow.Flow<List<Menu>>
}
