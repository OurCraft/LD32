package org.oc.ld32.gui

import java.util
import java.util.{HashMap, Map}

import org.lengine.render.{Sprite, Texture, TextureRegion}
import org.oc.ld32.Game
import org.oc.ld32.gui.editor.GuiEditor
import org.oc.ld32.input.keyboard.KeyControls

class GuiIngame extends GuiScreen {

  val map: Map[Float, Sprite] = new HashMap
  val texture: Texture = "assets/textures/gui/baguette.png"
  var guiEditor: GuiEditor = _

  var toRemove = new util.ArrayList[BaguetteGui]()

  def this(guiEditor: GuiEditor) {
    this()
    this.guiEditor = guiEditor

  }

  override def init(): Unit = {
  }

  def getSprite(p: Float): Sprite = {
    if(map.containsKey(p)) {
      map.get(p)
    } else {
      val sprite: Sprite = new Sprite(texture, new TextureRegion(0,0,p,1))
      map.put(p, sprite)
      sprite
    }
  }

  override def renderScreen(delta: Float): Unit = {
    elements removeAll toRemove
    toRemove.clear()
    if(Game.player != null) {
      val baguette: Sprite = getSprite(Game.player.baguetteCompletion)
      baguette.setPos(0,Game.getBaseHeight-baguette.height)
      baguette.render(delta)


      if (Game.isPaused) {
        this.fontRenderer.renderString("Pause", 800, 310, 0xFFFFFFFF, 2f)
      }
      else if(Game.player.isDead()) {
        if(guiEditor != null) {
          val text = "You are dead! Go back to the editor: [R]"
          this.fontRenderer.renderString(text, width-Game.fontRenderer.getWidth(text), 600)
        } else {
          this.fontRenderer.renderString("You are dead! Retry? [R]", 800, 600)
        }
      }

    }
  }
  override def onKeyPressed(keyCode: Int, char: Char): Unit = {

    if(Game.player != null && Game.player.isDead() && keyCode == KeyControls.retry)
    {
      if(guiEditor != null) {
        Game.level = null
        Game.player = null
        Game.displayGuiScreen(guiEditor)
      } else {
        Game.loadLevel(Game.level.getName(), true)
      }
    }
    else if(!Game.isPaused && keyCode == KeyControls.pause)
    {
      Game.pause()
      val resumeButton: GuiButton = new GuiButton("Resume", 775, 270)
      resumeButton.setHandler(button => {
        Game.resume()
        toRemove.addAll(elements)
      })

      elements.add(resumeButton)

      var quitButton: GuiButton = new GuiButton("Quit", 775, 230)
      if(guiEditor != null)
        quitButton = new GuiButton("Return to Editor", 800, 245)

      quitButton.setHandler(button => {
        Game.level = null
        Game.player = null
        Game.resume()
        if(guiEditor != null)
          Game.displayGuiScreen(this.guiEditor)
        else
          Game.displayGuiScreen(new GuiMainMenu)

        toRemove.add(button)
      })
      elements.add(quitButton)
    }
    else if(keyCode == KeyControls.pause)
    {
      Game.resume()
      elements.clear()
    }

    super.onKeyPressed(keyCode, char)
  }
}
