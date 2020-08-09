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

    private lateinit var filterModel: FilterModel
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
                context.resources.getStringArray(R.array.districts).toList()
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

            buttonCancel.setOnClickListener {
                onDismissListener.onResetFilterButtonClick()
                dismiss()
            }

            buttonApply.setOnClickListener {
                with(cardViewUtils) {
                    filterModel =
                        FilterModel(
                            address = editTextAddress.text.toString(),
                            districtId = spinnerDistrict.selectedIndex,
                            rating = rating.rating,
                            priceStart = pickerPrice.getCurrentStartValue() * 1000000,
                            priceEnd = pickerPrice.getCurrentEndValue() * 1000000,
                            areaStart = pickerArea.getCurrentStartValue(),
                            areaEnd = pickerArea.getCurrentEndValue(),
                            wifi = if (imageWifi.isSelected) 1 else null,
                            time = if (imageTime.isSelected) 1 else null,
                            key = if (imageKey.isSelected) 1 else null,
                            car = if (imageCar.isSelected) 1 else null,
                            air = if (imageAir.isSelected) 1 else null,
                            heater = if (imageHeater.isSelected) 1 else null
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
        //filterModel.address = query?:""
        show()
    }
}
