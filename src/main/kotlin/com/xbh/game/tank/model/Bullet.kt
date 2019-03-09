package com.xbh.game.tank.model

import com.xbh.game.tank.able.Attackable
import com.xbh.game.tank.able.AutoMoveable
import com.xbh.game.tank.able.Damageable
import com.xbh.game.tank.able.Destroyable
import com.xbh.game.tank.configuration.Config
import com.xbh.game.tank.enum.CurrentDirection
import com.xbh.game.tank.model.imp.Bomb
import org.itheima.kotlin.game.core.Composer
import org.itheima.kotlin.game.core.Painter
import java.awt.Rectangle

abstract class Bullet(
        direction: CurrentDirection, b: (
                bulletWidth: Int, bulletHeight: Int)-> Pair<Int, Int>)
                 : AutoMoveable, Destroyable, Attackable {
    override var view_y: Int = -1
    override var view_x: Int = -1

    override var view_width: Int = -1
    override var view_height: Int = -1

    override var atk: Int = 1
    override var destroyFlag: Boolean = false

    override lateinit var rect: Rectangle



    override var view_img: String =   when (direction) {
        CurrentDirection.UP -> "img/shot_top.gif"
        CurrentDirection.DOWN -> "img/shot_bottom.gif"
        CurrentDirection.RIGHT -> "img/shot_right.gif"
        CurrentDirection.LEFT -> "img/shot_left.gif"
    }

    init{

        val size = Painter.size(this.view_img)

        this.view_width = size[0]
        this.view_height = size[1]

        val pair = b.invoke(this.view_width, this.view_height)
        this.view_x = pair.first
        this.view_y = pair.second

        this.rect = Rectangle(view_x, view_y, view_width, view_height)
    }

    override var currentDirection: CurrentDirection = direction

    override var speed: Int = 10


    override fun move(currentDirection: CurrentDirection) {
        when (currentDirection) {
            CurrentDirection.UP -> this.view_y -= this.speed
            CurrentDirection.DOWN -> this.view_y += this.speed
            CurrentDirection.RIGHT -> this.view_x += this.speed
            CurrentDirection.LEFT -> this.view_x -= this.speed
        }
    }

    override fun autoMove(currentDirection: CurrentDirection, viewList: List<View>) {
        this.move(currentDirection)
    }

    override fun isDestroy(): Boolean {
        return when {
            this.view_y < -this.view_height -> true
            this.view_y > Config.windows_height -> true
            this.view_x < -this.view_width -> true
            this.view_x > Config.windows_width -> true
            else -> false
        }
    }

    override fun attack(wall: Damageable): Boolean {
        return this.rect.intersects(wall.rect)
    }


    override fun notifyCollide(direction: CurrentDirection?) {
        this.notifyBeResis()
    }

    override fun notifyBeResis() {
        //播放声音
        Composer.play("snd/fire.wav")
        //播放动画
        Bomb(this.view_x, this.view_y).draw()

        this.destroyFlag = true
    }

    override fun detectionCollide(currentDirection: CurrentDirection, blockable: List<View>): Boolean {
        return false
    }

    override fun getRandomDirection(max: Int): CurrentDirection {
        return this.currentDirection
    }
}