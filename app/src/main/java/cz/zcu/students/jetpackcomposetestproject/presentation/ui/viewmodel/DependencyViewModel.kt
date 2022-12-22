package cz.zcu.students.jetpackcomposetestproject.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import cz.zcu.students.jetpackcomposetestproject.data.repository.DependencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

// Lazy<DependencyRepository> will create repository on usage, not on injection

@HiltViewModel
class DependencyViewModel @Inject constructor(
    repository: DependencyRepository,
) : ViewModel() {
    val dependencyString = repository.getString()
    val stringOne = repository.getStringOne()
    val stringTwo = repository.getStringTwo()
}