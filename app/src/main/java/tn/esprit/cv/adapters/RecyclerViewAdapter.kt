package tn.esprit.cv.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tn.esprit.cv.R
import tn.esprit.cv.dao.CompanyDao
import tn.esprit.cv.data.Company
import tn.esprit.cv.data.CompanyType
import tn.esprit.cv.data.Data

class RecyclerViewAdapter (private val dao: CompanyDao, var companiesList: List<Company>, private val layoutResourceId: Int) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>(){
    lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the experience_item view
        context=parent.context
        val view = LayoutInflater.from(context)
            .inflate(layoutResourceId, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemsViewModel = companiesList[position]
        Glide.with(context).load(itemsViewModel.pic).into(holder.picIv) //set profile picture
        holder.nameTv.text = itemsViewModel.name
        holder.addressTv.text = itemsViewModel.address
        holder.startDateTv.text = itemsViewModel.start
        holder.endDateTv.text = itemsViewModel.end
        holder.deleteIv.setOnClickListener{
            showDialog(itemsViewModel)
        }
        //holder.descTv.text = itemsViewModel.desc ?: ""
        /*if (layoutResourceId==R.layout.experience_item) {
            // set the desc text only for experience_item layout
            holder.descTv?.text = itemsViewModel.desc
        }*/
    }

    private fun showDialog(company: Company) {
        val builder = AlertDialog.Builder(context)
        val title = if (company.type == CompanyType.EXPERIENCE) "Delete Experience" else "Delete Education"
        val content = if (company.type == CompanyType.EXPERIENCE) "Are you sure you want to delete this experience in ${company.name}?" else "Are you sure you want to delete ${company.name}?"
        builder.setTitle(title)
        builder.setMessage(content)
        builder.setPositiveButton("Yes") { dialog, which ->
            deleteCompany(company)
        }
        builder.setNegativeButton("No", null)
        val dialog = builder.create()
        dialog.show()
    }

    private fun deleteCompany(company: Company) {
        GlobalScope.launch {
            dao.deleteCompany(company)
            withContext(Dispatchers.Main) {
                companiesList = companiesList.filter { it.id != company.id }
                notifyDataSetChanged()
            }
        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return companiesList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val picIv: ImageView = itemView.findViewById(R.id.iv_company)
        val nameTv: TextView = itemView.findViewById(R.id.tv_name)
        val addressTv: TextView = itemView.findViewById(R.id.tv_address)
        val startDateTv: TextView = itemView.findViewById(R.id.tv_startDate)
        val endDateTv: TextView = itemView.findViewById(R.id.tv_endDate)
        val deleteIv: ImageView = itemView.findViewById(R.id.iv_delete)
        /*val descTv: TextView? = if (itemView.findViewById<TextView>(R.id.tv_desc) != null) {
            itemView.findViewById(R.id.tv_desc)
        } else {
            null
        }*/
    }
}