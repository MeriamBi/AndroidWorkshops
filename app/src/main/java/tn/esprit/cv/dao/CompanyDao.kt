package tn.esprit.cv.dao

import androidx.room.*
import tn.esprit.cv.data.Company
import tn.esprit.cv.data.CompanyType

@Dao
interface CompanyDao {
    @Insert
    fun insertCompany(company: Company)

    @Update
    fun updateCompany(company: Company)

    @Delete
    fun deleteCompany(company: Company)

    @Query("SELECT * FROM companies WHERE id = :id")
    fun getCompanyById(id: Int): Company?

    @Query("SELECT * FROM companies")
    fun getAllCompanies(): List<Company>

    @Query("SELECT * FROM companies WHERE type = :type")
    fun getCompaniesByType(type: CompanyType): List<Company>
}