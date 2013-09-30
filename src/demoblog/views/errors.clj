(ns demoblog.views.errors)

(defn not-found
  "Show a not found error"
  [msg]
  (str "Error: " msg " :("))