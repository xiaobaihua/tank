package com.xbh.game.tank.able

import com.xbh.game.tank.enum.CurrentDirection
import com.xbh.game.tank.model.View

interface Moveable : View{
    var currentDirection: CurrentDirection

    val speed: Int

    fun move(currentDirection: CurrentDirection)

    fun detectionCollide(currentDirection: CurrentDirection, blockable: List<View>): Boolean

    fun notifyCollide(direction: CurrentDirection?)


}