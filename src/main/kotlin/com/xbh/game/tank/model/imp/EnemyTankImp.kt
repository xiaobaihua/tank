package com.xbh.game.tank.model.imp

import com.xbh.game.tank.able.*
import com.xbh.game.tank.configuration.Config
import com.xbh.game.tank.enum.CurrentDirection
import com.xbh.game.tank.model.Bullet
import com.xbh.game.tank.model.View
import java.awt.Rectangle
import java.util.*

class EnemyTankImp(override var view_x: Int, override var view_y: Int) :AutoMoveable, Blockable, Destroyable, Damageable  {
    override var view_img: String = "img/enemy_1_d.gif"
    override var speed = 1
    override var view_width: Int = Config.model_width
    override var view_height: Int = Config.model_height
    override var rect: Rectangle = Rectangle(view_x, view_y, view_width, view_height)
    override var currentDirection: CurrentDirection = CurrentDirection.DOWN

    override var destroyFlag: Boolean = false

    override var hp: Int = 1

    private var changeDirectionInterval = Random().nextInt(5) + 1
    private var lastChangeDirection = System.currentTimeMillis()

    private var lastMove  = System.currentTimeMillis()
    private var moveInterval = 5
    private var direction = this.currentDirection


    override fun move(currentDirection: CurrentDirection) {
        when ( currentDirection ){
            CurrentDirection.UP -> {
                if (currentDirection == this.currentDirection){
                    this.view_y -= speed
                    this.currentDirection = CurrentDirection.UP
                    if (this.view_y < 0) this.view_y = 0
                } else {
                    this.view_img = "img/enemy_1_u.gif"
                    this.currentDirection = CurrentDirection.UP
                }
            }
            CurrentDirection.DOWN -> {
                if (currentDirection == this.currentDirection){
                    this.currentDirection = CurrentDirection.DOWN
                    this.view_y += speed
                    if (this.view_y + this.view_height > Config.windows_height) this.view_y = Config.windows_height - this.view_height
                } else {
                    this.view_img = "img/enemy_1_d.gif"
                    this.currentDirection = CurrentDirection.DOWN
                }

            }
            CurrentDirection.LEFT -> {
                if (currentDirection == this.currentDirection){
                    this.view_x -= speed
                    this.currentDirection = CurrentDirection.LEFT
                    if (this.view_x < 0) this.view_x = 0
                } else {
                    this.view_img = "img/enemy_1_l.gif"
                    this.currentDirection = CurrentDirection.LEFT
                }
            }
            CurrentDirection.RIGHT -> {
                if (currentDirection == this.currentDirection){
                    this.view_x += speed
                    this.currentDirection = CurrentDirection.RIGHT
                    if (this.view_x + this.view_width > Config.windows_width) this.view_x = Config.windows_width - this.view_width
                } else {
                    this.view_img = "img/enemy_1_r.gif"
                    this.currentDirection = CurrentDirection.RIGHT
                }

            }
        }
    }

    override fun autoMove(currentDirection: CurrentDirection, viewList: List<View>) {
        // 判断间隔是否达到
        if (lastMove + moveInterval >= System.currentTimeMillis()) return

            //获取随机方向
            direction  = getRandomDirection(4)

            // 判断是否碰撞, 如果碰撞了就换个方向
            while ( detectionCollide(direction, viewList) ) {
                lastChangeDirection -= changeDirectionInterval
                direction = getRandomDirection(4)
            }
        move(direction)
    }

    override fun detectionCollide(currentDirection: CurrentDirection, blockable: List<View>): Boolean {
        when (currentDirection) {
            CurrentDirection.UP -> {
                // 创建下一步移动后的物体矩形
                val rectangle = Rectangle(this.rect.x, this.rect.y - speed, this.rect.width, this.rect.height)

                //判断是否碰撞
                blockable.filter { it != this }.forEach {
                    if (it.rect.intersects(rectangle)) return true
                }

            }
            CurrentDirection.DOWN -> {
                val rectangle = Rectangle(this.rect.x, this.rect.y + speed, this.rect.width, this.rect.height)

                //判断是否碰撞
                blockable.filter { it != this }.forEach {
                    if (it.rect.intersects(rectangle)) return true
                }

            }
            CurrentDirection.LEFT -> {
                val rectangle = Rectangle(this.rect.x - speed, this.rect.y, this.rect.width, this.rect.height)

                //判断是否碰撞
                blockable.filter { it != this }.forEach {
                    if (it.rect.intersects(rectangle)) return true
                }
            }
            CurrentDirection.RIGHT -> {
                val rectangle = Rectangle(this.rect.x + speed, this.rect.y, this.rect.width, this.rect.height)

                //判断是否碰撞
                blockable.filter { it != this }.forEach {
                    if (it.rect.intersects(rectangle)) return true
                }
            }
        }
        return false
    }

    override fun notifyCollide(direction: CurrentDirection?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getRandomDirection(max: Int): CurrentDirection {

        // 在规定时间内不允许改变方向， 除非无法继续移动
        if (lastChangeDirection + changeDirectionInterval * 1000 <= System.currentTimeMillis()) {
            lastChangeDirection = System.currentTimeMillis()

            direction = when (Random().nextInt(max)) {
                0 -> CurrentDirection.UP
                1 -> CurrentDirection.DOWN
                2 -> CurrentDirection.LEFT
                3 -> CurrentDirection.RIGHT
                else -> {
                    CurrentDirection.UP
                }
            }
        }

        return direction
    }

    override fun notifyAttack(attackable: Attackable) {
        attackable as Bullet

        hp -= attackable.atk
        destroyFlag = this.hp <= 0

        attackable.notifyCollide(attackable.currentDirection)
    }
//
    override fun isDestroy(): Boolean {
        return destroyFlag
    }


}