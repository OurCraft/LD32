package org.oc.ld32.level

import java.util.{ArrayList, List}

import org.lengine.entity.Entity
import org.lengine.level.Level
import org.lengine.maths.Vec2f
import org.oc.ld32.entity.{EntityEnemy, BaguetteEntity, EntityBaguettePiece}
import org.oc.ld32.maths.AABB

class BaguetteLevel(name: String) extends Level {

  var nextID: String = null

  var numberEnemies = 0

  var music: String = null

  var spawnpoint: Vec2f = _

  def addBaguette(value: Float, x: Float, y: Float): EntityBaguettePiece = {
    val baguettePiece: EntityBaguettePiece = new EntityBaguettePiece(value)
    baguettePiece.setPos(new Vec2f(x,y))
    spawn(baguettePiece)
    baguettePiece
  }

  val walls: List[Wall] = new ArrayList[Wall]
  val floorDecorations: List[FloorDecoration] = new ArrayList

  override def update(delta: Float): Unit = {
    numberEnemies = 0
    super.update(delta)
  }

  override def onEntityUpdate(entity: Entity, delta: Float) = {
    entity match {
      case current: BaguetteEntity =>
        current.boundingBox.x = current.getPos.x
        current.boundingBox.y = current.getPos.y
        for (j <- 0 until entities.size) {
          val other: Entity = entities.get(j)
          if (other != current && other.isInstanceOf[BaguetteEntity]) {
            val otherEntity: BaguetteEntity = other.asInstanceOf[BaguetteEntity]
            otherEntity.boundingBox.x = otherEntity.getPos.x
            otherEntity.boundingBox.y = otherEntity.getPos.y
            if (otherEntity.boundingBox.collides(current.boundingBox)) {
              current.onCollide(otherEntity)
            }
          }
        }

        current match {
          case enemy: EntityEnemy => {
            numberEnemies += 1
          }
          case _ =>
        }

      case _ =>
    }
  }

  override def render(delta: Float): Unit = {
    for(i <- 0 until floorDecorations.size) {
      floorDecorations.get(i).render(delta)
    }

    super.render(delta)

    val n: Int = walls.size
    for(i <- 0 until n) {
      val w: Wall = walls.get(i)
      w.render(delta)
    }
  }

  def canGoTo(aabb: AABB, x: Float, y: Float): Boolean = {
    val n: Int = walls.size
    val bbCopy: AABB = new AABB(x,y, aabb.width, aabb.height)
    for(i <- 0 until n) {
      val w: Wall = walls.get(i)
      if(w.boundingBox.collides(bbCopy)) {
        return false
      }
    }
    true
  }

  def getName(): String = name

}
