package hu.uni.miskolc.iit.mobile.my2048.app.fragment

import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import hu.uni.miskolc.iit.mobile.my2048.app.R
import hu.uni.miskolc.iit.mobile.my2048.app.databinding.GameFragmentBinding
import hu.uni.miskolc.iit.mobile.my2048.core.domain.Level
import org.koin.android.ext.android.inject

import android.widget.TextView
import android.view.Gravity
import android.widget.FrameLayout


class GameFragment : Fragment() {

    companion object {
        fun newInstance() = GameFragment()
    }

    private var binding: GameFragmentBinding? = null

    private val viewModel: GameViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.game_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = GameFragmentBinding.bind(view)
        //val viewModel = ViewModelProvider(this).get(GameViewModel::class.java)
        binding?.vm = viewModel
        binding?.lifecycleOwner = this


        binding?.gameHeader?.giveUpButton?.setOnClickListener {
            viewModel.endGame()
        }

        viewModel.getCardBoard().observe(viewLifecycleOwner, {
            onChangeGameBoard()
        })

        binding?.gameFooter?.pause?.setOnClickListener{viewModel.pauseGame()
            /*binding?.gameFooter?.pause?.visibility= View.GONE
            binding?.gameFooter?.resume?.visibility= View.VISIBLE*/
        }
        binding?.gameFooter?.resume?.setOnClickListener{viewModel.resumeGame()
            /*binding?.gameFooter?.resume?.visibility= View.GONE
            binding?.gameFooter?.pause?.visibility= View.VISIBLE*/
        }

        binding?.gameFooter?.undo?.setOnClickListener{viewModel.undoGame()}

        viewModel.getGameOver().observe(viewLifecycleOwner, {
            val bundle = bundleOf("score" to viewModel.getScore())
            findNavController().navigate(R.id.action_gameFragment_to_resultFragment, bundle)
        })

        val level = (arguments?.get("level") as? Level) ?: Level.FOUR

        viewModel.startGame(level)

        view.setOnTouchListener { v, event ->
            viewModel.onTouchListener(v, event)
        }

    }

    fun onChangeGameBoard() {
        val gridLayout = requireActivity().findViewById<View>(R.id.gameView) as GridLayout
        gridLayout.alignmentMode = GridLayout.ALIGN_BOUNDS
        gridLayout.removeAllViews()


        var cardBoardCards = viewModel.getCardBoard().value?.getCards()
        if (cardBoardCards != null) {

            gridLayout.rowCount = cardBoardCards.size
            gridLayout.columnCount = cardBoardCards[0].size

            for (x in 0 until gridLayout.rowCount) {
                for (y in 0 until gridLayout.columnCount) {
                    var titleText: TextView = TextView(context)
                    titleText.text = cardBoardCards[x][y].text
                    titleText.setBackgroundColor(cardBoardCards[x][y].bgColor)
                    titleText.setAutoSizeTextTypeUniformWithConfiguration(16,32,1,TypedValue.COMPLEX_UNIT_DIP)

                    titleText.gravity = Gravity.CENTER

                    val param = FrameLayout.LayoutParams(-1, -1)
                    param.height = GridLayout.LayoutParams.MATCH_PARENT
                    param.width = GridLayout.LayoutParams.MATCH_PARENT
                    param.setMargins(10, 10, 0, 0)

                    var frameLayout = context?.let { FrameLayout(it) }
                    frameLayout?.addView(titleText, param)

                    var cardSize = (Math.min(
                        gridLayout.width,
                        gridLayout.height
                    ) - 10) / Math.max(gridLayout.rowCount, gridLayout.columnCount)
                    gridLayout.addView(frameLayout, cardSize, cardSize)

                }
            }
        }
    }
}