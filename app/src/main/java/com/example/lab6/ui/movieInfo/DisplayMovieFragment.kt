package com.example.lab6.ui.movieInfo

import android.app.PictureInPictureParams
import android.graphics.PixelFormat
import android.os.Bundle
import android.util.Rational
import android.view.*
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.transition.TransitionInflater
import com.example.lab6.R
import com.example.lab6.model.Movie
import com.example.lab6.model.MovieSDK
import com.example.lab6.model.getUriFromRaw
import kotlinx.android.synthetic.main.movie_display_fragment.*

class DisplayMovieFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.movie_display_fragment, container, false)
        (requireActivity() as AppCompatActivity).setSupportActionBar(v.findViewById(R.id.toolbar))
        return v
    }

    private var args: DisplayMovieFragmentArgs? = null

    override fun onStart() {
        super.onStart()
        args = arguments?.let { DisplayMovieFragmentArgs.fromBundle(it) }
        val movie = MovieSDK.movies[args?.movieIndex ?: 0]

        purchase.setOnClickListener {checkOut()}

        desc.text = movie.desc
        toolbar.title = movie.name

        conFigVideo(movie)
    }

    private fun checkOut() {
        val action = DisplayMovieFragmentDirections.actionDisplayMovieFragmentToTicketingFragment(args?.movieIndex ?: 0)
        Navigation.findNavController(requireView()).navigate(action)
    }

    private fun conFigVideo(movie: Movie) {
        requireActivity().window.setFormat(PixelFormat.TRANSLUCENT)
        itemVideo.setVideoURI(getUriFromRaw(requireContext(), movie.trailer_id))
        itemVideo.start()

        pip.setOnClickListener {enterPIP()}
    }

    override fun onPictureInPictureModeChanged(isPictureinPictureMode: Boolean) {
        super.onPictureInPictureModeChanged(isPictureinPictureMode)

        if(isPictureinPictureMode) {
            toolbar.visibility = View.GONE
            buttonLayout.visibility = View.GONE
            scrollingView.visibility = View.GONE
        }
        else {
            toolbar.visibility = View.VISIBLE
            buttonLayout.visibility = View.VISIBLE
            scrollingView.visibility = View.VISIBLE
        }

    }

    private fun enterPIP() {
        val rational = Rational(itemVideo.width, itemVideo.height)
        val params = PictureInPictureParams.Builder().setAspectRatio(rational).build()
        itemVideo.setMediaController(null)
        requireActivity().enterPictureInPictureMode(params)
    }

    //Creates the back button
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val activity = activity as AppCompatActivity
        activity.supportActionBar?.setHomeButtonEnabled(true)
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        super.onCreateOptionsMenu(menu, inflater)
    }

    //This helps press the button
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            android.R.id.home -> {
                Navigation.findNavController(toolbar).popBackStack()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}