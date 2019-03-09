package com.xbh.game.tank.model.imp

import com.xbh.game.tank.enum.CurrentDirection
import com.xbh.game.tank.model.Bullet

class BulletImp(direction: CurrentDirection, b: (width: Int, height: Int) -> Pair<Int, Int>) : Bullet(direction, b)
