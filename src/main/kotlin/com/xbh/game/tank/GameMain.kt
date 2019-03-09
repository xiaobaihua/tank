package com.xbh.game.tank

import com.xbh.game.tank.able.*
import com.xbh.game.tank.configuration.Config
import com.xbh.game.tank.model.Tank
import com.xbh.game.tank.map.Map
import com.xbh.game.tank.model.Bullet
import com.xbh.game.tank.model.View
import com.xbh.game.tank.model.Wall
import com.xbh.game.tank.model.imp.EnemyTankImp
import javafx.scene.input.KeyEvent
import org.itheima.kotlin.game.core.Window
import java.util.concurrent.CopyOnWriteArrayList

class GameMain : Window(title = "坦克大战", width = Config.windows_width, height = Config.windows_height) {
    private val viewList = CopyOnWriteArrayList<View>()
    override fun onCreate() {
        Map.getMapFile()
        viewList.add(Tank(0, 0))
        viewList.addAll(Map.createMap())
    }

    override fun onDisplay() {
        viewList.forEach{
            it.draw()
        }
    }

    override fun onKeyPressed(event: KeyEvent) {

        viewList.filter { it is Tank }.forEach { tank->
            tank as Tank

            //获取所有堵塞物体
            var list = viewList.filter { it is Blockable && it != tank }

            // 返回子弹
            tank.tankEvent(event, list)?.let {bullet ->
                viewList.add(bullet)
            }

        }

    }

    override fun onRefresh() {
//       移动具有自动移动的物体
        viewList.filter { it is AutoMoveable }.forEach {a->
            a as AutoMoveable
            a.autoMove( a.currentDirection, viewList.filter { it is Blockable })
        }


//        自动销毁可被销毁的物体
        viewList.filter { it is Destroyable }.forEach {
            it as Destroyable
            if (it.isDestroy()) viewList.remove(it)
        }


        //检测攻击
        //获取当前攻击对象
        viewList.filter { it is Attackable }.forEach { attack ->
            //获取当前防守对象
            viewList.filter { it is Damageable }.forEach { damage ->
                attack as Attackable
                damage as Damageable
//                判断是否攻击墙壁
                println("123")
                if (attack.attack(damage)) {
                    //通知防守对象和攻击对象
                    damage.notifyAttack(attack)

                    //判断攻击方是否达到销毁条件
                    if (attack.destroyFlag) viewList.remove(attack)
                    //判断防守方是否达到销毁条件
                    if (damage.destroyFlag) viewList.remove(damage)
                }
            }
        }
    }
}