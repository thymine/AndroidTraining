package me.zhang.laboratory.ui

import android.content.Context
import android.os.Bundle
import android.telephony.TelephonyManager
import android.telephony.TelephonyManager.*
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import me.zhang.laboratory.databinding.ActivityTelephonyBinding


/**
 * Created by zhangxiangdong on 2019/6/26 14:48.
 */
class TelephonyActivity : BaseActivity() {

    private lateinit var manager: TelephonyManager
    private lateinit var binding: ActivityTelephonyBinding

    companion object {
        const val NETWORK_TYPE_GSM = 16
        const val NETWORK_TYPE_TD_SCDMA = 17
        const val NETWORK_TYPE_IWLAN = 18
        const val NETWORK_TYPE_LTE_CA = 19

        const val NETWORK_CLASS_2_G = "2G"
        const val NETWORK_CLASS_3_G = "3G"
        const val NETWORK_CLASS_4_G = "4G"
        const val NETWORK_CLASS_UNKNOWN = "Unknown"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(me.zhang.laboratory.R.layout.activity_telephony)

        manager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

        binding.btnRefresh.setOnClickListener { refresh() }
    }

    private fun refresh() {
        binding.tvOperatorName.text = getNetworkOperatorName(manager.networkOperatorName)
        binding.tvNetworkType.text = getNetworkClass(manager.networkType)
    }

    private fun getNetworkOperatorName(networkOperatorName: String): String {
        return if (TextUtils.isEmpty(networkOperatorName)) "Unknown" else networkOperatorName
    }

    private fun getNetworkClass(networkType: Int): String {
        return when (networkType) {
            NETWORK_TYPE_GPRS, NETWORK_TYPE_GSM, NETWORK_TYPE_EDGE, NETWORK_TYPE_CDMA, NETWORK_TYPE_1xRTT, NETWORK_TYPE_IDEN -> NETWORK_CLASS_2_G
            NETWORK_TYPE_UMTS, NETWORK_TYPE_EVDO_0, NETWORK_TYPE_EVDO_A, NETWORK_TYPE_HSDPA, NETWORK_TYPE_HSUPA, NETWORK_TYPE_HSPA, NETWORK_TYPE_EVDO_B, NETWORK_TYPE_EHRPD, NETWORK_TYPE_HSPAP, NETWORK_TYPE_TD_SCDMA -> NETWORK_CLASS_3_G
            NETWORK_TYPE_LTE, NETWORK_TYPE_IWLAN, NETWORK_TYPE_LTE_CA -> NETWORK_CLASS_4_G
            else -> NETWORK_CLASS_UNKNOWN
        }
    }

}