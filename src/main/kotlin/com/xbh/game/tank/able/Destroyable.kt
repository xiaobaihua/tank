package com.xbh.game.tank.able

import com.xbh.game.tank.model.View


interface Destroyable: View {
    var destroyFlag: Boolean

    fun isDestroy(): Boolean
}