package uz.boywonder.mymovies.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import uz.boywonder.mymovies.R
import uz.boywonder.mymovies.databinding.CreditsRowLayoutBinding
import uz.boywonder.mymovies.models.CastByPerson
import uz.boywonder.mymovies.models.MoviesByPerson
import uz.boywonder.mymovies.util.Constants.Companion.BASE_POSTER_PATH
import uz.boywonder.mymovies.util.MyDiffUtil

class MoviesByPersonAdapter(private val listener: OnItemClickListener) :
    RecyclerView.Adapter<MoviesByPersonAdapter.MoviesByPersonViewHolder>() {

    private var movieCredits = emptyList<CastByPerson>()

    inner class MoviesByPersonViewHolder(private val binding: CreditsRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = movieCredits[position]
                    listener.OnItemClick(item)
                }
            }
        }

        fun bind(castByPerson: CastByPerson) {
            binding.apply {
                creditsMovieImageView.load(BASE_POSTER_PATH + castByPerson.backdropPath) {
                    crossfade(600)
                    error(R.drawable.ic_no_image)
                }
                creditsMovieTextView.text = castByPerson.title
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesByPersonViewHolder {
        val binding =
            CreditsRowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviesByPersonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MoviesByPersonViewHolder, position: Int) {
        val currentItem = movieCredits[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return movieCredits.size
    }

    interface OnItemClickListener {
        fun OnItemClick(castByPerson: CastByPerson)
    }

    // To check the updated data with older one to improve performance and accuracy of the app
    fun setNewData(newData: MoviesByPerson) {
        val diffUtil = MyDiffUtil(movieCredits, newData.cast)
        val diffUtilResult = DiffUtil.calculateDiff(diffUtil)
        movieCredits = newData.cast
        diffUtilResult.dispatchUpdatesTo(this)
    }
}