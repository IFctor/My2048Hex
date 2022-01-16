package hu.uni.miskolc.iit.mobile.my2048.app.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import hu.uni.miskolc.iit.mobile.my2048.app.R
import hu.uni.miskolc.iit.mobile.my2048.app.databinding.ResultFragmentBinding
import org.koin.android.ext.android.inject

class ResultFragment : Fragment() {

    companion object {
        fun newInstance() = ResultFragment()
    }

    private lateinit var binding : ResultFragmentBinding
    private val viewModel: ResultViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.result_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ResultFragmentBinding.bind(view)
        binding?.vm = viewModel
        binding?.lifecycleOwner = this

        //viewModel = ViewModelProvider(this).get(ResultViewModel::class.java)
        val score = arguments?.getInt("score")
        viewModel.setScore(score ?: 0)

        binding?.homeButton?.setOnClickListener {

            findNavController().navigate(R.id.action_resultFragment_to_startGameFragment, null)
        }
    }


}