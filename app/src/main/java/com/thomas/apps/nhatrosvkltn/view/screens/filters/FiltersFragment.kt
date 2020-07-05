package com.thomas.apps.nhatrosvkltn.view.screens.filters

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.jem.rubberpicker.RubberRangePicker
import com.thomas.apps.nhatrosvkltn.R
import com.thomas.apps.nhatrosvkltn.databinding.FragmentFiltersBinding
import com.thomas.apps.nhatrosvkltn.model.FilterModel


class FiltersFragment(context: Context, themeResId: Int) : Dialog(context, themeResId) {
    lateinit var onDismissListener: OnDismissListener

    interface OnDismissListener {
        fun onApplyButtonClick(filter: FilterModel) {}
        fun onResetFilterButtonClick() {}
        fun onCancelButtonClick() {}
    }

    private var filterModel = FilterModel()
    private lateinit var binding: FragmentFiltersBinding
    private lateinit var viewModel: FiltersViewModel

//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        binding = FragmentFiltersBinding.inflate(layoutInflater)
//
//        return binding.root
//    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
////        viewModel = ViewModelProviders.of(this).get(FiltersViewModel::class.java)
//        viewModel = ViewModelProvider(this).get(FiltersViewModel::class.java)
//        // TODO: Use the ViewModel
//        init()
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentFiltersBinding.inflate(layoutInflater)

        setContentView(binding.root)

        init()
    }

    private fun init() {
        with(binding) {
            val districts: List<String> =
                context.resources.getStringArray(R.array.districts).toList().drop(1)
            binding.spinnerDistrict.setItems(districts)


            pickerPrice.setOnRubberRangePickerChangeListener(object :
                RubberRangePicker.OnRubberRangePickerChangeListener {
                override fun onProgressChanged(
                    rangePicker: RubberRangePicker,
                    startValue: Int,
                    endValue: Int,
                    fromUser: Boolean
                ) {
                    textViewPriceStart.text = (rangePicker.getCurrentStartValue() / 10.0).toString()
                    textViewPriceEnd.text = (rangePicker.getCurrentEndValue() / 10.0).toString()
                }

                override fun onStartTrackingTouch(
                    rangePicker: RubberRangePicker,
                    isStartThumb: Boolean
                ) {

                }

                override fun onStopTrackingTouch(
                    rangePicker: RubberRangePicker,
                    isStartThumb: Boolean
                ) {
                }

            }
            )

            pickerArea.setOnRubberRangePickerChangeListener(object :
                RubberRangePicker.OnRubberRangePickerChangeListener {
                override fun onProgressChanged(
                    rangePicker: RubberRangePicker,
                    startValue: Int,
                    endValue: Int,
                    fromUser: Boolean
                ) {
                    textViewAreaStart.text = rangePicker.getCurrentStartValue().toString()
                    textViewAreaEnd.text = rangePicker.getCurrentEndValue().toString()
                }

                override fun onStartTrackingTouch(
                    rangePicker: RubberRangePicker,
                    isStartThumb: Boolean
                ) {

                }

                override fun onStopTrackingTouch(
                    rangePicker: RubberRangePicker,
                    isStartThumb: Boolean
                ) {
                }

            }
            )

            pickerPrice.setCurrentStartValue(10)
            pickerPrice.setCurrentEndValue(30)
            pickerArea.setCurrentStartValue(10)
            pickerArea.setCurrentEndValue(30)

            buttonCancel.setOnClickListener {
                onDismissListener.onResetFilterButtonClick()
                dismiss()
            }

            buttonApply.setOnClickListener {
                with(cardViewUtils) {
                    filterModel = FilterModel(
                        editTextAddress.text.toString(),
                        districts[spinnerDistrict.selectedIndex],
                        rating.rating,
                        pickerPrice.getCurrentStartValue() * 1000000,
                        pickerPrice.getCurrentEndValue() * 1000000,
                        pickerArea.getCurrentStartValue(),
                        pickerArea.getCurrentEndValue(),
                        imageWifi.isSelected,
                        imageTime.isSelected,
                        imageKey.isSelected,
                        imageCar.isSelected,
                        imageAir.isSelected,
                        imageHeater.isSelected
                    )
                }

                onDismissListener.onApplyButtonClick(filterModel)
                dismiss()
            }
            imageCancel.setOnClickListener {
                onDismissListener.onCancelButtonClick()
                dismiss()
            }
        }
    }

    private fun showFilter(query: String?) {
        filterModel.address = query
        show()
    }
}
