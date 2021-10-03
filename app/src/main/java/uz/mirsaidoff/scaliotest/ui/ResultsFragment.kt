package uz.mirsaidoff.scaliotest.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import uz.mirsaidoff.scaliotest.databinding.FragmentResultsBinding
import uz.mirsaidoff.scaliotest.model.UsersRepository

class ResultsFragment : Fragment() {

    companion object {
        private const val ARG_LOGIN = "login"

        fun newInstance(login: String): ResultsFragment {
            val fragment = ResultsFragment()
            fragment.arguments = bundleOf(ARG_LOGIN to login)
            return fragment
        }
    }

    private val vmFactory by lazy { ResultsViewModel.Factory(UsersRepository.create()) }
    private val vm by viewModels<ResultsViewModel> { vmFactory }
    private lateinit var resultsBinding: FragmentResultsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val login = arguments?.getString(ARG_LOGIN)
        vm.searchUsers(login)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentResultsBinding.inflate(inflater, container, false).also {
            resultsBinding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribe()
    }

    private fun subscribe() {
        vm.liveResults.observe(viewLifecycleOwner, {
            Log.d("USERS", it.toString())
        })
    }

}