package com.gostev.pizza_example.ui.menu

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.gostev.pizza_example.R
import com.gostev.pizza_example.databinding.MenuFragmentBinding
import com.gostev.pizza_example.ui.adapters.BannerAdapter
import com.gostev.pizza_example.ui.adapters.CategoryAdapter
import com.gostev.pizza_example.ui.adapters.PizzaAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MenuFragment : Fragment(R.layout.menu_fragment) {
    private var viewBinding: MenuFragmentBinding? = null
    private val binding get() = viewBinding!!

    private lateinit var bannerAdapter: BannerAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var pizzaAdapter: PizzaAdapter
    private val viewModel by viewModels<MenuViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding = MenuFragmentBinding.bind(view)

        binding.toolbar.apply {
            inflateMenu(R.menu.menu)
        }
        binding.appbar.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (verticalOffset <= -320) {
                binding.card.cardElevation = 50f
            } else {
                binding.card.cardElevation = 0f
            }
        }
        binding.title.text = getString(R.string.moscow)

        visibleProgress(true)

        initAdapters()

        viewModel.banners.observe(viewLifecycleOwner) {
            bannerAdapter.submitList(it)
        }

        viewModel.category.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.placeholder.visibility = View.VISIBLE
            }else {
                binding.placeholder.visibility = View.GONE
                categoryAdapter.submitList(it)
                viewModel.getBanners()
            }
        }

        viewModel.pizza.observe(viewLifecycleOwner) {
            visibleProgress(false)
            pizzaAdapter.submitList(it)
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }

    private fun initAdapters() {
        val pizzaLinearLayoutManager = LinearLayoutManager(requireContext())
        pizzaLinearLayoutManager.orientation = VERTICAL
        val categoryLinearLayoutManager = LinearLayoutManager(requireContext())
        categoryLinearLayoutManager.orientation = HORIZONTAL
        val bannerLinearLayoutManager = LinearLayoutManager(requireContext())
        bannerLinearLayoutManager.orientation = HORIZONTAL

        bannerAdapter = BannerAdapter()
        binding.bannerRecyclerView.layoutManager = bannerLinearLayoutManager
        binding.bannerRecyclerView.adapter = bannerAdapter

        categoryAdapter = CategoryAdapter() {
            binding.appbar.setExpanded(false)
            pizzaLinearLayoutManager.scrollToPositionWithOffset(it.startItemsIndex, 0)
        }
        binding.categoryRecyclerView.layoutManager = categoryLinearLayoutManager
        binding.categoryRecyclerView.adapter = categoryAdapter

        pizzaAdapter = PizzaAdapter {}
        binding.pizzaRecyclerView.layoutManager = pizzaLinearLayoutManager
        binding.pizzaRecyclerView.adapter = pizzaAdapter

        binding.pizzaRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == SCROLL_STATE_IDLE) {
                    val position = pizzaLinearLayoutManager.findFirstVisibleItemPosition()
                    val item = pizzaAdapter.currentList[position]
                    binding.categoryRecyclerView.scrollToPosition(item.categoryIndex)
                    categoryAdapter.setSelectItem(item.categoryIndex)
                }
            }
        })
    }

    private fun visibleProgress(isVisible: Boolean) {
        binding.progress.visibility = if (isVisible) View.VISIBLE else View.GONE
    }
}