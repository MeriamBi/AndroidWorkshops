package tn.esprit.cv.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "companies")
data class Company(
    @ColumnInfo(name = "PICTURE")
    val pic: String,
    @ColumnInfo(name = "NAME")
    val name: String,
    @ColumnInfo(name = "ADDRESS")
    val address: String,
    @ColumnInfo(name = "START")
    val start: String,
    @ColumnInfo(name = "END")
    val end: String,
    @ColumnInfo(name = "TYPE")
    val type: CompanyType

){
    @PrimaryKey(autoGenerate = true) var id: Int=0
}
