package com.mbeyaztas.csv.file.viewer.presentation.list

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.mbeyaztas.csv.file.csvcore.CsvFile
import com.mbeyaztas.csv.file.viewer.R
import com.mbeyaztas.csv.file.viewer.commons.mappers.RowMapper
import com.mbeyaztas.csv.file.viewer.commons.utils.Result
import com.mbeyaztas.csv.file.viewer.databinding.FragmentListBinding
import dagger.hilt.android.AndroidEntryPoint
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter


@AndroidEntryPoint
class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CsvViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
    }

    private fun initViews() {
        binding.selectCsv.setOnClickListener {
            startCsvIntent()
        }
    }

    private fun initObservers() {
        viewModel.csvData.observe(viewLifecycleOwner, {
            when (it) {
                Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.descText.visibility = View.GONE
                    binding.scrollView.visibility = View.GONE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.scrollView.visibility = View.VISIBLE
                    it.let {
                        if (it.data != null) {
                            updateTable(it.data)
                        } else {
                            showErrorMessage(R.string.error_file_read)
                        }
                    }
                }
                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.descText.visibility = View.VISIBLE
                    showErrorMessage(R.string.error_file_read)
                }
            }
        })
    }

    private fun updateTable(csvFile: CsvFile) {
        val csvFileToArray = RowMapper().csvFileToArray(csvFile)
        val adapter: SimpleTableDataAdapter
        // With header
        if (csvFile.size() > 1) {
            binding.tableView.columnCount = csvFileToArray[0].size - 1
            binding.tableView.headerAdapter =
                SimpleTableHeaderAdapter(context, *csvFileToArray[0])

            adapter = SimpleTableDataAdapter(
                context,
                csvFileToArray.subList(1, csvFileToArray.size)
            )
        } else {
            // Without header
            adapter = SimpleTableDataAdapter(
                context,
                csvFileToArray
            )
        }
        binding.tableView.setDataAdapter(adapter)
    }

    @SuppressLint("InlinedApi")
    private fun startCsvIntent() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "text/comma-separated-values"
        }

        csvResultCallback.launch(intent)
    }

    private var csvResultCallback = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data?.data
            if (data != null) fetchData(data)
            else showErrorMessage(R.string.error_permission)
        } else {
            showErrorMessage(R.string.error_permission)
        }
    }

    private fun fetchData(uri: Uri) {
        viewModel.readCsv(uri.toString())
    }

    private fun showErrorMessage(message: Int) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}