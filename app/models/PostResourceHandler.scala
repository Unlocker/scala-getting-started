package models

import javax.inject.Inject

import com.google.inject.Provider
import controllers.PostFormInput
import play.api.MarkerContext
import play.api.libs.json.{JsValue, Json, Writes}

import scala.concurrent.{ExecutionContext, Future}

/**
  * DTO for displaying post information
  */
case class PostResource(id: String, link: String, title: String, body: String)

/**
  * Helper methods to support DTO format transformation.
  */
object PostResource {
  /**
    * Mapping to write a PostResource out as a JSON value
    */
  implicit val implicitWrites = new Writes[PostResource] {
    override def writes(post: PostResource): JsValue = {
      Json.obj(
        "id" -> post.id,
        "link" -> post.link,
        "title" -> post.title,
        "body" -> post.body
      )
    }
  }
}


class PostResourceHandler @Inject()(routerProvider: Provider[PostRouter], postRepository: PostRepository)
                                   (implicit ec: ExecutionContext) {

  private def createPostResource(data: PostData): PostResource = {
    PostResource(data.id.toString, routerProvider.get.link(data.id), data.title, data.body)
  }

  def create(postInput: PostFormInput)(implicit mc: MarkerContext): Future[PostResource] = {
    val data = PostData(PostId("999"), postInput.title, postInput.body)
    // we don't actually create the post, so return what we have
    postRepository.create(data).map { id =>
      createPostResource(data)
    }
  }

  def lookup(id: String)(implicit mc: MarkerContext): Future[Option[PostResource]] = {
    val postFuture = postRepository.get(PostId(id))
    postFuture.map { maybePostData =>
      maybePostData.map { postData =>
        createPostResource(postData)
      }
    }
  }

  def find(implicit mc: MarkerContext): Future[Iterable[PostResource]] = {
    postRepository.list().map { postDataList =>
      postDataList.map {
        createPostResource
      }
    }
  }

}
