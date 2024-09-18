package club.premiumit.consentlab.data.source

import android.os.Looper
import android.os.NetworkOnMainThreadException
import club.premiumit.consentlab.data.models.CostRuleRemote
import club.premiumit.consentlab.data.models.DataTypeRemote
import kotlin.random.Random
import kotlinx.coroutines.delay

// This is a mocked remote service responding with the costs of the data types
class RemoteCostsService {

    private val random = Random(System.currentTimeMillis())

    private val dataTypesResponse = listOf(
        DataTypeRemote("Configuration of app settings", 1),
        DataTypeRemote("IP address", 2),
        DataTypeRemote("User behaviour", 2),
        DataTypeRemote("User agent", 3),
        DataTypeRemote("App crashes ", -2),
        DataTypeRemote("Browser information", 3),
        DataTypeRemote("Credit and debit card number", 4),
        DataTypeRemote("First name", 6),
        DataTypeRemote("Geographic location", 7),
        DataTypeRemote("Date and time of visit", 1),
        DataTypeRemote("Advertising identifier", 2),
        DataTypeRemote("Bank details", 5),
        DataTypeRemote("Purchase activity", 6),
        DataTypeRemote("Internet service provider", 4),
        DataTypeRemote("JavaScript support ", -1),
    )

    private val costRulesResponse = listOf<CostRuleRemote>(
        CostRuleRemote.CostStatic(
            listOf(
                "Purchase activity",
                "Bank details",
                "Credit and debit card number"
            ), costChangeRule = 10, priority = 1
        ),
        CostRuleRemote.CostStatic(
            listOf(
                "Search terms",
                "Geographic location",
                "Number of page views"
            ), costChangeRule = 27, priority = 2
        ),
        CostRuleRemote.CostDynamic.LessOrEquals(4, costChangeRule = -10, priority = 3),
    )

    suspend fun getCosts(): List<DataTypeRemote> {
        simulateNetworkDelayCoroutines()
        ensureNotMainThread()
        return dataTypesResponse
    }

    suspend fun getCostsRules(): List<CostRuleRemote> {
        simulateNetworkDelayCoroutines()
        ensureNotMainThread()
        return costRulesResponse
    }

    private suspend fun simulateNetworkDelayCoroutines() {
        delay(random.nextInt(0, 1500).toLong())
    }

    // To show that I am main safe aware :-)
    private fun ensureNotMainThread() {
        if (Thread.currentThread() == Looper.getMainLooper().thread) {
            throw NetworkOnMainThreadException()
        }
    }
}