package com.xbh.game.tank.model.imp

import com.xbh.game.tank.able.Blockable
import com.xbh.game.tank.model.Wall

class WallImp(override var view_x: Int, override var view_y: Int, override var view_img: String = "img/wall.gif") : Wall(), Blockable {

}