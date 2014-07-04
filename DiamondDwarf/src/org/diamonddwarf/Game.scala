package org.diamonddwarf

import org.diamonddwarf.actors.ActorFactory
import com.badlogic.gdx.ApplicationListener
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import org.diamonddwarf.boards.ActorBoard
import org.diamonddwarf.boards.BoardFactory
import org.diamonddwarf.boards.BoardFactory

class Game extends ApplicationListener {
  private var batch: SpriteBatch = null
  private var resources: Resources = null
  private var actorFactory: ActorFactory = null
  private var boardFactory: BoardFactory = null
  private var boardController: BoardController = null

  override def create() {
    batch = new SpriteBatch
    resources = new Resources
    actorFactory = new ActorFactory(resources)
    boardFactory = new BoardFactory(resources, actorFactory)
    boardController = new BoardController(boardFactory.createBoard(0, boardController), actorFactory)
  }

  override def dispose() {
    this.batch.dispose()
  }

  override def render() {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    boardController.update
    boardController.draw
  }

  override def resize(width: Int, height: Int) {
  }

  override def pause() {
  }

  override def resume() {
  }

}

object Game {
  val tilesize = 64
}