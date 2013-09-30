(ns demoblog.views.page
  (:use hiccup.core,
        hiccup.element
        hiccup.page))

(defn common-css
  "Return common css elements"
  []
  (map include-css ["/css/bootstrap.min.css" "/css/bootstrap-responsive.min.css" "/css/common.css"]))

(defn common-js
  "Return common javascript scripts"
  []
  (map include-js ["/js/jquery.js" "/js/bootstrap-collapse.js"]))

(defn common-meta
  "Return common meta for a page"
  []
  (conj [:meta {:author "Chris Guiney"}]
        [:meta {:name "viewport" :content "width=device-width, initial-scale=1.0"}]))

(defn common-head
  "Return common header elements"
  []
  (conj (common-css) (common-js) (common-meta)))

(defn nav-bar
  "Return the clichè bootstrap navbar"
  []
  [:div {:class "navbar navbar-inverse navbar-fixed-top"}
   [:div {:class "navbar-inner"}
    [:div {:class "container"}
     [:button {:type "button" :class "btn btn-navbar" :data-toggle "collapse" :data-target ".nav-collapse"}
      [:span {:class "icon-bar"}]
      [:span {:class "icon-bar"}]
      [:span {:class "icon-bar"}]
     ]
     [:a {:class "brand" :href "#"} "Demo Blog"]
     [:div {:class "nav-collapse collapse"}
      [:ul {:class "nav"}
       [:li {:class "active"} [:a {:href "#"} "All Posts"]]
       [:li {:class "active"} [:a {:href "#"} "Add Post"]]]]]]]
  )