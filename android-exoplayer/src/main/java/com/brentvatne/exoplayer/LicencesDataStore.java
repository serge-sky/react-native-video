package com.brentvatne.exoplayer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.Base64;

import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKeys;
import androidx.datastore.preferences.rxjava3.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava3.RxDataStore;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.core.Single;

public class LicencesDataStore {
    private static final Preferences.Key<String> licenceKey = PreferencesKeys.stringKey("drm_licences_id");
    private static final Gson gson = new Gson();
    public static final String LICENCE_URL = "licenceUrl";
    public static final String LICENCE = "licence";
    private static RxDataStore<Preferences> store;


    public static RxDataStore<Preferences> init(Context context) {
        if (store == null) {
            store = new RxPreferenceDataStoreBuilder(context, "drm_license_store").build();
        }
        return store;
    }

    @SuppressLint("CheckResult")
    public static Map<String, Map<String, String>> getLicences() {
        Map<String, Map<String, String>> licences;
        licences = gson.fromJson(store.data().map(prefs ->
                prefs.contains(licenceKey) ? prefs.get(licenceKey) : "").blockingFirst(""), Map.class);
        return licences != null ? licences : new HashMap();
    }

    public static byte[] getLicence(String assetId) {
        Map<String, String> license = getLicences().get(assetId);
        return license != null ? Base64.decode(license.get(LICENCE), Base64.NO_WRAP) : null;
    }

    public static Map<String, String> getCachedLicence(String assetId) {
        return getLicences().get(assetId);
    }

    public static void storeLicense(String assetId, String drmLicenseUrl, byte[] licence) {
        if (licence != null) {
            Map<String, String> cachedLicence = new HashMap<>();
            cachedLicence.put(LICENCE_URL, drmLicenseUrl);
            cachedLicence.put(LICENCE, Base64.encodeToString(licence, Base64.NO_WRAP));
            Map<String, Map<String, String>> drmLicences = getLicences();
            drmLicences.put(assetId, cachedLicence);

            store.updateDataAsync(prefsIn -> {
                MutablePreferences mutablePreferences = prefsIn.toMutablePreferences();
                mutablePreferences.set(licenceKey, gson.toJson(drmLicences));
                return Single.just(mutablePreferences);
            });
        }
    }
}
