package uz.boywonder.mymovies.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import uz.boywonder.mymovies.R
import uz.boywonder.mymovies.databinding.MovieRowLayoutBinding
import uz.boywonder.mymovies.models.MovieList
import uz.boywonder.mymovies.models.Result
import uz.boywonder.mymovies.util.MyDiffUtil

class MoviesListAdapter(private val listener: OnItemClickListener) :
    RecyclerView.Adapter<MoviesListAdapter.MoviesViewHolder>() {

    private var movies = emptyList<Result>()

    inner class MoviesViewHolder(private val binding: MovieRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = movies[position]
                    listener.OnItemClick(item)
                }
            }
        }

        fun bind(result: Result) {
            binding.apply {
                movieNameTextView.text = result.title
                movieDateTextView.text = result.releaseDate
                movieImageView.load(result.posterPath) {
                    crossfade(600)
                    error(R.drawable.ic_no_image)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val binding =
            MovieRowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MoviesViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val currentItem = movies[position]
        holder.bind(currentItem)
    }


    interface OnItemClickListener {
        fun OnItemClick(result: Result)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    // To check the updated data with older one to improve performance and accuracy of the app
    fun setNewData(newData: MovieList) {
        val diffUtil = MyDiffUtil(movies, newData.results)
        val diffUtilResult = DiffUtil.calculateDiff(diffUtil)
        movies = newData.results
        diffUtilResult.dispatchUpdatesTo(this)
    }
}