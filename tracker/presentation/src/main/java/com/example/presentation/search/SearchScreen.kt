package com.example.presentation.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.util.UiEvent
import com.example.core_ui.LocalSpacing
import com.example.domain.model.MealType
import com.example.presentation.search.components.SearchTextField
import com.example.presentation.search.components.TrackableFoodItem
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDate

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(
    snackbarHostState: SnackbarHostState,
    mealName: String,
    dayOfMonth: Int,
    month: Int,
    year: Int,
    onNavigateUp: () -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val spacing = LocalSpacing.current
    val state = viewModel.searchState
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(key1 = keyboardController) {
        viewModel.uiEvent.collectLatest { event ->
            when (event) {
                is UiEvent.Navigate -> Unit
                is UiEvent.NavigateUp -> onNavigateUp()
                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(message = event.message.asString(context))
                    keyboardController?.hide()
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.spaceMedium)
    ) {
        Text(
            text = stringResource(id = core.R.string.add_meal, mealName),
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        SearchTextField(
            text = state.query,
            onValueChange = { viewModel.onEvent(SearchEvent.OnSearchChange(it)) },
            onSearch = {
                keyboardController?.hide()
                viewModel.onEvent(SearchEvent.OnSearch)
            },
            onFocusChange = {
                viewModel.onEvent(SearchEvent.OnSearchFocusChange(it.isFocused))
            },
            shouldShowHint = state.isHintVisible
        )
        Spacer(modifier = Modifier.height(spacing.spaceSmall))
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(state.trackableFoodUiState) { trackableFood ->
                TrackableFoodItem(
                    trackableFoodUiState = trackableFood,
                    onClick = {
                        viewModel.onEvent(SearchEvent.OnToggleTrackableFood(trackableFood.trackableFood))
                    },
                    onAmountChange = {amount ->
                        viewModel.onEvent(
                            SearchEvent.OnAmountForFoodChange(
                                trackableFood.trackableFood,
                                amount
                            )
                        )
                    },
                    onTrack = {
                        keyboardController?.hide()
                        viewModel.onEvent(
                            SearchEvent.OnTrackFoodClick(
                                trackableFood.trackableFood,
                                MealType.fromString(mealName),
                                LocalDate.of(year, month, dayOfMonth)
                            )
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            if (state.isSearching){
                CircularProgressIndicator()
            } else if (state.trackableFoodUiState.isEmpty()){
                Text(text = stringResource(id = core.R.string.no_results))
            }
        }

    }
}