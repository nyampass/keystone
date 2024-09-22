(ns keystone.presentation.scenes.base
  (:require [integrant.core :as ig]
            [shadow.cljs.modern :refer [defclass]]
            ["phaser" :as phaser]))


(defclass Base
  (extends phaser/Scene)

  (constructor
   [this opts]
   (super opts))

  Object
  (disable-cursor
   [this]
   (set! (-> this .-game .-canvas .-style .-cursor) "none"))

  (load-tilemap [this key]
                (.tilemapTiledJSON (.-load this) (name key))))

