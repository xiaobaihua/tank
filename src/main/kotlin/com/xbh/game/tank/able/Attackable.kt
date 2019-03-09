package com.xbh.game.tank.able

import com.xbh.game.tank.model.View

interface Attackable : View, Destroyable{
    var atk: Int

    fun attack(wall: Damageable): Boolean

    fun notifyBeResis()
}