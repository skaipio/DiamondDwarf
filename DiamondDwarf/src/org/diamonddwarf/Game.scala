package org.diamonddwarf

import com.badlogic.gdx.ApplicationListener
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.Texture.TextureFilter
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.GL20
import org.diamonddwarf.stage._
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.Input
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.audio.Music
import org.diamonddwarf.stage.tileobjects.Player
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import org.diamonddwarf.stage.tileobjects._
import com.badlogic.gdx.Graphics
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion
import scala.collection.JavaConversions
import org.diamonddwarf.actors.ActorFactory

class Game extends ApplicationListener {
  private var batch: SpriteBatch = null
  private var resources: Resources = null
  private var actorFactory: ActorFactory = null
  private var activeStage : ActorStage = null

  override def create() {
    batch = new SpriteBatch
    resources = new Resources
    actorFactory  = new ActorFactory(resources)
    activeStage = new ActorStage(resources.stageTemplates(0), actorFactory)
  }

  override def dispose() {
    this.batch.dispose()
  }

  override def render() {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    activeStage.draw
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