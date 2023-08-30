package com.exchangerates.best.screens.home_screen

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exchangerates.best.data.model.Currencies
import com.exchangerates.best.data.model.Data
import com.exchangerates.best.data.model.ExchangeDates
import com.exchangerates.best.data.model.RateItemData
import com.exchangerates.best.data.model.UZS
import com.exchangerates.best.data.repository.abstraction.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    private val _openDialog: MutableState<Boolean> = mutableStateOf(false)
    val openDialog: State<Boolean> = _openDialog

    private var lastSearchText = ""
    private val _listDataLiveData = MutableLiveData<List<RateItemData>>()
    val listDataLiveData: LiveData<List<RateItemData>> get() = _listDataLiveData

    var listData = listOf<RateItemData>()

    fun getList(date: String) {
        viewModelScope.launch {
            repository.getList(date)
                .catch {
                    Log.d("TAGTAG", "getList: $it")
                }
                .collect {
                    _listDataLiveData.postValue(it)
                    Log.d("TAGTAG", "getList: $it")
                    listData = it
                }
        }
    }

    fun showDialog() {
        _openDialog.value = true
    }

    fun hideDialog() {
        _openDialog.value = false
    }

    fun getListSearch(str: String) {
        viewModelScope.launch {
            val list = arrayListOf<RateItemData>()
            if (str != "") {
                if (str != lastSearchText) {
                    listData.forEach { it ->
                        if ((it.ccyNmUZ ?: "").contains(str, ignoreCase = true)) {
                            list.add(it)
                        }
                    }
                    _listDataLiveData.postValue(list)
                    lastSearchText = str
                }
            } else {
                _listDataLiveData.postValue(listData)
            }
        }
    }

    private val _graphListLiveData = MutableLiveData<RateItemData>()
    val graphListLiveData: LiveData<RateItemData> get() = _graphListLiveData

    fun getGraphList(
        startHistoryData: String,
        endHistoryData: String,
        currency: String,
        current: RateItemData
    ) {
        viewModelScope.launch {
            current.exchangeDates = ExchangeDates(
                listOf(
                    Data(Currencies(UZS("uzs", Math.random() % 1500000)), "0000000012.03"),
                    Data(Currencies(UZS("uzs", Math.random() % 1500000)), "0000000012.03"),
                    Data(Currencies(UZS("uzs", Math.random() % 1500000)), "0000000012.03"),
                    Data(Currencies(UZS("uzs", Math.random() % 1500000)), "0000000012.03"),
                    Data(Currencies(UZS("uzs", Math.random() % 1500000)), "0000000012.03"),
                    Data(Currencies(UZS("uzs", Math.random() % 1500000)), "0000000012.03"),
                    Data(Currencies(UZS("uzs", Math.random() % 1500000)), "0000000012.03"),
                    Data(Currencies(UZS("uzs", Math.random() % 1500000)), "0000000012.03"),
                    Data(Currencies(UZS("uzs", Math.random() % 1500000)), "0000000012.03"),
                )
            )
            _graphListLiveData.postValue(current)
        }
    }


    private val _theme = MutableLiveData<Boolean>()
    val theme: LiveData<Boolean> = _theme

    fun onThemeChanged(newTheme: Boolean) {
        _theme.postValue(newTheme)
    }

}