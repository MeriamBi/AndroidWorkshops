package tn.esprit.cv.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tn.esprit.cv.R
import tn.esprit.cv.adapters.RecyclerViewAdapter
import tn.esprit.cv.dao.CompanyDao
import tn.esprit.cv.data.Company
import tn.esprit.cv.data.CompanyType
import tn.esprit.cv.data.Data
import tn.esprit.cv.utils.AppDataBase

class EducationFragment : Fragment(R.layout.fragment_education) {
    private lateinit var educationRv: RecyclerView
    private lateinit var dao: CompanyDao
    lateinit var database: AppDataBase
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        educationRv = view.findViewById(R.id.rv_education)
        educationRv.layoutManager = LinearLayoutManager(view.context)

        database = AppDataBase.getDatabase(requireContext())
        dao = database.companyDao()
        coroutineScope.launch {
            val cpList = withContext(Dispatchers.IO) {
                dao.getCompaniesByType(CompanyType.EDUCATION)
            }
            val adapter = RecyclerViewAdapter(cpList, R.layout.education_item)
            educationRv.adapter = adapter
        }
        // ArrayList of class ItemsViewModel
        /*val data = ArrayList<Data>()
        data.add(Data(R.drawable.mit,"MASSACHUSETTS","UNITED STATES","01/09/2020","TODAY",""))
        data.add(Data(R.drawable.oxford,"OXFORD","UNITED KINGDOM","01/09/2018","31/08/2020",""))
        data.add(Data(R.drawable.stanford,"STANFORD","UNITED STATES","01/09/2016","31/08/2018",""))
        data.add(Data(R.drawable.cambridge,"CAMBRIDGE","UNITED KINGDOM","01/09/2014","31/08/2016",""))
        data.add(Data(R.drawable.harvard,"HARVARD","UNITED STATES","01/09/2012","31/08/2014",""))
        data.add(Data(R.drawable.esprit,"ESPRIT","TUNISIA","01/09/2009","31/08/2012",""))
        val adapter= RecyclerViewAdapter(data,R.layout.education_item)
        educationRv.adapter=adapter*/
    }
}