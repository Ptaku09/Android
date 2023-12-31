package com.example.exercise02

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    private var checkCheckedFontSize = booleanArrayOf(false, true, false)
    private var checkCheckedFontType = booleanArrayOf(false, false)
    private var checkCheckedFontColor = booleanArrayOf(true, false, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applyTheme()
        setContentView(R.layout.activity_main)

        val fontSizeButton: Button = findViewById(R.id.font_size_button)
        registerForContextMenu(fontSizeButton)

        val fontTypeButton: Button = findViewById(R.id.font_type_button)
        registerForContextMenu(fontTypeButton)

        val fontColorButton: Button = findViewById(R.id.font_color_button)
        registerForContextMenu(fontColorButton)

        val lightThemeButton: Button = findViewById(R.id.light_theme_button)
        lightThemeButton.setOnClickListener {
            setPrefs(0)
            recreate()
        }

        val darkThemeButton: Button = findViewById(R.id.dark_theme_button)
        darkThemeButton.setOnClickListener {
            setPrefs(1)
            recreate()
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        val inflater: MenuInflater = menuInflater

        if (v?.id == R.id.font_size_button) {
            menu?.setHeaderTitle("Font size")
            inflater.inflate(R.menu.cm_fontsize, menu)

            for (i in checkCheckedFontSize.indices) {
                if (checkCheckedFontSize[i]) {
                    menu?.getItem(i)?.isChecked = true
                }
            }

            return super.onCreateContextMenu(menu, v, menuInfo)
        }

        if (v?.id == R.id.font_type_button) {
            menu?.setHeaderTitle("Font type")
            inflater.inflate(R.menu.cm_fonttype, menu)

            for (i in checkCheckedFontType.indices) {
                if (checkCheckedFontType[i]) {
                    menu?.getItem(i)?.isChecked = true
                }
            }

            return super.onCreateContextMenu(menu, v, menuInfo)
        }

        if (v?.id == R.id.font_color_button) {
            menu?.setHeaderTitle("Font color")
            inflater.inflate(R.menu.cm_fontcolor, menu)

            for (i in checkCheckedFontColor.indices) {
                if (checkCheckedFontColor[i]) {
                    menu?.getItem(i)?.isChecked = true
                }
            }

            return super.onCreateContextMenu(menu, v, menuInfo)
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val name: TextView = findViewById(R.id.author_name)

        when (item.itemId) {
            R.id.font_size_small -> {
                name.textSize = 14F

                checkCheckedFontSize[0] = true
                checkCheckedFontSize[1] = false
                checkCheckedFontSize[2] = false

                return true
            }

            R.id.font_size_medium -> {
                name.textSize = 18F

                checkCheckedFontSize[0] = false
                checkCheckedFontSize[1] = true
                checkCheckedFontSize[2] = false

                return true
            }

            R.id.font_size_large -> {
                name.textSize = 22F

                checkCheckedFontSize[0] = false
                checkCheckedFontSize[1] = false
                checkCheckedFontSize[2] = true

                return true
            }

            R.id.font_bold -> {
                item.isChecked = !item.isChecked
                checkCheckedFontType[0] = item.isChecked

                if (item.isChecked) {
                    when (checkCheckedFontType[1]) {
                        true -> {
                            name.typeface =
                                Typeface.defaultFromStyle(Typeface.BOLD_ITALIC)
                        }

                        false -> {
                            name.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
                        }
                    }
                } else {
                    when (checkCheckedFontType[1]) {
                        true -> {
                            name.typeface = Typeface.defaultFromStyle(Typeface.ITALIC)
                        }

                        false -> {
                            name.typeface = Typeface.DEFAULT
                        }
                    }
                }

                return true
            }

            R.id.font_italic -> {
                item.isChecked = !item.isChecked
                checkCheckedFontType[1] = item.isChecked

                if (item.isChecked) {
                    when (checkCheckedFontType[0]) {
                        true -> {
                            name.typeface =
                                Typeface.defaultFromStyle(Typeface.BOLD_ITALIC)
                        }

                        false -> {
                            name.typeface = Typeface.defaultFromStyle(Typeface.ITALIC)
                        }
                    }
                } else {
                    when (checkCheckedFontType[0]) {
                        true -> {
                            name.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
                        }

                        false -> {
                            name.typeface = Typeface.DEFAULT
                        }
                    }
                }

                return true
            }

            R.id.font_color_red -> {
                name.setTextColor(ContextCompat.getColor(this, R.color.red))

                checkCheckedFontColor[0] = true
                checkCheckedFontColor[1] = false
                checkCheckedFontColor[2] = false

                return true
            }

            R.id.font_color_green -> {
                name.setTextColor(ContextCompat.getColor(this, R.color.green))

                checkCheckedFontColor[0] = false
                checkCheckedFontColor[1] = true
                checkCheckedFontColor[2] = false

                return true
            }

            R.id.font_color_blue -> {
                name.setTextColor(ContextCompat.getColor(this, R.color.blue))

                checkCheckedFontColor[0] = false
                checkCheckedFontColor[1] = false
                checkCheckedFontColor[2] = true

                return true
            }
        }

        return super.onContextItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.hamburger, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.red_trail -> {
                val redTrailIntent = Intent(this, RedTrailActivity::class.java)
                startActivity(redTrailIntent)
                return true
            }

            R.id.green_trail -> {
                val greenTrailIntent = Intent(this, GreenTrailActivity::class.java)
                startActivity(greenTrailIntent)
                return true
            }

            R.id.blue_trail -> {
                val blueTrailIntent = Intent(this, BlueTrailActivity::class.java)
                startActivity(blueTrailIntent)
                return true
            }

            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    private fun setPrefs(themeNum: Int) {
        val data = getSharedPreferences("theme", MODE_PRIVATE)
        val editor = data.edit()

        editor.putInt("theme_num", themeNum)
        editor.apply()
    }

    private fun applyTheme() {
        val data = getSharedPreferences("theme", MODE_PRIVATE)

        when (data.getInt("theme_num", 0)) {
            0 -> {
                setTheme(R.style.ThemeLight)
            }

            1 -> {
                setTheme(R.style.ThemeDark)
            }

            else -> {
                setTheme(R.style.ThemeLight)
            }
        }
    }
}