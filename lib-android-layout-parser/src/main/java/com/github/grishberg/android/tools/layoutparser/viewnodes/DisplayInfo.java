package com.github.grishberg.android.tools.layoutparser.viewnodes;

public class DisplayInfo
{
	val willNotDraw: Boolean,
    val isVisible: Boolean,
    val left: Int,
    val top: Int,
    val width: Int,
    val height: Int,
    val scrollX: Int,
    val scrollY: Int,
    val clipChildren: Boolean,
    val translateX: Float,
    val translateY: Float,
    val scaleX: Float,
    val scaleY: Float,
    val contentDesc: String?
}
