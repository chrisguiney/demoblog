(ns demoblog.handler
  (:use compojure.core,
        demoblog.posts)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [demoblog.posts :as posts :refer (view-post view-posts
                                               add-post upvote-post downvote-post)]
            [demoblog.comments :as comments :refer (add-comment)]
            [demoblog.views.posts :as post-views :refer (show-post-form)]))

(defroutes app-routes
  (GET "/" [] (view-posts))
  (GET ["/post/:id" :id #"[a-z0-9]+"] [id] (view-post id))
  (GET "/post" [] (show-post-form))
  (POST "/post" [author content title] (add-post author content title))
  (POST ["/comment"] [post-id author content] (add-comment post-id author content))
  (GET "/upvote/:id" [id] (upvote-post id))
  (GET "/downvote/:id" [id] (downvote-post id))
  (route/resources "/")
  (route/not-found "Really Not Found"))

(def app
  (handler/site app-routes))
