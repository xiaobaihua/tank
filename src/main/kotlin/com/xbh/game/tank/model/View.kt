package com.xbh.game.tank.model

import org.itheima.kotlin.game.core.Painter
import java.awt.Rectangle


interface View {
    var view_width: Int
    var view_height: Int

    var view_x: Int
    var view_y: Int

    var view_img: String

    var rect: Rectangle

    fun draw(){
        this.rect = Rectangle(view_x, view_y, view_width, view_height)
        Painter.drawImage(view_img, view_x, view_y)
    }
}