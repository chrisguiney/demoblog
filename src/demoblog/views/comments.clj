(ns demoblog.views.comments
  (:use hiccup.core,
        hiccup.form,
        hiccup.page
        [taoensso.timbre :as log
         :refer (trace debug info warn error fatal spy with-log-level)]))

(defn show-comment-form
  "Show a comment form for a user to submit a comment"
  [post-id]
  (form-to [:post (str "/comment")]
    (hidden-field {:value post-id} "post-id")
    (label "author" "Author")
    (text-field "author")
    (label "Content" "content")
    (text-area "content")
    (submit-button "Add Comment!")))

(defn show-comment
  "Show a comment"
  [comment]
  [:div
   [:h3 (:title comment)]
   [:p [:strong (:author comment)]]
   [:p (:content comment)]])

(defn no-comments
  "Render some sadfaces, because noone commented"
  []
  [:div
   [:p "No comments yet :("]])

(defn show-comments
  "Decide wether to map over the comments, or to show no comments"
  [post]
  (let [comments (:comments post)
        has-comments? (and (not (nil? comments))
                        (not (zero? (count comments))))
        comment-content (if has-comments? (map show-comment comments) (no-comments))]
    (debug (str "in show-comments - has-comments? is " has-comments?))
    (conj comment-content (show-comment-form (:_id post)))))