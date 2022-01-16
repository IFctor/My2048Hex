package hu.uni.miskolc.iit.mobile.my2048.app.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import hu.uni.miskolc.iit.mobile.my2048.app.R
import hu.uni.miskolc.iit.mobile.my2048.app.databinding.LevelSelectorFragmentBinding
import hu.uni.miskolc.iit.mobile.my2048.core.domain.Level

import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.AdapterView.OnItemClickListener


class LevelSelectorSelectorFragment : Fragment() {

    companion object {
        fun newInstance() = LevelSelectorSelectorFragment()
    }

    private var binding: LevelSelectorFragmentBinding? = null

    private lateinit var viewModel: LevelSelectorViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.level_selector_fragment, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = LevelSelectorFragmentBinding.bind(view)
        viewModel = ViewModelProvider(this).get(LevelSelectorViewModel::class.java)

        val menuItems: Array<String> =
            enumValues<Level>().map { it.display }.toTypedArray()

        val myListView: ListView = view.findViewById(R.id.simpleListView)

        val listViewAdapter: ArrayAdapter<String>? =
            activity?.let { ArrayAdapter(it, android.R.layout.simple_list_item_1, menuItems) }

        myListView.adapter = listViewAdapter
        myListView.onItemClickListener = OnItemClickListener { adapterView, listenetView, pos, id ->
            val level: Level =
                gatherLevel(adapterView.getItemAtPosition(pos) as String)
            val bundle = bundleOf("level" to level)
            findNavController().navigate(R.id.action_categoryPickerFragment_to_gameFragment, bundle)
        }
    }

    private fun gatherLevel(display: String): Level {

        return Level.values().firstOrNull { it.display == display } ?: Level.FOUR

    }

}