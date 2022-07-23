import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android_imperative.R
import com.example.android_imperative.activity.DetailsActivity
import com.example.android_imperative.adapter.BaseAdapter
import com.example.android_imperative.databinding.ItemTvEpisodeBinding
import com.example.android_imperative.databinding.ItemTvShortBinding
import com.example.android_imperative.model.Episode

class TvEpisodeAdapter(var activity: DetailsActivity, var items: List<Episode>) : BaseAdapter() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_tv_episode, parent, false)
        return TvEpisodeHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val tvShort = items[position]
        if (holder is TvEpisodeHolder) {
            holder.binding.tvEpisode.text= "Episode :"+tvShort.episode.toString()
            holder.binding.tvEpisodeInfoName.text=tvShort.name
            holder.binding.tvAirDataInfo.text="Air Date: "+ tvShort.air_date.toString()
            holder.binding
        }
    }

    inner class TvEpisodeHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemTvEpisodeBinding.bind(view)
    }
}