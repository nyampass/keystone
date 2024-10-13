(ns keystone.scenes.base
  (:require [integrant.core :as ig]
            [shadow.cljs.modern :refer [defclass]]
            ["phaser" :as phaser]))

(clj->js (map inc [1 2 3]))


(defrecord Map [map])

(defprotocol MapBehavior
  (add-tileset-image! [this tileset-name])
  (create-layer! [this layer-name tilesets scale]))

(extend-type Map
  MapBehavior
  (add-tileset-image! [this tileset-name]
    (.addTilesetImage (:map this) (name tileset-name)))
  (create-layer! [this layer-name tilesets scale]
    (.setScale
     (.createLayer (:map this) (name layer-name) (clj->js tilesets))
     scale)))

(defclass Base
  (extends phaser/Scene)

  (constructor
   [this opts]
   (super opts))

  Object
  (disable-cursor
   [this]
   (set! (-> this .-game .-canvas .-style .-cursor) "none"))

  (load-tilemap [this name path]
                (.tilemapTiledJSON (.-load this) name path))

  (load-image [this name path]
              (.image (.-load this) name path))

  (load-spritesheet [this name path size]
                    (let [[width height] size]
                      (.spritesheet (.-load this)
                                    name path
                                    (js-obj "frameWidth" width "frameHeight" height))))

  (gen-tilemap [this name width height]
               (->Map (.tilemap (.-make this) (js-obj "key" name "tileWidth" width "tileHeight" height)))))


