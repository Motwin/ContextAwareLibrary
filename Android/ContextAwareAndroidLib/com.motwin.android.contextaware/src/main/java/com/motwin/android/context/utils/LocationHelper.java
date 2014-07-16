package com.motwin.android.context.utils;

import android.location.Location;

/**
 * Helper class for geolocation data
 * 
 */
public class LocationHelper {
    private static final int ONE_SECOND = 1000;

    // Hide constructor
    private LocationHelper() {
    }

    /**
     * Choose better location between two locations
     * 
     * @param anOldLocation
     *            the old Location
     * @param aNewLocation
     *            the new location
     * @return the better location
     */
    public static Location chooseBetterLocation(Location anOldLocation, Location aNewLocation) {
        Location result;

        if (anOldLocation == null && aNewLocation == null) {
            result = null;
        } else if (anOldLocation == null && aNewLocation != null) {
            result = aNewLocation;
        } else if (anOldLocation != null && aNewLocation == null) {
            result = anOldLocation;
        } else {
            // Check whether the new location fix is newer or older
            long timeDelta = aNewLocation.getTime() - anOldLocation.getTime();
            boolean isSignificantlyNewer = timeDelta > ONE_SECOND;
            boolean isSignificantlyOlder = timeDelta < -ONE_SECOND;
            boolean isNewer = timeDelta > 0;

            if (isSignificantlyNewer) {
                result = aNewLocation;
                // If the new location is more than on second older, it must be
                // worse
            } else if (isSignificantlyOlder) {
                result = anOldLocation;
            }

            // Check whether the new location fix is more or less accurate
            int accuracyDelta = (int) (aNewLocation.getAccuracy() - anOldLocation.getAccuracy());
            boolean isLessAccurate = accuracyDelta > 0;
            boolean isMoreAccurate = accuracyDelta < 0;
            boolean isSignificantlyLessAccurate = accuracyDelta > 200;
            // Check if the old and new location are from the same provider
            boolean isFromSameProvider = isSameProvider(aNewLocation.getProvider(), anOldLocation.getProvider());

            // Determine location quality using a combination of timeliness and
            // accuracy
            if (isMoreAccurate) {
                result = aNewLocation;
            } else if (isNewer && !isLessAccurate) {
                result = aNewLocation;
            } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
                result = aNewLocation;
            } else {
                result = anOldLocation;
            }
        }
        return result;

    }

    /**
     * Checks whether two providers are the same
     * 
     * @param provider1
     *            First provider
     * @param provider2
     *            Second provider
     * @return true if providers are the same
     */
    private static boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
            return provider2 == null;
        }
        return provider1.equals(provider2);

    }

}
