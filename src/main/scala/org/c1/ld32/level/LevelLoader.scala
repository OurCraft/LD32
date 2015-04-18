package org.c1.ld32.level

import com.google.gson.{JsonArray, JsonObject, Gson}
import org.c1.ld32.entity.EntityBaguettePiece
import org.lengine.maths.Vec2f
import org.lengine.utils.IOUtils

object LevelLoader {

  val gson: Gson = new Gson

  def handleBaguetteData(level: BaguetteLevel, levelData: JsonObject) = {
    val baguettesArray: JsonArray = levelData.getAsJsonArray("baguettes")
    for(i <- 0 until baguettesArray.size) {
      val baguetteData: JsonObject = baguettesArray.get(i).getAsJsonObject
      val value = baguetteData.get("value").getAsFloat
      val x = baguetteData.get("x").getAsFloat
      val y = baguetteData.get("y").getAsFloat
      level.addBaguette(value, x, y)
    }
  }

  def handleWallsData(level: BaguetteLevel, levelData: JsonObject) = {
    val wallArray: JsonArray = levelData.getAsJsonArray("walls")
    for(i <- 0 until wallArray.size) {
      val wallData: JsonObject = wallArray.get(i).getAsJsonObject
      val x = wallData.get("x").getAsFloat
      val y = wallData.get("y").getAsFloat
      val length = wallData.get("length").getAsFloat
      val vertical = wallData.get("vertical").getAsBoolean

      var maxX = x
      var maxY = y
      if(vertical) {
        maxX += 16f
        maxY += length
      } else {
        maxX += length
        maxY += 16f
      }
      val wall: Wall = new Wall(new Vec2f(x,y), new Vec2f(maxX,maxY))
      level.walls.add(wall)
    }
  }

  def handleFloorDecoration(level: BaguetteLevel, levelData: JsonObject) = {
    val floorArray = levelData.getAsJsonArray("floor")
    for (i <- 0 until floorArray.size) {
      val floorData = floorArray.get(i).getAsJsonObject
      val id = floorData.get("texture").getAsString
      val x = floorData.get("x").getAsFloat
      val y = floorData.get("y").getAsFloat
      val w = floorData.get("width").getAsFloat
      val h = floorData.get("height").getAsFloat
      val floor: FloorDecoration = new FloorDecoration(id, x, y, w, h)
      level.floorDecorations.add(floor)
    }
  }

  def load(id: String): BaguetteLevel = {
    val level: BaguetteLevel = new BaguetteLevel
    val json: String = IOUtils.read(s"assets/levels/$id.json", "UTF-8")
    val levelData: JsonObject = gson.fromJson(json, classOf[JsonObject])
    if(levelData.has("baguettes")) {
      handleBaguetteData(level, levelData)
    }

    if(levelData.has("spawnpoint")) {
      val spawnpoint = levelData.get("spawnpoint").getAsJsonArray
      val x: Float = spawnpoint.get(0).getAsFloat
      val y: Float = spawnpoint.get(1).getAsFloat
      level.spawnpoint = new Vec2f(x,y)
    }

    if(levelData.has("walls")) {
      handleWallsData(level, levelData)
    }

    if(levelData.has("floor")) {
      handleFloorDecoration(level, levelData)
    }

    if(levelData.has("music")) {
      level.music = levelData.get("music").getAsString
    }
    level
  }
}
