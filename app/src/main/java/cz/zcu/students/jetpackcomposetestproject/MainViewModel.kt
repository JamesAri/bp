package cz.zcu.students.jetpackcomposetestproject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _state = MutableStateFlow("")
    val state = _state.asStateFlow()

    val countDownFlow = flow<Int> {
        val startValue = 10
        var currentValue = startValue

        emit(startValue)
        while (currentValue > 0) {
            delay(1000L)
            currentValue--
            emit(currentValue)
        }
    }

    val dishFlow = flow<String> {
        delay(250L)
        emit("Appetizer")
        delay(1000L)
        emit("Main dish")
        delay(100L)
        emit("Dessert")
    }

    init {
//        collectFlow()
//        collectVsOnEach()
//        terminalFlowOperators()
        bufferDemo()
    }

    private fun bufferDemo() {
        viewModelScope.launch {
            dishFlow
                .onEach { println("FLOW: $it is delivered") }
//                .buffer()
                .conflate()
                .collect {
                    println("FLOW: Now eating $it")
                    delay(2000L)
                    println("FLOW: Finished eating $it")
                }
        }
    }

    private fun terminalFlowOperators() {
        viewModelScope.launch {
            val count = countDownFlow
                .filter { time -> time % 2 == 0 }
                .map { time -> time * time }
                .onEach { time -> println("The current countdown is $time") }
                .count { time -> time % 2 == 0 }
            println("Even count is $count")
        }

        viewModelScope.launch {
            val reduceResult = countDownFlow
                .reduce { acc, value ->
                    acc + value
                }
            println("Sum is $reduceResult")
        }

        viewModelScope.launch {
            val foldResult = countDownFlow
                .fold(100) { acc, value ->
                    acc + value
                }
            println("Fold is $foldResult")
        }
    }


    private fun collectVsOnEach() {
        countDownFlow
            .onEach { time -> println("OnEach: $time") }
            .launchIn(viewModelScope)

        viewModelScope.launch {
            countDownFlow.collect { time -> println("Collect: $time") }
        }

    }

    private fun collectFlow() {
        viewModelScope.launch {
            countDownFlow
                .filter { time -> time % 2 == 0 }
                .map { time -> time * time }
                .onEach { time -> println("The current countdown is $time - I return Flow") }
                .collect { time -> println("The current countdown is $time - I return Unit") }
        }
    }


}