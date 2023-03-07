package tn.esprit.cv.utils

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import tn.esprit.cv.dao.CompanyDao
import tn.esprit.cv.data.Company

@Database(entities = [Company::class], version = 1)
abstract class AppDataBase : RoomDatabase() {

    abstract fun companyDao(): CompanyDao

    companion object {
        @Volatile
        private var instance: AppDataBase? = null
        private val dbName = "cv"
        private val LOCK=Any()

        operator fun invoke(context: Context)= instance?: synchronized(LOCK){
            instance ?: buildDatabse(context).also {
                instance=it
            }
        }

        private fun buildDatabse(context: Context)=Room.databaseBuilder(
            context.applicationContext,
            AppDataBase::class.java,
            dbName
        ).build()

        fun getDatabase(context: Context): AppDataBase {
            if (instance == null) {
                synchronized(this) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDataBase::class.java,
                        dbName
                    ).fallbackToDestructiveMigration().build()
                }
            }
            return instance!!
        }
    }
}


