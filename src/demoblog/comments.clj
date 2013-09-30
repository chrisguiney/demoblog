(ns demoblog.comments
  (:use demoblog.db
        demoblog.views.comments)
  (:require [taoensso.timbre :as log
             :refer (trace debug info warn error fatal spy with-log-level)]))

(defn add-comment
  "Handle the request to save a comment"
  [post-id author content]
  (debug "Recieved request for adding a comment")
  (debug "Post ID: " post-id)
  (debug "Author: " author)
  (debug "Content: " content)
  (push-comment post-id {:author author
                         :content content})
  {:status 302 :headers {"Location" (str "/post/" post-id)}})