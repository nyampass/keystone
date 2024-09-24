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

  (load-tilemap [this path]
                (.tilemapTiledJSON (.-load this) path))

  (load-image [this name path]
              (.image (.-load this) name path))

  (load-spritesheet [this name path size]
                    (let [[width height] size]
                      (.spritesheet (.-load this) name path
                                    (js-obj "frameWidth" width "frameHeight" height)))))

