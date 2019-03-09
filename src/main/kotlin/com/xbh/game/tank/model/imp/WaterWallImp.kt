package com.xbh.game.tank.model.imp

import com.xbh.game.tank.able.Attackable
import com.xbh.game.tank.able.Blockable
import com.xbh.game.tank.model.Wall

class WaterWallImp(override var view_x: Int, override var view_y: Int, override var view_img: String = "img/water.gif") : Wall(), Blockable{
    override fun notifyAttack(attackable: Attackable) {

    }
}