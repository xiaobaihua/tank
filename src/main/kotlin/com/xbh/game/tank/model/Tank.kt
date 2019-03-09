package com.xbh.game.tank.model

import com.xbh.game.tank.able.Blockable
import com.xbh.game.tank.able.Moveable
import com.xbh.game.tank.configuration.Config
import com.xbh.game.tank.enum.CurrentDirection
import com.xbh.game.tank.model.imp.BulletImp
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import org.omg.CORBA.Current
import java.awt.Rectangle

class Tank(override var view_x: Int, override var view_y: Int) : Moveable, Blockable {


    override var view_width: Int = Config.model_width

    override var view_height: Int = Config.model_height

    override var view_img: String = "/img/tank_u.gif"

    override val speed: Int = Config.tank_Speed

    override var currentDirection = CurrentDirection.UP

    override lateinit var rect: Rectangle



    fun tankEvent(event: KeyEvent, blocks: List<View>) : View?{
         when (event.code) {
            KeyCode.UP -> {
                if (this.detectionCollide(CurrentDirection.UP, blocks)) {
                    notifyCollide(CurrentDirection.UP)
                    return null
                }
                this.move(CurrentDirection.UP)
            }
            KeyCode.DOWN -> {
                if (this.detectionCollide(CurrentDirection.DOWN, blocks)) {
                    notifyCollide(CurrentDirection.DOWN)
                    return null
                }
                this.move(CurrentDirection.DOWN)
            }
            KeyCode.LEFT -> {
                if (this.detectionCollide(CurrentDirection.LEFT, blocks)) {
                    notifyCollide(CurrentDirection.LEFT)
                    return null
                }
                this.move(CurrentDirection.LEFT)
            }
            KeyCode.RIGHT -> {
                if (this.detectionCollide(CurrentDirection.RIGHT, blocks)) {
                    notifyCollide(CurrentDirection.RIGHT)
                    return null
                }
                this.move(CurrentDirection.RIGHT)
            }
            KeyCode.SPACE -> {
                return BulletImp(this.currentDirection){w, h ->
                    var x: Int = -1
                    var y: Int = -1
                    when (this.currentDirection) {
                        CurrentDirection.UP -> {
                            x = this.view_x + this.view_width / 2 - w / 2 + 7
                            y = this.view_y - h / 2
                        }CurrentDirection.DOWN -> {
                            x = this.view_x + this.view_width / 2 - w / 2 + 7
                            y = this.view_y + this.view_height - h / 2
                        }CurrentDirection.RIGHT -> {
                            x = this.view_x + this.view_width - w / 2
                            y = this.view_y + this.view_height / 2 - h / 2
                        }CurrentDirection.LEFT -> {
                            x = this.view_x - w - 7
                            y = this.view_y + this.view_height / 2 - h / 2
                        }
                    }

                    Pair(x, y)
                }
            }
         }
        return null
    }


    override fun move(currentDirection: CurrentDirection) {
        when ( currentDirection ){
            CurrentDirection.UP -> {
                if (currentDirection == this.currentDirection){
                    this.view_y -= speed
                    this.currentDirection = CurrentDirection.UP
                    if (this.view_y < 0) this.view_y = 0
                } else {
                    this.view_img = "img/tank_u.gif"
                    this.currentDirection = CurrentDirection.UP
                }
            }
            CurrentDirection.DOWN -> {
                if (currentDirection == this.currentDirection){
                    this.currentDirection = CurrentDirection.DOWN
                    this.view_y += speed
                    if (this.view_y + this.view_height > Config.windows_height) this.view_y = Config.windows_height - this.view_height
                } else {
                    this.view_img = "img/tank_d.gif"
                    this.currentDirection = CurrentDirection.DOWN
                }

            }
            CurrentDirection.LEFT -> {
                if (currentDirection == this.currentDirection){
                    this.view_x -= speed
                    this.currentDirection = CurrentDirection.LEFT
                    if (this.view_x < 0) this.view_x = 0
                } else {
                    this.view_img = "img/tank_l.gif"
                    this.currentDirection = CurrentDirection.LEFT
                }
            }
            CurrentDirection.RIGHT -> {
                if (currentDirection == this.currentDirection){
                    this.view_x += speed
                    this.currentDirection = CurrentDirection.RIGHT
                    if (this.view_x + this.view_width > Config.windows_width) this.view_x = Config.windows_width - this.view_width
                } else {
                    this.view_img = "img/tank_r.gif"
                    this.currentDirection = CurrentDirection.RIGHT
                }

            }
        }
    }

    override fun detectionCollide(currentDirection: CurrentDirection, blockable: List<View>): Boolean {
        when (currentDirection){
            CurrentDirection.UP -> {
                // 创建下一步移动后的物体矩形
                val rectangle = Rectangle(this.rect.x, this.rect.y - speed, this.rect.width, this.rect.height)

                //判断是否碰撞
                blockable.forEach {
                    if (it.rect.intersects(rectangle)) return true
                }

            }
            CurrentDirection.DOWN -> {
                val rectangle = Rectangle(this.rect.x, this.rect.y + speed, this.rect.width, this.rect.height)

                //判断是否碰撞
                blockable.forEach {
                    if (it.rect.intersects(rectangle)) return true
                }

            }
            CurrentDirection.LEFT -> {
                val rectangle = Rectangle(this.rect.x - speed, this.rect.y, this.rect.width, this.rect.height)

                //判断是否碰撞
                blockable.forEach {
                    if (it.rect.intersects(rectangle)) return true
                }
            }
            CurrentDirection.RIGHT -> {
                val rectangle = Rectangle(this.rect.x + speed, this.rect.y, this.rect.width, this.rect.height)

                //判断是否碰撞
                blockable.forEach {
                    if (it.rect.intersects(rectangle)) return true
                }
            }
        }
        return false
    }

    override fun notifyCollide(direction: CurrentDirection?){
        when (direction){
            CurrentDirection.UP -> {
                this.view_img = "img/tank_u.gif"
                this.currentDirection = CurrentDirection.UP
            }
            CurrentDirection.DOWN -> {
                this.view_img = "img/tank_d.gif"
                this.currentDirection = CurrentDirection.DOWN
            }
            CurrentDirection.LEFT -> {
                this.view_img = "img/tank_l.gif"
                this.currentDirection = CurrentDirection.LEFT
            }
            CurrentDirection.RIGHT -> {
                this.view_img = "img/tank_r.gif"
                this.currentDirection = CurrentDirection.RIGHT
            }
        }
    }
}