package tn.esprit.leagueoflegends

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ChampionsFragment : Fragment(R.layout.fragment_champions) {
    private lateinit var championsRv:RecyclerView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        championsRv = view.findViewById(R.id.rv_champions)

        // this creates a vertical layout Manager
        championsRv.layoutManager = LinearLayoutManager(view.context)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<ChampionsData>()
        data.add(ChampionsData(R.drawable.ic_leesin, "Lee Sin","COMBATTANT: Jungler"))
        data.add(ChampionsData(R.drawable.ic_missfortune, "Miss Fortune","TIREUR: ADC"))
        data.add(ChampionsData(R.drawable.ic_thresh, "Thresh","SUPPORT"))
        data.add(ChampionsData(R.drawable.ic_nasus, "Nasus","COMBATTANT: Top"))
        data.add(ChampionsData(R.drawable.ic_ahri, "Ahri","MAGE: MID"))
        data.add(ChampionsData(R.drawable.ic_annie, "Annie","MAGE: MID"))
        data.add(ChampionsData(R.drawable.ic_ashe, "Ashe","TIREUR: ADC"))
        data.add(ChampionsData(R.drawable.ic_blitzcrank, "Blitzcranck","TANK: Support"))
        data.add(ChampionsData(R.drawable.ic_ekko, "Ekko","ASSASIN: MID/Jingle"))
        val adapter=ChampionsAdapter(data)
        championsRv.adapter=adapter

    }
}