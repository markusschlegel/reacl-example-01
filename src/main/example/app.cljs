(ns example.app
  (:require [reacl-c.main :as main]
            [example.current :as current]))

(defn init []
  (println "hello from clojurescript"))

(main/run (.getElementById js/document "app")
  current/signup-process
  {:initial-state {:step :personal-info}})



