package uz.mirsaidoff.scaliotest.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
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
    private val adapter by lazy(LazyThreadSafetyMode.NONE) { UsersAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentResultsBinding.inflate(inflater, container, false).also {
            resultsBinding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribe()
        resultsBinding.rvUsers.apply {
            adapter = this@ResultsFragment.adapter
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
            layoutManager = LinearLayoutManager(requireContext())
        }

        adapter.addLoadStateListener { state ->
            val refreshState = state.refresh
            resultsBinding.progress.isVisible = refreshState == LoadState.Loading
            resultsBinding.rvUsers.isVisible = refreshState != LoadState.Loading

            if (refreshState is LoadState.Error) {
                Log.e("ErrorLoading", refreshState.error.message.toString())
            }
        }
    }

    private fun subscribe() {
        val login = arguments?.getString(ARG_LOGIN)
        login?.also { l ->
            lifecycleScope.launchWhenResumed {
                vm.searchUsers(l).collectLatest { adapter.submitData(it) }
            }
        }
    }

}