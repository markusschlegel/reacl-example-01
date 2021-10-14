(ns example.app
  (:require [reacl-c.main :as main]
            [example.current :as current]
            [example.future :as future]))

(defn init []
  (println "hello from clojurescript"))

(main/run (.getElementById js/document "app")
  future/signup-process
  {:initial-state {:step :personal-info}})



