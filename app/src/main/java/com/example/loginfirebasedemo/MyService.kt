package com.example.loginfirebasedemo

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.KeyEvent
import android.view.accessibility.AccessibilityEvent
import android.widget.Toast

class MyService : AccessibilityService() {
    override fun onAccessibilityEvent(accessibilityEvent: AccessibilityEvent?) {}
    override fun onInterrupt() {}
    override fun onKeyEvent(event: KeyEvent): Boolean {
        val action: Int = event.getAction()
        val keyCode: Int = event.getKeyCode()
        if (action == KeyEvent.ACTION_UP) {
            if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
                Log.d("Check", "KeyUp")
                Toast.makeText(this, "KeyUp", Toast.LENGTH_SHORT).show()
            } else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
                Log.d("Check", "KeyDown")
                Toast.makeText(this, "KeyDown", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onKeyEvent(event)
    }
}