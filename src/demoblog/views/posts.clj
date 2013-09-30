(ns demoblog.views.posts
  (:use hiccup.core,
        hiccup.form,
        hiccup.page
        demoblog.views.comments)
  (:require [taoensso.timbre :as log
             :refer (trace debug info warn error fatal spy with-log-level)]
            [demoblog.views.page :refer (nav-bar common-head)]))

(defn show-post-form
  "Show a form to create a post"
  []
  (form-to [:post "/post"]
    [:p (label "author" "Author")
        (text-field "author")]
    [:p (label "Post Title" "title")
        (text-field "title")]
    [:p (label "Content" "content")
        (text-area "content")]
    [:p (submit-button {:class "btn btn-primary"} "Add Post!")]))

(defn show-post
  "render a single blog post"
  [post]
  (let [post-id (:_id post)
        upvotes (:upvotes post)
        upvotes (if (nil? upvotes) 0 upvotes)
        downvotes (:downvotes post)
        downvotes (if (nil? downvotes) 0 downvotes)
        total-votes (+ upvotes downvotes)
        net-votes (- upvotes downvotes)]
    (debug (str "in show post, attempting to render " post-id))
    [:div
     [:h2 [:a {:href (str "/post/" post-id)} (:title post)]]
     [:p.votes
      [:a {:class "btn" :href (str "/upvote/" post-id)}
       [:span {:class "icon-arrow-up"}]]
      [:a {:class "btn" :href (str "/downvote/" post-id)}
       [:span {:class "icon-arrow-down"}]]]
    [:p.vote-results
     [:span.upvotes (str "Upvotes: " upvotes)]
     [:span.downvotes (str "Downvotes: " downvotes)]
     [:span.total-votes (str "Total Votes: " total-votes)]
     [:span.net-votes (str "Net Votes: " net-votes)]]
     [:h4 (:author post)]
     [:h4 (:created post)]
     [:div (.replace (:content post) "\n" "<br/>")]]))

(defn show-post-page
  "Render a page for a single post"
  [post]
  (html5 [:head
          (common-head)
          [:title (:title post)]]
    [:body (nav-bar)
           [:div {:class "container"}
             (show-post post)
             (show-comments post)]]))

(defn show-posts-page
  "Display all the blogposts"
  [posts]
  (html5
    [:head
     (common-head)
     [:title "All Posts"]]
    [:body
     (nav-bar)
     [:div {:class "container"}
       [:h1 "These are the posts"]
       (map show-post posts)
       [:div (show-post-form)]]]))