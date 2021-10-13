(ns example.bind
  (:require [reacl-c.core :as c :include-macros true]))

(defn bind! [item handler]
  ;; Define local state to enable dynamic display of item
  (c/local-state
   {:next false :value nil}
   (c/dynamic
    (fn [[outter inner]]
      (c/handle-action
       ;; Should we move to next step?
       (if (not (:next inner))
         item
         ;; TODO: focus on outter state
         (handler (:value inner)))
       (fn [[outter inner] msg]
         (c/return :state [outter (assoc inner :next true :value msg)])))))))
