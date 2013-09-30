(defproject demoblog "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [compojure "1.1.5"]
                 [com.novemberain/monger "1.5.0"]
                 [hiccup "1.0.4"]
                 [clj-time "0.6.0"]
                 [com.taoensso/timbre "2.6.2"]]
  :plugins [[lein-ring "0.8.5"]]
  :ring {:handler demoblog.handler/app}
  :profiles
  {:dev {:dependencies [[ring-mock "0.1.5"]]}})
