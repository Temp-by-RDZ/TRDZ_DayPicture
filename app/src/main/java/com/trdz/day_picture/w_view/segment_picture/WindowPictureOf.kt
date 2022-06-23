package com.trdz.day_picture.w_view.segment_picture

import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.trdz.day_picture.R
import com.trdz.day_picture.w_view.Leader
import com.trdz.day_picture.w_view.MainActivity
import com.trdz.day_picture.x_view_model.MainViewModel
import com.trdz.day_picture.x_view_model.StatusProcess
import androidx.constraintlayout.widget.ConstraintLayout
import coil.clear
import coil.request.ImageRequest
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.trdz.day_picture.databinding.FragmentWindowPofBinding
import com.trdz.day_picture.z_utility.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.thread
import java.lang.Thread.sleep
import android.graphics.BitmapFactory
import java.io.*
import java.net.HttpURLConnection
import java.net.URL


class WindowPictureOf: Fragment() {

	//region Elements
	private var _executors: Leader? = null
	private val executors get() = _executors!!
	private var _binding: FragmentWindowPofBinding? = null
	private val binding get() = _binding!!
	private var _viewModel: MainViewModel? = null
	private val viewModel get() = _viewModel!!
	private var _bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>? = null
	private val bottomSheetBehavior get() = _bottomSheetBehavior!!
	private lateinit var prefix: String
	//endregion

	//region Base realization
	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
		_executors = null
		_viewModel = null
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		arguments?.let {
			prefix = it.getString(KEY_PREFIX, PREFIX_POD)
		}
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		_binding = FragmentWindowPofBinding.inflate(inflater, container, false)
		_executors = (requireActivity() as MainActivity)
		_viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		val observer = Observer<StatusProcess> { renderData(it) }
		when (prefix) {
			PREFIX_POD -> viewModel.getPodData().observe(viewLifecycleOwner, observer)
			PREFIX_MRP -> viewModel.getPomData().observe(viewLifecycleOwner, observer)
			PREFIX_EPC -> viewModel.getPoeData().observe(viewLifecycleOwner, observer)
		}
		buttonBinds()
		initialize()
	}

	//endregion

	//region Main functional
	private fun buttonBinds() {
		with(binding) {
			_bottomSheetBehavior = BottomSheetBehavior.from(popupSheet.bottomSheetContainer)
			imageView.setOnClickListener { bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED }
			bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
			bottomSheetBehavior.isHideable = true
			bottomSheetBehavior.addBottomSheetCallback(object:
				BottomSheetBehavior.BottomSheetCallback() {
				override fun onStateChanged(bottomSheet: View, newState: Int) {
					when (newState) {
						BottomSheetBehavior.STATE_DRAGGING -> {
						}
						BottomSheetBehavior.STATE_COLLAPSED -> {
							bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
						}
						BottomSheetBehavior.STATE_EXPANDED -> {
						}
						BottomSheetBehavior.STATE_HALF_EXPANDED -> {
						}
						BottomSheetBehavior.STATE_HIDDEN -> {
						}
						BottomSheetBehavior.STATE_SETTLING -> {
						}
					}
				}

				override fun onSlide(bottomSheet: View, slideOffset: Float) {}

			})
		}
	}

	private fun initialize() {
		viewModel.initialize(prefix)
	}

	private fun renderData(material: StatusProcess) {
		when (material) {
			StatusProcess.Load -> {
				bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
			}
			is StatusProcess.Error -> {
				Log.d("@@@", "App - $prefix catch $material.code")
				binding.imageView.clear()
				binding.imageView.setBackgroundResource(R.drawable.nofile)
				binding.popupSheet.title.text = getString(R.string.ERROR_TITLE)
				binding.popupSheet.explanation.text = StringBuilder(getString(R.string.ERROR_DISCRIPTIOn)).apply {
					append("\n")
					append(getString(R.string.Error_code_message))
					append(" ")
					append(material.code)
					append("\n")
					when (material.code) {
						-2 -> append(getString(R.string.error_desc_m2))
						-1 -> append(getString(R.string.error_desc_m1))
						in 200..299 -> append(getString(R.string.error_desc_200))
						in 300..399 -> append(getString(R.string.error_desc_300))
						in 400..499 -> append(getString(R.string.error_desc_400))
						in 500..599 -> append(getString(R.string.error_desc_500))
						else -> append(getString(R.string.error_desc_0))
					}
				}
				bottomSheetBehavior.halfExpandedRatio = 0.35f
				bottomSheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
			}
			is StatusProcess.Success -> {
				Log.d("@@@", "App - $prefix render")
				binding.imageView.setBackgroundResource(R.drawable.plaseholder)
				binding.imageView.load(material.data.url) {
					//placeholder(R.drawable.image_still_loading) баг скалирования
					listener(
						onSuccess = { _, _ -> // do nothing

						},
						onError = { request: ImageRequest, throwable: Throwable ->
							Log.d("@@@", "App - coil error $throwable")
							binding.imageView.loadSvg(material.data.url!!) //если вдруг coil помрет
						})
				}
				thread { sleep(5000L); galleryPic(material.data.url!!) }
				binding.popupSheet.title.text = material.data.name
				binding.popupSheet.explanation.text = material.data.description
			}
			is StatusProcess.Video -> {
				Log.d("@@@", "App - $prefix show")
				binding.youtubePlayer.visibility = View.VISIBLE
			}
		}
	}

	//endregion
	private fun galleryPic(path: String) {
		Log.d("@@@", "App - Start saving image")
		val file = getDisc()
		if (!file.exists() && !file.mkdirs()) { Log.d("@@@", "App - Gallery not found");return}
		if (!file.exists() && !file.mkdirs()) { Log.d("@@@", "App - Image don't exist");return}
		val calendar = Calendar.getInstance()
		calendar.add(Calendar.DATE, 0)
		val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
		val name = "${file.absolutePath}/img$prefix${dateFormat.format(calendar.time)}.jpeg";
		val newFile = File(name);
		try {
			val bitmap = getBitmapFromURL(path)
			val fOut = FileOutputStream(newFile)
			bitmap.compress(Bitmap.CompressFormat.PNG, 85, fOut)
			fOut.flush()
			fOut.close()
			Log.d("@@@", "App - Saving complete")
		}
		catch (ignored: FileNotFoundException) { Log.d("@@@T", "App - File corrupted") }
		catch (e: IOException) { Log.d("@@@", e.message.toString())
		}
	}

	private fun getBitmapFromURL(src: String): Bitmap {
			val url = URL(src)
			val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
			connection.doInput = true
			connection.connect()
			val input: InputStream = connection.inputStream
		return BitmapFactory.decodeStream(input)
	}

	private fun getDisc(): File {
		val file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
		return File(file, "Picture of my Day");
	}


	companion object {
		@JvmStatic
		fun newInstance(prefix: String) =
			WindowPictureOf().apply {
				arguments = Bundle().apply {
					putString(KEY_PREFIX, prefix)
				}
			}
	}

}