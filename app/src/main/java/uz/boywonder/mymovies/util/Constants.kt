package uz.boywonder.mymovies.util

class Constants {

    companion object {

        const val API_KEY = "2dcc887812d1c6da6e8097e28ba2ea4f"
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val BASE_POSTER_PATH = "https://image.tmdb.org/t/p/w342"
        const val BASE_BACKDROP_PATH = "https://image.tmdb.org/t/p/w780"

        // CATEGORIES
        const val CAT_POPULAR = "popular"
        const val CAT_TOP_RATED = "top_rated"
        const val CAT_UPCOMING = "upcoming"

        // API QUERIES

        const val QUERY_PAGE_NUMBER = "page"
        const val QUERY_PAGE_SIZE = 20
        const val QUERY_LANG_ENG = "en-US"
    }
}