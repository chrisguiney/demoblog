(ns demoblog.posts
  (:use demoblog.workshop,
        demoblog.db
        demoblog.views.posts
        demoblog.views.errors)
  (:require [monger.core :as mg]
            [monger.collection :as mc]
            [clj-time.local :as tlocal]
            [taoensso.timbre :as log
             :refer (trace debug info warn error fatal spy with-log-level)]))

(defn add-post
  "Insert a new post to the db"
  [author content title]
  (let [post {:author author
              :content content
              :created (tlocal/format-local-time (tlocal/local-now) :mysql)
              :title title}]
    (insert-post post)
    {:status 302 :headers {"Location" "/"}}))

(defn show-no-post
  "Render no posts"
  []
  (debug "post is nil")
  (not-found "Could not find post"))

(defn view-post
  "Retrieve and view a single post"
  [post-id]
  (debug (str "in view-post, attempting to retrieve " post-id))
  (let [post (fetch-post post-id)
        post-exists? (not (nil? post))]
    (if post-exists? (show-post-page post)
                     (show-no-post))))

(defn view-posts
  "Fetch posts and reder"
  []
  (show-posts-page (fetch-posts)))

(defn upvote-post
  "Increment the number of upvotes for a post"
  [post-id]
  (increment-value post-id :upvotes)
  {:status 302 :headers {"Location" "/"}})

(defn downvote-post
  "Increment the number of downvotes for a post"
  [post-id]
  (increment-value post-id :downvotes)
  {:status 302 :headers {"Location" "/"}})