(ns example.current
  (:require [reacl-c.core :as c :include-macros true]
            [reacl-c.main :as main]
            [reacl-c.dom :as dom]))

(c/defn-item personal-info []
  (c/local-state
   {:name "" :email ""}
   (c/dynamic
    (fn [[outter inner]]
      (dom/div {:class "personal-info-container"}
               (dom/h4 "Personal Info:")
               (dom/samp (pr-str inner))
               (dom/input {:placeholder "name"
                           :value (:name inner)
                           :onChange (fn [[outter inner] e] (c/return :state [outter (assoc inner :name (.. e -target -value))]))})
               (dom/input {:placeholder "email"
                           :value (:email inner)
                           :onChange (fn [[outter inner] e] (c/return :state [outter (assoc inner :email (.. e -target -value))]))})
               (dom/button {:onclick (fn [state action] (c/return :action (merge {:step :verifaction-code} inner)))
                            :class "confirm-button"
                            :disabled (or (< (count (:name inner)) 1) (< (count (:email inner)) 1))}
                           "Continue"))))))

(defn generate-code []
  "A8J22")

(c/defn-item create-and-send-verification-code []
  (c/local-state
   {:code (generate-code) :entered-code ""}
   (c/dynamic
    (fn [[outter inner]]
      (dom/div {:class "verification-container"}
               (dom/h4 "Verification Info:")
               (dom/samp (pr-str inner))
               (dom/input {:placeholder "Code"
                           :value (:entered-code inner)
                           :onChange (fn [[outter inner] e] (c/return :state [outter (assoc inner :entered-code (.. e -target -value))]))})
               (dom/button {:onclick (fn [state action] (c/return :action (merge {:step :done} inner)))
                            :class "confirm-button"
                            :disabled (not (== (:entered-code inner) (:code inner)))}
                           "Send code"))))))

(c/def-item signup-process
  (c/with-state-as state
    (c/handle-action
     (dom/div
      (dom/samp (pr-str state))
      (dom/h2 "Signup:")
      (case (:step state)
        :personal-info (personal-info)
        :verifaction-code (create-and-send-verification-code)
        :done (dom/h2 "Successfully registered!")))
     (fn [state action]
       (c/return :state (merge state action))))))

