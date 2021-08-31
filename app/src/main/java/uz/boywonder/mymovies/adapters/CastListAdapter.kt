package uz.boywonder.mymovies.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import uz.boywonder.mymovies.R
import uz.boywonder.mymovies.databinding.CastRowLayoutBinding
import uz.boywonder.mymovies.models.Cast
import uz.boywonder.mymovies.models.CastList
import uz.boywonder.mymovies.util.Constants.Companion.BASE_POSTER_PATH
import uz.boywonder.mymovies.util.MyDiffUtil

class CastListAdapter(private val listener: OnItemClickListener) :
    RecyclerView.Adapter<CastListAdapter.CastViewHolder>() {

    private var castList = emptyList<Cast>()

    inner class CastViewHolder(private val binding: CastRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = castList[position]
                    listener.OnItemClick(item)
                }
            }
        }

        fun bind(cast: Cast) {
            binding.apply {
                castImageView.load(BASE_POSTER_PATH + cast.profilePath) {
                    crossfade(600)
                    error(R.drawable.ic_no_image)
                }
                castTextView.text = cast.name
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        val binding =
            CastRowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        val currentItem = castList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return castList.size
    }

    interface OnItemClickListener {
        fun OnItemClick(cast: Cast)
    }

    fun setNewData(newData: CastList) {
        val diffUtil = MyDiffUtil(castList, newData.cast)
        val diffUtilResult = DiffUtil.calculateDiff(diffUtil)
        castList = newData.cast
        diffUtilResult.dispatchUpdatesTo(this)
    }
}