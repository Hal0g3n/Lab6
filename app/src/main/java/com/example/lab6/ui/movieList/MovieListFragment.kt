package com.example.lab6.ui.movieList

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.GridLayoutManager
import com.example.lab6.R
import kotlinx.android.synthetic.main.movie_list_fragment.*

class MovieListFragment : Fragment(), MovieRecyclerAdapter.OnItemClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.movie_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        movieRecyclerView.adapter = MovieRecyclerAdapter(this)
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
    }

    override fun onItemClick(position: Int, viewHolder: MovieRecyclerAdapter.ViewHolder) {
        val extras = FragmentNavigatorExtras(
                viewHolder.itemVideo to "itemVideo"
        )
        val action = MovieListFragmentDirections.actionMovieListFragmentToDisplayMovieFragment(position)
        Navigation.findNavController(requireView()).navigate(action, extras)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    //Creates the back button
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
    }

    //This helps press the button
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.menu_cart -> {
                Navigation.findNavController(requireView())
                    .navigate(R.id.action_movieListFragment_to_cartFragment)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}