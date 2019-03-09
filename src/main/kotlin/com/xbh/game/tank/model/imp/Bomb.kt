package com.xbh.game.tank.model.imp

import com.xbh.game.tank.able.Destroyable
import com.xbh.game.tank.configuration.Config
import org.itheima.kotlin.game.core.Painter
import java.awt.Rectangle
import kotlin.concurrent.thread

class Bomb(override var view_x: Int, override var view_y: Int): Destroyable {

    override var view_width: Int = Config.model_width
    override var view_height: Int = Config.model_height
    override var rect: Rectangle = Rectangle(view_x, view_y, view_width, view_height)

    var indexImg = 0

    //上一次改变的时间
    private var lastChange: Long = System.currentTimeMillis()
    //间隔时间， 毫秒值
    private var changeInterval = 10

    private var imgArr = Array(32){
        "img/blast_${it + 1}.png"
    }

    override var destroyFlag: Boolean = false
    override var view_img: String = imgArr[0]

    override fun isDestroy(): Boolean {
        return this.destroyFlag
    }

    override fun draw() {
        imgArr.forEach {
            Painter.drawImage(it, view_x, view_y)
        }
    }
}
