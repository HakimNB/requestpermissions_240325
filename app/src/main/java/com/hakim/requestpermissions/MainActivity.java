package com.hakim.requestpermissions;

import android.Manifest;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.androidgamesdk.GameActivity;

public class MainActivity extends GameActivity {
    static {
        System.loadLibrary("requestpermissions");
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (hasFocus) {
            hideSystemUi();
        }
    }

    private void hideSystemUi() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
        );
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        super.dispatchTouchEvent(ev);
        if ( ev.getAction() == MotionEvent.ACTION_DOWN ) {
            Log.d("MainActivity", "dispatchTouchEvent ACTION_DOWN: " + ev.toString());
            int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS);
            Log.d("MainActivity", "dispatchTouchEvent checkSelfPermission returns: " + permissionCheck);
//            if ( permissionCheck == PackageManager.PERMISSION_DENIED ) {
//                boolean shouldShow = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.POST_NOTIFICATIONS);
//                Log.d("MainActivity", "dispatchTouchEvent shouldShowRequestPermissionRationale: " + shouldShow);
//                if ( !shouldShow ) {
//                    String[] requestedPermissions = {Manifest.permission.POST_NOTIFICATIONS};
//                    requestPermissions(requestedPermissions, 8);
//                }
//            }
            boolean shouldShow = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.POST_NOTIFICATIONS);
            Log.d("MainActivity", "dispatchTouchEvent shouldShowRequestPermissionRationale returns: " + shouldShow);
            String[] requestedPermissions = {Manifest.permission.POST_NOTIFICATIONS};
            requestPermissions(requestedPermissions, 8);
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for ( int i = 0; i < permissions.length; i++ ) {
            int iPermission = (i < permissions.length) ? i : -1;
            int iResult = (i < grantResults.length) ? i : -1;
            String szPermission = iPermission >= 0 && iPermission < permissions.length ? permissions[iPermission] : "PERMISSIONS OUT OF BOUND ";
            String szResult = iResult >= 0 && iResult < grantResults.length ? "" + grantResults[iResult] : " GRANT RESULT OUT OF BOUND";
            Log.d("MainActivity", "onRequestPermissionsResult requestCode: " + requestCode + " index : " + i + " permission : " + szPermission + " result : " + szResult);
        }
    }
}