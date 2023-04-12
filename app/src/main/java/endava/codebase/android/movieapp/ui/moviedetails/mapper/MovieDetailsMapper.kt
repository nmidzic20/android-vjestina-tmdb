package endava.codebase.android.movieapp.ui.moviedetails.mapper

import endava.codebase.android.movieapp.model.MovieDetails
import endava.codebase.android.movieapp.ui.moviedetails.MovieDetailsViewState

interface MovieDetailsMapper {
    fun toMovieDetailsViewState(movieDetails: MovieDetails): MovieDetailsViewState
}
