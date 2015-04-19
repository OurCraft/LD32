package org.oc.ld32.input.keyboard

import java.io.{File, FileReader, FileWriter}
import java.util

import com.google.gson._
import com.google.gson.annotations.Expose
import org.lwjgl.input.Keyboard
import org.oc.ld32.Game

import scala.collection.JavaConversions._

object KeyControls {

  private val gson: Gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().enableComplexMapKeySerialization().create()

  private val conf: File = new File(Game.getGameDir, "config.json")

  @Expose
  var keys: util.Map[String, String] = new util.HashMap[String, String]()

  var defaultBinding: util.Map[String, String] = new util.HashMap[String, String]()


  def init() = {
    defaultBinding.put("forward", String.valueOf(Keyboard.KEY_UP))
    defaultBinding.put("back", String.valueOf(Keyboard.KEY_DOWN))
    defaultBinding.put("left", String.valueOf(Keyboard.KEY_LEFT))
    defaultBinding.put("right", String.valueOf(Keyboard.KEY_RIGHT))

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

    if(configObject.get("keys") != null)
    {
      keys = configObject.get("keys").asInstanceOf[util.Map[String, String]]
    }

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
