package tn.esprit.cv.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import tn.esprit.cv.R
import tn.esprit.cv.data.Company
import tn.esprit.cv.data.Data

class RecyclerViewAdapter (var companiesList: List<Company>,private val layoutResourceId: Int) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>(){
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
        //holder.descTv.text = itemsViewModel.desc ?: ""
        /*if (layoutResourceId==R.layout.experience_item) {
            // set the desc text only for experience_item layout
            holder.descTv?.text = itemsViewModel.desc
        }*/
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
        /*val descTv: TextView? = if (itemView.findViewById<TextView>(R.id.tv_desc) != null) {
            itemView.findViewById(R.id.tv_desc)
        } else {
            null
        }*/
    }
}