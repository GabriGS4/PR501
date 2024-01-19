package com.gabrielsanchez.pr501.models

import kotlin.math.pow

class SimuladorHipoteca(
    val capital: Double,
    val plazo: Int,
    val tasaInteres: Double
) {

    companion object {
        const val DEFAULT_TASA_INTERES = 0.01605
    }

    fun calcularCuota(): Double {
        return capital * tasaInteres/12/(1-Math.pow(1+(tasaInteres/12),-plazo.toDouble()*12));
    }
}
