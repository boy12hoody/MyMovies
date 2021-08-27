package uz.boywonder.mymovies.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import uz.boywonder.mymovies.R
import uz.boywonder.mymovies.databinding.MovieRowLayoutBinding
import uz.boywonder.mymovies.models.Result

class MoviesListAdapter(private val listener: OnItemClickListener) :
    PagingDataAdapter<Result, MoviesListAdapter.MoviesViewHolder>(MOVIE_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val binding =
            MovieRowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MoviesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }


    inner class MoviesViewHolder(private val binding: MovieRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null) {
                        listener.OnItemClick(item)
                    }
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

    interface OnItemClickListener {
        fun OnItemClick(result: Result)
    }

    companion object {
        private val MOVIE_COMPARATOR = object : DiffUtil.ItemCallback<Result>() {
            override fun areItemsTheSame(oldItem: Result, newItem: Result) =
                oldItem.id == newItem.id


            override fun areContentsTheSame(oldItem: Result, newItem: Result) =
                oldItem == newItem

        }
    }
}