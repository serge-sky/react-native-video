package com.brentvatne.exoplayer;

import android.annotation.SuppressLint;
import android.content.Context;
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
    private static RxDataStore<Preferences> store;


    public static RxDataStore<Preferences> init(Context context) {
        if (store == null) {
            store = new RxPreferenceDataStoreBuilder(context, "drm_license_store").build();
        }
        return store;
    }

    @SuppressLint("CheckResult")
    public static Map<String, String> getLicences() {
        Map<String, String> licences;
            licences = gson.fromJson(store.data().map(prefs ->
                    prefs.contains(licenceKey) ? prefs.get(licenceKey) : "").blockingFirst(""), Map.class);
        return licences != null ? licences : new HashMap();
    }

    public static byte[] getLicence(String assetId) {
        String license = getLicences().get(assetId);
        return license != null ? Base64.decode(license, Base64.NO_WRAP) : null;
    }

    public static void storeLicense(String assetId, byte[] licence) {
        if (licence != null) {
            Map<String, String> drmLicences = getLicences();
            drmLicences.put(assetId, Base64.encodeToString(licence, Base64.NO_WRAP));

            store.updateDataAsync(prefsIn -> {
                MutablePreferences mutablePreferences = prefsIn.toMutablePreferences();
                mutablePreferences.set(licenceKey, gson.toJson(drmLicences));
                return Single.just(mutablePreferences);
            });
        }
    }
}
