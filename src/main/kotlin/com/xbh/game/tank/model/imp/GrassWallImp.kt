package com.xbh.game.tank.model.imp
import com.xbh.game.tank.model.Wall

class GrassWallImp(override var view_x: Int, override var view_y: Int, override var view_img: String = "img/grass.gif") : Wall() {

    override var hp: Int = 1
}