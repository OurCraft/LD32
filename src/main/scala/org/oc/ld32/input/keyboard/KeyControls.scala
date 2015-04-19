package org.oc.ld32.input.keyboard

import java.io.{File, FileReader, FileWriter}
import java.util

import com.google.gson._
import com.google.gson.annotations.Expose
import org.lwjgl.input.Keyboard
import org.oc.ld32.Game

import scala.collection.JavaConversions._

object KeyControls {

  var confirm: Int = Keyboard.KEY_RETURN
  var left: Int = Keyboard.KEY_LEFT
  var up: Int = Keyboard.KEY_UP
  var down: Int = Keyboard.KEY_DOWN
  var right: Int = Keyboard.KEY_RIGHT

  private val gson: Gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().enableComplexMapKeySerialization().create()

  private val conf: File = new File(Game.getGameDir, "config.json")

  @Expose
  var keys: util.Map[String, String] = new util.HashMap[String, String]()

  var defaultBinding: util.Map[String, String] = new util.HashMap[String, String]()


  def init() = {
    defaultBinding.put("up", String.valueOf(up))
    defaultBinding.put("down", String.valueOf(down))
    defaultBinding.put("left", String.valueOf(left))
    defaultBinding.put("right", String.valueOf(right))
    defaultBinding.put("confirm", String.valueOf(confirm))

    if(!conf.exists())
    {
      keys.putAll(defaultBinding)
      this.saveConfig();
    }
    else
    {

      this.loadConfig()

      for (id: String <- defaultBinding.keySet())
      {
        if(!keys.containsKey(id))
          keys.put(id, defaultBinding.get(id))
      }

      this.saveConfig()
    }
  }

  def loadConfig() = {
    val reader: FileReader = new FileReader(conf)
    val configObject: util.Map[String, Object] = gson.fromJson(reader, classOf[util.Map[String, Object]])

    if(configObject.get("keys") != null) {
      keys = configObject.get("keys").asInstanceOf[util.Map[String, String]]
    }

    confirm = getKeyCode("confirm")
    left = getKeyCode("left")
    right = getKeyCode("right")
    down = getKeyCode("down")
    up = getKeyCode("up")


    reader.close()
  }

  def saveConfig() = {
    val writer: FileWriter = new FileWriter(conf)
    gson.toJson(this, writer)
    writer.close()
  }

  def getKeyCode(name: String) : Int = {
    if(keys.containsKey(name))
      // Todo: caching int or fix the double issue
      return Integer.valueOf(keys.get(name))
    else
      return -1
  }

}
