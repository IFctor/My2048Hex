package hu.uni.miskolc.iit.mobile.my2048.app.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import hu.uni.miskolc.iit.mobile.my2048.app.R
import hu.uni.miskolc.iit.mobile.my2048.app.databinding.StartGameFragmentBinding

class StartGameFragment : Fragment(R.layout.start_game_fragment) {

    private var binding: StartGameFragmentBinding? = null

    companion object {
        fun newInstance() = StartGameFragment()
    }

    private lateinit var viewModel: StartGameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.start_game_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = StartGameFragmentBinding.bind(view)
        viewModel = ViewModelProvider(this).get(StartGameViewModel::class.java)

        binding?.startGameButton?.setOnClickListener {
            findNavController().navigate(R.id.action_startGameFragment_to_levelSelectorFragment)
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

}