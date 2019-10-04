/**
 * Copyright (c) 2014-present, Facebook, Inc. All rights reserved.
 * <p/>
 * You are hereby granted a non-exclusive, worldwide, royalty-free license to use,
 * copy, modify, and distribute this software in source code or binary form for use
 * in connection with the web services and APIs provided by Facebook.
 * <p/>
 * As with any software that integrates with the Facebook platform, your use of
 * this software is subject to the Facebook Developer Principles and Policies
 * [http://developers.facebook.com/policy/]. This copyright notice shall be
 * included in all copies or substantial portions of the software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.facebook.reactnative.androidsdk;

import android.net.Uri;

import com.facebook.AccessToken;
import com.facebook.AccessTokenSource;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import androidx.annotation.Nullable;


/**
 * This class is solely for the use of other packages within the React Native Facebook SDK for Android.
 * Use of any of the classes in this package is unsupported, and they may be modified or removed
 * without warning at any time.
 */
public final class Utility {

    public static AccessToken buildAccessToken(ReadableMap accessTokenMap) {
        AccessTokenSource accessTokenSource = AccessTokenSource
                .valueOf(accessTokenMap.getString("accessTokenSource"));
        Date expirationTime = new Date((long) accessTokenMap.getDouble("expirationTime"));
        Date lastRefreshTime = new Date((long) accessTokenMap.getDouble("lastRefreshTime"));
        Date dataAccessExpirationTime = new Date((long) accessTokenMap.getDouble("dataAccessExpirationTime"));
        return new AccessToken(
                accessTokenMap.getString("accessToken"),
                accessTokenMap.getString("applicationID"),
                accessTokenMap.getString("userID"),
                reactArrayToStringList(accessTokenMap.getArray("permissions")),
                reactArrayToStringList(accessTokenMap.getArray("declinedPermissions")),
                reactArrayToStringList(accessTokenMap.getArray("expiredPermissions")),
                accessTokenSource,
                expirationTime,
                lastRefreshTime,
                dataAccessExpirationTime
        );
    }

    public static WritableMap accessTokenToReactMap(AccessToken accessToken) {
        WritableMap map = Arguments.createMap();
        map.putString("accessToken", accessToken.getToken());
        map.putString("applicationID", accessToken.getApplicationId());
        map.putString("userID", accessToken.getUserId());
        map.putArray(
            "permissions",
            Arguments.fromJavaArgs(setToStringArray(accessToken.getPermissions())));
        map.putArray(
            "declinedPermissions",
            Arguments.fromJavaArgs(setToStringArray(accessToken.getDeclinedPermissions())));
        map.putArray(
            "expiredPermissions",
            Arguments.fromJavaArgs(setToStringArray(accessToken.getExpiredPermissions())));
        map.putString("accessTokenSource", accessToken.getSource().name());
        map.putDouble("expirationTime", (double) accessToken.getExpires().getTime());
        map.putDouble("lastRefreshTime", (double) accessToken.getLastRefresh().getTime());
        map.putDouble("dataAccessExpirationTime", (double) accessToken.getDataAccessExpirationTime().getTime());
        return map;
    }

    public static @Nullable List<String> reactArrayToStringList(@Nullable ReadableArray array) {
        if (array == null) {
            return null;
        }
        List<String> list = new ArrayList<>(array.size());
        for (int i = 0; i < array.size(); i++) {
            list.add(array.getString(i));
        }
        return list;
    }

    public static String[] setToStringArray(Set<String> set) {
        String[] array = new String[set.size()];
        int i = 0;
        for (String e : set) {
            array[i++] = e;
        }
        return array;
    }
}
