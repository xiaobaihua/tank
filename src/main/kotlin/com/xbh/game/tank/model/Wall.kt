package com.xbh.game.tank.model

import com.xbh.game.tank.able.Attackable
import com.xbh.game.tank.able.Damageable
import com.xbh.game.tank.able.Destroyable
import com.xbh.game.tank.configuration.Config
import java.awt.Rectangle

abstract class Wall: Damageable, Destroyable{
    override var view_width: Int = Config.model_width
    override var view_height: Int = Config.model_height
    override lateinit var rect: Rectangle

    override var hp: Int = 3

    override var destroyFlag: Boolean = false

    init {
        this.rect = Rectangle(view_x, view_y, view_width, view_height)
    }


    override fun notifyAttack(attackable: Attackable) {
        attackable as Bullet

        this.hp -= attackable.atk
        this.destroyFlag = this.hp <= 0

        attackable.notifyCollide(attackable.currentDirection)
    }

    override fun isDestroy(): Boolean {
        return this.destroyFlag
    }
}