package org.oc.ld32.entity

import java.util.{List, ArrayList}

import org.oc.ld32.entity.ai.AITask

class EntityEnemy(id: String) extends EntityBiped(id) {
  var target: EntityPlayer = null

  val aiList: List[AITask] = new ArrayList[AITask]

  def updateAITasks(delta: Float): Unit = {
    if(isDead())
      return
    for(i <- 0 until aiList.size) {
      val task: AITask = aiList.get(i)
      if(task.running) {
        if(task.shouldContinue) {
          task.perform(delta)
        } else {
          task.stop
        }
      } else {
        if(task.canExecute) {
          task.reset
          task.start
        }
      }
    }
  }
  override def update(delta: Float): Unit = {
    super.update(delta)
    updateAITasks(delta)
  }
}
