package com.gabrielsanchez.pr501.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gabrielsanchez.pr501.models.SimuladorHipoteca

class SimuladorHipotecaViewModel() : ViewModel() {

    private val _capital = MutableLiveData<String>()
    val capital: LiveData<String>
        get() = _capital

    private val _plazo = MutableLiveData<String>()
    val plazo: LiveData<String>
        get() = _plazo


    fun setCapital(capital: String) {
        _capital.value = capital
    }

    fun setPlazo(plazo: String) {
        _plazo.value = plazo
    }

    fun checkCapitalAndPlazo(): Boolean {
        return _capital.value?.isNotEmpty() == true && _plazo.value?.isNotEmpty() == true
    }


    fun calcularHipoteca(): Double {
        val simuladorHipoteca = SimuladorHipoteca(
            capital = _capital.value?.toDouble() ?: 0.0,
            plazo = _plazo.value?.toInt() ?: 0,
            tasaInteres = SimuladorHipoteca.DEFAULT_TASA_INTERES
        )
        return simuladorHipoteca.calcularCuota()
    }
}
