package com.xbh.game.tank.model.imp

import com.xbh.game.tank.able.Blockable
import com.xbh.game.tank.model.Wall

class SteelWallImp(override var view_x: Int, override var view_y: Int, override var view_img: String = "img/steel.gif") : Wall(), Blockable {

    override var hp: Int = 20
}
