package uz.yusufbekibragimov.valyutauz.screens.home_screen

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import uz.yusufbekibragimov.valyutauz.data.model.RateItemData
import uz.yusufbekibragimov.valyutauz.data.repository.abstraction.Repository
import javax.inject.Inject

/**
 * Created by Ibragimov Yusufbek
 * Date: 24.02.2022
 * Project: ComposeNavigation
 **/

/**
 * [Flow] extensions function that collects loading and exception
 * When flow start default calls loading state and stops on each flow and exception
 * @param [action] call's [onEach] flow
 * */

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
                    Log.d("III", "getList: ${it.message}")
                }
                .collect {
                    _listDataLiveData.postValue(it)
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
            repository.getGraphList(startHistoryData, endHistoryData, currency)
                .catch {}
                .collect { it_Exchange ->
                    current.exchangeDates = it_Exchange
                    _graphListLiveData.postValue(current)
                }
        }
    }


    private val _theme = MutableLiveData<Boolean>()
    val theme: LiveData<Boolean> = _theme

    fun onThemeChanged(newTheme: Boolean) {
        _theme.postValue(newTheme)
    }

}