package com.xbh.game.tank.able

import com.xbh.game.tank.model.View

interface Damageable : View, Destroyable {
    var hp: Int
    fun notifyAttack(attackable: Attackable)
}