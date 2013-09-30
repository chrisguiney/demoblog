(ns demoblog.db
  (:use monger.operators)
  (:require [monger.core :as mg]
            [monger.collection :as mc]
            [taoensso.timbre :as log
             :refer (trace debug info warn error fatal spy with-log-level)])
  (:import [org.bson.types ObjectId]))

; This is for the persistence of the connection.
; I would love to know a way to do this instead of having it so global
; but it doesn't look like the monger library ever actually returns the connection
; I'd love to see it do so -- http://www.infoq.com/presentations/Clojure-Large-scale-patterns-techniques
(debug "Connecting to mongodb")
(mg/connect!)
(mg/set-db! (mg/get-db "demoblog"))

(defn fetch-posts
  "Get all the blog posts from the db"
  []
  (mc/find-maps "posts"))

(defn insert-post
  "Insert the actual post to the db"
  [post]
  (mc/insert-and-return "posts" post))

(defn fetch-post
  "Fetch a single post from the database, return it as a map"
  [post-id]
  (debug "fetch-post called")
  (mc/find-map-by-id "posts" (ObjectId. post-id)))

(defn push-comment
  "Push a post into a post in mongodb"
  [post-id comment]
  (debug "Pushing comment to mongodb")
  (mc/update "posts" {:_id (ObjectId. post-id)} {$push {:comments comment}}))

(defn increment-value
  "Increment a value on a document"
  [document-id field]
  (let [field (keyword field)]
    (debug (str "Incrementing " field " on document " document-id))
    (mc/update "posts" {:_id (ObjectId. document-id)} {$inc {field 1}})))
