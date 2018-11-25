package com.melmy.melmyprototype.version

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.melmy.melmyprototype.R
import com.melmy.melmyprototype.databinding.ActivityVersionBinding
import com.melmy.melmyprototype.utils.InjectorUtil
import com.melmy.melmyprototype.utils.getAppVersion
import com.melmy.melmyprototype.utils.needUpdate
import org.jsoup.Jsoup
import java.io.IOException
import java.lang.ref.WeakReference

//TODO : MVVM으로 수정
class VersionActivity : AppCompatActivity() {
    lateinit var viewModel: VersionViewModel
    lateinit var binding: ActivityVersionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_version)

        setUpToolbar()
        setUpViews()
        setUpViewModel()

        VersionChecker(this).execute()
    }

    private fun setUpToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_back)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setUpViewModel() {
        val factory = InjectorUtil.provideVersionViewModelFactory(this)
        viewModel = ViewModelProviders.of(this, factory).get(VersionViewModel::class.java)
    }

    private fun setUpViews() {
        with(binding) {
            deviceVersionTextView.text = getAppVersion()
        }
    }

    private fun updateUI(marketVersion: String?) {
        if (marketVersion == null) {
            binding.updateButton.text = getString(R.string.message_network_not_enabled)
            binding.updateButton.isEnabled = true
            binding.updateButton.setOnClickListener { this.attemptUpdate(it) }
            return
        }
        val deviceVersion = getAppVersion()
        if (needUpdate(deviceVersion, marketVersion)) {
            binding.updateButton.text = getString(R.string.action_update)
            binding.updateButton.isEnabled = true
            binding.updateButton.setOnClickListener { this.attemptUpdate(it) }
        } else {
            binding.updateButton.text = getString(R.string.message_version_latest)
            binding.updateButton.isEnabled = false
        }
    }

    private fun attemptUpdate(view: View) {
        val appPackageName = packageName
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName")))
        } catch (anfe: android.content.ActivityNotFoundException) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")))
        } catch (e: Exception) {
            Toast.makeText(this, getString(R.string.error_common), Toast.LENGTH_SHORT).show()
        }
    }

    //마켓에 올려야 작동함
    private class VersionChecker internal constructor(ref: VersionActivity) : AsyncTask<Void, Void, String>() {
        private val ref: WeakReference<VersionActivity> = WeakReference(ref)

        override fun doInBackground(vararg voids: Void): String? {
            return try {
                Jsoup.connect("https://play.google.com/store/apps/details?id=com.melmy.melmyprototype&hl=en")
                        .timeout(20000)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                        .referrer("http://www.google.com")
                        .get()
                        .select("div[itemprop=softwareVersion]")
                        .first()
                        .ownText()
            } catch (e: IOException) {
                null
            }
        }

        override fun onPostExecute(marketVersion: String?) {
            super.onPostExecute(marketVersion)
            val activity = ref.get() ?: return
            activity.updateUI(marketVersion)
        }
    }

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, VersionActivity::class.java)
        }
    }
}
