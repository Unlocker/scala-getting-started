package models

import play.api.i18n.MessagesApi
import play.api.mvc.{MessagesRequestHeader, PreferredMessagesProvider, Request, WrappedRequest}

/**
  * A wrapped request for post resources.
  *
  * This is commonly used to hold request-specific information like
  * security credentials, and useful shortcut methods.
  */
class PostRequest[A](request: Request[A], val messagesApi: MessagesApi)
  extends WrappedRequest(request)
    with MessagesRequestHeader
    with PreferredMessagesProvider
