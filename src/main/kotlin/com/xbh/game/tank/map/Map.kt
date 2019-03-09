package com.xbh.game.tank.map

import com.xbh.game.tank.configuration.Config
import com.xbh.game.tank.model.View
import com.xbh.game.tank.model.Wall
import com.xbh.game.tank.model.imp.*
import java.io.File

object Map {
    private lateinit var mapFile: File

    fun getMapFile(): File {
        this.mapFile = File(javaClass.getResource("/map/1.map").path)
        return this.mapFile
    }

    fun createMap(): ArrayList<View> {
        var row: Int = 0

        var mapViewObjs = ArrayList<View>()

//
//        遍历每列
        for ((col, readLine) in this.mapFile.readLines().withIndex()) {
//            遍历每行
            readLine.toCharArray().forEach {
                when (it) {
                    '砖' -> mapViewObjs.add(WallImp(view_y = Config.model_width * col,
                            view_x = Config.model_height * row))
                    '铁' -> mapViewObjs.add(SteelWallImp(view_y = Config.model_width * col,
                            view_x = Config.model_height * row))
                    '水' -> mapViewObjs.add(WaterWallImp(view_y = Config.model_width * col,
                            view_x = Config.model_height * row))
                    '草' -> mapViewObjs.add(GrassWallImp(view_y = Config.model_width * col,
                            view_x = Config.model_height * row))
                    '敌' -> mapViewObjs.add(EnemyTankImp(view_y = Config.model_width * col,
                            view_x = Config.model_height * row))
                }
                row++
            }
            row = 0
        }
        return mapViewObjs
    }
}

