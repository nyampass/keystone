(ns keystone.models.usecase)
;;   (:require [integrant.core :as ig]
;;             [keystone.models.storyline :as storyline]))

;; (defmethod ig/init-key :usecase/storyline [_ opts]
;;   (storyline/gen-usecase opts))


;; (def make-data []
;;   {:a 3})


(defprotocol Animal
  (speak [this])
  (move [this]))

; 共通の実装関数を定義
(defn common-speak [this]
  (str (:name this) " makes a sound."))

; Dogデータ型とAnimalプロトコルの実装
(defrecord Dog [name]
  Animal
  (speak [this]
    (str (common-speak this) " Woof!"))  ; 共通の実装を呼び出しつつ、Woof!を追加
  (move [this]
    (str (:name this) " is running!")))

(speak (->Dog 3))
