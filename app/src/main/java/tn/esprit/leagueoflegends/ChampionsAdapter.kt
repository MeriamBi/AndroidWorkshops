package tn.esprit.leagueoflegends
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChampionsAdapter(private val championsList: List<ChampionsData>) : RecyclerView.Adapter<ChampionsAdapter.ViewHolder>() {
    lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the champion_item view
        context=parent.context
        val view = LayoutInflater.from(context)
            .inflate(R.layout.champion_item, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ItemsViewModel = championsList[position]
        holder.picIv.setImageResource(ItemsViewModel.pic)
        holder.nameTv.text = ItemsViewModel.name
        holder.roleTv.text = ItemsViewModel.role

        holder.itemView.setOnClickListener{
            var intent= Intent(context, ChampionInfoActivity::class.java)
            intent.putExtra("pic", ItemsViewModel.pic)
            intent.putExtra("name", ItemsViewModel.name)
            intent.putExtra("role", ItemsViewModel.role)
            context.startActivity(intent)
        }

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return championsList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val picIv: ImageView = itemView.findViewById(R.id.iv_pic)
        val nameTv: TextView = itemView.findViewById(R.id.tv_name)
        val roleTv: TextView = itemView.findViewById(R.id.tv_role)
    }
}