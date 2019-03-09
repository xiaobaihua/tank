package com.xbh.game.tank.able

import com.xbh.game.tank.enum.CurrentDirection
import com.xbh.game.tank.model.View

interface AutoMoveable: View, Moveable {
    fun getRandomDirection(max: Int): CurrentDirection

    fun autoMove(currentDirection: CurrentDirection, viewList: List<View>)
}