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
import org.diamonddwarf.stage.Stage
import org.diamonddwarf.stage.Gem
import org.diamonddwarf.stage.Player
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.Color
import org.diamonddwarf.ui.StageRenderer
import org.diamonddwarf.ui.StageRenderer

class Game extends ApplicationListener {
  private var camera: OrthographicCamera = null
  private var stageRenderer: StageRenderer = null
  
  private var texture: Texture = null
  private var sprite: Sprite = null

  private var game = new DiamondDwarf(new Player("Hessu"))
  game.startStage(Stage.stage1)
  val controller = new Controller(game)

  @Override
  def create() {
    val w = Gdx.graphics.getWidth()
    val h = Gdx.graphics.getHeight()

    camera = new OrthographicCamera(1, h / w)
    stageRenderer = new StageRenderer(game)
    texture = new Texture(Gdx.files.internal("data/libgdx.png"))
    texture.setFilter(TextureFilter.Linear, TextureFilter.Linear)

    val region = new TextureRegion(texture, 0, 0, 512, 275)

    sprite = new Sprite(region);
    sprite.setSize(0.9f, 0.9f * sprite.getHeight() / sprite.getWidth());
    sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
    sprite.setPosition(-sprite.getWidth() / 2, -sprite.getHeight() / 2);
    
    Gdx.input.setInputProcessor(controller)
  }

  @Override
  def dispose() {
    stageRenderer.dispose;
    texture.dispose();
  }

  @Override
  def render() {
    Gdx.gl.glClearColor(1, 1, 1, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

//    batch.setProjectionMatrix(camera.combined);
//    batch.begin();    
//    sprite.draw(batch);
//    batch.end();
    
    stageRenderer.render
  }

  @Override
  def resize(width: Int, height: Int) {
  }

  @Override
  def pause() {
  }

  @Override
  def resume() {
  }
}