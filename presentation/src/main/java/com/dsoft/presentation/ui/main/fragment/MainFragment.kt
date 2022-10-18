package com.dsoft.presentation.ui.main.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dsoft.core.service.NotificationService
import com.dsoft.core.util.Resource
import com.dsoft.domain.model.Favourite
import com.dsoft.domain.model.TemperatureType
import com.dsoft.domain.model.Weather
import com.dsoft.presentation.R
import com.dsoft.presentation.common.BaseFragment
import com.dsoft.presentation.databinding.FragmentMainBinding
import com.dsoft.presentation.ui.favourite.fragment.FavouriteFragment
import com.dsoft.presentation.ui.main.vm.MainViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<MainViewModel>()
    private var fusedLocationManager: FusedLocationProviderClient? = null

    private val requestPermission =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    getWeatherByUserLocation()
                }
                else -> {}
            }
        }

    private var temperatureType: TemperatureType = TemperatureType.CELSIUS
        set(value) {
            field = value
            updateWeatherInfo(value)
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fusedLocationManager = LocationServices.getFusedLocationProviderClient(requireActivity())
        isLocationPermissionGranted()
        initObservers()
        initListeners()
    }

    private fun createService(coordinates: Weather.Coordinates) {
        Intent(requireActivity().applicationContext, NotificationService::class.java).apply {
            putExtra("latitude", coordinates.latitude)
            putExtra("longitude", coordinates.longitude)
        }.also(requireActivity()::startService)
    }

    private fun isLocationPermissionGranted(): Boolean {
        val coarseLocationGranted = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        return if (coarseLocationGranted) {
            getWeatherByUserLocation()
            true
        } else {
            createPermissionDialog()
            false
        }
    }

    @SuppressLint("MissingPermission")
    private fun getWeatherByUserLocation() {
        fusedLocationManager?.lastLocation
            ?.addOnSuccessListener { location: Location? ->
                val latitude = location?.latitude ?: 0.0
                val longitude = location?.longitude ?: 0.0
                val coordinates = Weather.Coordinates
                    .createCoordinates(latitude, longitude)
                    .also(::createService)

                viewModel.currentCoordinates = coordinates
                viewModel.getWeatherByUserLocation(coordinates)
            }
    }

    private fun createPermissionDialog() {
        requestPermission.launch(
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    private fun initListeners() {
        with(binding) {
            favouriteCities.setOnClickListener {
                findNavController().navigate(R.id.to_favouriteFragment)
            }
            currentLocation.setOnClickListener {
                isLocationPermissionGranted()
            }
            searchView.addTextChangedListener { editable ->
                viewModel.searchQuery = editable.toString()
            }
        }

        requireActivity().supportFragmentManager.setFragmentResultListener(
            FavouriteFragment.FAVOURITE_RESULT_KEY,
            viewLifecycleOwner
        ) { _, bundle ->
            val favourite = bundle.getParcelable<Favourite>(FavouriteFragment.FAVOURITE_KEY)
            binding.searchView.setText(favourite?.cityName)
            binding.toolbar.title = favourite?.cityName
        }
    }

    private fun updateWeatherInfo(value: TemperatureType) {
        with(binding) {
            viewModel.currentWeatherItem?.let {
                tvCurrentTemp.text = it.mainInfo.getCurrentTemperature(value)
                tvFeelsLike.text = it.mainInfo.getFeelsLikeTemperature(value)
                tvMinMaxTemp.text = it.mainInfo.getCurrentMinMaxTemperature(value)
                tvHumidity.text = it.mainInfo.humidityString
                tvPressure.text = it.mainInfo.pressureString
            }

        }
    }

    private fun initObservers() {
        binding.switchTemperatureType.setOnCheckedChangeListener { _, isChecked ->
            temperatureType = when (isChecked) {
                true -> TemperatureType.FAHRENHEIT
                false -> TemperatureType.CELSIUS
            }
        }
        viewModel.weatherData.observe(viewLifecycleOwner) { resource ->
            resource?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        showAddToFavouritesButton(true)
                        hideProgressBar()
                        with(binding) {
                            addToFavouritesButton.setOnClickListener {
                                viewModel.addCityToFavourites(response.data.name)
                            }
                            toolbar.title = response.data.name
                            viewModel.currentWeatherItem = response.data
                            updateWeatherInfo(temperatureType)
                        }
                    }
                    is Resource.Loading -> {
                        showProgressBar()
                        showAddToFavouritesButton(false)
                    }
                    is Resource.Failure -> {
                        hideProgressBar()
                        showAddToFavouritesButton(false)
                        showErrorDialog(response.message.toString())
                    }
                }
            }
        }
    }

    private fun showAddToFavouritesButton(show: Boolean) {
        when (show) {
            true -> binding.addToFavouritesButton.visibility = VISIBLE
            false -> binding.addToFavouritesButton.visibility = GONE
        }
    }

    override fun showProgressBar() {
        binding.progressBarContainer.visibility = VISIBLE
    }

    override fun hideProgressBar() {
        binding.progressBarContainer.visibility = GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}