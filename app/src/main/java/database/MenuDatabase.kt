package database

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.foodsample.entity.MenuEntity
import com.example.foodsample.models.Menu

//@Database(
//    entities = [MenuEntity::class],
//    version = 1,
//    exportSchema = true)
@Database(
    entities = [MenuEntity::class],
//    autoMigrations = [
//        AutoMigration (from = 1, to = 2)
//    ],
    version = 1,
    exportSchema = true
)

abstract class MenuDatabase : RoomDatabase() {

    abstract fun DaoMenu(): DaoMenu

    companion object {
        @Volatile
        private var INSTANCE: MenuDatabase? = null

        fun getDatabase(context: Context): MenuDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            if (INSTANCE == null) {
                synchronized(this) {
                    // Pass the database to the INSTANCE
                    INSTANCE = buildDatabase(context)
                }
            }
            // Return database.
            return INSTANCE!!
        }

        private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // The following query will add a new column called lastUpdate to the notes database
                database.execSQL("ALTER TABLE notes ADD COLUMN lastUpdate INTEGER NOT NULL DEFAULT 0")
            }
        }

        private fun buildDatabase(context: Context): MenuDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                MenuDatabase::class.java,
                "notes_database"
            )
                //.addMigrations(MIGRATION_1_2)
                .build()
        }
    }
}