import javax.inject.Inject

import play.api.http.{DefaultHttpRequestHandler, HttpConfiguration, HttpErrorHandler, HttpFilters}
import play.api.mvc.request.RequestTarget
import play.api.mvc.{Handler, RequestHeader}
import play.api.routing.Router

/**
  * Handles all requests.
  *
  * https://www.playframework.com/documentation/2.5.x/ScalaHttpRequestHandlers#extending-the-default-request-handler
  */
class RequestHandler @Inject()(router: Router,
                               errorHandler: HttpErrorHandler,
                               configuration: HttpConfiguration,
                               filters: HttpFilters)
  extends DefaultHttpRequestHandler(router, errorHandler, configuration, filters) {

  override def handlerForRequest(request: RequestHeader): (RequestHeader, Handler) = {
    super.handlerForRequest {
      // ensures that REST API does not need a trailing "/"
      if (isRest(request)) {
        addTrailingSlash(request)
      } else {
        request
      }
    }
  }

  private def isRest(request: RequestHeader): Boolean = {
    request.uri match {
      case uri: String if uri.contains("post") => true
      case _ => false
    }
  }

  private def addTrailingSlash(request: RequestHeader): RequestHeader = {
    if (request.path.endsWith("/")) {
      request
    } else {
      val path = request.path + "/"
      if (request.rawQueryString.isEmpty) {
        request.withTarget(RequestTarget(path = path, uriString = path, queryString = Map()))
      } else {
        request.withTarget(RequestTarget(path = path, uriString = request.uri, queryString = request.queryString))
      }
    }
  }


}
